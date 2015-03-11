package com.example.amapdemo.poisearch;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps2d.MapView;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.utils.Constants;

public class PoiKeyWordSearchActivity extends BasicActivity
implements OnClickListener, TextWatcher{

	AutoCompleteTextView keywordEt;
	EditText cityEt;
	
	Button searchBt;
	Button nextBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poikeywordsearch);
		
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		keywordEt = (AutoCompleteTextView) findViewById(R.id.keyword);
		cityEt = (EditText) findViewById(R.id.city);
		
		keywordEt.addTextChangedListener(this);
		
		searchBt = (Button) findViewById(R.id.searchButton);
		nextBt = (Button) findViewById(R.id.nextButton);
		
		searchBt.setOnClickListener(this);
		nextBt.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.searchButton:
			//开始搜索
			searchBt();
			
			break;
		case R.id.nextButton:
			
			break;
		default:
			break;
		}
	}
	
	private void searchBt() {
		String keyword = keywordEt.getText().toString();
		
		if(keyword.length() < 1) {
			Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
		} else {
			doQuery();
		}
	}
	private void doQuery() {
		//poisearch
	}
	

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		Log.i(Constants.TAG, "onTextChanged");
		
		//去服务器去相关数据
		
		List<String> list = new ArrayList<String>();
		
		for(int i = 0; i < 10; i ++)
			list.add(i + " " + arg0);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.route_inputs, list);
		
		keywordEt.setAdapter(adapter);
	}
	
	
	
}
