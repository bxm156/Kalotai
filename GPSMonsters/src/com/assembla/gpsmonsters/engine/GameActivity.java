package com.assembla.gpsmonsters.engine;

import com.assembla.gpsmonsters.battle.BattleActivity;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.energy.EnergyPoolActivity;
import com.assembla.gpsmonsters.krytpedia.KrytpediaActivity;
import com.assembla.gpsmonsters.krytpedia.KrytpediaMainActivity;
import com.assembla.gpsmonsters.krytselect.KrytSelectActivity;
import com.assembla.gpsmonsters.storyboard.StoryBoardActivity;
import com.assembla.gpsmonsters.tokryt.TokrytActivity;
import com.assembla.gpsmonsters.travelpoints.TravelActivity;
import com.assembla.gpsmonsters.R;

//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class GameActivity extends Activity {
	/** Called when the activity is first created. */

	protected GameManagerInterface _gameManager;
	protected GameManagerConnector _connection;
	protected boolean activityStartsStopsGameManager = false;
	
	public boolean inBattle = false;
	public boolean inStealthMode = false;
	protected boolean krytWasDeleted = false;
	
	public static final int REQUESTKRYTSELECT = 1;
	public static final int LEAVINGBATTLE = 2;
	public static final int INSTANTBATTLE = 3;
	
	private Vibrator _vibe;
	
	private static final int VIBRATE_TIME = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (activityStartsStopsGameManager) {
			GameManager.setMainActivity(this);
			startService(new Intent(this, GameManager.class));
		}
		_connection = new GameManagerConnector(this);
		bindService(new Intent(this, GameManager.class), _connection,
				Context.BIND_AUTO_CREATE);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		_vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}

	public void setGameManagerInterface(GameManagerInterface gameManager) {
		_gameManager = gameManager;
	}

	public void onLoadGameManager() {
		// Override in Subclass
	}

	public void switchTravelButton(boolean traveling)
	{
		//Override in Subclass
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.unbindService(_connection);
		if (activityStartsStopsGameManager) {
			this.stopService(new Intent(this, GameManager.class));
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		_connection = new GameManagerConnector(this);
		bindService(new Intent(this, GameManager.class), _connection,
				Context.BIND_AUTO_CREATE);

	}

	public void toggleSneakMode(View v) {
		_vibe.vibrate(100);
		if(this.inStealthMode) {
			this.inStealthMode = false;
			Toast.makeText(this, "Stealth Mode Disabled", Toast.LENGTH_LONG).show();
		} else {
			this.inStealthMode = true;
			Toast.makeText(this, "Stealth Mode Enabled", Toast.LENGTH_LONG).show();
		}

	}

	public void runKrytpediaMain(View v) {
		_vibe.vibrate(VIBRATE_TIME);
		this.startActivity(new Intent(this, KrytpediaMainActivity.class));
	}
	
	public void loadTravelMap(View v) {
		_vibe.vibrate(VIBRATE_TIME);
		if(_gameManager.getLocationModule().getTraveling())
		{
			_gameManager.getLocationModule().setTraveling(false);
			_gameManager.getLocationModule()._needsNewMap=true;
		}
		else
		{
			if(_gameManager.getTravelpointsModule().getTravelPoints() > _gameManager.getTravelpointsModule().getUsePoints())
			{
				this.startActivity(new Intent(this, TravelActivity.class));
			}
				else
			{
					AlertDialog.Builder dialog = new AlertDialog.Builder(this);
					  dialog.setTitle("Insufficient Points");
					  dialog.setMessage("Cannot travel at this time.  You need 200 points.");
					  dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() 
				  	  {
							@Override
							public void onClick(DialogInterface dialog, int button)
							{
							}
				  	});
				  	
					  dialog.show();
			}
		}
	}
	

	public void runBattle(View v) {
		DatabaseModule _databaseModule = _gameManager.getDatabaseModule();
		_databaseModule.openDataBase();
		int i = _databaseModule.getRandomEncounterId();
		_databaseModule.close();
		_vibe.vibrate(VIBRATE_TIME);
		Intent intent = new Intent(this, BattleActivity.class);
		intent.putExtra("enemyID", i);
		intent.putExtra("instant",true);
		this.startActivityForResult(intent,INSTANTBATTLE);
	}
	public void runBattleWithEncounterId(int encounter) {
		Intent intent = new Intent(this, BattleActivity.class);
		intent.putExtra("enemyID", encounter);
		this.startActivityForResult(intent,LEAVINGBATTLE);
	}  
	public void runKrytSelect(View v) {
		_vibe.vibrate(VIBRATE_TIME);
		this.startActivityForResult(new Intent(this, KrytSelectActivity.class),
				REQUESTKRYTSELECT);
	}

	public void runEnergy(View v) {
		_vibe.vibrate(VIBRATE_TIME);
		this.startActivity(new Intent(this, EnergyPoolActivity.class));

	}

	public void runTutorial()
	{
		_vibe.vibrate(VIBRATE_TIME);
		this.startActivity(new Intent(this, StoryBoardActivity.class));
	}
	
	public void runKrytpedia(int _krytId) {
		_vibe.vibrate(VIBRATE_TIME);
		Intent i = new Intent(getApplicationContext(), KrytpediaActivity.class);
		i.putExtra("krytId", _krytId); // _krytId starts at 1, points to _id in
										// database
		startActivityForResult(i, 0);
	}
	
	public void runSelectedKryt(int _krytId) {
		_vibe.vibrate(VIBRATE_TIME);
		Intent i = new Intent(getApplicationContext(), TokrytActivity.class);
		i.putExtra("krytId", _krytId); // _krytId starts at 1, points to _id in
										// database
		startActivityForResult(i, 0);
	}
}