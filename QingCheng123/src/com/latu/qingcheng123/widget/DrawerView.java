package com.latu.qingcheng123.widget;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.latu.qingcheng123.R;

/**
 * 自定义SlidingMenu 测拉菜单类
 * */
public class DrawerView {
	private final Activity m_activity;
	private SlidingMenu localSlidingMenu;

	private ImageView ivPortrait;
	private TextView tvName;
	private RelativeLayout rlMenu1, rlMenu2, rlMenu3;

	public DrawerView(Activity activity) {
		this.m_activity = activity;
	}

	public SlidingMenu initSlidingMenu() {
		localSlidingMenu = new SlidingMenu(m_activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT); // 设置左右滑菜单
		localSlidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW); // 设置要使菜单滑动，触碰屏幕的范围
		// localSlidingMenu.setTouchModeBehind(SlidingMenu.RIGHT);
		localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); // 设置阴影图片的宽度
		localSlidingMenu.setShadowDrawable(R.drawable.shadow); // 设置阴影图片
		localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); // SlidingMenu划出时主页面显示的剩余宽度
		localSlidingMenu.setFadeDegree(0.35F); // SlidingMenu滑动时的渐变程度
		localSlidingMenu.attachToActivity(m_activity, SlidingMenu.RIGHT); // 使SlidingMenu附加在Activity右边
		// localSlidingMenu.setBehindWidthRes(R.dimen.left_drawer_avatar_size);
		// //设置SlidingMenu菜单的宽度
		localSlidingMenu.setMenu(R.layout.left_drawer_fragment); // 设置menu的布局文件
		// localSlidingMenu.toggle(); //动态判断自动关闭或开启SlidingMenu
		localSlidingMenu
				.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
					public void onOpened() {

					}
				});

		initView();
		return localSlidingMenu;
	}

	private void initView() {
		ivPortrait = (ImageView) localSlidingMenu
				.findViewById(R.id.iv_portrait);
		ivPortrait.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(m_activity, "click on portrait!",
						Toast.LENGTH_SHORT).show();
			}
		});

		tvName = (TextView) localSlidingMenu.findViewById(R.id.tv_name);
		tvName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(m_activity, "click on name!", Toast.LENGTH_SHORT)
						.show();
			}
		});

		rlMenu1 = (RelativeLayout) localSlidingMenu
				.findViewById(R.id.ll_menu_1);
		rlMenu1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(m_activity, "click on menu1!",
						Toast.LENGTH_SHORT).show();
				// // 跳转页面用下方方式
				// m_activity.startActivity(new Intent(m_activity,
				// AboutActivity.class));
			}
		});

		rlMenu2 = (RelativeLayout) localSlidingMenu
				.findViewById(R.id.ll_menu_2);
		rlMenu2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(m_activity, "click on menu2!",
						Toast.LENGTH_SHORT).show();
				// // 跳转页面用下方方式
				// m_activity.startActivity(new Intent(m_activity,
				// AboutActivity.class));
			}
		});

		rlMenu3 = (RelativeLayout) localSlidingMenu
				.findViewById(R.id.ll_menu_3);
		rlMenu3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(m_activity, "click on menu3!",
						Toast.LENGTH_SHORT).show();
				// // 跳转页面用下方方式
				// m_activity.startActivity(new Intent(m_activity,
				// AboutActivity.class));
			}
		});
	}
}