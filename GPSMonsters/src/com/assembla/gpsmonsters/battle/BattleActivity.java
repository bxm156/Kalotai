package com.assembla.gpsmonsters.battle;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.assembla.gpsmonsters.Creature;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.database.DatabaseFields;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.energy.EnergyConnection;
import com.assembla.gpsmonsters.energy.EnergyTank;
import com.assembla.gpsmonsters.engine.GameActivity;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.gamepanels.BattlePanel;
import com.assembla.gpsmonsters.particleengine.VectorF2D;

public class BattleActivity extends GameActivity implements OnGesturePerformedListener, OnGestureListener{
	
	private BattlePanel _gamePanel;
	private GestureLibrary mLibrary;
	public int MOVE, enemyID;
	public boolean playerturn = false, doOnce = true, energyUsed=true, start=false;
	public int sX = 0,sY = 0;
	public GestureOverlayView gestures;
	public RelativeLayout view;
	public ArrayList<VectorF2D> captureArray;
	private GameManagerInterface _gm;
	private EnergyConnection energyConnection;

	private Button healthEnergyButton;
	private Button attackEnergyButton;
	private Button defenseEnergyButton;
	private final int ENERGY_USE = 5;
	Handler handler;
	private PowerManager pm;
	private PowerManager.WakeLock wl;
	public boolean _isInstantBattle = false;
	Move temp;
	long time;
	
