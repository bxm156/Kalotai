package com.assembla.gpsmonsters.weather;

import com.assembla.gpsmonsters.engine.GameManager;
import com.assembla.gpsmonsters.location.LocationModule;


public class WeatherThread extends Thread{
	
	
	private GameManager _gameManager;
	private LocationModule _locationModule;
	private WeatherModule _weatherModule;
	private boolean _hasZipCode = false;
	private boolean _isRunning = false;
	
	public WeatherThread(GameManager gm)
	{
		_gameManager = gm;
		_weatherModule = _gameManager.getWeatherModule();
	}

	@Override
	public void run()
	{
		while(_isRunning)
		{
			if(!_hasZipCode)
			{
				_hasZipCode = tryGetWeather(_locationModule.getZipcode());
				_isRunning = false;
			}
		}
	}
	
	public void setRunning(boolean run)
	{
		_isRunning = run;
	}
	
	private boolean tryGetWeather(int zipCode)
	{
		int weather;
		weather=_weatherModule.getCondition();
		if (weather ==0)
			return false;
		else
			return true;
	}

}
