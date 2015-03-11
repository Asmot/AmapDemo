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
	 * SupportMapFragment 是一个地图的容器。这个碎片（fragment）是在APP 中显示地图最简单的方法。
	 * SupportMapFragment 类已经自动处理了一个View 运行的生命同期。
	 * 用户可以在activity 的layout 文件里加入一段简单的XML 代码来实现SupportMapFragment。示例如下： 
	 * 使用这个类之前必须在Android项目里引入Android Support Library。
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
		
		options.zoomGesturesEnabled(false);//禁止缩放
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
	 * 初始化AMap对象
	 */
	private void initMap() {
		if (aMap == null) {
			aMap = mapFragment.getMap();// amap对象初始化成功
		}
	}
	
	
}
