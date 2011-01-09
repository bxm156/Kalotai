package com.assembla.gpsmonsters.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.assembla.gpsmonsters.GPSMonsters;
import com.assembla.gpsmonsters.Player;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.battle.BattleActivity;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.location.LocationModule;
import com.assembla.gpsmonsters.sound.SoundManager;
import com.assembla.gpsmonsters.weather.WeatherModule;
import com.assembla.gpsmonsters.travelpoints.TravelpointsModule;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class GameManager extends Service {
	private GamePanel[] _gamePanels = new GamePanel[4];
	private int numGamePanels = 0;

	private volatile DrawingThread _drawingThread;
	private volatile EncounterThread _encounterThread;
	private GameManagerBinder _gameManagerBinder = new GameManagerBinder();
	
	//Modules
	private SoundManager _soundManager;
	private LocationModule _locationModule;
	private WeatherModule _weatherModule;
	private DatabaseModule _databaseModule;
	private TravelpointsModule _travelpointsModule;
	private Player _player;
	private Random _rand;
	
	public int _currentWeather;
	
	public static GameActivity _mainActivity;
	private Handler _encounterHandler;
	
	public static void setMainActivity(GameActivity gameActivity)
    {
      _mainActivity = gameActivity;     
    }
	public static final int ECOUNTERNOTIFICATION_ID = 1;
	@Override
	public void onCreate()
	{
		_gameManagerBinder.setGameManager(this);
		//_gamePanels = new ArrayList<GamePanel>();
		
		_soundManager = new SoundManager(this);
		_locationModule = new LocationModule(this);
		_travelpointsModule = new TravelpointsModule(this);
		_databaseModule = new DatabaseModule(this);
		_weatherModule = new WeatherModule(this);
		_currentWeather = _weatherModule.getCondition();
	
		try
		{
			_databaseModule.createDataBase();
			
		} catch (IOException ioe) {
			throw new Error("Unable to create database!");
		}
		
	     _encounterHandler = new Handler()
	     {
	    	 @Override
	    	 public void handleMessage(Message msg){ 		 
	    		 if(msg.what == 1){
	    			doEncounter();
	    		 }
	    	 }
	     };
		
		startDrawingThread();
		startEncounterThread(_encounterHandler);
		
		_rand = new Random(System.currentTimeMillis());
	
		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		stopDrawingThread();
		stopEncounterThread();		 
		//_gamePanels = null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
		//Start Thread

	    return START_NOT_STICKY;
	}
	public void addGamePanel(GamePanel panel)
	{
		_gamePanels[numGamePanels] = panel;
		numGamePanels++;
		if(!_drawingThread.isAlive())
		{
			_drawingThread = new DrawingThread(this);
			_drawingThread.setRunning(true);
			_drawingThread.start();
		}
	}
	public void removeGamePanel(GamePanel gamePanel)
	{
		//what's the faster array for loop? says somewhere in the
		// android docs
		int size = _gamePanels.length; 
		for(int i = 0; i < numGamePanels; i++)
		{
			if(_gamePanels[i].equals(gamePanel))
			{
				for(int j = i + 1; j < size; j++)
				{
					_gamePanels[j - 1] = _gamePanels[j];
				}
				numGamePanels--;
				break;
			}
		}
		if(numGamePanels == 0)
		{
			stopDrawingThread();
		}
	}
	public GamePanel[] getGamePanels()
	{
		return _gamePanels;//_gamePanels.toArray(new GamePanel[_gamePanels.size()]);
		
	}
	public GamePanel getCurrentPanel()
	{
		if(numGamePanels < 1)
			return null;
		return _gamePanels[numGamePanels - 1];
	}
	public int getNumGamePanels()
	{
		return numGamePanels;
	}
	public GameActivity getStartActivity()
	{
		return _mainActivity;
	}
	private void doEncounter()
	{
		//oast.makeText(this, "Encounter!", Toast.LENGTH_LONG).show();
		
		if(_mainActivity.inStealthMode || _drawingThread.isAlive()) {
			return;
		}
		
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		int icon = R.drawable.attack_water;
		CharSequence tickerText = "Kryt Encounter!";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		
		Context context = getApplicationContext();
		CharSequence contentTitle = "Kryt Encounter!";
		CharSequence contentText = "Tap to enter the battle!";
		Intent notificationIntent = new Intent(this, BattleActivity.class);
		
		_databaseModule.openDataBase();
		int i = _databaseModule.getRandomEncounterId();
		_databaseModule.close();
		notificationIntent.putExtra("enemyID", i);
		notificationIntent.putExtra("notificationEncounter",true);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
		

		mNotificationManager.notify(ECOUNTERNOTIFICATION_ID, notification);
		
	}
	public void stopDrawingThread()
	{
		//use join() instead of stop()...stop is unsafe.
   	 	//join() will block the currently executing thread
   	 	//until the receiver finishes execution and dies.
		if(_drawingThread == null)
			return;
		boolean retry = true;
		 _drawingThread.setRunning(false);
		 while (retry) 
		 {
		     try 
		     {
		         _drawingThread.join();	
		         if(!_drawingThread.isAlive())
		        	 retry = false;
		     } 
		     catch (InterruptedException e) { }
		 }
		 
		 
	}
	public void startDrawingThread()
	{
		if(_drawingThread == null)
		{
			_drawingThread = new DrawingThread(this);
		}
		if(!_drawingThread.isAlive())
		{
			_drawingThread.setRunning(true);
			_drawingThread.start();
		}
	}
	public void startEncounterThread(Handler handler)
	{
		if(_encounterThread == null)
		{
			_encounterThread = new EncounterThread(handler);
		}
		if(!_encounterThread.isAlive())
		{
			_encounterThread.setRunning(true);
			_encounterThread.start();
		}
	}
	public void stopEncounterThread()
	{
		if(_encounterThread == null || !_encounterThread.isAlive())
			return;
		boolean retry = true;

		 _encounterThread.setRunning(false);
		 while (retry) 
		 {
		     try 
		     {
		         _encounterThread.join();
		         if(!_encounterThread.isAlive())
		        	 retry = false;
		     } 
		     catch (InterruptedException e) { }
		 }
	}
	public SoundManager getSoundManager()
	{
		return _soundManager;
	}
	public LocationModule getLocationModule()
	{
		return _locationModule;
	}
	public WeatherModule getWeatherModule()
	{
		return _weatherModule;
	}
	public DatabaseModule getDatabaseModule()
	{
		return _databaseModule;
	}
	
	public TravelpointsModule getTravelpointsModule() 
	{
		return _travelpointsModule;
	}
	@Override
	public IBinder onBind(Intent intent) {
		return _gameManagerBinder;
	}
	public class GameManagerBinder extends Binder implements GameManagerInterface {
		private GameManager _gameManager;
		@Override
		public void setGameManager(GameManager gameManager)
		{
			_gameManager = gameManager;
		}
		@Override
		public GamePanel[] getGamePanels() {
			return _gameManager.getGamePanels();
		}

		@Override
		public Activity getStartActivity() {
			return _gameManager.getStartActivity();
		}

		@Override
		public void removeGamePanel(GamePanel gamePanel) {
			_gameManager.removeGamePanel(gamePanel);
			
		}

		@Override
		public void addGamePanel(GamePanel panel) {
			_gameManager.addGamePanel(panel);
			
		}
		@Override
		public SoundManager getSoundManager()
		{
			return _gameManager.getSoundManager();
		}
		@Override
		public LocationModule getLocationModule()
		{
			return _gameManager.getLocationModule();
		}
		
		@Override
		public TravelpointsModule getTravelpointsModule()
		{
			return _gameManager.getTravelpointsModule();
		}
		@Override
		public WeatherModule getWeatherModule()
		{
			return _gameManager.getWeatherModule();
		}
		@Override
		public DatabaseModule getDatabaseModule()
		{
			return _gameManager.getDatabaseModule();
		}
		@Override
		public void setPlayer(Player player)
		{
			_player = player;
		}
		
		@Override
		public Player getPlayer()
		{
			return _player;
		}
		@Override
		public void stopDrawingThread() {
			_gameManager.stopDrawingThread();
			
		}
		@Override
		public void stopEncounterThread() {
			_gameManager.stopEncounterThread();
			
		}
		@Override
		public void startEncounterThread(Handler handler) {
			_gameManager.startEncounterThread(handler);
			
		}
		@Override
		public int getCurrentWeather() {
			return _gameManager._currentWeather;
		}
	}
}
