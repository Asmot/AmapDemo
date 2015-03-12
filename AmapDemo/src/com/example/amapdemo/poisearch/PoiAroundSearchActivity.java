package com.example.amapdemo.poisearch;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BaseMapFragmentActivity;
import com.example.amapdemo.utils.Constants;

public class PoiAroundSearchActivity extends FragmentActivity
implements OnItemSelectedListener, OnClickListener, 
OnPoiSearchListener, OnMarkerClickListener, OnMapClickListener, OnInfoWindowClickListener{

	private AMap aMap;
	
	private Spinner deepSpinner;
	private Spinner searchTypeSpinner;
	private String[] itemDeep = { "餐饮", "景区", "酒店", "影院" };
	private String[] itemTypes = { "所有poi", "有团购", "有优惠", "有团购或者优惠" };
	
	
	private Button locationBt;
	private Button searchBt;
	private Button nextBt;
	
	private PoiSearch.Query query;
	private PoiSearch search;
	private PoiResult poiResult;
	
	private String searchDeep = itemDeep[0];
	private int searchType = 0;
	private String city = "";
	
	private int currentPage = 0;
	private int pageSize = 10;
	
	private Marker locationMarker;
	private LatLonPoint lp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poiaroundsearch);
		
		init();
		
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(aMap == null) {
			aMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		}
	}


	private void init() {
		if(aMap == null) {
			aMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			deepSpinner = (Spinner) findViewById(R.id.spinnerdeep);
			searchTypeSpinner = (Spinner) findViewById(R.id.searchType);
			ArrayAdapter<CharSequence> adapter = 
					new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, itemDeep);
			deepSpinner.setAdapter(adapter);
			adapter = 
					new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, itemTypes);
			searchTypeSpinner.setAdapter(adapter);
		
			
			locationBt = (Button) findViewById(R.id.locationButton);
			searchBt = (Button) findViewById(R.id.searchButton);
			nextBt = (Button) findViewById(R.id.nextButton);
			
			lp = new LatLonPoint(Constants.BEIJING.latitude, Constants.BEIJING.longitude);
			
			locationMarker = aMap.addMarker(new MarkerOptions()
			.anchor(0.5f, 1.0f)
			.position(Constants.BEIJING)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
			.title("默认以北京坐标为中心"));	
			locationMarker.showInfoWindow();
			
			deepSpinner.setOnItemSelectedListener(this);
			searchTypeSpinner.setOnItemSelectedListener(this);
			
			locationBt.setOnClickListener(this);
			searchBt.setOnClickListener(this);
			nextBt.setOnClickListener(this);
			
		}
	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long id) {
		// TODO Auto-generated method stub
//		System.out.println("onItemSelected:" + );
		if(arg0.getId() == R.id.spinnerdeep) {
//			System.out.println(itemDeep[position]);
			searchDeep = itemDeep[position];
		} else if(arg0.getId()  == R.id.searchType) {
//			System.out.println(position);
			searchType = position;
		}
	} 

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
//		System.out.println("onNothingSelected");
//		if(arg0.getId() == R.id.spinnerdeep) {
////			System.out.println(itemDeep[position]);
//			searchDeep = itemDeep[0];
//		} else if(arg0.getId()  == R.id.searchType) {
////			System.out.println(position);
//			searchType = position;
//		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.locationButton:
//			System.out.println("location");
			dealLocation();
			break;
		case R.id.searchButton:
//			System.out.println("searchButton");
			doQuery();
			break;
		case R.id.nextButton:
//			System.out.println("nextButton");
			nextBt();
			break;
		default:
			break;
		}
	}
	

	
	private void nextBt() {
		//显示下一波内容
		
		if(query != null && search != null && poiResult.getPageCount() - 1 > currentPage) {
			currentPage ++;
			query.setPageNum(currentPage);
			search.searchPOIAsyn();
			
		} else {
			Toast.makeText(this, "没有下一页了！", Toast.LENGTH_SHORT).show();
		}
	}
	
	//搜索
	private void doQuery() {
		
		//清除marker
		aMap.clear();
		//清除onmapclick监听
		aMap.setOnMapClickListener(null);
		
		currentPage = 0;
		query = new PoiSearch.Query("", searchDeep, city);
		query.setPageNum(currentPage);
		query.setPageSize(pageSize);
		//设置搜索中心
		
		
		switch (searchType) {
		//{ "所有poi", "有团购", "有优惠", "有团购或者优惠" };
		/*
		 * query.setLimitGroupbuy(boolean); //团购 默认false
		   query.setLimitDiscount(boolean); //优惠 默认false
		 */
		case 0:
			query.setLimitGroupbuy(false);
			query.setLimitDiscount(false);
			break;
		case 1:
			query.setLimitGroupbuy(true);
			query.setLimitDiscount(false);
			break;
		case 2:
			query.setLimitGroupbuy(false);
			query.setLimitDiscount(true);
			break;
		case 3:
			query.setLimitGroupbuy(true);
			query.setLimitDiscount(true);
			break;
		default:
			break;
		}
		
		if(lp != null) {
			search = new PoiSearch(this, query);
			search.setOnPoiSearchListener(this);
			
			/*
			 * center - 该范围的中心点。
				radiusInMeters - 半径，单位：米。
				isDistanceSort - 是否按照距离排序。
			 */
			search.setBound(new SearchBound(lp, 2000, true));
			
			search.searchPOIAsyn();//异步搜索
		}
		
		
		
	}


	
	
	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub
		System.out.println("onPoiItemDetailSearched...这个啥时候执行啊。。。");
	}


	@Override
	public void onPoiSearched(PoiResult arg0, int arg1) {
		if(arg1 == 0) {
			poiResult = arg0;
			ArrayList<PoiItem> pois = poiResult.getPois();
			List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
			
			if(pois != null && pois.size() > 0) {
				PoiOverlay overlay = new PoiOverlay(aMap, pois);
				overlay.addToMap();
				overlay.zoomToSpan();
				
			} else if(pois != null && pois.size() > 0) {
				tipSuggestionCity(suggestionCities);
			} else {
				Toast.makeText(this, "没有结果", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	private void tipSuggestionCity(List<SuggestionCity> suggestionCities) {
		StringBuilder builder = new StringBuilder();
		for(SuggestionCity city : suggestionCities) {
			builder.append("城市名称: " + city.getCityName() + 
					", 城市区号: " + city.getCityCode() +
					", 城市编码: " + city.getAdCode() + "\n");
		}
		
		
		//显示的时候去掉最后一个回车
		Toast.makeText(this, builder.subSequence(0, builder.length() - 1).toString(), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 设置以哪里为中心
	 */
	private void dealLocation() {
		aMap.clear();//去掉上次的marker
		
		//注册监听
		//marker监听
		//地图点击事件的监听
		aMap.setOnMarkerClickListener(this);
		aMap.setOnMapClickListener(this);
		aMap.setOnInfoWindowClickListener(this);
		
	}


	@Override
	public boolean onMarkerClick(Marker arg0) {
		locationMarker.hideInfoWindow();
		return false;
	}


	@Override
	public void onInfoWindowClick(Marker arg0) {
		locationMarker.hideInfoWindow();
	}


	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		//地图点击之后 清除marker，如果有的话
		aMap.clear();
		locationMarker = aMap.addMarker(new MarkerOptions()
		.anchor(0.5f, 1.0f)
		.position(arg0)
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
		.title("以此为中心"));	
		locationMarker.showInfoWindow();
		
		lp = new LatLonPoint(arg0.latitude, arg0.longitude);
	}
	
}
