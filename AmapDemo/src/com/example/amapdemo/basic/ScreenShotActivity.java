package com.example.amapdemo.basic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.maps2d.AMap.OnMapScreenShotListener;
import com.amap.api.maps2d.MapView;
import com.example.amapdemo.R;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ScreenShotActivity extends BasicActivity 
implements OnMapScreenShotListener{

	private Button screenShotBt;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screenshot);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
	}
	
	@Override
	protected void init() {
		if(aMap == null) {
			aMap = mapView.getMap();
			
			screenShotBt = (Button) findViewById(R.id.screenshot_bt);
			
			screenShotBt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
					aMap.getMapScreenShot(ScreenShotActivity.this);
					aMap.invalidate();//ˢ�µ�ͼ
					//ò�Ʋ�ˢ��Ҳ�����������а���������
					
				}
			});
		}
	}

	@Override
	public void onMapScreenShot(Bitmap bitmap) {
		System.out.println("��ʼ����");
		try {
			FileOutputStream fos = new FileOutputStream(
					Environment.getExternalStorageDirectory() + "/screenshot_" +
			new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png");
			
			//ͼƬѹ��
			boolean flag = bitmap.compress(CompressFormat.PNG, 100, fos);//100��ʾѹ���ʣ���ʾѹ��0%
			
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(flag) {
				Toast.makeText(this, "��ͼ�ɹ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "��ͼʧ��", Toast.LENGTH_SHORT).show();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
