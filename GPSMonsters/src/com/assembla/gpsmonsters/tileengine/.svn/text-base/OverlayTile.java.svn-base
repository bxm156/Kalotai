package com.assembla.gpsmonsters.tileengine;

import com.assembla.gpsmonsters.engine.Vector2D;

public class OverlayTile implements Comparable<OverlayTile> {

	public static int WATER_TILE 	= 0;
	public static int TREE_TILE 	= 3;
	public static int BUSH_TILE 	= 1;
	public static int RUIN_TILE 	= 2;
	public static int KRYT_TILE 	= 4;
	
	private Vector2D _worldCoords;
	private Vector2D _localCoords;
	
	private String _overlayID;
	private int _tileType;
	private boolean _passable;
	
	private int _krytEncounterId;
	
	
	public OverlayTile(Vector2D worldCoords, Vector2D localCoords, int tileType, String overlayID, boolean passable)
	{
		_tileType = tileType;
		_overlayID = overlayID;
		_passable = passable;
		_worldCoords = worldCoords;
		_localCoords = localCoords;
	}
	
	public int TileType()
	{
		return _tileType;
	}
	
	public String OverlayID()
	{
		return _overlayID;
	}
	public void setPassable(boolean flag)
	{
		_passable = flag;
	}
	public boolean isPassable()
	{
		return _passable;
	}
	
	public Vector2D worldCoords()
	{
		return _worldCoords;
	}
	
	public Vector2D localCoords()
	{
		return _localCoords;
	}
	public int getGameX()
	{
		return (_worldCoords.getX() * 512 + _localCoords.getX() * 256);
	}
	public int getGameY()
	{
		return (_worldCoords.getY() * 512 + _localCoords.getY() * 256);
	}

	/**
	 * @param _krytEncounterId the _krytEncounterId to set
	 */
	public void setKrytEncounterId(int _krytEncounterId) {
		this._krytEncounterId = _krytEncounterId;
	}

	/**
	 * @return the _krytEncounterId
	 */
	public int getKrytEncounterId() {
		return _krytEncounterId;
	}

	@Override
	public int compareTo(OverlayTile another) {
		// TODO Auto-generated method stub
		if(_tileType < another.TileType())
		{
			return -1;
		}
		if(_tileType > another.TileType())
		{
			return 1;
		}
		return 0;
	}
}