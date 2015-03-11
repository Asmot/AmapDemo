package com.example.amapdemo.basic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.CameraPosition;
import com.example.amapdemo.utils.Constants;

public class MapOptionActivity extends FragmentActivity{

	private static final String MAP_FRAGMENT_TAG = "map1";
	private static final CameraPosition LUJIAZUI =  
			new CameraPosition.Builder().target(Constants.SHANGHAI).zoom(18).bearing(0).tilt(30).build();
	
	/*
	 * SupportMapFragment ��һ����ͼ�������������Ƭ��fragment������APP ����ʾ��ͼ��򵥵ķ�����
	 * SupportMapFragment ���Ѿ��Զ�������һ��View ���е�����ͬ�ڡ�
	 * �û�������activity ��layout �ļ������һ�μ򵥵�XML ������ʵ��SupportMapFragment��ʾ�����£� 
	 * ʹ�������֮ǰ������Android��Ŀ������Android Support Library��
	 */
	private SupportMapFragment mapFragment;
	
	private AMap aMap;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		init();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initMap();
	}
	
	private void init() {
		AMapOptions options = new AMapOptions();
		
		options.zoomGesturesEnabled(false);//��ֹ����
		options.scrollGesturesEnabled(false);
		options.camera(LUJIAZUI);
		
		if(mapFragment == null) {
			mapFragment = SupportMapFragment.newInstance(options);
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			
			transaction.add(android.R.id.content, mapFragment, MAP_FRAGMENT_TAG);
			transaction.commit();
			
		}
		
		
	}


	
	/**
	 * ��ʼ��AMap����
	 */
	private void initMap() {
		if (aMap == null) {
			aMap = mapFragment.getMap();// amap�����ʼ���ɹ�
		}
	}
	
	
}
