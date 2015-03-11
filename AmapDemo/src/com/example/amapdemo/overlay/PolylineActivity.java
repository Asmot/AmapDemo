package com.example.amapdemo.overlay;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.utils.Constants;

public class PolylineActivity extends BasicActivity implements OnSeekBarChangeListener{

	private static final int WIDTH_MAX = 50;
	private static final int HUE_MAX = 255;
	private static final int ALPHA_MAX = 255;
	
	private SeekBar mColorBar;
	private SeekBar mAlphaBar;
	private SeekBar mWidthBar;
	
	private Polyline polyline;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poly);
		
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
		
		addPolies();
	}
	
	private void addPolies() {
		
		ArrayList<LatLng> polies = new ArrayList<LatLng>();
		
//		LatLng(39.90403, 116.407525);
		
		double latitude = Constants.BEIJING.latitude;
		double longitude = Constants.BEIJING.longitude;
		
		Random random = new Random();
		int max = 10;
		
		for(int i = 0; i < 10; i ++) {
			
			polies.add(new LatLng(latitude + random.nextDouble() * max, longitude + random.nextDouble() * max));
		}
		
		PolylineOptions polylineOptions = new PolylineOptions();
		polylineOptions.addAll(polies);
		polylineOptions.color(Color.MAGENTA);
		
		aMap.addPolyline(polylineOptions);
	}
	
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
		}
		
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
		
		//amap.addPolyline会返回一个PolyLine
		//然后可以通过polyline对线条进行操作处理
		//出行路线 应该就是由这样的一对线段组成
		polyline = aMap.addPolyline(new PolylineOptions()
				.add(Constants.SHANGHAI, Constants.BEIJING, Constants.CHENGDU)
				.width(10)
				.color(Color.argb(255, 1, 1, 1)));
		
		
		
		// 绘制一个乌鲁木齐到哈尔滨的线
		aMap.addPolyline((new PolylineOptions()).add(
				new LatLng(43.828, 87.621), new LatLng(45.808, 126.55)).color(
				Color.RED));
		//绘制一条成都到郑州的虚线
		aMap.addPolyline((new PolylineOptions()).add(
				Constants.CHENGDU, Constants.ZHENGZHOU).color(
				Color.RED)).setDottedLine(true);
		//绘制一条广州到乌鲁木齐的大地曲线
		aMap.addPolyline((new PolylineOptions()).add(
				new LatLng(23.15,113.26), new LatLng(43.828, 87.621)).color(
				Color.RED)).setGeodesic(true);
		
	}

	
	
	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		switch (arg0.getId()) {
		case R.id.hueSeekBar:
			float[] hsv = new float[3];
			Color.colorToHSV(polyline.getColor(), hsv);
			polyline.setColor(Color.HSVToColor(progress, hsv));
			break;
		case R.id.alphaSeekBar:
			polyline.setColor(Color.argb(progress, 1, 1, 1));
			break;
		case R.id.widthSeekBar:
			polyline.setWidth(progress);
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
