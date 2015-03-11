package com.example.amapdemo.basic;

import com.amap.api.maps2d.AMap.OnMyLocationChangeListener;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.example.amapdemo.R;

import android.location.Location;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;

public class UISettingActivity extends BasicActivity 
implements OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, LocationSource, OnMyLocationChangeListener{

	private UiSettings uiSettings;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ui_setting);
		
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
		
	}

	@Override
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
			
			uiSettings = aMap.getUiSettings();
			
			addCheckBoxListener(R.id.scale_toggle);
			addCheckBoxListener(R.id.zoom_toggle);
			addCheckBoxListener(R.id.compass_toggle);
			addCheckBoxListener(R.id.scroll_toggle);
			addCheckBoxListener(R.id.zoom_gestures_toggle);
			
			RadioGroup group = (RadioGroup) findViewById(R.id.logo_position);
			
			group.setOnCheckedChangeListener(this);
			
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		if(aMap != null) {
			if(id == R.id.bottom_left) {
				uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
			} else if(id == R.id.bottom_center) {
				uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
			} else if(id == R.id.bottom_right) {
				uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
			}
		}
	}
	
	private void addCheckBoxListener(int id) {
		((CheckBox) findViewById(id)).setOnCheckedChangeListener(this);
	}
	

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.scale_toggle:
			uiSettings.setScaleControlsEnabled(arg1);
			break;
		case R.id.zoom_toggle:
			uiSettings.setZoomControlsEnabled(arg1);
			break;
		case R.id.compass_toggle:
			uiSettings.setCompassEnabled(arg1);
			break;
		case R.id.mylocation_toggle:
			uiSettings.setMyLocationButtonEnabled(arg1);
			
			aMap.setMyLocationEnabled(arg1);
			aMap.setLocationSource(this);
			aMap.setOnMyLocationChangeListener(this);
			
			break;
		case R.id.scroll_toggle:
			uiSettings.setScrollGesturesEnabled(arg1);
			break;
		case R.id.zoom_gestures_toggle:
			uiSettings.setZoomGesturesEnabled(arg1);
			break;
		default:
			break;
		}
	}
	
	

	@Override
	public void activate(OnLocationChangedListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onMyLocationChange(Location arg0) {
		// TODO Auto-generated method stub
		
	}


	
	
}
