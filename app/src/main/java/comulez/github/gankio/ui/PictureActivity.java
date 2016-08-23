package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.ui.base.ToolbarActivity;

public class PictureActivity extends ToolbarActivity {

    public static String IMAGE_DESC="image_desc";
    public static String IMAGE_URL="image_url";
    @Bind(R.id.image)
    ImageView image;
    private String desc;
    private String url;
    public static String TRANSIT_IMG ="image";

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
    }

    private void parseIntent(Intent intent) {
        desc = intent.getStringExtra(IMAGE_DESC);
        url = intent.getStringExtra(IMAGE_URL);
    }

    public static Intent newIntent(Context mContext, String desc, String url) {
        Intent intent=new Intent(mContext,PictureActivity.class);
        intent.putExtra(IMAGE_DESC,desc);
        intent.putExtra(IMAGE_URL,url);
        return intent;
    }
}
