package com.assembla.gpsmonsters.tileengine;

import java.util.ArrayList;
import java.util.Collections;

import com.assembla.gpsmonsters.engine.Vector2D;

public class Tile {
	
	private boolean _isPassable;
	private Vector2D _coordinates;
	private ArrayList<OverlayTile> _children;
	private float _xPos, _yPos;
	
	public Tile(Vector2D coords, boolean canPassThru)
	{
		_isPassable = canPassThru;
		_coordinates = coords;
		_children = new ArrayList<OverlayTile>();
	}
	public void setXPos(float x)
	{
		_xPos = x;
	}
	public void setYPos(float y)
	{
		_yPos = y;
	}
	public float xPos()
	{
		return _xPos;
	}
	
	public float yPos()
	{
		return _yPos;
	}
	public void addChild(OverlayTile child)
	{
		_children.add(child);
	}
	public void killChild(OverlayTile child)
	{
		_children.remove(child);
	}
	public void orderChildren()
	{
		Collections.sort(_children);
	}
	
	public int NumChildren()
	{
		return _children.size();
	}
	
	public ArrayList<OverlayTile> getChildren()
	{
		return _children;
	}
	
	public Vector2D getCoordinates()
	{
		return _coordinates;
	}
	
	public boolean isPassable()
	{
		return _isPassable;
	}
	
}
