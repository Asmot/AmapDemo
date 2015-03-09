package com.example.amapdemo.basic;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.example.amapdemo.R;

public class EventsActivity extends BasicActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);//必须重写，要不然不会显示
		
		
		init();
	}

	@Override
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
			
			aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
				
				@Override
				public void onMapClick(LatLng arg0) {
					// TODO Auto-generated method stub
					showMsg("OnMapClickListener");
				}
			});
			
			aMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
				
				@Override
				public void onMapLongClick(LatLng arg0) {
					// TODO Auto-generated method stub
					showMsg("OnMapLongClickListener");
				}
			});
			
			aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
				
				@Override
				public void onTouch(MotionEvent arg0) {
					// TODO Auto-generated method stub
					showMsg("OnMapTouchListener");
				}
			});

		}
		
		
	}
	
	
	
	private void showMsg(String msg) {
//		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		System.out.println(msg);
	}
	
	
	
}
