package com.example.amapdemo.basic;

import android.os.Bundle;

import com.amap.api.maps2d.MapView;
import com.example.amapdemo.R;

public class HelloActivity extends BasicActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);//必须重写，要不然不会显示
		
		
		init();
	}
	
}
