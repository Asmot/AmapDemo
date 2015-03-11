package com.example.amapdemo.location;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.basic.HelloActivity;
import com.example.amapdemo.utils.Constants;

public class LocationSourceActivity extends HelloActivity
implements LocationSource, AMapLocationListener{
	
	private OnLocationChangedListener locationChangedListener;
	private LocationManagerProxy mAMapLocationManager;
	

	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		MyLocationStyle locationStyle = new MyLocationStyle();
		locationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));
		locationStyle.strokeColor(Color.BLACK);
		locationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));
		locationStyle.strokeWidth(1.0f);
		
		aMap.setMyLocationStyle(locationStyle);
		aMap.setLocationSource(this);
		aMap.getUiSettings().setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		
//		aMap.setOnMyLocationChangeListener(this);
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	public void activate(OnLocationChangedListener arg0) {
		// TODO Auto-generated method stub
		Log.i(Constants.TAG, "activate");
		//开始定位
		
		locationChangedListener = arg0;
		
		if(mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			mAMapLocationManager.setGpsEnable(true);
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		//停止定位
		Log.i(Constants.TAG, "deactivate");
		
		locationChangedListener = null;
		
		if(mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
			
		}
		mAMapLocationManager = null;
	}
	



	//这个方法已经没有用了。。。。
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}



	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// TODO Auto-generated method stub
		Log.i(Constants.TAG, "onLocationChanged");
		
		if(locationChangedListener != null && arg0 != null) {
			locationChangedListener.onLocationChanged(arg0);//放置小蓝点
			
			
			
//			aMap.moveCamera();
			aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 18), 2000, null);
		}
	}
	
}
///**
// * AMapV1地图中简单介绍显示定位小蓝点
// */
//public class LocationSourceActivity extends Activity implements LocationSource,
//		AMapLocationListener {
//	private AMap aMap;
//	private MapView mapView;
//	private OnLocationChangedListener mListener;
//	private LocationManagerProxy mAMapLocationManager;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_basic);
//		mapView = (MapView) findViewById(R.id.map);
//		mapView.onCreate(savedInstanceState);// 此方法必须重写
//		init();
//	}
//
//	/**
//	 * 初始化AMap对象
//	 */
//	private void init() {
//		if (aMap == null) {
//			aMap = mapView.getMap();
//			setUpMap();
//		}
//	}
//
//	/**
//	 * 设置一些amap的属性
//	 */
//	private void setUpMap() {
//		// 自定义系统定位小蓝点
//		MyLocationStyle myLocationStyle = new MyLocationStyle();
//		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
//		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
//		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
//		aMap.setMyLocationStyle(myLocationStyle);
//		aMap.setLocationSource(this);// 设置定位监听
//		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//	   // aMap.setMyLocationType()
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onResume() {
//		super.onResume();
//		mapView.onResume();
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mapView.onPause();
//		deactivate();
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		mapView.onSaveInstanceState(outState);
//	}
//
//	/**
//	 * 方法必须重写
//	 */
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mapView.onDestroy();
//	}
//
//	/**
//	 * 此方法已经废弃
//	 */
//	@Override
//	public void onLocationChanged(Location location) {
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//	}
//
//	/**
//	 * 定位成功后回调函数
//	 */
//	@Override
//	public void onLocationChanged(AMapLocation aLocation) {
//		if (mListener != null && aLocation != null) {
//			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
//		}
//	}
//
//	/**
//	 * 激活定位
//	 */
//	@Override
//	public void activate(OnLocationChangedListener listener) {
//		mListener = listener;
//		if (mAMapLocationManager == null) {
//			mAMapLocationManager = LocationManagerProxy.getInstance(this);
//			/*
//			 * mAMapLocManager.setGpsEnable(false);
//			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
//			 * API定位采用GPS和网络混合定位方式
//			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
//			 */
//			mAMapLocationManager.requestLocationUpdates(
//					LocationProviderProxy.AMapNetwork, 2000, 10, this);
//		}
//	}
//
//	/**
//	 * 停止定位
//	 */
//	@Override
//	public void deactivate() {
//		mListener = null;
//		if (mAMapLocationManager != null) {
//			mAMapLocationManager.removeUpdates(this);
//			mAMapLocationManager.destory();
//		}
//		mAMapLocationManager = null;
//	}
//}
////
