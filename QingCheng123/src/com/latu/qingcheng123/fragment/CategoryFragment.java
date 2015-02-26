package com.latu.qingcheng123.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.latu.qingcheng123.R;
import com.latu.qingcheng123.adapter.NewsAdapter;
import com.latu.qingcheng123.util.GlobalParam;
import com.latu.qingcheng123.vo.AdsItem;
import com.latu.qingcheng123.vo.NewsItem;
import com.latu.qingcheng123.widget.xlistview.XListView;
import com.latu.qingcheng123.widget.xlistview.XListView.IXListViewListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryFragment extends Fragment implements IXListViewListener {

	private Activity m_activity;
	private String m_categoryName;

	private XListView mListView;
	private NewsAdapter mAdapter;
	private LinkedList<NewsItem> newsList = new LinkedList<NewsItem>();
	private ArrayList<AdsItem> adsList = new ArrayList<AdsItem>();
	private Handler mHandler;
	private ScheduledExecutorService scheduledExecutorService; // 控制广告滚动
	private int indexNew = 20;
	private int indexOld = 20;

	private static final String[] URLS = {
			"http://img.my.csdn.net/uploads/201403/03/1393854094_4652.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854084_6138.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854084_1323.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854084_8439.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854083_6511.jpg",
			"http://img.my.csdn.net/uploads/201403/03/1393854083_2323.jpg" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		m_categoryName = args != null ? args.getString("name") : "";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewContent = LayoutInflater.from(m_activity).inflate(
				R.layout.fragment_category, null);

		mListView = (XListView) viewContent.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new NewsAdapter(m_activity, newsList, adsList);
		mListView.setAdapter(mAdapter);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		getAds();
		getNews();
		mAdapter.notifyDataSetChanged();

		return viewContent;
	}

	@Override
	public void onStart() {
		// if (scheduledExecutorService != null)
		// {
		// scheduledExecutorService.shutdown();
		// }
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		if (scheduledExecutorService != null) {
			scheduledExecutorService.shutdown();
		}
		super.onStop();
	}

	/**
	 * 广告滚动线程
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			if (mAdapter != null) {
				synchronized (mAdapter) {
					mAdapter.scrollAds();
				}
			}
		}

	}

	private void getAds() {
		adsList.clear();

		AdsItem item1 = new AdsItem();
		item1.adsId = "1";
		item1.title = "广告1";
		item1.imgURL = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
		adsList.add(item1);

		AdsItem item2 = new AdsItem();
		item2.adsId = "2";
		item2.title = "广告内容2";
		item2.imgURL = "http://img.my.csdn.net/uploads/201403/03/1393854084_8439.jpg";
		adsList.add(item2);

		AdsItem item3 = new AdsItem();
		item3.adsId = "3";
		item3.title = "广告推广信息3";
		item3.imgURL = "http://img.my.csdn.net/uploads/201403/03/1393854083_2323.jpg";
		adsList.add(item3);
	}

	private void getNews() {
		for (int i = 0; i != 5; ++i) {
			NewsItem item = new NewsItem();
			item.newsId = "" + indexOld;
			item.leftImgURL = URLS[i % 6];
			item.title = "新闻标题" + indexOld;
			item.source = "新浪新闻";
			item.commentNum = indexOld;

			newsList.add(item);
			indexOld++;
		}
	}

	private void getLatestNews() {
		for (int i = 0; i != 5 && indexNew > 0; ++i) {
			indexNew--;
			NewsItem item = new NewsItem();
			item.newsId = "" + indexNew;
			item.leftImgURL = URLS[i % 6];
			item.title = "新闻标题" + indexNew;
			item.source = "新浪新闻";
			item.commentNum = indexNew;

			newsList.addFirst(item);

		}
	}

	private void onFinishLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(GlobalParam.g_dateFormat.format(new Date(
				System.currentTimeMillis())));
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getLatestNews();
				mAdapter.notifyDataSetChanged();
				onFinishLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getNews();
				mAdapter.notifyDataSetChanged();
				onFinishLoad();
			}
		}, 1000);
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {

		} else {
			// fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onAttach(Activity activity) {
		this.m_activity = activity;
		super.onAttach(activity);
	}

}
