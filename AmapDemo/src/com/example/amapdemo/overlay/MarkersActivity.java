package com.example.amapdemo.overlay;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.utils.Constants;

public class MarkersActivity extends BasicActivity 
implements OnMarkerDragListener, OnClickListener, 
RadioGroup.OnCheckedChangeListener, OnMapLoadedListener,
OnMarkerClickListener, OnMapClickListener{
	
	private final static String TAG = "MarkersActivity";
	
	private Marker marker1;
	
	private TextView markerText;
	private Button markerButton;// 获取屏幕内所有marker的button
	private RadioGroup radioOption;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maker);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
		
		addMakersOnMap();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		markerText = (TextView) findViewById(R.id.mark_listenter_text);
		markerButton = (Button) findViewById(R.id.marker_button);
		radioOption = (RadioGroup) findViewById(R.id.custom_info_window_options);
		
		markerButton.setOnClickListener(this);
		((Button)findViewById(R.id.clearMap)).setOnClickListener(this);
		((Button)findViewById(R.id.resetMap)).setOnClickListener(this);
		
		radioOption.setOnCheckedChangeListener(this);
		
		aMap.setOnMarkerDragListener(this);
		aMap.setOnMapLoadedListener(this);
		aMap.setOnMarkerClickListener(this);
		
		aMap.setOnMapClickListener(this);
	}
	
	private void addMakersOnMap() {
		MarkerOptions options = new MarkerOptions();
		
//		options.anchor(1.0f, 1.0f);//锚点
		options.snippet("this is a test" + "\n aaa!!" + "\n aaa!!" + "\n aaa!!");
		options.title("这是标题");
		options.position(Constants.BEIJING);//在地图上的位置
		options.draggable(true);
		
		marker1 = aMap.addMarker(options);
		
		options = new MarkerOptions();
		
//		options.anchor(1.0f, 1.0f);//锚点
		options.snippet("second");
		options.title("title");
		options.position(Constants.ZHENGZHOU);//在地图上的位置
		options.draggable(true);
		
		aMap.addMarker(options);
	}

	@Override
	public void onMarkerDrag(Marker marker) {
		// TODO Auto-generated method stub
		System.out.println(marker.getTitle() + "drag");
		
		LatLng latLng = marker.getPosition();
		
		
		markerText.setText("正在拖动。。" + marker.getTitle() + "坐标为: " + latLng.toString());
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.clearMap:
			if(aMap != null) {
				aMap.clear();
			}
			break;

		case R.id.resetMap:
			if(aMap != null) {
				aMap.clear();
				addMakersOnMap();
			}
			break;
		case R.id.marker_button:
			List<Marker> markers = aMap.getMapScreenMarkers();
			
			if(markers == null || markers.size() == 0) {
				Toast.makeText(this, "当前屏幕没有Marker", Toast.LENGTH_SHORT).show();
			}
			
			StringBuilder builder = new StringBuilder();
			for(Marker marker : markers) {
				builder.append("Title:" + marker.getTitle() + " Snippet:" + marker.getSnippet() + "\n");
			}
			Toast.makeText(this, builder.toString(), Toast.LENGTH_SHORT).show();
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		Log.i(TAG, " " + arg1);
	}

	@Override
	public void onMapLoaded() {
		Log.i(TAG, " " + "onMapLoaded");
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		
		Log.i(TAG, " " + "onMarkerClick: " + arg0.getTitle());
		
		return false;
	}

	@Override
	public void onMapClick(LatLng arg0) {
		
		//点击屏幕的时候关闭marker的info window
		Log.i(TAG, " " + "onMapClick: ");
		
		List<Marker> markers = aMap.getMapScreenMarkers();
		
		if(markers == null || markers.size() == 0) {
			return;
		}
		
		for(Marker marker : markers) {
			marker.hideInfoWindow();
		}
	}
	
}
