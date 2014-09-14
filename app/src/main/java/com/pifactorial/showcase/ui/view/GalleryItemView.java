package com.pifactorial.showcase.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.pifactorial.showcase.R;
import timber.log.Timber;

public class GalleryItemView extends FrameLayout {
  // @InjectView(R.id.gallery_image_image) ImageView image;
  @InjectView(R.id.title) TextView title;

  private float aspectRatio = 1;
  private RequestCreator request;

  public GalleryItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  public void bindTo(Image item, Picasso picasso) {
    request = picasso.load(item.link);
    aspectRatio = 1f * item.width / item.height;
    Timber.e("Width %d Height %d", item.width, item.height);
    requestLayout();

    title.setText(item.title);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int mode = MeasureSpec.getMode(widthMeasureSpec);
    if (mode != MeasureSpec.EXACTLY) {
      throw new IllegalStateException("layout_width must be match_parent");
    }

    int width = MeasureSpec.getSize(widthMeasureSpec);
    // Honor aspect ratio for height but no larger than 2x width.
    int height = Math.min((int) (width / aspectRatio), width * 2);
    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (request != null) {
      // request.resize(width, height).centerCrop().into(image);
      request = null;
    }
  }
}
