package com.assembla.gpsmonsters.engine;

import com.assembla.gpsmonsters.Player;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.location.LocationModule;
import com.assembla.gpsmonsters.sound.SoundManager;
import com.assembla.gpsmonsters.travelpoints.TravelpointsModule;
import com.assembla.gpsmonsters.weather.WeatherModule;

import android.app.Activity;
import android.os.Handler;

public interface GameManagerInterface {
	public GamePanel[] getGamePanels();
	public Activity getStartActivity();
	public void removeGamePanel(GamePanel gamePanel);
	public void addGamePanel(GamePanel panel);
	public void setGameManager(GameManager gameManager);
	public SoundManager getSoundManager();
	public LocationModule getLocationModule();
	public TravelpointsModule getTravelpointsModule();
	public WeatherModule getWeatherModule();
	public DatabaseModule getDatabaseModule();
	public void setPlayer(Player player);
	public Player getPlayer();
	public void stopDrawingThread();
	public void stopEncounterThread();
	public void startEncounterThread(Handler handler);
	public int getCurrentWeather();

}
