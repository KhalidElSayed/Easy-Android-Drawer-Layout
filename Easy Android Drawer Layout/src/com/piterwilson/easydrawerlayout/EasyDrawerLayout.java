package com.piterwilson.easydrawerlayout;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class EasyDrawerLayout extends Activity {
	
	// whether or not the menu is open
	private boolean mOpen = false;
	//the button that toggles the opening and closing of the menu
	private Button mMenuToggleButton;
	// whether or not the menu is already animating
	private boolean mAnimating;
	// the main content we move left and right to open/close menu
	private LinearLayout mMainContent;
	// width of the menu content
	private int mMenuWidth = 300;
	// onclick listener for the button
	private View.OnClickListener toggleButtonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// if already moving do nothing...
			if(mAnimating) return;
			if(!mOpen)
			{
				Animation animation = AnimationUtils.loadAnimation(EasyDrawerLayout.this, R.anim.drawer_right);
				animation.setAnimationListener(new Animation.AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						mMainContent.clearAnimation();
						
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mMainContent.getLayoutParams());
						params.setMargins(0, 0, 0, 0);
						mMainContent.setLayoutParams(params);
						mAnimating = false;
						mMenuToggleButton.setText(R.string.close);
					}
				});
				mAnimating = true;
				mMainContent.startAnimation(animation);
			}
			else
			{
				Animation animation = AnimationUtils.loadAnimation(EasyDrawerLayout.this, R.anim.drawer_left);
				animation.setAnimationListener(new Animation.AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						
						mMainContent.clearAnimation();
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mMainContent.getLayoutParams());
						params.setMargins(-mMenuWidth, 0, 0, 0);
						mMainContent.setLayoutParams(params);
						mAnimating = false;
						mMenuToggleButton.setText(R.string.open);
					}
				});
				mAnimating = true;
				mMainContent.startAnimation(animation);
			}
			mOpen = !mOpen;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mOpen = false;
		mAnimating = false;
		//get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// add the content view
		setContentView(R.layout.activity_easy_drawer_layout);
		
		mMainContent = (LinearLayout)findViewById(R.id.content_holder);
		FrameLayout.LayoutParams mainLayoutParams = new FrameLayout.LayoutParams(width + mMenuWidth,height);
		mainLayoutParams.setMargins(-mMenuWidth, 0, 0, 0);
		
		mMainContent.setLayoutParams(mainLayoutParams);
		
		mMenuToggleButton = (Button)this.findViewById(R.id.button1);
		mMenuToggleButton.setOnClickListener(toggleButtonClickListener);
		
		LinearLayout left = (LinearLayout)findViewById(R.id.left_content);
		left.setLayoutParams(new LinearLayout.LayoutParams(mMenuWidth,height));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy_drawer_layout, menu);
		return true;
	}

}
