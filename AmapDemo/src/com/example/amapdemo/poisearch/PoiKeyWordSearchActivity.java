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
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.utils.Constants;

public class PoiKeyWordSearchActivity extends BasicActivity
implements OnClickListener, TextWatcher, OnPoiSearchListener{

	AutoCompleteTextView keywordEt;
	EditText cityEt;
	
	Button searchBt;
	Button nextBt;
	
	PoiSearch search;
	PoiSearch.Query query;
	PoiResult poiResult;	
	private int currentPage = 0;
	private int pageSize = 10;
	
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
			nextBt();
			break;
		default:
			break;
		}
	}
	
	//搜寻下一页对的内容
	private void nextBt() {
		//结果的总页面大于当前显示的页面 才可以查询下一页的内容
		if(search != null && query != null && poiResult != null && poiResult.getPageCount() - 1 > currentPage) {
			currentPage ++ ;
			query.setPageNum(currentPage);
			search.searchPOIAsyn();
		} else {
			Toast.makeText(this, "没有结果", Toast.LENGTH_SHORT).show();
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
	/**
	 * 开始搜索
	 */
	private void doQuery() {
		
		//搜索之前清除掉map上面的marker
		aMap.clear();
		
		//poisearch
		currentPage = 0;
		query =  new PoiSearch.Query(keywordEt.getText().toString(), cityEt.getText().toString());
		query.setPageSize(pageSize);
		query.setPageNum(currentPage);
		
		search = new PoiSearch(this, query);
		search.setOnPoiSearchListener(this);
		search.searchPOIAsyn();//异步处理
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
//		final String newText = arg0.toString();
		//去服务器去相关数据
		
		Inputtips inputtips = new Inputtips(this, new InputtipsListener() {
			
			@Override
			public void onGetInputtips(List<Tip> arg0, int arg1) {
				// TODO Auto-generated method stub
				
				List<String> list = new ArrayList<String>();
				
				for(Tip tip : arg0) {
					list.add(tip.getName());
				}
//				System.out.println(list.toString());
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(PoiKeyWordSearchActivity.this, R.layout.route_inputs, list);
				keywordEt.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		});
		String keyWord = arg0.toString();
		String city = cityEt.getText().toString();
		
		try {
			inputtips.requestInputtips(keyWord, city);
		} catch (AMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub
//		Log.i(Constants.TAG, "PoiItemDetail: " + arg0.toString() + " " + arg1);
		System.out.println("PoiItemDetail: " + arg0.toString() + " " + arg1);
	}

	@Override
	public void onPoiSearched(PoiResult arg0, int arg1) {
		// TODO Auto-generated method stub
//		Log.i(Constants.TAG, "PoiResult: " + arg0.toString() + " " + arg1);
//		System.out.println("PoiResult: " + arg0.toString() + " " + arg1);
		if(arg1 == 0) {
			
			poiResult = arg0;//搜索结果
			
			ArrayList<PoiItem> pois = arg0.getPois();
			List<SuggestionCity> suggestionCities = arg0.getSearchSuggestionCitys();
			
			if(pois != null && pois.size() >0) {
				PoiOverlay overlay = new PoiOverlay(aMap, pois);
				overlay.addToMap();
				overlay.zoomToSpan();
			} else if(suggestionCities != null && suggestionCities.size() > 0){
				tipSuggestionCity(suggestionCities);
			} else {
				Toast.makeText(this, "没有结果", Toast.LENGTH_SHORT).show();
			}
			
			//amap2d中自带的poioverlay 不喜欢还可以自己修改
		
//			for(PoiItem item : pois) {
////				System.out.println(item.getTitle() + ", " + item.getSnippet());
//				
//				options.title(item.getTitle());
//				options.snippet(item.getSnippet());
//				options.position(item.get)
//			}
		} else {
			Toast.makeText(this, "没有结果", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void tipSuggestionCity(List<SuggestionCity> suggestionCities) {
		StringBuilder builder = new StringBuilder();
		for(SuggestionCity city : suggestionCities) {
			builder.append("城市名称: " + city.getCityName() + 
					", 城市区号: " + city.getCityCode() +
					", 城市编码: " + city.getAdCode() + "\n");
		}
		
		Toast.makeText(this, builder.subSequence(0, builder.length() - 1).toString(), Toast.LENGTH_SHORT).show();
	}
	
	
	
}
