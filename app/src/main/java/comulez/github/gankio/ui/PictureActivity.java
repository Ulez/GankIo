package comulez.github.gankio.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.ui.base.ToolbarActivity;
import comulez.github.gankio.util.Tutil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
    private String TAG = "PictureActivity";
    private int CODE_FOR_WRITE_PERMISSION = 2;
    private Matrix defualt;

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

        defualt = mPhotoViewAttacher.getImageView().getMatrix();
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
                        .setMessage("保存到手机？")
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Tutil.t("允许写入权限");
            } else {
                Tutil.t("拒绝写入权限");
            }
        }
    }

    /**
     * 保存图片到本地；
     *
     * @param url
     */
    private void saveImage(final String url) {
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                Activity activty = this;
                ActivityCompat.requestPermissions(activty, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
                return;
            }
        }
        Observable
                .create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = Picasso.with(mContext).load(url).get();
                            if (bitmap != null) {
                                subscriber.onNext(bitmap);
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new Exception("无法下载图片"));
                            }
                        } catch (IOException e) {
                            subscriber.onError(e);
                            e.printStackTrace();
                        }
                    }
                })
                .map(new Func1<Bitmap, Uri>() {
                    @Override
                    public Uri call(Bitmap bitmap) {
                        Uri uri = null;
                        File appDir = new File(Environment.getExternalStorageDirectory(), "GankIo");
                        Log.e(TAG, "dir1=" + appDir.getAbsolutePath());
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
//                        String fileName = desc.replace('/', '-') + ".jpg";
                        String fileName = desc.replace('/', '-') + ".jpg";
                        File file = new File(appDir, fileName);
                        if (file.exists()) {
                            Tutil.t(getString(R.string.action_about));
                            return Uri.fromFile(file);
                        }
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            try {
                                outputStream.flush();
                                outputStream.close();
                                Log.e(TAG, getString(R.string.has_saved));
                                uri = Uri.fromFile(file);
                                // 通知图库更新
                                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                                mContext.sendBroadcast(scannerIntent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return uri;
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                        Tutil.t(e.getMessage());
                    }

                    @Override
                    public void onNext(Uri uri) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "GankIo");
                        Log.e(TAG, "onNext++" + appDir.getAbsolutePath());
                        String message = String.format("图片已保存到%s文件夹", appDir.getAbsolutePath());
                        Tutil.t(message);
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
