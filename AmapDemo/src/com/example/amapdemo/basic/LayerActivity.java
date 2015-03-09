package com.example.amapdemo.basic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.example.amapdemo.R;

public class LayerActivity extends BasicActivity
implements OnCheckedChangeListener, OnItemSelectedListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_layer);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		
		init();
	}
	
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
		}
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.traffic);
		checkBox.setOnCheckedChangeListener(this);
		
		Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.layers_array,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
		
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		aMap.setTrafficEnabled(arg1);
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if(arg2 == 0) {
			aMap.setMapType(AMap.MAP_TYPE_NORMAL);
		} else {
			aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
