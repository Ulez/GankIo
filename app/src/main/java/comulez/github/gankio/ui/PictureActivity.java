package comulez.github.gankio.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.ui.base.ToolbarActivity;
import comulez.github.gankio.util.Tutil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends ToolbarActivity {

    public static String IMAGE_DESC = "image_desc";
    public static String IMAGE_URL = "image_url";
    @Bind(R.id.image)
    ImageView image;
    private String desc;
    private String url;
    public static String TRANSIT_IMG = "image";
    private PhotoViewAttacher mPhotoViewAttacher;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        parseIntent(getIntent());
        ViewCompat.setTransitionName(image, TRANSIT_IMG);
//        Glide.with(mContext)
//                .load(url)
//                .into(image);
        Picasso.with(this).load(url).into(image);

        mPhotoViewAttacher = new PhotoViewAttacher(image);
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });
        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(PictureActivity.this)
                        .setMessage("保存到本地？")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                saveImage(url);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;
            }
        });
    }

    private void saveImage(final String url) {
        Observable.just(url) // 输入类型 String
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Uri>() {
                    @Override
                    public Uri call(String filePath) {
                        Uri uri = null;
                        Bitmap bitmap = null;
                        try {
                            bitmap = Picasso.with(mContext).load(url).get();
                            File appDir = new File(Environment.getExternalStorageDirectory(), "Ulez");
                            if (!appDir.exists()) {
                                appDir.mkdir();
                            }
                            String fileName = desc.replace('/', '-') + ".jpg";
                            File file = new File(appDir, fileName);
                            try {
                                FileOutputStream outputStream = new FileOutputStream(file);
                                assert bitmap != null;
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                uri = Uri.fromFile(file);
                                // 通知图库更新
                                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                                mContext.sendBroadcast(scannerIntent);
                            } catch (IOException e) {
                                Log.e("LCY", "图片写入错误");
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            Log.e("LCY", "picasso获取错误");
                            e.printStackTrace();
                        } finally {
                            return uri;// 返回类型 Bitmap
                        }
                    }
                })
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        if (uri != null)
                            Tutil.t("success");
                        else Tutil.t("fail");
                    }
                });
    }

    private void parseIntent(Intent intent) {
        desc = intent.getStringExtra(IMAGE_DESC);
        url = intent.getStringExtra(IMAGE_URL);
    }

    public static Intent newIntent(Context mContext, String desc, String url) {
        Intent intent = new Intent(mContext, PictureActivity.class);
        intent.putExtra(IMAGE_DESC, desc);
        intent.putExtra(IMAGE_URL, url);
        return intent;
    }
}
