package com.example.amapdemo.basic;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.SupportMapFragment;
import com.example.amapdemo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseMapFragmentActivity extends FragmentActivity{
	protected AMap aMap;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_basesupportmap);
		
		setUpMap();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		setUpMap();
	}
	
	protected void setUpMap() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
	}
	
}
