package com.assembla.gpsmonsters.energy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.AnimatedFrameSprite2D;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;
import com.assembla.gpsmonsters.engine.Sprite2D;
import com.assembla.gpsmonsters.R;


public class EnergyPoolPanel extends GamePanel {
	
	//Tank constructs
	private EnergyTank _healthTank;
	private EnergyTank  _attackTank;
	private EnergyTank  _defenseTank;
	private EnergyTank _whiteTank;
	
	//Tank Sprites
	//TODO: Implement the sprites once we have images
	private Sprite2D _healthSprite;
	private Sprite2D _attackSprite;
	private Sprite2D _defenseSprite;
	private Sprite2D _miscSprite;
	
	private DatabaseModule _databaseModule;
	
	private int[] tankList;
	private int _currentTank = EnergyTank.HEALTH;
	

	
	public EnergyPoolPanel(Context context, GameManagerInterface gameManager) {
		super(context, gameManager);
		// TODO Auto-generated constructor stub
		_healthTank = new EnergyTank(EnergyTank.HEALTH);
		_attackTank = new EnergyTank(EnergyTank.ATTACK);
		_defenseTank = new EnergyTank(EnergyTank.DEFENSE);
		_whiteTank = new EnergyTank(EnergyTank.WHITE);
		

		
		//Get values from Database
		_databaseModule = _gameManager.getDatabaseModule();
		_databaseModule.openDataBase();
		_healthTank.setCurrentLevel(_databaseModule.getEnergy(EnergyTank.HEALTH));
		_attackTank.setCurrentLevel(_databaseModule.getEnergy(EnergyTank.ATTACK));
		_defenseTank.setCurrentLevel(_databaseModule.getEnergy(EnergyTank.DEFENSE));
		_whiteTank.setCurrentLevel(_databaseModule.getEnergy(EnergyTank.WHITE));
		_databaseModule.close();
		
		//_healthSprite = new AnimatedFrameSprite2D(_context.getResources(),R.drawable.energytankgreen0, _context, "energytankgreen",23, 43.47);
		_healthSprite = new Sprite2D(_context.getResources(),R.drawable.energytankgreen0);
		_healthSprite.getTransform().setTranslate(0, 0);
	//	_healthSprite.setShouldPlayAnimation(true);
		
		
		tankList = new int[] {
			EnergyTank.HEALTH,EnergyTank.ATTACK,EnergyTank.DEFENSE,EnergyTank.MISC
		};
		
	
	}
	public void showHealth()
	{
		if(_healthSprite == null) {
			///_healthSprite = new AnimatedFrameSprite2D(_context.getResources(),R.drawable.energytankgreen0, _context, "energytankgreen",23, 43.47);
			_healthSprite = new Sprite2D(_context.getResources(),R.drawable.energytankgreen0);
			_healthSprite.getTransform().setTranslate(0, 0);
			//_healthSprite.setShouldPlayAnimation(true);
		}
		TextView reading;
		reading = (TextView) _context.findViewById(R.id.TankReading);
		reading.setText("Health: " + _healthTank.getCurrentLevel() + " Joules");
		_currentTank = EnergyTank.HEALTH;
		
	}
	public void showAttack()
	{
		if(_attackSprite == null) {
			//_attackSprite = new AnimatedFrameSprite2D(_context.getResources(),R.drawable.energytankred0, _context, "energytankred",23, 43.47);
			_attackSprite = new Sprite2D(_context.getResources(),R.drawable.energytankred0);
			_attackSprite.getTransform().setTranslate(0, 0);
			//_attackSprite.setShouldPlayAnimation(true);
		}
		TextView reading;
		reading = (TextView) _context.findViewById(R.id.TankReading);
		reading.setText("Attack: " + _attackTank.getCurrentLevel() + " Joules");
		_currentTank = EnergyTank.ATTACK;
	}
	public void showDefense()
	{
		if(_defenseSprite == null)
		{
			//_defenseSprite = new AnimatedFrameSprite2D(_context.getResources(),R.drawable.energytankblue0, _context, "energytankblue",23, 43.47);
			_defenseSprite = new Sprite2D(_context.getResources(),R.drawable.energytankblue0);
			_defenseSprite.getTransform().setTranslate(0, 0);
			//_defenseSprite.setShouldPlayAnimation(true);
		}
		TextView reading;
		reading = (TextView) _context.findViewById(R.id.TankReading);
		reading.setText("Defense: " + _defenseTank.getCurrentLevel() + " Joules");
		_currentTank = EnergyTank.DEFENSE;
	}
	public void showMisc()
	{
		if(_miscSprite == null)
		{
			//_miscSprite = new AnimatedFrameSprite2D(_context.getResources(),R.drawable.energytankwhite0, _context, "energytankwhite",23, 43.47);
			_miscSprite = new Sprite2D(_context.getResources(),R.drawable.energytankwhite0);
			_miscSprite.getTransform().setTranslate(0, 0);
			//_miscSprite.setShouldPlayAnimation(true);
		}
		TextView reading;
		reading = (TextView) _context.findViewById(R.id.TankReading);
		reading.setText("Free: " + _whiteTank.getCurrentLevel() + " Joules");
		_currentTank = EnergyTank.MISC;
	}
	public void setupLayout()
	{
		//Pull Font from assets
		Typeface face= Typeface.createFromAsset(_context.getAssets(), "lithosproregula.ttf"); 
		
		//Title
		TextView reading;
		reading = (TextView) _context.findViewById(R.id.TankReading);
		reading.setTypeface(face);
		reading.setTextSize(35f);
		/*switch(_currentTank) {
		case EnergyTank.HEALTH:
			reading.setText("Health: " + (int)_healthTank.getCurrentLevel() + "%");
			break;
		case EnergyTank.ATTACK:
			reading.setText("Attack: " + (int)_attackTank.getCurrentLevel() + "%");
			break;
		case EnergyTank.DEFENSE:
			reading.setText("Defense" + (int)_defenseTank.getCurrentLevel() + "%");
			break;
		case EnergyTank.MISC:
			reading.setText("Misc " + (int)_whiteTank.getCurrentLevel() + "%");
			break;
		default:
			reading.setText("No Tank Selected");
			break;
		}*/
		showHealth();
		
	}
	@Override
	public void update()
	{
		super.update();
		try {
		_healthSprite.update(System.currentTimeMillis());
		} catch (Exception e) { }
		try {
		_attackSprite.update(System.currentTimeMillis());
		} catch (Exception e) { }
		try {
		_defenseSprite.update(System.currentTimeMillis());
		} catch (Exception e) { }
		try {
		_miscSprite.update(System.currentTimeMillis());
		} catch (Exception e) { }
	}
	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
		canvas.drawColor(Color.BLACK);
		switch (_currentTank) {
		case EnergyTank.HEALTH:
			_healthSprite.draw(canvas);
			break;
		case EnergyTank.DEFENSE:
			_defenseSprite.draw(canvas);
			break;
		case EnergyTank.ATTACK:
			_attackSprite.draw(canvas);
			break;
		case EnergyTank.MISC:
			_miscSprite.draw(canvas);
			break;
		default:
			break;
		}
		
	}
	public void updateTank()
	{
		switch(_currentTank) {
		case EnergyTank.HEALTH:
			showHealth();
			break;
		case EnergyTank.ATTACK:
			showAttack();
			break;
		case EnergyTank.DEFENSE:
			showDefense();
			break;
		case EnergyTank.MISC:
			showMisc();
			break;
		default:
			break;
		}
	}
	public void freeTank(int i)
	{
		switch(i) {
		case EnergyTank.HEALTH:
			///_healthSprite.recycle();
			//_healthSprite = null;
			break;
		
		case EnergyTank.ATTACK:
			//_attackSprite.recycle();
			//_attackSprite = null;
			break;
		case EnergyTank.DEFENSE:
			//_defenseSprite.recycle();
			//_defenseSprite = null;
			break;
		case EnergyTank.MISC:
			//_miscSprite.recycle();
			//_miscSprite = null;
			break;
		}
	}
	public void freeMiscTank()
	{
		//_miscSprite.recycle();
		_miscSprite = null;
	}
	public int getMiscEnergyAmount()
	{
		return _whiteTank.getCurrentLevel();
	}
	public int getCurrentTank()
	{
		return _currentTank;
	}
	public void nextEnergy()
	{
		freeTank(_currentTank);
		_currentTank = (_currentTank + 1) % tankList.length;
		updateTank();
	}
	public void prevEnergy()
	{
		freeTank(_currentTank);
		if (_currentTank == 0)
		{
			_currentTank = tankList.length - 1;
		} else {
			_currentTank--;
		}
		updateTank();

	}
	public void updateHealth(int offset)
	{
		_healthTank.setCurrentLevel(_healthTank.getCurrentLevel() + offset);
		_databaseModule.openDataBase();
		_databaseModule.updateEnergy(EnergyTank.HEALTH, _healthTank.getCurrentLevel());
		_databaseModule.close();
		
		showHealth();
		freeMiscTank();
	}
	public void updateAttack(int offset)
	{
		_attackTank.setCurrentLevel(_attackTank.getCurrentLevel() + offset);
		_databaseModule.openDataBase();
		_databaseModule.updateEnergy(EnergyTank.ATTACK, _attackTank.getCurrentLevel());
		_databaseModule.close();
	
		showAttack();
		freeMiscTank();

	}
	public void updateDefense(int offset)
	{
		_defenseTank.setCurrentLevel(_defenseTank.getCurrentLevel() + offset);
		_databaseModule.openDataBase();
		_databaseModule.updateEnergy(EnergyTank.DEFENSE, _defenseTank.getCurrentLevel());
		_databaseModule.close();
		
		showDefense();
		freeMiscTank();
	}
	public void updateMisc(int offset)
	{
		_whiteTank.setCurrentLevel(_whiteTank.getCurrentLevel() + offset);
		_databaseModule.openDataBase();
		_databaseModule.updateEnergy(EnergyTank.MISC, _whiteTank.getCurrentLevel());
		_databaseModule.close();
	}
	
	

}
