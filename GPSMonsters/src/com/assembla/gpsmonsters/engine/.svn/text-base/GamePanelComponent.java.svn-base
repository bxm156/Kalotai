package com.assembla.gpsmonsters.engine;

import android.graphics.Canvas;

public class GamePanelComponent implements Comparable<GamePanelComponent> {
	private int _drawOrder;
	public GamePanelComponent()
	{
		_drawOrder = 0;
	}
	public void update() 
	{
		
	}
	public void draw(Canvas canvas)
	{
		
	}
	public int getDrawOrder()
	{
		return _drawOrder;
	}
	public void setDrawOrder(int drawOrder)
	{
		_drawOrder = drawOrder;
		return;
	}
	@Override
	public int compareTo(GamePanelComponent another) {
		if(_drawOrder < another.getDrawOrder())
		{
			return -1;
		}
		if(_drawOrder > another.getDrawOrder())
		{
			return 1;
		}
		return 0;
	}
}
