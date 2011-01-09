/************************************************************************
 * Creature Class							*
 * Developer: Matt DelBrocco						*
 * Create Date: 9/??/10							*
 * Last Revision: 9/25/10 						*
 * 									*
 * Code for the creature. Governs all stats, level, species, name, etc.	*
 *  									*
 ************************************************************************/

package com.assembla.gpsmonsters;

import java.util.ArrayList;

import com.assembla.gpsmonsters.battle.Move;
import com.assembla.gpsmonsters.battle.MoveList;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.AnimatedSprite2D;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;
import com.assembla.gpsmonsters.engine.Sprite2D;

import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Canvas;

public class Creature {
	/************
	 * variables *
	 ************/
	// Matt's defined variables, just for fun. Easter egg?
	public char mander;
	public char meleon;
	public char izard;

	// declaration of private variables
	private String _name; // "species" name of the creature
	private String _nick; // creature nickname
	// TODO: Implement type?
	private String _type; // creature type: Aerial, Aquatic, Beast,
							// Dragon/Reptilian, Earth, Insect, Mechanical,
							// Undead, etc... (also Fire, Electric, Plant,
							// Ice...?)
	private int _level; // the creature's experience level
	private int _constitution; // affects max hit points
	private int _strength; // affects attack power
	private int _endurance; // affects defense power
	private int _agility; // affects speed
	private int _intelligence; // affects special/magic attack power
	private int _wisdom; // affects special/magic defense power
	private int _charisma; // affects charm (maybe this stat could make
							// enemy creatures more likely to join you once
							// they're defeated?)
	private int _maxHealth;
	private int _currentHealth;
	private int _experience;
	private int _attack;
	private int _defense;
	private int _speed;
	private int _magicPower;
	private int _magicDefense;
	private int _charm;
	private int _accuracy = 100;

	private int _expYield; // how much base exp. the creature gives when
							// defeated

	private boolean _isTotem; // if true, the creature won't permanently die.
	private ArrayList<Move> _moves; // variable to store moves that the creature
									// has. (In the form of strings? Or integer
									// IDs to correspond to the MoveList class?)

	private int _battleCry;
	private Sprite2D _sprite;
	private GamePanel _gamePanel;

	// Speed of its movement in relationship to map
	private int _walkSpeed;
	
	private int _krytId;

	/***************
	 * constructors *
	 ***************/
	public Creature(Resources r, GamePanel gamePanel) {
		this("DelBroccoli", "DelBroccoli", "Neutral", 1, 0, 1, 1, 1, 1, 1, 1,
				1, 10, false, null, 0, null, r, gamePanel, 5);
	}

	public Creature(String n, String nn, String t, int l, int exp, int con,
			int s, int e, int a, int i, int w, int c, int xy, boolean totem,
			ArrayList<Move> moves, int cry, Sprite2D sprite, Resources r,
			GamePanel gamePanel, int walkSpeed) {
		_name = n;
		_nick = nn;
		_type = t;
		_level = l;
		_experience = exp;
		_constitution = con;
		_strength = s;
		_endurance = e;
		_agility = a;
		_intelligence = i;
		_wisdom = w;
		_charisma = c;
		this.calculateStats();
		_currentHealth = _maxHealth;
		_expYield = xy;
		_isTotem = totem;

		// INIT THIS!
		_moves = new ArrayList<Move>();
		_moves.add(MoveList.getMoveByName("Bite")); // just add a couple default
													// moves
		_moves.add(MoveList.getMoveByName("Protect"));

		_battleCry = cry;
		_gamePanel = gamePanel;
		_walkSpeed = walkSpeed;
		if (sprite == null)
			_sprite = new Sprite2D(r, R.drawable.monster7topdown, 9, 9, 0, 0);
		else
			_sprite = sprite;

		// Move sprite to middle screen
		_sprite.setTranslate(
				_gamePanel.getDisplayWidth() / 2 - _sprite.getWidth() / 2,
				_gamePanel.getDisplayHeight() / 2 - _sprite.getHeight() / 2);
	}

