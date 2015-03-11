package com.example.amapdemo.overlay;


import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.GroundOverlay;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.example.amapdemo.R;
import com.example.amapdemo.basic.HelloActivity;
import com.example.amapdemo.utils.Constants;

public class GroundOverlayActivity extends HelloActivity{

	GroundOverlay groundOverlay;
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		GroundOverlayOptions options = new GroundOverlayOptions();
		
		LatLngBounds bounds = new LatLngBounds(Constants.BEIJING,
				new LatLng(Constants.BEIJING.latitude + 0.1, Constants.BEIJING.longitude + 0.1));
		
//		options.position(Constants.BEIJING, 0.5f, 1.0f);
//		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.BEIJING, 18));
		options.image(BitmapDescriptorFactory.fromResource(R.drawable.groundoverlay));
		options.positionFromBounds(bounds);
		options.zIndex(1000);
		options.bearing(90);
		
		groundOverlay = aMap.addGroundOverlay(options);
		
//		groundOverlay.setZIndex(1000);
		groundOverlay.setDimensions(100, 1000);
	}
	
	

}
