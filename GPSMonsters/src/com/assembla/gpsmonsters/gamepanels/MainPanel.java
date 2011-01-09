package com.assembla.gpsmonsters.gamepanels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.assembla.gpsmonsters.Player;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;
import com.assembla.gpsmonsters.engine.Vector2D;
import com.assembla.gpsmonsters.sound.SoundList;
import com.assembla.gpsmonsters.sound.SoundManager;
import com.assembla.gpsmonsters.tileengine.*;
import com.assembla.gpsmonsters.travelpoints.TravelPointComponent;
import com.assembla.gpsmonsters.weather.WeatherModule;

public class MainPanel extends GamePanel {
	
	boolean soundPlaying = false;
	MapFactory _mapFactory;
	Bitmap map;
	boolean hasMap = false;
	Player _player;
	Paint _pLoading;
	private double _oldAngle;
	//private TravelPointComponent _tpc;
	Vector2D _mapScrollDirection;
	double _mapScrollRotation;
	private int moveSpeedAdjustment = 0;
	SoundManager _soundManager;
	float leftX,rightX,topY,bottomY;
	int midX,midY;
	
	Bitmap _snowOverlay;
	
	private Vector2D _axisVector;
	
	public MainPanel(Context context, GameManagerInterface gameManager) {
		super(context, gameManager);
		//do loading stuff here? Or at least pass in a bundle loaded from the database
		_soundManager = gameManager.getSoundManager();
		_mapScrollDirection = new Vector2D(0,0);
		//_player = new Player(gameManager, this);
		//gameManager.setPlayer(_player);
		//_mapFactory = new MapFactory(gameManager, getResources(), getDisplay(), _player);	
		//_tpc = new TravelPointComponent(this, gameManager.getTravelpointsModule());
		//this.addGamePanelCompontent(_tpc);
		leftX = (float) (_viewWidth*(.25));
		rightX = (float) (_viewWidth*(.75));
		topY = (float) (_viewHeight*(.75));
		bottomY = (float) (_viewHeight*(.25));
		midX = _viewWidth/2;
		midY = _viewHeight/2;
		_snowOverlay = BitmapFactory.decodeResource(getResources(), R.drawable.snowoverlay);
		_axisVector = new Vector2D(0,0);
	}
	public void setPlayer(Player p)
	{
		_player = p;
	}
	public void setMapFactory(MapFactory mf)
	{
		_mapFactory = mf;
		_mapFactory.setMapScroll(false);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
		try
		{
		if(e.getAction() != MotionEvent.ACTION_UP)
		{
			//_mapFactory.setOldX((int)e.getX());
			//_mapFactory.setOldY((int)e.getY());
			_player.getCurrentCreature().getSprite().setShouldPlayAnimation(true);
			_mapFactory.setMapScroll(true);
			_mapScrollDirection.setX((int)e.getX() - midX);
			_mapScrollDirection.setY(-((int)e.getY() - midY));
			moveSpeedAdjustment = 0;
			/*if((e.getX() < rightX && e.getX() > leftX) || (e.getY() < topY && e.getY() > bottomY)) {
				moveSpeedAdjustment = -2;
			}*/
			if((e.getX() > rightX || e.getX() < leftX) || (e.getY() > topY || e.getY() < bottomY)) {
				moveSpeedAdjustment = 3;
			}
			_axisVector.setX(1);
			_axisVector.setY(0);
			double angle = -_axisVector.getAngleBetween(_mapScrollDirection);
			if(Double.isNaN(angle))
			{
				angle = _oldAngle;
			}
			_oldAngle = angle;
			_player.getCurrentCreature().getSprite().setRotation((float)angle);
		}
		else
		{
			_player.getCurrentCreature().getSprite().setShouldPlayAnimation(false);
			_mapFactory.setMapScroll(false);
		}
		}
		catch (Exception ex) { _mapFactory.setMapScroll(false); Log.d("MME", "Map Move Error: " + ex.getMessage()); }
		return true;
	}
	
	@Override
	public synchronized void draw(Canvas canvas)
	{
	
		canvas.drawARGB(255, 40, 120, 71);
		
		_mapFactory.drawMap(canvas);
		//_player.getCurrentCreature().getSprite().setScale(0.5f);
		//_player.getCurrentCreature().getSprite().draw(canvas);
		//draw other stuff here
		
		//Weather overlays
		if(_gameManager.getCurrentWeather() == WeatherModule.SNOW) {
			canvas.drawBitmap(_snowOverlay, 0, 0, null);
		}
		if(_gameManager.getCurrentWeather() == WeatherModule.RAIN)
		{
			//TODO: Draw rain drop overlay
		}
		
		
		super.draw(canvas);
	}
	
	@Override
	public synchronized  void update()
	{
		if(soundPlaying == false)
		{
		    //_soundManager.playSong();
		    //_soundManager.playSound(SoundList.AQUA_BEAR_ROAR);
		    soundPlaying = true;
		}
		
		_mapFactory.updateMapPosition(_player.getCurrentCreature().getWalkSpeed() + moveSpeedAdjustment, _oldAngle);
		_player.getCurrentCreature().getSprite().update(System.currentTimeMillis());
		
		super.update();
	}
	@Override
	protected void cleanup()
	{
		super.cleanup();
	
		if(_mapFactory != null)
		{
			_mapFactory.stop();
		}
		_snowOverlay.recycle();
	}
	
	public Player getPlayer()
	{
		return _player;
	}
	
	

}
