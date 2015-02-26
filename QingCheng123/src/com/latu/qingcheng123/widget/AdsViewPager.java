package com.latu.qingcheng123.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AdsViewPager extends ViewPager {

	public AdsViewPager(Context context) {
		super(context);
	}
	
	public AdsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true); //只需这句话，让父类不拦截触摸事件就可以了。
        return super.dispatchTouchEvent(ev);
	}
}
