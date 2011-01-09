package com.assembla.gpsmonsters.travelpoints;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.energy.EnergyTank;
import com.assembla.gpsmonsters.energy.EnergyPoolActivity.EnergyGestureListener;
import com.assembla.gpsmonsters.engine.GameManager;
import com.assembla.gpsmonsters.engine.GameManagerConnector;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class TravelActivity extends MapActivity
{
	private MapView mapView;
	private GeoPoint clickedPoint;
	private TravelMapItemizedOverlay itemOverlay;
	private Context _context;
	//private MapTouchOverlay touchOverlay;
	private List<Overlay> mapOverlays;
	private Long timeDown;
	protected GameManagerInterface _gameManager;
	protected GameManagerConnector _connection;
	private TravelGestureListener _gestureListener;
	private GestureDetector _gestureDetector;
	private Vibrator _vibe;
	

	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelmap);
        mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        mapView.setBuiltInZoomControls(true);
        mapView.displayZoomControls(true);
        _context = this;
        
        _connection = new GameManagerConnector(this);
		bindService(new Intent(this, GameManager.class), _connection,
				Context.BIND_AUTO_CREATE);
        
        Resources res = _context.getResources();
        Drawable myImage = res.getDrawable(R.drawable.pin);
        itemOverlay = new TravelMapItemizedOverlay(myImage,this);
    	mapOverlays = mapView.getOverlays();
    	_gestureListener = new TravelGestureListener();
		_gestureDetector = new GestureDetector(this,_gestureListener);
		_vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    	//mapOverlays.add(itemOverlay);
    	//touchOverlay = new MapTouchOverlay();
    	//mapOverlays.add(touchOverlay);
    }

    public void setGameManagerInterface(GameManagerInterface gameManager) {
		_gameManager = gameManager;
	}

	public void onLoadGameManager() {

	}
   
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) 
    { 
    	
    	 super.dispatchTouchEvent(ev);
    	 return _gestureDetector.onTouchEvent(ev);
    }
    
    public class TravelGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onDoubleTap(MotionEvent e)
		{
			_vibe.vibrate(300);
			 Projection p= mapView.getProjection(); 
		        GeoPoint g=p.fromPixels((int)e.getX(), (int)e.getY());
		        OverlayItem overlay = new OverlayItem(g,"Travel Point", "Travel Here");
		        
		        itemOverlay.addOverlay(overlay);
		    	mapOverlays = mapView.getOverlays();
		    	
		    	if(!mapOverlays.isEmpty())
		    		mapOverlays.remove(0);
		        
		    	mapOverlays = mapView.getOverlays();

		    	mapOverlays.add(itemOverlay); 
		    	return true;			
		}
		@Override
		public void onLongPress (MotionEvent e)
		{
			onDoubleTap(e);
		}
	}
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public void end(int lat, int lon)
    {
    	Location travelLocation = new Location("TravelMap");
    	travelLocation.setLatitude(lat);
    	travelLocation.setLongitude(lon);
    	_gameManager.getLocationModule().setLocation(travelLocation);
    	_gameManager.getLocationModule().setTraveling(true);
    	_gameManager.getTravelpointsModule().usePoints();
    	finish();
    }
    
}
