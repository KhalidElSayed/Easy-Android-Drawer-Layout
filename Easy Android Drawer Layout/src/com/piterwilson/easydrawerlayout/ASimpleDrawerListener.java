package com.piterwilson.easydrawerlayout;

import android.util.Log;

import com.piterwilson.easydrawerlayout.interfaces.EasyDrawerMenuListener;

public class ASimpleDrawerListener implements EasyDrawerMenuListener {
	
	private static final String TAG = "ASimpleDrawerListener";
	
	public ASimpleDrawerListener() {
		Log.d(TAG,"born to listen...");
	}

	@Override
	public void onMenuOpen() {
		Log.d(TAG,"Dude... did someone just open the drawer?");
	}

	@Override
	public void onMenuClosed() {
		Log.d(TAG,"Dude... did someone just close the drawer?");
	}

}
