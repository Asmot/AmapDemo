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

	private static final int SCROLL_BY_PX = 100;//ÿ���ƶ��ľ���
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		super.onCreate(savedInstanceState);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// �˷���������д
		
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
		
		if(isAnimated) {//�Զ�����ʽ�ƶ���ȥ
			aMap.animateCamera(update);
//			aMap.animateCamera(update, 1000, callbck);//�ƶ�һ��  ����ص�
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
				����һ��CameraPosition ���� ��
				����:
				target - Ŀ��λ�õ���Ļ���ĵ㾭γ������ ��
				zoom - Ŀ�������������ż������ż���Ϊ3~20��
				tilt - Ŀ������������б�ȣ�2D��ͼ�˲��������� ��
				bearing - ��������ָ��ķ����ԽǶ�Ϊ��λ���������򵽵�ͼ������ʱ����ת�ĽǶȣ���Χ��0�ȵ�360�ȡ�
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
				����һ��CameraUpdate ���󣬴˶���Ϊ�ı������������ĵ�ֵ����λ���ء������������ĵ㽫����x ��y �᷽�򰴲��������ݽ����ƶ���
				����:
				
				��������xPixel = 5 �� yPixel = 0����ϵͳ���������������ƶ������Ե�ͼ����ʾΪ�����ƶ�5 �����ء�
				
				�������xPixel = 0 �� yPixel = 5����ϵͳ���������������ƶ������Ե�ͼ��ʾΪ�����ƶ���5 �����ء� 
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