	//-Matt-
	//Not sure if this constructor is doable because of the GameManagerInterface thingy
	//Added constructor below that seems to work for constructing from database values
	public Creature(int playerKrytId, GameManagerInterface gm, GamePanel panel) {
		DatabaseModule _db = gm.getDatabaseModule();
		ContentValues content = new ContentValues();
		_db.openDataBase();
		if (playerKrytId == -1) {
			content = _db.getPlayerSelectedKryt();
		} else {
			content = _db.getPlayerKrytById(playerKrytId);
		}
		_db.close();
		_krytId = content.getAsInteger("krytId");
		
		_name = content.getAsString("name");
		if (content.getAsString("nickname") != null)
			_nick = content.getAsString("nickname");
		else
			_nick = "";
		// _type = t;
		_level = content.getAsInteger("level");
		_experience = content.getAsInteger("experience");
		_constitution = content.getAsInteger("constitution");
		_strength = content.getAsInteger("strength");
		_endurance = content.getAsInteger("endurance");
		_agility = content.getAsInteger("agility");
		_intelligence = content.getAsInteger("intelligence");
		_wisdom = content.getAsInteger("wisdom");
		_charisma = content.getAsInteger("charisma");
		this.calculateStats();
		_currentHealth = content.getAsInteger("currentHealth"); // shouldn't it
																// always be
																// equal to
																// maxHealth?
																// -Matt
		_currentHealth = _maxHealth; // maxHealth is properly set by the
										// calculateStats() method based on the
										// constitution stat. -Matt
		if (content.getAsString("expYield") != null)
			_expYield = content.getAsInteger("expYield");
		else
			_expYield = 0;
		if (content.getAsInteger("isTotem") == 1)
			_isTotem = true;
		else
			_isTotem = false;
		
		_moves = new ArrayList<Move>();
		MoveList.populateMoveList();
		// TODO: Get move IDs from DB as integers
		try {
			if (content.getAsInteger("move1") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move1")));
			if (content.getAsInteger("move2") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move2")));
			if (content.getAsInteger("move3") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move3")));
			if (content.getAsInteger("move4") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move4")));
			if (content.getAsInteger("move5") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move5")));
		} catch (ArrayIndexOutOfBoundsException e) {
			// error, integer specified in DB doesn't match a move.
		}
		_gamePanel = panel;
		_battleCry = content.getAsInteger("cry");
		_walkSpeed = content.getAsInteger("walkSpeed");
		// Generate Sprite
		// Get the right image based on the naming convention
		int resourceId;
		resourceId = _gamePanel.getContext().getResources().getIdentifier("monster" + _krytId + "topdownanimated", "drawable", _gamePanel.getContext().getPackageName());
		
		if(resourceId == 0) {
			resourceId = _gamePanel.getContext().getResources().getIdentifier("monster" + _krytId + "topdown", "drawable", _gamePanel.getContext().getPackageName());
			Sprite2D sprite = new Sprite2D(_gamePanel.getContext().getResources(),resourceId);
			_sprite = sprite;
		} else {
			AnimatedSprite2D sprite = new AnimatedSprite2D(_gamePanel.getContext().getResources(),resourceId);
			sprite.setAnimationSettings(200, 200, 6, 0, 0, 100);
			sprite.setShouldPlayAnimation(false);
			_sprite = sprite;
		}
		
		_sprite.setTranslate(
				_gamePanel.getDisplayWidth() / 2 - _sprite.getWidth() / 2,
				_gamePanel.getDisplayHeight() / 2 - _sprite.getHeight() / 2);
		

	}
	
	//Constructor for building Kryt from database(ContentValues)
	public Creature(ContentValues content, GamePanel panel){
		_krytId = content.getAsInteger("krytId");
		_name = content.getAsString("name");
		if (content.getAsString("nickname") != null)
			_nick = content.getAsString("nickname");
		else
			_nick = "";
		// _type = t;
		_level = content.getAsInteger("level");
		_experience = content.getAsInteger("experience");
		_constitution = content.getAsInteger("constitution");
		_strength = content.getAsInteger("strength");
		_endurance = content.getAsInteger("endurance");
		_agility = content.getAsInteger("agility");
		_intelligence = content.getAsInteger("intelligence");
		_wisdom = content.getAsInteger("wisdom");
		_charisma = content.getAsInteger("charisma");
		this.calculateStats();
		_currentHealth = Math.round((float)(_constitution*3.5)*(content.getAsFloat("currentHealth")/100)); // shouldn't it
																
		if (content.getAsString("expYield") != null)
			_expYield = content.getAsInteger("expYield");
		else
			_expYield = 0;
		try
		{
			if (content.getAsInteger("isTotem") == 1)
				_isTotem = true;
			else
				_isTotem = false;
		}
		catch (Exception ex){}

		_moves = new ArrayList<Move>();
		MoveList.populateMoveList();
		// TODO: Get move IDs from DB as integers
		try {
			if (content.getAsInteger("move1") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move1")));
			if (content.getAsInteger("move2") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move2")));
			if (content.getAsInteger("move3") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move3")));
			if (content.getAsInteger("move4") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move4")));
			if (content.getAsInteger("move5") != null)
				_moves.add(MoveList.getMoveByIndex(content
						.getAsInteger("move5")));
		} catch (ArrayIndexOutOfBoundsException e) {
			// error, integer specified in DB doesn't match a move.
		}
		_gamePanel = panel;
		_battleCry = content.getAsInteger("cry");
		_walkSpeed = content.getAsInteger("walkSpeed");
		// Generate Sprite
		// Get the right image based on the naming convention
		int resourceId;
		resourceId = _gamePanel
				.getContext()
				.getResources()
				.getIdentifier("monster" + _krytId, "drawable",
						_gamePanel.getContext().getPackageName());
		Sprite2D sprite = new Sprite2D(_gamePanel.getContext().getResources(),
				resourceId);
		sprite.setTranslate(
				_gamePanel.getDisplayWidth() / 2 - sprite.getWidth() / 2,
				_gamePanel.getDisplayHeight() / 2 - sprite.getHeight() / 2);
		_sprite = sprite;
	}

	/**************
	 * get methods *
	 **************/
	public String getName() {
		return _name;
	}

	public String getNick() {
		return _nick;
	}

	public String getType() {
		return _type;
	}

	public int getLevel() {
		return _level;
	}

	public int getExperience() {
		return _experience;
	}

	public int getConstitution() {
		return _constitution;
	}

	public int getStrength() {
		return _strength;
	}

	public int getEndurance() {
		return _endurance;
	}

	public int getAgility() {
		return _agility;
	}

	public int getIntelligence() {
		return _intelligence;
	}

	public int getWisdom() {
		return _wisdom;
	}

	public int getCharisma() {
		return _charisma;
	}

	public int getMaxHealth() {
		return _maxHealth;
	}

	public int getCurrentHealth() {
		return _currentHealth;
	}

	public int getAttackStat() {
		return _attack;
	}

	public int getDefenseStat() {
		return _defense;
	}

	public int getSpeedStat() {
		return _speed;
	}

	public int getMagicPowerStat() {
		return _magicPower;
	}

	public int getMagicDefenseStat() {
		return _magicDefense;
	}

	public int getExpYield() {
		return _expYield;
	}

	public int getCharmStat() {
		return _charm;
	}

	public int getAccuracyStat() {
		return _accuracy;
	}

	public ArrayList<Move> getMoves() {
		return _moves;
	}

	public int getCry() {
		return _battleCry;
	}

	public Sprite2D getSprite() {
		return _sprite;
	}

	public boolean isTotem() {
		return _isTotem;
	}

	public int getWalkSpeed() {
		return _walkSpeed;
	}
	public int getKrytId()
	{
		return _krytId;
	}

	/**************
	 * set methods *
	 **************/
	public void setName(String n) {
		_name = n;
	}

	public void setNick(String nn) {
		_nick = nn;
	}

	public void setType(String t) {
		_type = t;
	}

	public void setLevel(int l) {
		_level = l;
	}

	public void setExperience(int exp) {
		_experience = exp;
	}

	public void setConstitution(int con) {
		_constitution = con;
	}

	public void setStrength(int s) {
		_strength = s;
	}

	public void setEndurance(int e) {
		_endurance = e;
	}

	public void setAgility(int a) {
		_agility = a;
	}

	public void setIntelligence(int i) {
		_intelligence = i;
	}

	public void setWisdom(int w) {
		_wisdom = w;
	}

	public void setCharisma(int c) {
		_charisma = c;
	}

	public void setMaxHealth(int mh) {
		_maxHealth = mh;
	}

	public void setCurrentHealth(int ch) {
		_currentHealth = ch;
	}

	public void setAttackStat(int as) {
		_attack = as;
	}

	public void setDefenseStat(int ds) {
		_defense = ds;
	}

	public void setSpeedStat(int ss) {
		_speed = ss;
	}

	public void setMagicPowerStat(int mps) {
		_magicPower = mps;
	}

	public void setMagicDefenseStat(int mds) {
		_magicDefense = mds;
	}

	public void setExpYield(int xy) {
		_expYield = xy;
	}

	public void setCharmStat(int cs) {
		_charm = cs;
	}

	public void setAccuracyStat(int a) {
		_accuracy = a;
	}

	public void setMoves(ArrayList<Move> moves) {
		for (Move m : moves) {
			_moves.add(m);
		}
	}

	public void setCry(int cry) {
		_battleCry = cry;
	}

	public void setSprite(Sprite2D sprite) {
		_sprite = sprite;
	}

	/**************
	 * add methods *
	 **************/

	public void addMaxHealth(int mh) {
		_maxHealth += mh;
	}

	public void addCurrentHealth(int ch) {
		_currentHealth += ch;
	}

	public void addAttackStat(int as) {
		_attack += as;
	}

	public void addDefenseStat(int ds) {
		_defense += ds;
	}

	public void addSpeedStat(int ss) {
		_speed += ss;
	}

	public void addMagicPowerStat(int mps) {
		_magicPower += mps;
	}

	public void addMagicDefenseStat(int mds) {
		_magicDefense += mds;
	}

	public void addCharmStat(int cs) {
		_charm += cs;
	}

	public void addAccuracyStat(int a) {
		_accuracy += a;
	}

	// adds a single move to the creature's usable moves
	public void addMove(Move m) {
		_moves.add(m);
	}

	// always check for level up after either addExp method is called!!!
	public boolean addExp(int added) {
		_experience += added;
		return this.checkExp();
	}

	public static boolean addExp(Creature defeated, Creature victor) {
		return victor.addExp((defeated.getExpYield() * defeated.getLevel())/5);
	}

	/****************
	 * other methods *
	 ****************/

	// you know the creature's level, need to set experience...
	public void findExpFromLevel() {
		// gets a value for use in the experience equation
		int summation = 0;
		for (int i = 1; i <= _level; i++) {
			summation += i;
		}
		_experience = (int) (10 + Math.floor((1 / 4) * summation
				* Math.pow(2.0, (_level) / 5.0)));
	}

	// you want to know what level the creature should be if it has some amount
	// of experience
	public void findLevelFromExp() {
		_level = 1;
		while (true) {
			// gets a value for use in the experience equation
			int summation = 0;
			for (int i = 1; i <= _level; i++) {
				summation += i;
			}
			if (10 + Math.floor((1 / 4) * summation
					* Math.pow(2.0, (_level) / 5.0)) <= _experience)
				_level++;
			else
				break;
		}
	}

	// Check for level up every time experience is added to a creature.
	public boolean checkExp() {
		// gets a value for use in the experience equation
		int summation = 0;
		for (int i = 1; i <= _level + 1; i++) // level + 1 because we want next										// lvl
		{
			summation += i;
		}
		double x = 10 + Math.floor((1 / 4) * summation * Math.pow(2.0, (_level + 1) / 5.0));
		if (x <= _experience)
		{
		    	_experience = _experience - (int)(x);
			this.levelUp();
			return true;
		}
		else
			this.calculateStats();
		return false;

	}

	// method to be called when a creature levels up
	public void levelUp() {
		_level++;
		int oldMaxHealth = _maxHealth;
		setConstitution(_constitution + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		setStrength(_strength + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		setEndurance(_endurance + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		setAgility(_agility + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		setIntelligence(_intelligence + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		setWisdom(_wisdom + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		setCharisma(_charisma + 1
				+ (int) (Math.random() * Math.pow(_level, 0.75) / 2));
		calculateStats();
		_currentHealth += (_maxHealth - oldMaxHealth); // give them the HP for
														// leveling up
		// TODO: call an as yet unwritten method that sees if the creature
		// learns
		// a new move.
	}

	// this method should be called whenever the creature levels up to
	// recalculate
	// its stats based on its new ability scores.
	public void calculateStats() {
		// TODO: implement some move advanced formula here for calculation...
		// perhaps make this unique for different creatures or more random.
		// I'm just going to toss up something extremely basic.
		_maxHealth = (int) (_constitution * 3.5);
		_attack = (int) (_strength * 2.5);
		_defense = (int) (_endurance * 2.5);
		_speed = (int) (_agility * 2.5);
		_magicPower = (int) (_intelligence * 2.5);
		_magicDefense = (int) (_wisdom * 2.5);
		_charm = (int) (_charisma * 2.5);
		_accuracy = 100;
	}
	
	public Move hasMove(String m){
		for(Move v:this.getMoves()){
			if(m.compareTo(Integer.toString(v.getMoveType()))==0){
				return v;
			}
		}
		return null;
	}
}
