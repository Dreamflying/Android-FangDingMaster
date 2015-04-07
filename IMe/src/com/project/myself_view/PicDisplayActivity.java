package com.project.myself_view;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.base.view.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.project.iwant.R;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.DragImageView;

public class PicDisplayActivity extends BaseActivity{
	private ViewPager picViewPager;
	String[] imageUrls = Constants.IMAGES_LIST;
	private Button back_front_btn;
	DisplayImageOptions options;
	public PicDisplayActivity() {
		super("");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picdisplay);
		Intent intent=getIntent();
		picViewPager=(ViewPager)findViewById(R.id.pic_vp);
		back_front_btn=(Button)findViewById(R.id.back_front_btn);
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.gou1)
		.showImageOnFail(R.drawable.gou1)
		.resetViewBeforeLoading(true)
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
		picViewPager.setAdapter(new ImageAdapter());
		picViewPager.setCurrentItem(intent.getIntExtra("img_index", 0));
		back_front_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
	}
	
	
	
	private class ImageAdapter extends PagerAdapter {

		private LayoutInflater inflater;

		ImageAdapter() {
			inflater = LayoutInflater.from(PicDisplayActivity.this);
		}

		@Override 
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			ImageLoader.getInstance().displayImage(imageUrls[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = "图片加载失败";
					/*switch (failReason.getType()) {
						case IO_ERROR:
							message = "图片加载失败";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}*/
					Toast.makeText(PicDisplayActivity.this, message, Toast.LENGTH_SHORT).show();
					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
					attacher.update();
				}
			});
			
			
			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
}
