/***************************************
 * LocationModule
 * 
 * Author: Bryan Marty
 * 
 * Description: A module that is used to interface the Android's Location Services,
 * and various APIs for gathering information about a Lat/Long
 * 
 * 
***************************************/

package com.assembla.gpsmonsters.location;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.assembla.gpsmonsters.engine.GameManager;
import com.google.gson.Gson;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


public class LocationModule {
	
	private LocationManager _locationManager;
	private Location _location;
	
	private double _lat = 0.0;//41.50127;
	private double _lo = 0.0;//-81.60027;
	
	private static int DISTANCE_BETWEEN_WEATHER_CHECK = 1000; //1000m
	
	public double _mapCenterLat, _mapCenterLon;
	
	private GameManager _gameManager;
	
	private boolean _isStartingUp = true;
	
	public boolean _needsNewMap = false;
	private boolean traveling = false;
	
	public LocationModule(GameManager gameManager)
	{
		_gameManager = gameManager;
		
		
		_locationManager = (LocationManager) _gameManager.getStartActivity().getSystemService(Context.LOCATION_SERVICE);
		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    @Override
			public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		    	setLocation(location);
		    	
		    }

		    @Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}

		    @Override
			public void onProviderEnabled(String provider) {}

		    @Override
			public void onProviderDisabled(String provider) {}
		    
		  };

		
		// Register the listener with the Location Manager to receive location updates
		_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
	public void setLocation(Location location)
	{
		if(!traveling)
		{
			try {
				if(_location.distanceTo(location) > DISTANCE_BETWEEN_WEATHER_CHECK ) {
					_gameManager._currentWeather = _gameManager.getWeatherModule().getCondition();
				}	
			} catch (Exception e) { } //_location is null until found
			_location = location;
			if(_isStartingUp)
			{
				// Tell SGM we are ready
				_needsNewMap = true;
				//
				_isStartingUp = false;
			}
			else
			{
				Location _mapCenterLoc = new Location("");
				_mapCenterLoc.setLatitude(_mapCenterLat);
				_mapCenterLoc.setLongitude(_mapCenterLon);
				
				double distance = getDistanceBetween(_mapCenterLoc, _location);
				
				if(distance > 75.0) //greater than 75 meters
				{
					_needsNewMap = true;
				}
			}
		}
	}
	
	public void setTraveling(boolean _traveling)
	{
		traveling = _traveling;
		_gameManager.getStartActivity().switchTravelButton(traveling);
	}
	
	public boolean getTraveling()
	{
		return traveling;
	}
	
	public int getZipcode()
	{
		 try{
		        Gson gson = new Gson();
		        Reader r = new InputStreamReader(getJSONData("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + _location.getLatitude() +"," + _location.getLongitude() +"&sensor=true"));
		        GoogleGeocode objs = gson.fromJson(r, GoogleGeocode.class);
		        if(objs.getStatus().equals("OK"))
		        {
		        	List<Results> results = objs.getResults();
		        	Results result = results.get(0);
		        	List<AddressComponents> components = result.getAddress_components();
		        	for(AddressComponents c : components)
		        	{
		        		if(c.getTypes().contains("postal_code")) {
		        			return Integer.parseInt(c.getLong_name());
		        		}
		        	}
		        	
		        }
		 }catch(Exception ex){
		            ex.printStackTrace();
		 }
		return 44106; //default value
	}
	public InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return data;
    }
	public double getLatitude()
	{
		try
		{
			return _location.getLatitude();
		}
		catch (NullPointerException e)
		{
			return 0.0;
		}
	}
	public double getLongitude()
	{
		try
		{
			return _location.getLongitude();	
		}
		catch (NullPointerException e)
		{
			return 0.0;
		}
	}
	public String getLocationString()
	{
		if(_isStartingUp)
		{
			return _lat + "," + _lo;
		} else {
			return "" + _location.getLatitude() + "," + _location.getLongitude();
		}
	}
	public float getSpeed()
	{
		return _location.getSpeed();
	}

	public float getDistanceBetween(Location startLocation, Location endLocation)
	{
		//Returns distance in meters
		System.out.print(startLocation.distanceTo(endLocation));
		return startLocation.distanceTo(endLocation);
	}
	public float getDistanceFromUserTo(Location endLocation)
	{
		//Returns distance in meters
		System.out.print(_location.distanceTo(endLocation));
		return _location.distanceTo(endLocation);
	}
}