	private Toast _toast;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		_toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);	
		_toast.setGravity(48, 0, 0); //48 is TOP
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		Bundle myBundle = getIntent().getExtras();
		 _isInstantBattle = myBundle.getBoolean("instant",false);
		super.onCreate(savedInstanceState);
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
	public void onPause()
	{
		try {
			_gm.getSoundManager().stopSong();
		} catch (Exception e) {}
		super.onPause();
	}
	@Override
	public void onStop()
	{
	  	wl.release();
		try {
			_gm.getSoundManager().stopSong();
		} catch (Exception e) {}
		super.onStop();
	}
	
	@Override
	public void onLoadGameManager()
    {
		//Load gesture Library
		mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!mLibrary.load()) {
        	//finish();
			//Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
			_toast.setText("Failed!");
			_toast.show();
        }
		view = new RelativeLayout(this);
		energyConnection = new EnergyConnection(_gameManager);
		
		gestures = new GestureOverlayView(this);
    	gestures.setOrientation(GestureOverlayView.ORIENTATION_HORIZONTAL);
    	gestures.setEventsInterceptionEnabled(true);
    	gestures.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
    	gestures.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
    	gestures.setGestureStrokeWidth(4.0f);
    	gestures.setGestureColor(0x7F453552);
    	gestures.setUncertainGestureColor(0x3F453552);
    	
    	final Context con = this;
	     handler = new Handler()
	     {
	    	 @Override
	    	 public void handleMessage(Message msg){
	    		 int type = msg.getData().getInt("type");
	    		 
	    		 if(type == 1){
	    			 //Provide Toast
	    			 try{
	    				 _toast.setText( msg.getData().getString("toast"));
	    				 _toast.show();
	    			 } catch (Exception e)
	    			 {
	    				 Log.e("battle",e.getMessage());
	    				 e.printStackTrace();
	    			 }
	    		 }
	    		 else if(type == 2){
	    			 try{
	    				if(playerturn){
	    					gestures.setGestureColor(0x7F453552);
	    			    	gestures.setUncertainGestureColor(0x3F453552);
	    				}
	    				else{
	    					gestures.setGestureColor(0x00000000);
	    			    	gestures.setUncertainGestureColor(0x00000000);
	    				}
	    			 }
	    			 catch (Exception e)
	    			 {
	    				 Log.e("battle",e.getMessage());
	    				 e.printStackTrace();
	    			 }
	    		 }
	    		 else if(type == 3){
	    			if(playerturn)
	    			{
    					healthEnergyButton.setEnabled(true);
    					attackEnergyButton.setEnabled(true);
    					defenseEnergyButton.setEnabled(true);
	    					
	    				if(energyConnection.getEnergy(EnergyTank.HEALTH) < ENERGY_USE)
	    				{
	    					healthEnergyButton.setEnabled(false);
	    				}
	    				if(energyConnection.getEnergy(EnergyTank.ATTACK) < ENERGY_USE)
	    				{
	    					attackEnergyButton.setEnabled(false);
	    				}
	    				if(energyConnection.getEnergy(EnergyTank.DEFENSE) < ENERGY_USE)
	    				{
	    					defenseEnergyButton.setEnabled(false);
	    				}
	    			}
	    		 }
	    	 }
	     };
		
    	_gamePanel = new BattlePanel(this,_gameManager);
    	_gameManager.addGamePanel(_gamePanel);
    	_gm = _gameManager;
    	
    	//Magic happens here, sets up layout and everything
    	View buttonsView = getLayoutInflater().inflate(R.layout.battle, null, false);
    	buttonsView.setPadding(this.getWindowManager().getDefaultDisplay().getWidth()-150,0,0,0);
        
        BitmapFactory.Options opts = new BitmapFactory.Options();
		Bitmap healthEnergy = BitmapFactory.decodeResource(getResources(),
				R.drawable.energytankgreen0, opts);
		Bitmap attackEnergy = BitmapFactory.decodeResource(getResources(),
				R.drawable.energytankred0, opts);
		Bitmap defenseEnergy = BitmapFactory.decodeResource(getResources(),
				R.drawable.energytankblue0, opts);

		healthEnergy = _gamePanel.resize(healthEnergy,10);
		attackEnergy = _gamePanel.resize(attackEnergy,10);
		defenseEnergy = _gamePanel.resize(defenseEnergy,10);
    	
		
        view.setWillNotDraw(false);
        view.addView(_gamePanel);   
        view.addView(gestures);
        view.addView(buttonsView);
        
		setContentView(view);
		
		 healthEnergyButton = (Button)findViewById(R.id.battleHealthEnergy);
	     attackEnergyButton = (Button)findViewById(R.id.battleAttackEnergy);
	     defenseEnergyButton = (Button)findViewById(R.id.battleDefenseEnergy);
	     
	     
		
    }
	
	public Handler getHandler(){
		return handler;
	}
	@Override
    public void onNewIntent(Intent intent)
    {
        Bundle b = intent.getExtras();
            if (b != null) {
                 if(b.getBoolean("notificationEncounter", false)) {
                         //We have a notification encounter!
                         
                         finish();
                         int id = b.getInt("enemyID", 1);
                         this.runBattleWithEncounterId(id);
                        
                 } else if (b.getBoolean("instant",false)) {
                	 _isInstantBattle = true;
                 }
            }
    }
	
	//This is the onGesturePerformed method that is used when a gesture is actually performed
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		//More Magic
			if(playerturn){	
				ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
				ArrayList<Gesture> pre;
			    // We want at least one prediction
			    if (predictions.size() > 0) {
			        Prediction prediction = predictions.get(0);
			        // We want at least some confidence in the result
			        if (prediction.score > 1.0) {
			        	pre = mLibrary.getGestures(prediction.name);
			        	//Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
			        	temp = _gamePanel.getPlayer().hasMove(prediction.name);
			        	if(temp != null){
			        		double score = getScore(gesture, pre)+ (prediction.score);
			        		_gamePanel.setMove(temp,score);
			        		//Toast.makeText(this, temp.getName(), Toast.LENGTH_SHORT).show();
			        		playerTurn();
			        	}
			        }
			    }
			}
	}
	
	public void playerTurn(){
			
		healthEnergyButton.setBackgroundResource(R.drawable.energytankgreen0mini);
		attackEnergyButton.setBackgroundResource(R.drawable.energytankred0mini);
		defenseEnergyButton.setBackgroundResource(R.drawable.energytankblue0mini);
		
		playerturn = !playerturn;
		if(playerturn)
		{		
			gestures.addOnGesturePerformedListener(this);
		}
		else
			gestures.removeAllOnGesturePerformedListeners();
	}
	
	public void captureMode(int x, int y){
		//Should switch gesture so it draws an invisible line, but refuses to cooperate
		gestures.setGestureColor(0x00000000);
    	gestures.setUncertainGestureColor(0x00000000);
    	gestures.addOnGestureListener(this);
    	sX = x;
    	sY = y;
	}
	
	public int enemyId(){
		enemyID = getIntent().getIntExtra("enemyID",1);
		return enemyID;
	}
	
	
	public double getScore(Gesture gesture, ArrayList<Gesture> prediction){
		double score = 0;
		Bitmap g,p;
		g = gesture.toBitmap(50, 50,1, 0x7F453552);
		p = prediction.get(0).toBitmap(50,50,1,0x7F453552);
		
		for(int y=0;y<50;y++){
			for(int x=0;x<50;x++){
				if(g.getPixel(x,y) != p.getPixel(x, y))
					score = score + 1.0;
			}
		}
		score = (score/25);
		score = 100-score;
		return score;
	}
	
	public void populateCapture(ArrayList<VectorF2D> array){
		captureArray = array;
	}

	//These guys handle gesture activity while in capture mode
	//Used solely because it gives us direct access to the MotionEvents
	// from which we can pull position and time information.
	//Good idea would be to implement Rob's Stopwatch, track time from start to finish, maybe even display a timer
	// with target time, like a time trial sort of style.
	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
		//Toast.makeText(this, Float.toString(event.getRawX()), Toast.LENGTH_SHORT).show();
		if(start){
			if(!captureArray.isEmpty()){
				VectorF2D pos = new VectorF2D(event.getRawX(),event.getRawY());
				VectorF2D node = captureArray.get(0);
				float d = (float) Math.sqrt(Math.pow(pos.getX()-node.getX(),2)+ Math.pow(pos.getY()-node.getY(), 2));
				if(d <= 30){
					captureArray.remove(0);
					_gamePanel.updateCapture(captureArray);
				}
			}
			else{
				if(doOnce){
					long boo  = System.currentTimeMillis();
					if(boo<= time+2500){
						_gamePanel.end();
						doOnce = false;
						_gamePanel.setCapSuccess(true);
					}
					else{
						_gamePanel.end();
						doOnce = false;
						_gamePanel.setCapSuccess(false);
					}
				} else {
					//time delay then add kryt
					
				}
			}
		}
	}

	//Not sure when this one gets triggered, if we never figure it out probably just throw identical code from
	// onGestureEnded just so there's less chance of random glitches popping up
	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {	
	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		VectorF2D pos = new VectorF2D(event.getRawX(),event.getRawY());
		if(!start){
			if(pos.getX()>=sX && pos.getY()>=sY){
				start = true;
				_gamePanel.setCapStart(true);
				time = System.currentTimeMillis();
			}
		}
		else{
			VectorF2D node = captureArray.get(0);
			float d = (float) Math.sqrt(Math.pow(pos.getX()-node.getX(),2)+ Math.pow(pos.getY()-node.getY(), 2));
			if(d <= 40){
				captureArray.remove(0);
				_gamePanel.updateCapture(captureArray);
			}
		}
	}
	
	public void startCap(){
		time = System.currentTimeMillis();
	}
	
	public void healthEnergyForBattle(View v)
	{	
		_gamePanel.increaseHealth(ENERGY_USE);
		energyConnection.setEnergy(EnergyTank.HEALTH,(int) energyConnection.getEnergy(EnergyTank.HEALTH)-ENERGY_USE);
		healthEnergyButton.setBackgroundResource(R.drawable.plus);
		healthEnergyButton.setEnabled(false);
		attackEnergyButton.setEnabled(false);
		defenseEnergyButton.setEnabled(false);
    	Toast.makeText(this, "Health increased by " + ENERGY_USE , Toast.LENGTH_SHORT).show();
		
	}

	public void attackEnergyForBattle(View v)
	{		
		_gamePanel.increaseAttack(ENERGY_USE);
		energyConnection.setEnergy(EnergyTank.ATTACK,(int) energyConnection.getEnergy(EnergyTank.ATTACK)-ENERGY_USE);
		attackEnergyButton.setBackgroundResource(R.drawable.plus);
		healthEnergyButton.setEnabled(false);
		attackEnergyButton.setEnabled(false);
		defenseEnergyButton.setEnabled(false);
    	Toast.makeText(this, "Attack increased by " + ENERGY_USE , Toast.LENGTH_SHORT).show();


	}

	public void defenseEnergyForBattle(View v)
	{
		_gamePanel.increaseDefense(ENERGY_USE);
		energyConnection.setEnergy(EnergyTank.DEFENSE,(int) energyConnection.getEnergy(EnergyTank.DEFENSE)-ENERGY_USE);
		defenseEnergyButton.setBackgroundResource(R.drawable.plus);
		healthEnergyButton.setEnabled(false);
		attackEnergyButton.setEnabled(false);
		defenseEnergyButton.setEnabled(false);
    	Toast.makeText(this, "Defense increased by " + ENERGY_USE , Toast.LENGTH_SHORT).show();
	}
	public void deletePlayerKryt()
	{
		 //Player lost the battle!
		DatabaseModule db =  _gameManager.getDatabaseModule();
		db.openDataBase();
		db.deletePlayerSelectedKryt();
		db.close();
		//
	}
	public void addPlayerKryt(Creature c)
	{
		ContentValues content = new ContentValues();
		content.put(DatabaseFields.PLAYERKRYTS.KRYT_ID, c.getKrytId());
		content.put(DatabaseFields.PLAYERKRYTS.LEVEL, c.getLevel());
		content.put(DatabaseFields.PLAYERKRYTS.CURRENT_HEATH,100);
		content.put(DatabaseFields.PLAYERKRYTS.SELECTED,0);
		content.put(DatabaseFields.PLAYERKRYTS.EXPERIENCE,0);
		content.put(DatabaseFields.PLAYERKRYTS.CONSTITUTION,c.getConstitution());
		content.put(DatabaseFields.PLAYERKRYTS.STRENGTH, c.getStrength());
		content.put(DatabaseFields.PLAYERKRYTS.ENDURANCE,c.getEndurance());
		content.put(DatabaseFields.PLAYERKRYTS.AGILITY,c.getAgility());
		content.put(DatabaseFields.PLAYERKRYTS.INTELLIGENCE,c.getIntelligence());
		content.put(DatabaseFields.PLAYERKRYTS.WISDOM,c.getWisdom());
		content.put(DatabaseFields.PLAYERKRYTS.CHARISMA,c.getCharisma());
		DatabaseModule db =  _gameManager.getDatabaseModule();
		db.openDataBase();
		db.addPlayerKryt(content);
		db.close();
		Log.d("krytAdd","yes");
	}
	public void addSelectedPlayerKryt(Creature c)
	{
		ContentValues content = new ContentValues();
		content.put(DatabaseFields.PLAYERKRYTS.KRYT_ID, c.getKrytId());
		content.put(DatabaseFields.PLAYERKRYTS.LEVEL, c.getLevel());
		content.put(DatabaseFields.PLAYERKRYTS.CURRENT_HEATH,100);
		content.put(DatabaseFields.PLAYERKRYTS.SELECTED,1);
		content.put(DatabaseFields.PLAYERKRYTS.EXPERIENCE, c.getExperience());
		content.put(DatabaseFields.PLAYERKRYTS.CONSTITUTION,c.getConstitution());
		content.put(DatabaseFields.PLAYERKRYTS.STRENGTH, c.getStrength());
		content.put(DatabaseFields.PLAYERKRYTS.ENDURANCE,c.getEndurance());
		content.put(DatabaseFields.PLAYERKRYTS.AGILITY,c.getAgility());
		content.put(DatabaseFields.PLAYERKRYTS.INTELLIGENCE,c.getIntelligence());
		content.put(DatabaseFields.PLAYERKRYTS.WISDOM,c.getWisdom());
		content.put(DatabaseFields.PLAYERKRYTS.CHARISMA,c.getCharisma());
		DatabaseModule db =  _gameManager.getDatabaseModule();
		db.openDataBase();
		db.addPlayerKryt(content);
		db.close();
		Log.d("krytAdd","yes");
	}
	
}