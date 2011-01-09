package com.assembla.gpsmonsters.energy;

import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameManagerInterface;

public class EnergyConnection {
	private GameManagerInterface _gameManager;
	private DatabaseModule _databaseModule;
	
	public EnergyConnection(GameManagerInterface gameManager)
	{
		_gameManager = gameManager;
		_databaseModule = _gameManager.getDatabaseModule();
	}
	public float getEnergy(int type)
	{
		float result = 0;
		_databaseModule.openDataBase();
		result = _databaseModule.getEnergy(type);
		_databaseModule.close();
		return result;
	}
	public void setEnergy(int type, int value)
	{
		_databaseModule.openDataBase();
		_databaseModule.updateEnergy(type, value);
		_databaseModule.close();
	}
}
