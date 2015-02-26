package com.latu.qingcheng123.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.latu.qingcheng123.R;
import com.latu.qingcheng123.util.BaseTools;
import com.latu.qingcheng123.util.GlobalParam;
import com.latu.qingcheng123.util.ProfessionalTools;
import com.latu.qingcheng123.vo.AdsItem;
import com.latu.qingcheng123.vo.NewsItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private Context mContext;
	private List<NewsItem> newsList;
	private List<AdsItem> adsList;
	private NewsViewHolder mHolder;
	private LayoutInflater mInflater;

	private ViewPager vpAds;
	private AdsAdapter adsAdapter;
	private TextView tvAdsTitle;
	private LinearLayout llAdsPage;
	private List<ImageView> ads_imageViews; // 滑动的图片集合
	private List<View> ads_dots; // 图片标题正文的那些点
	private int ads_currentItem = 0; // 当前图片的索引号

	public NewsAdapter(Context context, List<NewsItem> newsList,
			List<AdsItem> adsList) {
		super();
		this.mContext = context;
		this.newsList = newsList;
		this.adsList = adsList;
		mInflater = LayoutInflater.from(mContext);

		ads_imageViews = new ArrayList<ImageView>();
		ads_dots = new ArrayList<View>();
		adsAdapter = new AdsAdapter();
	}

	@Override
	public int getCount() {
		if (adsList != null && adsList.size() > 0) {
			if (newsList != null && newsList.size() > 0) {
				return newsList.size() + 1;
			} else {
				return 1;
			}
		} else {
			if (newsList != null && newsList.size() > 0) {
				return newsList.size();
			} else {
				return 0;
			}
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (adsList != null && adsList.size() > 0) {
			if (position == 0) {
				convertView = mInflater.inflate(R.layout.ads_head, null);

				tvAdsTitle = (TextView) convertView
						.findViewById(R.id.tv_ads_title);
				llAdsPage = (LinearLayout) convertView
						.findViewById(R.id.ll_ads_page);
				vpAds = (ViewPager) convertView.findViewById(R.id.vp_ads);
				vpAds.setAdapter(adsAdapter);
				vpAds.setOnPageChangeListener(new MyPageChangeListener());

				// llAdsPage.removeAllViews();
				// vpAds.removeAllViews();
				ads_dots.clear();
				ads_imageViews.clear();
				for (int i = 0; i < adsList.size(); i++) {
					final AdsItem item = adsList.get(i);
					ImageView imageView = new ImageView(mContext);
					setImageViewPic(imageView, item.imgURL);
					imageView.setScaleType(ScaleType.CENTER_CROP);
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(mContext, item.title,
									Toast.LENGTH_SHORT).show();
						}
					});
					ads_imageViews.add(imageView);

					View vDot = new View(mContext);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							10, 10);
					lp.setMargins(5, 0, 5, 0);
					vDot.setLayoutParams(lp);
					if (i == ads_currentItem) {
						vDot.setBackgroundResource(R.drawable.dot_focused);
					} else {
						vDot.setBackgroundResource(R.drawable.dot_normal);
					}
					llAdsPage.addView(vDot);
					ads_dots.add(vDot);
				}
				vpAds.setCurrentItem(ads_currentItem);
				tvAdsTitle.setText(adsList.get(ads_currentItem).title);

				return convertView;
			} else {
				convertView = mInflater.inflate(R.layout.news_list_item, null);
				mHolder = new NewsViewHolder();

				mHolder.llCommon = (LinearLayout) convertView
						.findViewById(R.id.ll_common);
				mHolder.ivLeftImg = (ImageView) convertView
						.findViewById(R.id.iv_left_image);
				mHolder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				mHolder.tvSource = (TextView) convertView
						.findViewById(R.id.tv_source);
				mHolder.tvCommentCount = (TextView) convertView
						.findViewById(R.id.tv_comment_count);

				if (newsList != null && newsList.size() > position - 1) {
					NewsItem item = newsList.get(position - 1);
					mHolder.tvTitle.setText(item.title);
					mHolder.tvSource.setText(item.source);
					mHolder.tvCommentCount.setText("" + item.commentNum);
					setImageViewPic(mHolder.ivLeftImg, item.leftImgURL);
				}

				return convertView;
			}
		} else {
			convertView = mInflater.inflate(R.layout.news_list_item, null);
			mHolder = new NewsViewHolder();

			mHolder.llCommon = (LinearLayout) convertView
					.findViewById(R.id.ll_common);
			mHolder.ivLeftImg = (ImageView) convertView
					.findViewById(R.id.iv_left_image);
			mHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_title);
			mHolder.tvSource = (TextView) convertView
					.findViewById(R.id.tv_source);
			mHolder.tvCommentCount = (TextView) convertView
					.findViewById(R.id.tv_comment_count);

			if (newsList != null && newsList.size() > position) {
				NewsItem item = newsList.get(position);
				mHolder.tvTitle.setText(item.title);
				mHolder.tvSource.setText(item.source);
				mHolder.tvCommentCount.setText("" + item.commentNum);
				setImageViewPic(mHolder.ivLeftImg, item.leftImgURL);
			}

			return convertView;
		}
	}

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (vpAds != null) {
				vpAds.setCurrentItem(ads_currentItem);// 切换当前显示的图片
			}
		};
	};

	/**
	 * 广告滚动
	 */
	public void scrollAds() {
		ads_currentItem = (ads_currentItem + 1) % ads_imageViews.size();
		handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			ads_currentItem = position;
			tvAdsTitle.setText(adsList.get(position).title);
			ads_dots.get(oldPosition).setBackgroundResource(
					R.drawable.dot_normal);
			ads_dots.get(position)
					.setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 */
	private class AdsAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return ads_imageViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager vp = (ViewPager) arg0;
			vp.addView(ads_imageViews.get(arg1));
			return ads_imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			ViewPager vp = (ViewPager) arg0;
			vp.removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	private void setImageViewPic(final ImageView iv, String picUrl) {
		if (picUrl != null && !"".equals(picUrl)) {
			String picNameTemp = ProfessionalTools.MD5(picUrl);
			final String strFilePath = BaseTools.getImageCachePath(mContext)
					+ File.separator + picNameTemp;
			File picFile = new File(strFilePath);
			if (picFile.exists()) // 本地有缓存
			{
				Bitmap bitmap = BaseTools.getLocalBitmap(strFilePath);
				if (bitmap != null) {
					iv.setImageBitmap(bitmap);
				}
			} else {
				ImageRequest imageRequest = new ImageRequest(picUrl,
						new Response.Listener<Bitmap>() {
							@Override
							public void onResponse(Bitmap response) {
								iv.setImageBitmap(response);
								BaseTools.saveBitmapInLocal(response,
										strFilePath);
							}
						}, 0, 0, Config.RGB_565, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								iv.setImageResource(R.drawable.loadpic_empty_listpage);
							}
						});
				GlobalParam.g_volleyRequestQueue.add(imageRequest);
			}
		}
	}

	static class NewsViewHolder {
		LinearLayout llCommon;
		ImageView ivLeftImg;
		TextView tvTitle;
		TextView tvSource;
		TextView tvCommentCount;
	}

}
