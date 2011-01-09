package com.assembla.gpsmonsters;

import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.*;
import com.assembla.gpsmonsters.gamepanels.*;
import com.assembla.gpsmonsters.tileengine.MapFactory;
import com.assembla.gpsmonsters.travelpoints.TravelpointsModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Debug;

public class GPSMonsters extends GameActivity {
    /** Called when the activity is first created. */
	private MainPanel _mainPanel;
	private MapFactory	_mapFactory;
	private PowerManager pm;
	private PowerManager.WakeLock wl;
	private Button travelButton;
	private TextView pointsText;
	private TravelpointsModule _travelpointsModule;

	
    @Override
	 public void onCreate(Bundle savedInstanceState) {
    	this.activityStartsStopsGameManager = true; //Only set for the Main Application Activity
    	pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    	//Debug.startMethodTracing("gpsmonstertrace");
		super.onCreate(savedInstanceState);
		
	}
    
    @Override 
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
      super.onActivityResult(requestCode, resultCode, data); 
      switch(requestCode) { 
        case (REQUESTKRYTSELECT) : { 
        	if (resultCode == Activity.RESULT_OK) { 
        		int krytId = data.getIntExtra("selectedId",-1); // -1 = default value
	        	if (krytId != -1) {
	        		_mainPanel.getPlayer().swapKryt(krytId);
	         	}
	        	if(krytWasDeleted)
	        	{
	        		krytWasDeleted = false;
	        	}
          	} else {
          		// They went back, which is normally not a problem unless their is no valid creature on the map if it was just deleted, and we havn't swaped a kryt
          		if(krytWasDeleted)
          		{
          			this.runKrytSelect(null);
          		}
          	}
        } 
         break;
         case (LEAVINGBATTLE) : 
         {
        	 inBattle = false;
        	 if(resultCode == Activity.RESULT_OK)
        	 {
        		 String outcome = data.getStringExtra("outcome");
        		 if(outcome.equals("lost"))
        		 {
        		/*	 //Player lost the battle!
        			DatabaseModule db =  _gameManager.getDatabaseModule();
        			db.openDataBase();
        			db.deletePlayerSelectedKryt();
        			db.close();*/
        			//
        			krytWasDeleted = true;
        			this.runKrytSelect(null);
        			
        		 }
        	 } else {
        		// Player hit back button
        		 if(DebugSettings.DEBUG && DebugSettings.ALWAYREMOVEPLAYERKRYT)
        		 {
        			 //Player lost the battle!
         			DatabaseModule db =  _gameManager.getDatabaseModule();
         			db.openDataBase();
         			db.deletePlayerSelectedKryt();
         			db.close();
         			krytWasDeleted = true;
         			this.runKrytSelect(null);
        		 }
        		 
        	 }
        	
        	
         }

        
      } 
    }
    private static boolean _loadedOnce = false;
    private static Player _player;
    @Override
	public void onLoadGameManager()
    {
    	//_mainPanel is not instantiated yet for the player constructor
    	if(!_loadedOnce)
    	{
        	_mapFactory = new MapFactory(_gameManager);

        	
        	_loadedOnce = true;
    	
    	}
    	
    	_mainPanel = new MainPanel(this,_gameManager);
    	_player = new Player(_gameManager, _mainPanel);
    	_mapFactory.runMapFactorySetup(_mainPanel.getDisplay(), _player);
    	_gameManager.setPlayer(_player);
    	_mainPanel.setPlayer(_player);
    	_mainPanel.setMapFactory(_mapFactory);
    	_gameManager.addGamePanel(_mainPanel);
    	
    	setContentView(_mainPanel);
    	
    	 //This adds the buttons and sliding drawer to the screen
    	 View buttonsView = getLayoutInflater().inflate(R.layout.buttonarrayandslidingdrawer, null, false);
         ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
         addContentView(buttonsView,layoutParams);
		 travelButton = (Button)findViewById(R.id.travelButton);
		 if(!_gameManager.getLocationModule().getTraveling())
			   travelButton.setBackgroundResource(R.drawable.travelbutton);
		   else
			   travelButton.setBackgroundResource(R.drawable.travelbuttondown);
		 _travelpointsModule = _gameManager.getTravelpointsModule();
		 pointsText = (TextView)findViewById(R.id.travelPointsText);
		 String points ="Travel Points:\n" + _travelpointsModule.getNewTravelPoints();
		 if(points.length()>30)
		 points.substring(0, 30);
		 pointsText.setText(points);
    }
    @Override
    public void onStart()
    {
    	 
    	 wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "GPSMonsters WAKELOCK ENABLE");
    	 try {
    		 wl.acquire();
    	 } catch (Exception e)
    	 {
    		 Log.d("Error with Power Lock:",e.getMessage());
    	 }
    	 super.onStart();
    }
    @Override
    public void onStop()
    {
    //	 Debug.stopMethodTracing();
    	wl.release();
    	super.onStop();
    }
    
    @Override
    public void switchTravelButton(boolean traveling)
    {
    	this.onLoadGameManager();
    }
    
   @Override
   public void onDestroy()
   {
	   _gameManager.stopDrawingThread();
	   _gameManager.stopEncounterThread();
	   super.onDestroy();
	  
   }
   
}