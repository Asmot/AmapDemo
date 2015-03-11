package com.example.amapdemo.location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.HelloActivity;
import com.example.amapdemo.utils.Constants;

public class LocationSensorSourceActivity extends HelloActivity 
implements SensorEventListener, LocationSource, AMapLocationListener{

	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private Marker gpsMarker;
	private long lastTime = 0;
	private final int TIME_SENSOR = 100;
	private float mAngle;
	
	private OnLocationChangedListener listener;
	private LocationManagerProxy locationManager;
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		
		gpsMarker = aMap.addMarker(new MarkerOptions()
				.anchor(0.5f, 0.5f)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
		
		//定位太慢 就不定为了  直接把图标放上去
//		aMap.setLocationSource(this);
//		aMap.getUiSettings().setMyLocationButtonEnabled(true);
//		aMap.setMyLocationEnabled(true);
		
		gpsMarker.setPosition(Constants.BEIJING);
		
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		if(mSensor == null) {
			Log.i(Constants.TAG, "get Sensor faild");
		} else {
			Log.i(Constants.TAG, "get Sensor success");
		}
		
		Log.i(Constants.TAG, "start reigtser Sensor");
		//添加传感器监听
		boolean flag = mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
		
		if(flag) {
			Log.i(Constants.TAG, "reigtser faild");
		} else {
			Log.i(Constants.TAG, "reigtser success");
		}
		
		super.onResume();
		mapView.onResume();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		deactivate();
		
		super.onPause();
		mapView.onPause();
	}



	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		//隔TIME_SENSOR 才刷新一次
		if(System.currentTimeMillis() - lastTime < TIME_SENSOR) {
			return;
		}
		switch (arg0.sensor.getType()) {
		case Sensor.TYPE_ORIENTATION:
			float x = arg0.values[0];
			Log.i(Constants.TAG, x + "");
			
			x += getScreenRotationOnPhone();
			x %= 360.0f;
			
			if(x > 180.0) {
				x -= 360.0f;
			} else if(x < -180.0f) {
				x += 360.0f;
			} else if(Math.abs(mAngle - 90 + x) < 3.0f) {
				break;
			}
			
			mAngle = x;
			if(gpsMarker != null) {
				gpsMarker.setRotateAngle(mAngle);
				aMap.invalidate();
			}
			lastTime = System.currentTimeMillis();
			break;

		default:
			break;
		}
	}

	/**
	 * 获取当前屏幕的朝向
	 * @return
	 */
	private int getScreenRotationOnPhone(){
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		switch (display.getOrientation()) {
		case Surface.ROTATION_0:
			return 0;
		case Surface.ROTATION_90:
			return 90;
		case Surface.ROTATION_180:
			return 180;
		case Surface.ROTATION_270:
			return -90;
		}
		return 0;
	}
	


	@Override
	public void activate(OnLocationChangedListener arg0) {
		// TODO Auto-generated method stub
		listener = arg0;
		if(locationManager == null) {
			locationManager = LocationManagerProxy.getInstance(this);
			locationManager.requestLocationUpdates(LocationManagerProxy.GPS_PROVIDER, 2000, 10, this);
		}
	}



	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		listener = null;
		if(locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destory();
		}
		locationManager = null;
		
		
		//取消传感器监听
		mSensorManager.unregisterListener(this);
			
	}



	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
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
		if(listener != null && arg0 != null) {
			//定位成功设置marker的位置
			gpsMarker.setPosition(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
		}
	}
	
}
