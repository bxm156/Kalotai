package com.assembla.gpsmonsters.tileengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.assembla.gpsmonsters.*;
import com.assembla.gpsmonsters.database.DatabaseFields;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameActivity;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.Vector2D;

import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;

public class MapFactory {
	
	static final int MAP_WIDTH = 19;
	static final int MAP_HEIGHT = 19;
	
	private char[][] _map;
	private Tile[][] _mapTiles;
	
	private SGMConnection mapConn;
	private Display _display;
	
	private Resources _res;
	private boolean _isMapReady;
	private boolean _mapScroll;
	private int _viewWidth, _viewHeight;
	private double _xOffset, _yOffset;
	private int _stride;
	private int _oldX, _oldY;
	private int _currentX, _currentY;
	
	// image assets
	private Bitmap grass, water;
	private Bitmap kryt1, kryt2, kryt3, kryt4, kryt5;
	private Bitmap kryt6, kryt7, kryt8, kryt9, kryt10;
	private Bitmap _loadingScreen;
	private Bitmap tree1, tree2, tree3, tree4;
	
	private Random rand;
	
	private ArrayList<OverlayTile> _krytTileList;
	
	private GameManagerInterface _gameManager;
	private DatabaseModule _db;
	private GameActivity mainActivity;
	
	private Matrix _overlayMatrix;	
	private Player _player;
	
	private Vector2D _playerVector;
	
	public MapFactory(GameManagerInterface gameManager)
	{
		_gameManager = gameManager;
		_db = _gameManager.getDatabaseModule();
		
		mapConn = new SGMConnection(gameManager, this);
		mapConn.setRunning(true);
		mapConn.start();		
		
		mainActivity = (GameActivity) _gameManager.getStartActivity();
		_krytTileList = new ArrayList<OverlayTile>();
		rand = new Random(System.currentTimeMillis());
		_overlayMatrix = new Matrix();
		_stride = 0;
		_xOffset = _yOffset = 0;
		_mapScroll = false;
		_map = null;
		_oldX = _oldY = 0;
		_res = mainActivity.getResources();
		_loadingScreen = BitmapFactory.decodeResource(_res, R.drawable.loadingscreen);
		_playerVector = new Vector2D(0,0);
		//TODO: Create cached lake bitmap to load and 
		//display when necessary
		//_cachedLake = Bitmap.createBitmap(1024, 1024, Config.ARGB_8888);
	}
	
	public void runMapFactorySetup(Display display, Player player)
	{
		_player = player;
		_display = display;
		_viewWidth = _display.getWidth();//scrWidth;
		_viewHeight = _display.getHeight();//scrHeight;
		
	}
	
	private void assembleMap()
	{
		DisplayMetrics dm = new DisplayMetrics();
		_display.getMetrics(dm);

		Options opts = new Options();
		opts.inPreferredConfig = Config.RGB_565;
		grass = BitmapFactory.decodeResource(_res, R.drawable.grass_s_512, opts);
		water = BitmapFactory.decodeResource(_res, R.drawable.water_s_512, opts);

		kryt1 = BitmapFactory.decodeResource(_res, R.drawable.monster1topdown);
		kryt2 = BitmapFactory.decodeResource(_res, R.drawable.monster2topdown);
		kryt3 = BitmapFactory.decodeResource(_res, R.drawable.monster3topdown);
		kryt4 = BitmapFactory.decodeResource(_res, R.drawable.monster4topdown);
			
		kryt5 = BitmapFactory.decodeResource(_res, R.drawable.monster5topdown);
		kryt6 = BitmapFactory.decodeResource(_res, R.drawable.monster6topdown);
		kryt7 = BitmapFactory.decodeResource(_res, R.drawable.monster7topdown);
		kryt8 = BitmapFactory.decodeResource(_res, R.drawable.monster8topdown);
		kryt9 = BitmapFactory.decodeResource(_res, R.drawable.monster9topdown);
		kryt10 = BitmapFactory.decodeResource(_res, R.drawable.monster10topdown);
		
		tree1 = BitmapFactory.decodeResource(_res, R.drawable.forest_01);
		tree2 = BitmapFactory.decodeResource(_res, R.drawable.forest_02);
		tree3 = BitmapFactory.decodeResource(_res, R.drawable.forest_03);
		tree4 = BitmapFactory.decodeResource(_res, R.drawable.forest_04);
		
		int rows = _map.length;
		int cols = _map[0].length;
		
		_mapTiles = new Tile[rows][cols];
		
		for (int i = 0; i < _map.length; i++) 
		{
			char[] row = _map[i];
            for (int j = 0; j < row.length; j++) 
            {
            	Tile tile = new Tile(new Vector2D(i, j), true);            	
            	_mapTiles[i][j] = tile;
            }
        }
		_stride = grass.getWidth();
		
		generateOverlays();
		
		_xOffset = (_stride * MAP_WIDTH) / 2.0;
		_yOffset = (_stride * MAP_HEIGHT) / 2.0;
		
		_currentX = (int) Math.floor(_xOffset + ((_stride * MAP_WIDTH) / 2.0)) / _stride;
		_currentY = (int) Math.floor(_yOffset + ((_stride * MAP_HEIGHT) / 2.0))/ _stride;
		_currentX -= 9;
		_currentY -= 9;

		_isMapReady = true;
		screen = new Rect(-_viewWidth - _stride, -_viewHeight  - _stride, 
				   (_viewWidth + _stride),( _viewHeight + _stride));
	}
	
