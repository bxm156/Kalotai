package com.assembla.gpsmonsters;

import android.content.ContentValues;

import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;

public class Player {
	private Creature _currentKryt;
	private GameManagerInterface _gameManager;
	private GamePanel _gp;
	private DatabaseModule _db;
	public Player(GameManagerInterface gameManager,GamePanel gp)
	{
		_gameManager = gameManager;
		_gp =  gp;
		_db = _gameManager.getDatabaseModule();
		//Get selected kryt from database
		Creature kryt = new Creature(-1,_gameManager,_gp);
		_currentKryt = kryt;
			
	
	}
	public void swapKryt(int newKrytId)
	{
		_db.openDataBase();
		ContentValues krytValues;
		krytValues = _db.getPlayerKrytById(newKrytId);
		_db.close();
		if(krytValues.getAsBoolean("success"))
		{
			Creature kryt  = new Creature(newKrytId,_gameManager,_gp);
			_currentKryt = kryt;
			_db.openDataBase();
			_db.setPlayerKryAsSelected(newKrytId);
			_db.close();
		} else {
			
		}
		_db.close();
	}
	public Creature getCurrentCreature()
	{
		return _currentKryt;
	}
}
