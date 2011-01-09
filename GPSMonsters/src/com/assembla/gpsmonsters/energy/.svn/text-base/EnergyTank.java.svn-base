package com.assembla.gpsmonsters.energy;

public class EnergyTank {
	private int _currentLevel;
	private int _totalLevel;
	private int _color;
	
	public static final int GREEN = 0;
	public static final  int RED = 1;
	public static final int BLUE = 2;
	public static final int HEALTH = 0;
	public static final int ATTACK = 1;
	public static final int DEFENSE = 2;
	public static final int MISC = 3;
	public static final int WHITE = 3;
	
	public EnergyTank(int type)
	{
		this._color = type;
		_totalLevel = 100;
		_currentLevel = 0;
	}
	
	/* Auto generated get/set methods by Eclipse :) */
	public void setCurrentLevel(int currentLevel) {
		this._currentLevel = currentLevel;
	}
	public int getCurrentLevel() {
		return _currentLevel;
	}
	public void setTotalLevel(int totalLevel) {
		this._totalLevel = totalLevel;
	}
	public int getTotalLevel() {
		return _totalLevel;
	}
	public void setColor(int color) {
		this._color = color;
	}
	public int getColor() {
		return _color;
	}
	/* End */
	public boolean isEmpty()
	{
		return (_currentLevel == 0) ? true : false;
	}
}
