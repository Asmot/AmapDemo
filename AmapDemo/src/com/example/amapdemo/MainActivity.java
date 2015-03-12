package com.example.amapdemo;

import com.amap.api.maps2d.LocationSource;
import com.example.amapdemo.basic.BaseMapFragmentActivity;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.basic.CameraActivity;
import com.example.amapdemo.basic.EventsActivity;
import com.example.amapdemo.basic.HelloActivity;
import com.example.amapdemo.basic.LayerActivity;
import com.example.amapdemo.basic.MapOptionActivity;
import com.example.amapdemo.basic.ScreenShotActivity;
import com.example.amapdemo.basic.UISettingActivity;
import com.example.amapdemo.location.LocationSensorSourceActivity;
import com.example.amapdemo.location.LocationSourceActivity;
import com.example.amapdemo.overlay.GroundOverlayActivity;
import com.example.amapdemo.overlay.MarkersActivity;
import com.example.amapdemo.overlay.PolygonActivity;
import com.example.amapdemo.overlay.PolylineActivity;
import com.example.amapdemo.poisearch.PoiAroundSearchActivity;
import com.example.amapdemo.poisearch.PoiKeyWordSearchActivity;
import com.example.amapdemo.route.RouteActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView listView;
	
	
	private static final DemoDetails[] demos= {
		new DemoDetails("HelloActivity", "创建一个基本地图，helloworld", HelloActivity.class),
		new DemoDetails("CameraActivity", "介绍在地图中操作Camera各种功能", CameraActivity.class),
		new DemoDetails("EventsActivity", "介绍在地图中各种事件", EventsActivity.class),
		new DemoDetails("LayerActivity", "介绍在地图中各种图层", LayerActivity.class),
		new DemoDetails("MapOptionActivity", "禁止地图中的一些操作", MapOptionActivity.class),
		new DemoDetails("BaseMapFragmentActivity", "BaseMapFragmentActivity", BaseMapFragmentActivity.class),
		new DemoDetails("ScreenShotActivity", "截图功能", ScreenShotActivity.class),
		new DemoDetails("UISettingActivity", "各种小功能", UISettingActivity.class),
		new DemoDetails("PolylineActivity", "在地图上划线", PolylineActivity.class),
		new DemoDetails("PolygonActivity", "在地图上画多边形", PolygonActivity.class),
		new DemoDetails("MarkersActivity", "在地图上加Markers", MarkersActivity.class),
		new DemoDetails("GroundOverlayActivity", "在地图上放一张图片", GroundOverlayActivity.class),
		new DemoDetails("LocationSource", "地图上的小蓝点", LocationSourceActivity.class),
		new DemoDetails("LocationSensorSourceActivity", "小蓝点 跟随转动", LocationSensorSourceActivity.class),
		new DemoDetails("PoiKeyWordSearchActivity", "关键字搜索", PoiKeyWordSearchActivity.class),
		new DemoDetails("PoiAroundSearchActivity", "周边搜索", PoiAroundSearchActivity.class),
		new DemoDetails("RouteActivity", "线路查询", RouteActivity.class),
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}
	
	
	private void init() {
		listView = (ListView) findViewById(R.id.lv_main);
		listView.setAdapter(new MyAdatpet());
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, demos[arg2].activityClass);
				startActivity(intent);
			}
		});
	}


	private static class DemoDetails {
		private String title;
		private String describe;
		private Class<? extends Activity> activityClass;
		
		public DemoDetails(String title, String describe,
				Class<? extends Activity> activities) {
			super();
			this.title = title;
			this.describe = describe;
			this.activityClass = activities;
		}
	}
	
	private class MyAdatpet extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return demos.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View currentView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(currentView == null)
			{
				holder = new ViewHolder();
				currentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_item, null);
				holder.title_tv = (TextView) currentView.findViewById(R.id.title);
				holder.describe_tv = (TextView) currentView.findViewById(R.id.description);
				
				currentView.setTag(holder);
			}
			holder = (ViewHolder) currentView.getTag();
			
			DemoDetails demo = demos[arg0];
			holder.title_tv.setText(demo.title);
			holder.describe_tv.setText(demo.describe);
			
			
			return currentView;
		}
		
	}
	
	private class ViewHolder {
		public TextView title_tv;
		public TextView describe_tv;
	}
	
}