	private void generateOverlays()
	{	
		rand = new Random(System.currentTimeMillis());

		//***********************************
		// TAKE THIS OUT LATER!!!!!!!!!!!!!!!
		_map[7][7] = 'b';
		//***********************************
		//***********************************
		
		
		for(int i = 0; i < 10; i++)
		{
			int z = rand.nextInt(4);
			if(z == 0)
				_map[rand.nextInt(19)][rand.nextInt(19)] = 'w';
			else if (z == 1)
				_map[rand.nextInt(19)][rand.nextInt(19)] = 'y';
			else if (z == 2)
				_map[rand.nextInt(19)][rand.nextInt(19)] = 'x';
			else
				_map[rand.nextInt(19)][rand.nextInt(19)] = 'z';
		}
		
		//Assign some Kryts!
		_krytTileList.clear();
		_db.openDataBase();
		_db.emptyEncounterTable();
		for (int krytCount = 0; krytCount < 50; krytCount++)
		{
			int kryt = rand.nextInt(10) + 1; // Kryts: 5, 6,7,8
			
			
			
			/*Insert encounter */
			ContentValues values = new ContentValues();
			values.put(DatabaseFields.ENCOUNTERKRYTS.KRYT_ID, kryt);
			values.put(DatabaseFields.ENCOUNTERKRYTS.LEVEL, rand.nextInt(5) + 1);
			values.put(DatabaseFields.ENCOUNTERKRYTS.CURRENT_HEATH, 100.0f);
			values.put(DatabaseFields.ENCOUNTERKRYTS.CONSTITUTION, rand.nextInt(10) + 10 + 1);
			values.put(DatabaseFields.ENCOUNTERKRYTS.STRENGTH, rand.nextInt(20) + 10 +1);
			values.put(DatabaseFields.ENCOUNTERKRYTS.ENDURANCE, rand.nextInt(10) + 10 + 1);
			values.put(DatabaseFields.ENCOUNTERKRYTS.AGILITY, rand.nextInt(10) + 10 + 1);
			values.put(DatabaseFields.ENCOUNTERKRYTS.INTELLIGENCE, rand.nextInt(10) + 10 + 1);
			values.put(DatabaseFields.ENCOUNTERKRYTS.WISDOM, rand.nextInt(10) + 10 + 1 );
			values.put(DatabaseFields.ENCOUNTERKRYTS.CHARISMA, rand.nextInt(10) + 10 + 1);
			
			int encounterId = (int) _db.insertEncounter(values);
			if(encounterId != -1) {
				
				/* position them on the map */
				int i = rand.nextInt(19); //0-18
				int j = rand.nextInt(19); //0-18
				int x = rand.nextInt(2); //0-1
				int y = rand.nextInt(2); //0-1
				
				addKryt(new Vector2D(i,j),new Vector2D(x,y),encounterId,kryt);
			}
		}
		_db.close();
		
		//Canvas cLakes = new Canvas(_cachedLake);
		/*int r, g, b;
		r = g = b = 0;
		for(int i = 0; i < MAP_WIDTH; i++)
		{
			for(int j = 0; j < MAP_HEIGHT; j++)
 			{
				if(_map[i][j] == 'g' )
				{
					//Generate SHRUBBERY!
	
					Vector2D[] vArray = new Vector2D[] { new Vector2D(0, 0),new Vector2D(1, 0),new Vector2D(0, 1),new Vector2D(1, 1) };
					
					int random = rand.nextInt(4) + 1; //generates 1 through 4
					int vArrayIndex = random;
					
					
					OverlayTile[] overlayTiles = new OverlayTile[random];
					
					for( int count = 0; count < random; count++) {
						int tree = rand.nextInt(14); //tree from 0 to 13
						overlayTiles[count] = new OverlayTile(new Vector2D(i,j), vArray[vArrayIndex%random ], OverlayTile.TREE_TILE, "" + tree, false);
						vArrayIndex++;
					}
					for( int count = 0; count < random; count++) {
						addOverlayTile(overlayTiles[count]);
					}
					
				}
			}
		}*/
		
		/*_overlayList = new ArrayList<Overlay>();

		for(int i = 0; i < 30; i++) // < b
		{
			_overlayList.add(new Overlay(256, 256, rand.nextInt(MAP_WIDTH * _stride), 
							 			 rand.nextInt(MAP_HEIGHT * _stride), Overlay.TREE));
		}
		_overlayList.add(new Overlay(256, 256, 
				(MAP_WIDTH * _stride) / 2, 
				(MAP_HEIGHT * _stride) / 2, 
				Overlay.TREE));
//		_overlayList.add(new Overlay(1024, 1024, 0, 0, Overlay.LAKE));
		//_overlayList.add(new Overlay(1024, 1024, -100, -100, Overlay.LAKE));*/
	}
	
