package com.assembla.gpsmonsters.engine;

import com.assembla.gpsmonsters.travelpoints.TravelActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class GameManagerConnector implements ServiceConnection {
	GameManagerInterface _gameManager;
	GameActivity _activity;
	TravelActivity _travelActivity;

	public GameManagerConnector(GameActivity activity)
	{
		_activity = activity;
	}
	
	public GameManagerConnector(TravelActivity activity)
	{
		_travelActivity = activity;
	}
	
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		_gameManager = (GameManagerInterface) service;
		if(_activity!=null)
		{
			_activity.setGameManagerInterface(_gameManager);
			_activity.onLoadGameManager();
		}
		if(_travelActivity!=null)
		{
			_travelActivity.setGameManagerInterface(_gameManager);
			_travelActivity.onLoadGameManager();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		
	}

}
