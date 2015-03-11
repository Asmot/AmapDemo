package com.example.amapdemo.overlay;

import java.lang.reflect.Array;
import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.utils.Constants;

public class PolygonActivity extends BasicActivity implements OnSeekBarChangeListener{

	private static final int WIDTH_MAX = 50;
	private static final int HUE_MAX = 255;
	private static final int ALPHA_MAX = 255;
	
	private SeekBar mColorBar;
	private SeekBar mAlphaBar;
	private SeekBar mWidthBar;
	
	private Polygon polygon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poly);
		
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
		
		//画圆
//		createCircle();
		createOval();
//		cereteRect();
	}
	
	protected void init() {
		super.init();
		
		mColorBar = (SeekBar) findViewById(R.id.hueSeekBar);
		mColorBar.setMax(HUE_MAX);
		mColorBar.setOnSeekBarChangeListener(this);
		
		mAlphaBar = (SeekBar) findViewById(R.id.alphaSeekBar);
		mAlphaBar.setMax(ALPHA_MAX);
		mAlphaBar.setOnSeekBarChangeListener(this);
		
		mWidthBar = (SeekBar) findViewById(R.id.widthSeekBar);
		mWidthBar.setMax(WIDTH_MAX);
		mWidthBar.setOnSeekBarChangeListener(this);
		
		
		aMap.moveCamera(CameraUpdateFactory.zoomTo(4));
		
		
		

		
	}
	
	private void createCircle() {
		CircleOptions options = new CircleOptions();
		
		
		options.center(Constants.BEIJING);
		
		options.radius(300000);
		
		aMap.addCircle(options.strokeWidth(25)
				.strokeColor(Color.argb(50, 1, 1, 1))
				.fillColor(Color.argb(50, 1, 1, 1)));
	}
	
	private void createOval() {
		PolygonOptions options = new PolygonOptions();
		int numPoints = 400;
		float semiHorizontalAxis = 5f;
		float semiVerticalAxis = 2.5f;
		double phase = 2 * Math.PI / numPoints;
		for (int i = 0; i <= numPoints; i++) {
			options.add(new LatLng(Constants.BEIJING.latitude
					+ semiVerticalAxis * Math.sin(i * phase),
					Constants.BEIJING.longitude + semiHorizontalAxis
							* Math.cos(i * phase)));
		}
		
		
		polygon = aMap.addPolygon(options.strokeWidth(25)
				.strokeColor(Color.argb(50, 1, 1, 1))
				.fillColor(Color.argb(50, 1, 1, 1)));
	}

	private void cereteRect() {
		PolygonOptions options = new PolygonOptions();
		double latitude = Constants.BEIJING.latitude;
		double longitude = Constants.BEIJING.longitude;
		double width = 10;
		double height = 10;
		options.addAll(Arrays.asList(
				new LatLng(latitude + width, longitude + height),
				new LatLng(latitude + width, longitude - height),
				new LatLng(latitude - width, longitude - height),
				new LatLng(latitude - width, longitude + height)
				));
		polygon = aMap.addPolygon(options.strokeWidth(25)
				.strokeColor(Color.argb(150, 1, 1, 1))
				.fillColor(Color.argb(50, 1, 1, 1)));
	}
	
	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		switch (arg0.getId()) {
		case R.id.hueSeekBar:
			polygon.setFillColor(Color.argb(progress, 1, 1, 1));
			break;
		case R.id.alphaSeekBar:
			polygon.setStrokeColor(Color.argb(progress, 1, 1, 1));
			break;
		case R.id.widthSeekBar:
			polygon.setStrokeWidth(progress);
			break;
		default:
			break;
		}
		
		aMap.invalidate();//不写的话 只有手动拖动地图之后，才能看到修改效果
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
