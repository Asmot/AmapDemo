package com.example.amapdemo.basic;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.example.amapdemo.R;

import android.app.Activity;
import android.os.Bundle;

public class BasicActivity extends Activity{

	protected AMap aMap;
	protected MapView mapView;
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_basic);
//		mapView = (MapView) findViewById(R.id.map);
//		mapView.onCreate(savedInstanceState);//������д��Ҫ��Ȼ������ʾ
//		
//		
//		init();
//	}
//
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
		}
	}

	//����д�Ļ������������ٴ򿪣��ֱ�ɳ�ʼ״̬��
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	
	
	
}
