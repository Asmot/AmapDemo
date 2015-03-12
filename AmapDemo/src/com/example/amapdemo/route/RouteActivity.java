package com.example.amapdemo.route;

import java.util.List;

import org.apache.http.util.LangUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.BusRouteOverlay;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.FromAndTo;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.BasicActivity;
import com.example.amapdemo.utils.Constants;

public class RouteActivity extends BasicActivity 
implements View.OnClickListener, OnRouteSearchListener, InfoWindowAdapter, 
 OnInfoWindowClickListener, OnMapClickListener, OnMarkerClickListener{

	private Button drivingButton;
	private Button busButton;
	private Button walkButton;

	private ImageButton startImageButton;
	private ImageButton endImageButton;
	private ImageButton routeSearchImagebtn;
	
	private EditText startTextView;
	private EditText endTextView;
	
	private RouteSearch search;
	private RouteResult routeResult;
	
	//记录是否在地图上选择起点和终点
	private boolean isSelectingStart = false;
	private boolean isSelectingEnd = false;
	
	//起点和终点
	private LatLonPoint startPoint;
	private LatLonPoint endPoint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		search = new RouteSearch(this);
		search.setRouteSearchListener(this);

		startTextView = (EditText) findViewById(R.id.autotextview_roadsearch_start);
		endTextView = (EditText) findViewById(R.id.autotextview_roadsearch_goals);
		
		busButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_transit);
		busButton.setOnClickListener(this);
		drivingButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_driving);
		drivingButton.setOnClickListener(this);
		walkButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_walk);
		walkButton.setOnClickListener(this);
		startImageButton = (ImageButton) findViewById(R.id.imagebtn_roadsearch_startoption);
		startImageButton.setOnClickListener(this);
		endImageButton = (ImageButton) findViewById(R.id.imagebtn_roadsearch_endoption);
		endImageButton.setOnClickListener(this);
		routeSearchImagebtn = (ImageButton) findViewById(R.id.imagebtn_roadsearch_search);
		routeSearchImagebtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.imagebtn_roadsearch_tab_transit:
				
				break;
			case R.id.imagebtn_roadsearch_tab_driving:
				
				break;
			case R.id.imagebtn_roadsearch_tab_walk:
				
				break;
			case R.id.imagebtn_roadsearch_startoption:
				Toast.makeText(this, "在地图上选择起点", Toast.LENGTH_SHORT).show();
				isSelectingStart = true;
				isSelectingEnd = false;
				registerListener();
				break;
			case R.id.imagebtn_roadsearch_endoption:
				Toast.makeText(this, "在地图上选择终点", Toast.LENGTH_SHORT).show();
				isSelectingStart = false;
				isSelectingEnd = true;
				registerListener();
				break;
			case R.id.imagebtn_roadsearch_search:
				aMap.setOnMapClickListener(null);//取消对地图的点击事件监听
				doSearch();
				break;
	
			default:
				break;
		}
		
	}
	
	private void doSearch() {
		/*
		 * ft - 路径的起终点。
			mode - 计算路径的模式。可选，默认为最快捷。
			city - 城市名称/城市区号/电话区号。此项不能为空。
			nightflag - 是否计算夜班车，默认为不计算。0：不计算，1：计算。可选
		 */
		if(startPoint !=null && endPoint != null) {
			RouteSearch.FromAndTo ft = new RouteSearch.FromAndTo(startPoint, endPoint);
			BusRouteQuery query = new BusRouteQuery(ft, RouteSearch.BusDefault, "010", 0);
			search.calculateBusRouteAsyn(query);
		} else {
			Toast.makeText(this, "请选择起点和终点", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	private void registerListener() {
		aMap.setOnInfoWindowClickListener(this);
		aMap.setOnMapClickListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
	}
	

	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
//		System.out.println("onBusRouteSearched");
		if(arg1 == 0) {
			aMap.clear();
			BusRouteOverlay overlay = new BusRouteOverlay(this, aMap, arg0.getPaths().get(0), arg0.getStartPos(), arg0.getTargetPos());
			overlay.removeFromMap();
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMapClick(LatLng arg0) {
		if(isSelectingStart) {
			//设置开始位置
			Marker marker = aMap.addMarker(new MarkerOptions()
			.anchor(0.5f, 1.0f)
			.position(arg0)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
			.title("选择此地为起点"));	
			marker.showInfoWindow();
		} else if(isSelectingEnd) {
			Marker marker = aMap.addMarker(new MarkerOptions()
			.anchor(0.5f, 1.0f)
			.position(arg0)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
			.title("选择此地为终点"));	
			marker.showInfoWindow();
		}		
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		if(isSelectingStart) {
			//设置开始位置
			startTextView.setText("地图上的起点");
			startPoint = new LatLonPoint(arg0.getPosition().latitude, arg0.getPosition().longitude);
			aMap.clear();
			
		} else if(isSelectingEnd) {
			endTextView.setText("地图上的终点");
			endPoint = new LatLonPoint(arg0.getPosition().latitude, arg0.getPosition().longitude);
			aMap.clear();
		}	
	}
	
	
}
