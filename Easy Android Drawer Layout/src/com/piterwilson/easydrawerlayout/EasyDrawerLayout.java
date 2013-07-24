/*
    Easy Drawer Layout
    Copyright (C) 2013  Juan Carlos Ospina Gonzalez

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
 */

package com.piterwilson.easydrawerlayout;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
	private int mMenuWidth = 400;
	// duration of the transition
	private int mTransitionDuration = 250;
	// OnClickListener listener for the button
	private View.OnClickListener toggleButtonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// if already moving do nothing...
			if(mAnimating) return;
			if(!mOpen)
			{
				Animation animation = new TranslateAnimation (0, mMenuWidth, 0, 0);
				animation.setDuration(mTransitionDuration);
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
				Animation animation = new TranslateAnimation (0, -mMenuWidth, 0, 0);
				animation.setDuration(mTransitionDuration);
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
		// not open!
		mOpen = false;
		// not animating!
		mAnimating = false;
		//get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		// make fullscreen!
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
