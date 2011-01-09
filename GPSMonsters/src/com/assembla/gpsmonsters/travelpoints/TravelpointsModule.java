package com.assembla.gpsmonsters.travelpoints;

import android.util.Log;

import com.assembla.gpsmonsters.engine.GameManager;
import com.assembla.gpsmonsters.location.LocationModule;

public class TravelpointsModule {
	private GameManager _gameManager;
	private LocationModule _locationModule;
	private double _lat;
	private double _long;
	private double _oldLat;
	private double _oldLong;
	private int _travelPoints = 400;
	private final int USE_POINTS = 200;


	public TravelpointsModule(GameManager gameManager) {

		_gameManager = gameManager;
		_locationModule = _gameManager.getLocationModule();

		_oldLat = _locationModule.getLatitude();
		_oldLong = _locationModule.getLongitude();
		calculateTP();
		// TODO Auto-generated constructor stub
	}
	
	public void calculateTP(){
		
			_lat = _locationModule.getLatitude();
			_long = _locationModule.getLongitude();
			if(!_locationModule.getTraveling())
			{
			//sqrt(|dx|^2+|dy|^2)
			if(_oldLat != 0.0 || _oldLong != 0.0 || _lat != 0.0 || _long != 0.0){
			if(((long) Math.sqrt(Math.pow((Math.abs((long)_oldLat - (long)_lat)*1000),2.0) + Math.pow((Math.abs((long)_oldLong - (long)_long)*1000),2))) == 0.0) {
				_travelPoints += 0.0;
			}
			else {
				_travelPoints += ((long) Math.sqrt(Math.pow((Math.abs((long)_oldLat - (long)_lat)*1000),2.0) + Math.pow((Math.abs((long)_oldLong - (long)_long)*1000),2))); 
			}
			
			//Log.d("message1", "travel points should = " + _travelPoints);
			}
		
			else{
				
			}
			
		}
			_oldLat = _lat;
			_oldLong = _long;
	}
	
	public int getTravelPoints(){
		return _travelPoints;
	}
	
	public int getNewTravelPoints(){
		calculateTP();
		return _travelPoints;
	}
	
	public double getOldLatitude() {
		return _oldLat;
		
	}
	
	public double getLatitude() {
		return _oldLat;
		
	}
	public double getOldLongitude() {
		return _oldLong;
		
	}
	
	public double getLongitude() {
		return _long;
		
	}
	
	public void usePoints()
	{
		_travelPoints -= USE_POINTS;
	}

	public int getUsePoints()
	{
		return USE_POINTS;
	}
}
