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

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.piterwilson.easydrawerlayout.interfaces.EasyDrawerMenuListener;

public class EasyDrawerLayout extends Activity {
	// the left side content (usually a menu)
	View mMenu;
	// the right side content
	View mContent;
	// the amount of motion performed when opening the drawer
	int mMenuWidth;
	// the time it takes to open the drawer
	int mTransitionDuration;
	// the button that opens/closes the drawer
	Button mOpenCloseButton;
	// whether or not the drawer is open
	boolean mOpen;
	// an array of listeners for the open/close eventss
	private ArrayList<EasyDrawerMenuListener> listeners;
	
	// this is just a listener for testing to demonstrate how to listen for open/close of the drawer
	private ASimpleDrawerListener aListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy_drawer_layout);

		// init
		listeners = new ArrayList<EasyDrawerMenuListener>();
		mOpen = false;
		mMenuWidth = 200;
		mTransitionDuration = 250;

		// init views
		mMenu = this.findViewById(R.id.menu_wrapper);
		mContent = this.findViewById(R.id.content_wrapper);

		// init button
		mOpenCloseButton = (Button) findViewById(R.id.openCloseButton);
		mOpenCloseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOpen) {
					closeDrawer();
				} else {
					openDrawer();
				}
			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();

		// add a listener, just for kicks
		aListener = new ASimpleDrawerListener();
		addEasyDrawerListener(aListener);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		//remove it
		removeEasyDrawerListener(aListener);
		aListener = null;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy_drawer_layout, menu);
		return true;
	}

	/****************************************
	 * 
	 * Open / Close drawer
	 * 
	 ****************************************/

	private void openDrawer() {
		ObjectAnimator anim = ObjectAnimator.ofFloat(mContent, "translationX",
				mContent.getTranslationX(), mMenuWidth);
		anim.setDuration(mTransitionDuration);
		anim.addListener(new AnimatorListenerAdapter() {
			public void onAnimationEnd(Animator animation) {
				// Log.d(TAG, "onAnimationEnd");
				mOpen = true;
				mOpenCloseButton.setText(R.string.close);
				dispatchOnMenuOpen();
			}
		});
		anim.start();
	}

	private void closeDrawer() {

		ObjectAnimator anim = ObjectAnimator.ofFloat(mContent, "translationX",
				mContent.getTranslationX(), 0);
		anim.setDuration(mTransitionDuration);
		anim.addListener(new AnimatorListenerAdapter() {
			public void onAnimationEnd(Animator animation) {
				// Log.d(TAG, "onAnimationEnd");
				mOpen = false;
				mOpenCloseButton.setText(R.string.open);
				dispatchOnMenuClosed();
			}
		});
		anim.start();

	}

	protected void dispatchOnMenuClosed() {
		for (EasyDrawerMenuListener l : listeners) {
			l.onMenuClosed();
		}
	}

	/****************************************
	 * 
	 * Notify others
	 * 
	 ****************************************/

	protected void dispatchOnMenuOpen() {
		for (EasyDrawerMenuListener l : listeners) {
			l.onMenuOpen();
		}
	}

	public void addEasyDrawerListener(EasyDrawerMenuListener l) {
		listeners.add(l);
	}

	public void removeEasyDrawerListener(EasyDrawerMenuListener l) {
		listeners.remove(l);
	}

}