	private void addOverlayTile(OverlayTile ot)
	{
		try
		{
			Tile parent = _mapTiles[ot.worldCoords().getX()][ot.worldCoords().getY()];
			parent.addChild(ot);

			int transWorldX = ot.worldCoords().getX() + ot.localCoords().getX();
			int transWorldY = ot.worldCoords().getY() + ot.localCoords().getY();

			//_layerWater[transWorldX][transWorldY] = ot;
			
			
			parent.orderChildren();
			//_freeMapTiles.remove(new Vector2D(transWorldX, transWorldY));
		}
		catch (Exception ex){ }
	}
	Rect tile = new Rect();
	Rect screen;//
	ArrayList<OverlayTile> children = null;	
	ArrayList<Tile> tileList = new ArrayList<Tile>();
	
	public void drawMap(Canvas canvas)
	{
		if(!_isMapReady)
		{
			canvas.drawColor(Color.BLACK);
			
			//canvas.drawText("Loading...", 10, 10, _pLoading);
			canvas.drawBitmap(_loadingScreen, 0, 0, null);
			return;
		}
		_loadingScreen.recycle();
		// will loop through entire map, but only draw
		// tiles that are actually visible
		
		Tile t = null;
		float xPos, yPos;
		xPos = yPos = 0;
		//int collisions = 0;
		for(int i = _currentX - 2; i < _currentX + 2; i++)
		{
			for(int j = _currentY - 2; j < _currentY + 3; j++)
			{
				if(i < 0) i = 0;
				if(j < 0) j = 0;
				
				t = _mapTiles[i][j]; //* 0.99f
				xPos = (i * _stride) - (float)_xOffset;
				yPos = (j * _stride) - (float)_yOffset;
				
				tile.left = (int)xPos;
				tile.top = (int)yPos;
				tile.right = (int)xPos + _stride;
				tile.bottom =  (int)yPos + _stride;
				
				if(Rect.intersects(tile, screen))
				{
					//draw the grass, then draw all children		
					if(_map[i][j] == 'g' || _map[i][j] == 'r')
						canvas.drawBitmap(grass, xPos, yPos, null);
					else if (_map[i][j] == 'w')
					{
						canvas.drawBitmap(tree1,xPos, yPos, null);
					}
					else if (_map[i][j] == 'x')
						canvas.drawBitmap(tree2, xPos, yPos, null);
					else if (_map[i][j] == 'y')
						canvas.drawBitmap(tree3, xPos, yPos, null);
					else if (_map[i][j] == 'z')
						canvas.drawBitmap(tree4, xPos, yPos, null);
					else
						canvas.drawBitmap(water, xPos, yPos, null);	
		
					children = t.getChildren();
					
					if(t.NumChildren() == 0)
						continue;
				
					for(int c = 0; c < children.size(); c++)
					{
						OverlayTile ot = children.get(c);
						if(ot == null)
							continue;
						
						// position of overlay tile = position of main tile + (0, 0) | (256, 0) | (0, 256) |256, 256)
						// we can get this by multiplying the local X and Y coordinates of each child tile with the
						// overlaytile width (256)
						float subXPos = xPos + (float)ot.localCoords().getX() * 256;
						float subYPos = yPos + (float)ot.localCoords().getY() * 256;
						_overlayMatrix.setTranslate(subXPos, subYPos);
						
						//draw enemy kryts
						if(ot.TileType() == OverlayTile.KRYT_TILE) 
						{
							drawEnemyKryts(canvas, ot, subXPos, subYPos);
						}
					}
				}
			}	
		}
		_player.getCurrentCreature().getSprite().draw(canvas);
		
	}
	
	
	private void drawEnemyKryts(Canvas canvas, OverlayTile ot, float subXPos, float subYPos)
	{
		if(ot.OverlayID().equals("5"))
		{
			canvas.drawBitmap(kryt5, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("6"))
		{
			canvas.drawBitmap(kryt6, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("7"))
		{
			canvas.drawBitmap(kryt7, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("8"))
		{
			canvas.drawBitmap(kryt8, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("1"))
		{
			canvas.drawBitmap(kryt1, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("2"))
		{
			canvas.drawBitmap(kryt2, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("3"))
		{
			canvas.drawBitmap(kryt3, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("4"))
		{
			canvas.drawBitmap(kryt4, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("9"))
		{
			canvas.drawBitmap(kryt9, subXPos, subYPos,null);
		}
		else if(ot.OverlayID().equals("10"))
		{
			canvas.drawBitmap(kryt10, subXPos, subYPos,null);
		}
	}

	public void addKryt(Vector2D worldCoord, Vector2D localCoord, int encounterId, int krytId)
	{
		OverlayTile krytTile = new OverlayTile(worldCoord,localCoord,OverlayTile.KRYT_TILE,"" + krytId, false);
		krytTile.setKrytEncounterId(encounterId);
		_krytTileList.add(krytTile);
		addOverlayTile(krytTile);
	}
	
	public void setMapScroll(boolean isScrolling)
	{
		_mapScroll = isScrolling;
	}
	
	Iterator<OverlayTile> _itKrytTileOverlays;
	OverlayTile t;
	public void updateMapPosition(int moveSpeed, double direction)
	{
		//Vector2D currentCritPos = _player.getCurrentCreature().getSprite().getPosition();

		if(!_mapScroll)
			return;
		
		//moveSpeed = 10;
		
		_currentX = (int) Math.floor(_xOffset + ((_stride * MAP_WIDTH) / 2.0)) / _stride;
		_currentY = (int) Math.floor(_yOffset + ((_stride * MAP_HEIGHT) / 2.0))/ _stride;
		_currentX -= 9;
		_currentY -= 9;
		//Log.d("KrytLoc", "Kryt is in tile: <" + _currentX + ", " + _currentY + ">.");		

		
		int xComponent = (int)(moveSpeed * Math.cos(direction));
		int yComponent = -(int)(moveSpeed * Math.sin(direction));
		
		//checks bounds on map
		//i.e. if we are right of 0, and moving left, or left of the right edge, and moving left
		
		if(_xOffset > 0 && xComponent < 0) 
			_xOffset += xComponent;
		if(_xOffset < (MAP_WIDTH * _stride * 0.98) - _viewWidth - 5 && xComponent > 0) //should be 0, but 5 as a precaution
			_xOffset += xComponent;
		if(_yOffset > 0 && yComponent > 0)
			_yOffset -= yComponent;
		if(_yOffset < (MAP_HEIGHT * _stride * 0.98) - (_viewHeight / 2.0) && yComponent < 0) // same as above
			_yOffset -= yComponent;
		
		if(!mainActivity.inStealthMode)
		{
			if(!mainActivity.inBattle) {
				//Check collisions via distances!
				// = new Vector2D((int)_xOffset + _viewWidth/2,(int)_yOffset + _viewHeight/2); //this isn't quite right
				 _playerVector.setX((int)_xOffset + _viewWidth/2);
				 _playerVector.setY((int)_yOffset + _viewHeight/2);
				 
				_itKrytTileOverlays = _krytTileList.iterator();
				t = _krytTileList.get(0);
				while(_itKrytTileOverlays.hasNext())
				{
					if(_playerVector.distanceBetween(t.getGameX(),t.getGameY()) < 200 && !t.isPassable())
					{
						t.setPassable(true);
						mainActivity.inBattle = true;
						mainActivity.runBattleWithEncounterId(t.getKrytEncounterId());
						_itKrytTileOverlays.remove();
						int x = t.worldCoords().getX();
						int y = t.worldCoords().getY();
						_mapTiles[x][y].killChild(t);
						break;						
					}
					t = _itKrytTileOverlays.next();
				}
			}	
		}
	}
	
	public void setOldX(int oldX)
	{
		_oldX = oldX;
	}
	
	public void setOldY(int oldY)
	{
		_oldY = oldY;
	}
	public Vector2D getOldCoords()
	{
		return new Vector2D(_oldX, _oldY);
	}
	
	public void update()
	{
		
	}
	
	public void setMap(char[][] map)
	{
		_map = map;		
		assembleMap();
	}
	
	public boolean isMapReady()
	{
		return _isMapReady;
	}
	public void stop()
	{
		//use join() instead of stop()...stop is unsafe.
   	 	//join() will block the currently executing thread
   	 	//until the receiver finishes execution and dies.
		boolean retry = true;
		 mapConn.setRunning(false);
		 while (retry) 
		 {
		     try 
		     {
		         mapConn.join();	
		         retry = false;
		     } 
		     catch (InterruptedException e) { }
		 }
		 
		 
	}
	
}
