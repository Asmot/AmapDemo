package com.example.amapdemo.basic;

import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.example.amapdemo.R;
import com.example.amapdemo.utils.Constans;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class CameraActivity extends BasicActivity implements OnClickListener{

	private static final int SCROLL_BY_PX = 100;//每次移动的距离
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		super.onCreate(savedInstanceState);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		
		init();
	}

	
	@Override
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
		}
		
		Button stopAnimation = (Button) findViewById(R.id.stop_animation);
		stopAnimation.setOnClickListener(this);
		ToggleButton animate = (ToggleButton) findViewById(R.id.animate);
		animate.setOnClickListener(this);
		Button Lujiazui = (Button) findViewById(R.id.Lujiazui);
		Lujiazui.setOnClickListener(this);
		Button Zhongguancun = (Button) findViewById(R.id.Zhongguancun);
		Zhongguancun.setOnClickListener(this);
		Button scrollLeft = (Button) findViewById(R.id.scroll_left);
		scrollLeft.setOnClickListener(this);
		Button scrollRight = (Button) findViewById(R.id.scroll_right);
		scrollRight.setOnClickListener(this);
		Button scrollUp = (Button) findViewById(R.id.scroll_up);
		scrollUp.setOnClickListener(this);
		Button scrollDown = (Button) findViewById(R.id.scroll_down);
		scrollDown.setOnClickListener(this);
		Button zoomIn = (Button) findViewById(R.id.zoom_in);
		zoomIn.setOnClickListener(this);
		Button zoomOut = (Button) findViewById(R.id.zoom_out);
		zoomOut.setOnClickListener(this);
	}

	
	private void changeCamera(CameraUpdate update){
		boolean isAnimated = ((ToggleButton)findViewById(R.id.animate)).isChecked();
		
		if(isAnimated) {//以动画方式移动过去
			aMap.animateCamera(update);
//			aMap.animateCamera(update, 1000, callbck);//移动一秒  还会回调
		} else {
			aMap.moveCamera(update);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.stop_animation:
			aMap.stopAnimation();
			break;
		case R.id.Lujiazui:
			
			/*
			 * public CameraPosition(LatLng target,
                      float zoom,
                      float tilt,
                      float bearing)
				构造一个CameraPosition 对象 。
				参数:
				target - 目标位置的屏幕中心点经纬度坐标 。
				zoom - 目标可视区域的缩放级别。缩放级别为3~20。
				tilt - 目标可视区域的倾斜度，2D地图此参数无意义 。
				bearing - 可视区域指向的方向，以角度为单位，正北方向到地图方向逆时针旋转的角度，范围从0度到360度。
			 */
			changeCamera(CameraUpdateFactory.newCameraPosition(
					new CameraPosition(Constans.SHANGHAI, 18, 0, 0)));
			break;
		case R.id.Zhongguancun:
			changeCamera(CameraUpdateFactory.newCameraPosition(
					new CameraPosition(Constans.ZHONGGUANCUN, 18, 0, 0)));
			break;
		case R.id.scroll_down:
			/*
			 *public static CameraUpdate scrollBy(float xPixel,
                                    float yPixel)
				返回一个CameraUpdate 对象，此对象为改变可视区域的中心的值，单位像素。可视区域中心点将会在x 和y 轴方向按参数的数据进行移动。
				案例:
				
				如果传入的xPixel = 5 、 yPixel = 0，则系统将可视区域向右移动，所以地图将显示为向左移动5 个像素。
				
				如果传入xPixel = 0 、 yPixel = 5，则系统将可视区域向下移动，所以地图显示为向上移动了5 个像素。 
			 */
			changeCamera(CameraUpdateFactory.scrollBy(0, SCROLL_BY_PX));
			break;
		case R.id.scroll_up:
			
			changeCamera(CameraUpdateFactory.scrollBy(0, -SCROLL_BY_PX));
			break;
		case R.id.scroll_left:
			
			changeCamera(CameraUpdateFactory.scrollBy(-SCROLL_BY_PX, 0));
			break;
		case R.id.scroll_right:
	
			changeCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX, 0));
		break;
			
		default:
			break;
		}
	}
	
}
