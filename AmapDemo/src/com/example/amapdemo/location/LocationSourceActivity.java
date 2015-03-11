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
		//��ʼ��λ
		
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
		//ֹͣ��λ
		Log.i(Constants.TAG, "deactivate");
		
		locationChangedListener = null;
		
		if(mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
			
		}
		mAMapLocationManager = null;
	}
	



	//��������Ѿ�û�����ˡ�������
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
			locationChangedListener.onLocationChanged(arg0);//����С����
			
			
			
//			aMap.moveCamera();
			aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 18), 2000, null);
		}
	}
	
}
///**
// * AMapV1��ͼ�м򵥽�����ʾ��λС����
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
//		mapView.onCreate(savedInstanceState);// �˷���������д
//		init();
//	}
//
//	/**
//	 * ��ʼ��AMap����
//	 */
//	private void init() {
//		if (aMap == null) {
//			aMap = mapView.getMap();
//			setUpMap();
//		}
//	}
//
//	/**
//	 * ����һЩamap������
//	 */
//	private void setUpMap() {
//		// �Զ���ϵͳ��λС����
//		MyLocationStyle myLocationStyle = new MyLocationStyle();
//		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//				.fromResource(R.drawable.location_marker));// ����С�����ͼ��
//		myLocationStyle.strokeColor(Color.BLACK);// ����Բ�εı߿���ɫ
//		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// ����Բ�ε������ɫ
//		// myLocationStyle.anchor(int,int)//����С�����ê��
//		myLocationStyle.strokeWidth(1.0f);// ����Բ�εı߿��ϸ
//		aMap.setMyLocationStyle(myLocationStyle);
//		aMap.setLocationSource(this);// ���ö�λ����
//		aMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
//		aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
//	   // aMap.setMyLocationType()
//	}
//
//	/**
//	 * ����������д
//	 */
//	@Override
//	protected void onResume() {
//		super.onResume();
//		mapView.onResume();
//	}
//
//	/**
//	 * ����������д
//	 */
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mapView.onPause();
//		deactivate();
//	}
//
//	/**
//	 * ����������д
//	 */
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		mapView.onSaveInstanceState(outState);
//	}
//
//	/**
//	 * ����������д
//	 */
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mapView.onDestroy();
//	}
//
//	/**
//	 * �˷����Ѿ�����
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
//	 * ��λ�ɹ���ص�����
//	 */
//	@Override
//	public void onLocationChanged(AMapLocation aLocation) {
//		if (mListener != null && aLocation != null) {
//			mListener.onLocationChanged(aLocation);// ��ʾϵͳС����
//		}
//	}
//
//	/**
//	 * ���λ
//	 */
//	@Override
//	public void activate(OnLocationChangedListener listener) {
//		mListener = listener;
//		if (mAMapLocationManager == null) {
//			mAMapLocationManager = LocationManagerProxy.getInstance(this);
//			/*
//			 * mAMapLocManager.setGpsEnable(false);
//			 * 1.0.2�汾��������������true��ʾ��϶�λ�а���gps��λ��false��ʾ�����綨λ��Ĭ����true Location
//			 * API��λ����GPS�������϶�λ��ʽ
//			 * ����һ�������Ƕ�λprovider���ڶ�������ʱ�������2000���룬������������������λ���ף����ĸ������Ƕ�λ������
//			 */
//			mAMapLocationManager.requestLocationUpdates(
//					LocationProviderProxy.AMapNetwork, 2000, 10, this);
//		}
//	}
//
//	/**
//	 * ֹͣ��λ
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
