package com.example.amapdemo;

import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.basic.CameraActivity;
import com.example.amapdemo.basic.HelloActivity;

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
