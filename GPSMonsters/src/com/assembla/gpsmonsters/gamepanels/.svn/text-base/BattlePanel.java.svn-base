package com.assembla.gpsmonsters.gamepanels;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.widget.Toast;

import com.assembla.gpsmonsters.Creature;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.battle.BattleActivity;
import com.assembla.gpsmonsters.battle.Move;
import com.assembla.gpsmonsters.battle.TrashTalker;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;
import com.assembla.gpsmonsters.particleengine.ParticleSystem;
import com.assembla.gpsmonsters.particleengine.Path;
import com.assembla.gpsmonsters.particleengine.VectorF2D;
import com.assembla.gpsmonsters.sound.SoundList;
import com.assembla.gpsmonsters.sound.SoundManager;
import com.assembla.gpsmonsters.timemodule.TimeModule;

public class BattlePanel extends GamePanel {

	boolean soundPlaying = false,captureMode = false, end = false, endState = false, capSuccess = false;
	private boolean capStart = false;
	private static int CURRENT_PLAYER = 1;
	public int playerX, playerY, enemyX, enemyY, sX, sY;
	public String MOVE_NAME = null;
	public Move chosenMove = null;
	Bitmap sky, ground, player, enemy, PlayerHealth, EnemyHealth, capcha, endMsg, start, bubble;
	DatabaseModule database;
	int playerID, enemyID;
	ParticleSystem ps1;
	TextPaint _daPaint;
	Paint oblivionPaint, hPaint;
	//Paint krytPaint, enemyPaint;
	BitmapFactory.Options opts;
	Toast _toast;
	
	Creature a;// = new Creature("Popo", "Po", "Orange", 1, 0, 20, 5, 5, 5, 5, 5,5, 10, false, null, 0, as, getResources(), this, 5);
	Creature b;// = new Creature("LoLo", "Lo", "Blue", 1, 0, 20, 5, 5, 5, 5, 5, 5,10, false, null, 0, bs, getResources(), this, 5);
	ArrayList<Move> playerMoves;
	ArrayList<Move> enemyMoves;
	battleTimeline timeline;
	long delay;
	Message msg;
	Handler handler;
	Bundle bu;
	TrashTalker pT;
	Random r;
	
	private boolean hasAddedKryt;
	
	boolean gestured = false;

	private int battleState = 0;
	
	public ArrayList<VectorF2D> captureArray;
	
	private SoundManager _soundManager;
	
	boolean enemyPaintChanged = false;
	boolean krytPaintChanged = false;

	public BattlePanel(Context context, GameManagerInterface gameManager) {
		super(context, gameManager);
		
		enemyID = ((BattleActivity) context).enemyId();
		_context = (BattleActivity) context;
		database = new DatabaseModule(context);
		database.openDataBase();
		a = new Creature(database.getPlayerSelectedKryt(),this);
		b = new Creature(database.getEncounteredKrytById(enemyID),this);
		database.close();
		
		handler = ((BattleActivity) context).getHandler();
		pT = new TrashTalker(a,b);
		r = new Random();
		
		timeline = new battleTimeline(a, b);
		
		playerMoves = a.getMoves();
		enemyMoves = b.getMoves();
		
		opts = new BitmapFactory.Options();
		sky = BitmapFactory.decodeResource(getResources(),
				R.drawable.grass_sky, opts);
		int timeofday = TimeModule.getTimeOfDay();
		if(timeofday == 3 || timeofday==4){
			ground = BitmapFactory.decodeResource(getResources(),
					R.drawable.night_scene, opts);
		}
		else{
			ground = BitmapFactory.decodeResource(getResources(),
					R.drawable.grass_flat, opts);
		}
		player = a.getSprite().getImage();
		enemy = b.getSprite().getImage();
		
		EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
				R.drawable.healthbarfull, opts),false);
		PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
				R.drawable.healthbarfull, opts),true);
		capcha = BitmapFactory.decodeResource(getResources(),
				R.drawable.capcha, opts);
		endMsg = scaleToScreen(BitmapFactory.decodeResource(getResources(),
				R.drawable.dead, opts));
		start = BitmapFactory.decodeResource(getResources(),
				R.drawable.start, opts);
		bubble = stretch(BitmapFactory.decodeResource(getResources(),
				R.drawable.text_bg, opts));
		oblivionPaint = new Paint();
		//krytPaint = new Paint();
		//enemyPaint = new Paint();
		oblivionPaint.setARGB(255, 0, 0, 0);
		//krytPaint.setARGB(255, 0, 0, 0);
		//enemyPaint.setARGB(255, 0, 0, 0);
		hPaint = new Paint();
		hPaint.setARGB(128, 0, 0, 0);

		sky = scaleToScreen(sky);
		ground = scaleToScreen(ground);
		player = scaleSprite(player, true);
		enemy = scaleSprite(enemy, false);
		capcha = resize(capcha,10);
		
		playerX = (int) (getDisplayWidth() * 0.05);
		playerY = (int) (getDisplayHeight() * 0.27);
		
		enemyX = (int) (getDisplayWidth() * 0.95 - enemy.getWidth());
		enemyY = (int) (getDisplayHeight() * 0.27);
		
		sX = (int)(getDisplayWidth()*0.95-start.getWidth());
		sY = (int)(getDisplayHeight()*0.98-start.getHeight());
		
		_soundManager = gameManager.getSoundManager();
		_toast = Toast.makeText(this.getContext(), "", Toast.LENGTH_SHORT);
	}
	String _playerInfo;
	String _enemyInfo;
	@Override
	public void draw(Canvas canvas) {
		// Do all the happy fun time drawing
		
		if(!captureMode && !endState){
		    	_daPaint = new TextPaint();
	               _daPaint.setColor(Color.BLACK);
	               _daPaint.setTextSize(20);
	               //_daPaint.bgColor = Color.GRAY;  // doesn't work. WTF! -mvd
	               _playerInfo = a.getName() + " : Level " + a.getLevel();
	               _enemyInfo = b.getName() + " : Level " + b.getLevel();
				canvas.drawBitmap(sky, 0, 0, null);
				canvas.drawBitmap(ground, 0, 0, null);
				canvas.drawBitmap(PlayerHealth, playerX, playerY, hPaint);
				canvas.drawBitmap(EnemyHealth, enemyX, enemyY, hPaint);
				canvas.drawBitmap(player, playerX, playerY, null);//krytPaint);
				canvas.drawBitmap(enemy, enemyX, enemyY, null);//enemyPaint);
				canvas.drawBitmap(bubble, playerX-15, playerY-bubble.getHeight(),null);
				canvas.drawBitmap(bubble, enemyX-15, enemyY-bubble.getHeight(),null);
				//canvas.drawRect(playerX+5, playerY-39, playerX + (_playerInfo.length())*10, playerY-18, _rectPaint);
				//canvas.drawRect(enemyX+5, enemyY-39, enemyX + (_enemyInfo.length())*10, enemyY-18, _rectPaint);
				canvas.drawText(_playerInfo, playerX+5, playerY-15, _daPaint);
				canvas.drawText(_enemyInfo, enemyX+5, enemyY-15, _daPaint);
				
				if(ps1 != null)
					ps1.Draw(canvas);
		}
		else if(captureMode){
			canvas.drawBitmap(sky, 0, 0, null);
			canvas.drawBitmap(ground, 0, 0, null);
			canvas.drawBitmap(enemy, enemyX, enemyY, oblivionPaint);
			if(capStart){	
				for(VectorF2D v : captureArray){
					canvas.drawBitmap(capcha,v.getX(),v.getY(),null);
				}
			}
			else
				canvas.drawBitmap(start,sX,sY, null);
		}
		else{
			canvas.drawBitmap(endMsg, 0, 0, null);
			canvas.drawBitmap(player, playerX, playerY, null);
		}
	}

	@Override
	public void update() {

		
		if(ps1 != null)	
			ps1.Update();
		
		// Battle State Machine

		switch (battleState) {
		case 0: // battle start
		    	_soundManager.setSong(0);  // 0 as parameter sets song to battle song. -mvd
		    	_soundManager.playSong();  // play music
		    	if (soundPlaying == false) {  // TODO: still need to test if this is the right time to play this. -mvd
				this._soundManager.playSound(b.getCry());
				soundPlaying = true;
				postEncounter();
			    }
		    	battleState = 9;
		    	hasAddedKryt = false;
			
			break;
		case 1: // player move
			if(chosenMove != null){
			    battleState = 2;
			    delay = System.currentTimeMillis();
			}
			break;
		case 2: // draw move
			ps1 = new ParticleSystem(this.getContext(), this._viewWidth, this._viewHeight, 
					new VectorF2D(0	,220), new VectorF2D(45f,0), chosenMove.getParticle(), 
					Path.LINEAR, true);
			post(true);
			battleState = 3;
			break;
		case 3: // update stats
		    if (System.currentTimeMillis() >= delay+500)  // wait for animation to finish before moving on.
		    {
		    	Execute(chosenMove);
		    	enemyHealth();
			chosenMove = null;
			MOVE_NAME = null;
			delay = System.currentTimeMillis();
			if (a.getCurrentHealth() > 0 && b.getCurrentHealth() > 0)
				battleState = 9;
			else
				battleState = 7;
		    }
			break;
		case 4: // opponent move
			//Force delay between player and enemy moves, mostly so Toasts of move names have time to fade
			if(System.currentTimeMillis()>= delay+3000){	
				chosenMove = AISelect(b);
				battleState = 5;
			}
			break;
		case 5: // draw enemy move
			ps1 = new ParticleSystem(this.getContext(), this._viewWidth, this._viewHeight, 
					new VectorF2D(0,220), new VectorF2D(30f,0), chosenMove.getParticle(), 
					Path.LINEAR, false);
			delay = System.currentTimeMillis();
			post(false);
			battleState = 6;
			break;
		case 6: // update stats
		    if (System.currentTimeMillis() >= delay+500)  // wait for animation to finish before moving on.
		    {
		   	Execute(chosenMove);
			playerHealth();
			chosenMove = null;
			MOVE_NAME = null;
			if (a.getCurrentHealth() > 0 && b.getCurrentHealth() > 0)
				battleState = 9;
			else
				battleState = 7;
		    }
			break;
		case 7: // battle end
			
			if(((BattleActivity)_context)._isInstantBattle) {
				if (a.getCurrentHealth() > 0){
					endState = true;
					battleState = 10;
					endMsg = scaleToScreen(BitmapFactory.decodeResource(getResources(),
							R.drawable.instant_win, opts));
					delay = System.currentTimeMillis();
				}
				else{
					((BattleActivity)_context).deletePlayerKryt();
					Intent resultIntent = new Intent();
					resultIntent.putExtra("outcome", "lost");
					((BattleActivity)_context).setResult(Activity.RESULT_OK, resultIntent);
					battleState = 10;
					playerX = (getDisplayWidth() - player.getWidth())/2;
					endState = true;
					delay = System.currentTimeMillis();
				}
			} else {
			
			 
			if (a.getCurrentHealth() > 0){ // player won battle
		    	        _soundManager.stopSong();
			    	if(Creature.addExp(b, a))
			    	{
			    	    _toast.setText("YOUR " + a.getName().toUpperCase() + " LEVELS UP!!!");
			    	    _toast.show();
			    	}
				_soundManager.playSound(SoundList.WIN);  // or put somewhere else?
				((BattleActivity)_context).deletePlayerKryt();
			    	((BattleActivity)_context).addSelectedPlayerKryt(a);
				battleState = 8;
				enemyX = (getDisplayWidth() - enemy.getWidth())/2;
				captureMode = true;
				((BattleActivity) _context).captureMode(sX, sY);
				populateCapture();
				//((BattleActivity) _context).startCap();
			}
			else{
				if(a.isTotem()) {
					Intent resultIntent = new Intent();
					resultIntent.putExtra("outcome", "totemLost");
					((BattleActivity)_context).setResult(Activity.RESULT_OK, resultIntent);
					((BattleActivity)_context).finish();
				} else {
					((BattleActivity)_context).deletePlayerKryt();
					Intent resultIntent = new Intent();
					resultIntent.putExtra("outcome", "lost");
					((BattleActivity)_context).setResult(Activity.RESULT_OK, resultIntent);
					battleState = 10;
					playerX = (getDisplayWidth() - player.getWidth())/2;
					endState = true;
					delay = System.currentTimeMillis();
				}
			}
			// a.die() // player lost battle

			}
			break;
		case 8:   // capture mode
			if(end)
			{
				if(capSuccess){	
					if(!hasAddedKryt)
					{
						((BattleActivity)_context).addPlayerKryt(getEnemy());
						hasAddedKryt = true;
					}
					ground = scaleToScreen(BitmapFactory.decodeResource(getResources(),
							R.drawable.capsuccess, opts));;
				}
				else{
					ground = scaleToScreen(BitmapFactory.decodeResource(getResources(),
							R.drawable.capfail, opts));;
				}
			    //((BattleActivity)_context).finish();
				battleState = 10;
				delay = System.currentTimeMillis();
				//Will eventually be used for Capture stuff
				//Since capture requires no stat changes, sit there until flagged for captured or failure
				// then add kryt to player's kryts if captured else end and return to map
				
			}
			break; 
			
		case 9: 
		    battleState = CURRENT_PLAYER;  // either 1 or 4
		    // If it's the player's turn, send flag to activity, goes here so
		    // it's only run once
		    if (battleState == 1)
		    	playerTurn(); // for the gestures
		    break;
		//Battle End Hold State
		case 10:
			if(captureMode){
				oblivionPaint.setAlpha(255-(int)(255*(System.currentTimeMillis()-delay)/3000));
			}
			if(System.currentTimeMillis()>= delay+3000){
				
				((BattleActivity)_context).finish();
			}
			break;
		}
		
		super.update();
	}

	public void setMove(Move m, double s) {
		chosenMove = m;
	}

	public void passKrytID(int i) {

	}
	
	public void playerTurn(){
		((BattleActivity) _context).playerTurn();
		msg = new Message();
		bu = new Bundle();
		bu.putInt("type", 3);
		msg.setData(bu);
		handler.sendMessage(msg);
	}

	@Override
	protected void cleanup() {
		sky.recycle();
		ground.recycle();
		player.recycle();
		enemy.recycle();
		capcha.recycle();
		System.gc();
		super.cleanup();
	}
	
	public void post(boolean f){
		msg = new Message();
		bu = new Bundle();
		bu.putInt("type", 1);
		int i;
		if(f){
			pT.setPMove(chosenMove.getName());
			i = r.nextInt(pT.attacksP.size());
		}
		else{
			pT.setEMove(chosenMove.getName());
			i = r.nextInt(pT.attacksE.size());
		}
		bu.putString("toast",pT.attacks(i,f));
		msg.setData(bu);
		handler.sendMessage(msg);
	}
	
	public void postEncounter(){
		msg = new Message();
		bu = new Bundle();
		bu.putInt("type", 1);
		int i = r.nextInt(pT.encounters.size());
		bu.putString("toast",pT.encounters(i));
		msg.setData(bu);
		handler.sendMessage(msg);
	}
	
	public void playerHealth(){
		float h = ((float)a.getCurrentHealth())/((float)a.getMaxHealth());
		if(h>0.95){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
				R.drawable.healthbarfull, opts), true);
		}
		if(h<=0.95 && h>0.8){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar1, opts), true);
		}
		else if(h<=0.8 && h>0.7){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar2, opts), true);
		}
		else if(h<=0.7 && h>0.6){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar3, opts), true);
		}
		else if(h<=0.6 && h>0.5){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar4, opts), true);
		}
		else if(h<=0.5 && h>0.4){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar5, opts), true);
		}
		else if(h<=0.4 && h>0.3){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar6, opts), true);
		}
		else if(h<=0.3 && h>0.2){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar7, opts), true);
		}
		else if(h<=0.2 && h>0.1){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar8, opts), true);
		}
		else if(h<=0.1 && h>0){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar9, opts), true);
		}
		else if(h<=0){
			PlayerHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar0, opts), true);
		}
	}
	
	public void enemyHealth(){
		float h = ((float)b.getCurrentHealth())/((float)b.getMaxHealth());
		if(h>0.95){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
				R.drawable.healthbarfull, opts), true);
		}
		if(h<=0.95 && h>0.8){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar1, opts), false);
		}
		else if(h<=0.8 && h>0.7){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar2, opts), false);
		}
		else if(h<=0.7 && h>0.6){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar3, opts), false);
		}
		else if(h<=0.6 && h>0.5){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar4, opts), false);
		}
		else if(h<=0.5 && h>0.4){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar5, opts), false);
		}
		else if(h<=0.4 && h>0.3){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar6, opts), false);
		}
		else if(h<=0.3 && h>0.2){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar7, opts), false);
		}
		else if(h<=0.2 && h>0.1){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar8, opts), false);
		}
		else if(h<=0.1 && h>0){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar9, opts), false);
		}
		else if(h<=0){
			EnemyHealth = scaleSprite(BitmapFactory.decodeResource(getResources(),
					R.drawable.healthbar0, opts), false);
		}
	}

	public Bitmap scaleToScreen(Bitmap orig) {
		Bitmap resized;

		int width = orig.getWidth();
		int height = orig.getHeight();
		int newWidth = getDisplayWidth();
		int newHeight = getDisplayHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		resized = Bitmap.createBitmap(orig, 0, 0, width, height, matrix, true);

		return resized;
	}
	
	public Bitmap resize(Bitmap orig, int scale) {
		Bitmap resized;

		int width = orig.getWidth();
		int height = orig.getHeight();
		int newWidth = scale;
		int newHeight = scale;


		float scaleWidth = ((float) newWidth)/width;
		float scaleHeight = ((float) newHeight)/height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		resized = Bitmap.createBitmap(orig, 0, 0, width, height, matrix, true);

		return resized;
	}

	// Used to scale sprite so they'll properly fit on different resolution
	// screens
	public Bitmap scaleSprite(Bitmap bit, boolean player) {
		Bitmap resized;
		int width = bit.getWidth();
		int height = bit.getHeight();
		int newHeight = getDisplayHeight();

		float scaleWidth = (newHeight * 0.60f) / width;
		float scaleHeight = scaleWidth;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// Flips player so they're facing opponent
		if (player)
			matrix.postScale(-1, 1);
		resized = Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);
		return resized;
	}
	
	public Bitmap stretch(Bitmap orig){
		Bitmap resized;

		int width = orig.getWidth();
		int height = orig.getHeight();
		int newWidth = (int)(player.getWidth()*0.65);
		int newHeight = (int)(getDisplayHeight()*0.1);


		float scaleWidth = ((float) newWidth)/width;
		float scaleHeight = ((float) newHeight)/height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		resized = Bitmap.createBitmap(orig, 0, 0, width, height, matrix, true);

		return resized;
	}
	
	//Probably no longer used
	public Creature getEnemy(){
		return b;
	}
	
	public Creature getPlayer(){
		return a;
	}
	
	// already implemented in Creature class -mvd
	public void increaseHealth(int amount)
	{
		a.addCurrentHealth(amount);
	}
	public void increaseAttack(int amount)
	{
		a.addAttackStat(amount);
	}
	public void increaseDefense(int amount)
	{
		a.addDefenseStat(amount);
	}
	
	
	public Move AISelect(Creature cre){
		//For now move choice is randomish
		// 65% chance use type 1 attack
		// 10% chance use type 2 attack
		// 10% chance use type 3 attack
		// 10% chance use type 4 attack
		// 5% chance use type 5 attack
		// IF it rolls and gets a slot the creature has no move for, reroll
		
		int m = r.nextInt(100);
		//Check which slot is rolled, if they have a move in that slot, use it, if not reroll
		//Currently this is recursive, if someone knows if looping would be more efficient then maybe switch to that
		if(m<65){
			for(Move v : cre.getMoves()){
				if(v.getMoveType() == 1)
					return v;
			}
			return AISelect(cre);
		}
		else if(m<75){
			for(Move v : cre.getMoves()){
				if(v.getMoveType() == 2)
					return v;
			}
			return AISelect(cre);
		}
		else if(m<85){
			for(Move v : cre.getMoves()){
				if(v.getMoveType() == 3)
					return v;
			}
			return AISelect(cre);
		}
		else if(m<95){
			for(Move v : cre.getMoves()){
				if(v.getMoveType() == 4)
					return v;
			}
			return AISelect(cre);
		}
		for(Move v : cre.getMoves()){
			if(v.getMoveType() == 5)
				return v;
		}
		return AISelect(cre);
	}
	

	public void Execute(Move m) {
		if (CURRENT_PLAYER == 1) {
		    //if(m.getMoveType() == 1 || m.getMoveType() == 2 || m.getMoveType() == 4 || m.getMoveType() == 5)
			//{enemyPaint.setARGB(255, 100, 0, 0); enemyPaintChanged = true;}
		    //else if (m.getMoveType() == 3)
			//{krytPaint.setARGB(255, 0, 100, 0); krytPaintChanged = true;}
		    m.execute(b, a);
		} 
		else {
		    //if(m.getMoveType() == 1 || m.getMoveType() == 2 || m.getMoveType() == 4 || m.getMoveType() == 5)
			//{krytPaint.setARGB(255, 100, 0, 0); krytPaintChanged = true;}
		    //else if (m.getMoveType() == 3)
			//{enemyPaint.setARGB(255, 0, 100, 0); enemyPaintChanged = true;}
		    m.execute(a, b);
		}
		timeline.addTime(m);
		timeline.getNext();
	}
	
	public void end(){
		end = true;
	}
	
	public void setCapSuccess(boolean s){
		capSuccess = s;
	}
	
	public void populateCapture(){
		VectorF2D c = new VectorF2D(getDisplayWidth()/2, getDisplayHeight()/2);
		int rawr  = r.nextInt(3);
		float ra = (float) (getDisplayHeight()*0.45);
		float half = ra/2;
		float sq3o2 = (float) (ra*(Math.sqrt(3)/2));
		captureArray = new ArrayList<VectorF2D>();
		if(rawr == 0){
			captureArray.add(new VectorF2D(c.getX(),c.getY()-ra));
			captureArray.add(new VectorF2D(c.getX()-half,c.getY()-sq3o2));
			captureArray.add(new VectorF2D(c.getX()-sq3o2,c.getY()-half));
			captureArray.add(new VectorF2D(c.getX()-ra,c.getY()));
			captureArray.add(new VectorF2D(c.getX()-sq3o2,c.getY()+half));
			captureArray.add(new VectorF2D(c.getX()-half,c.getY()+sq3o2));
			captureArray.add(new VectorF2D(c.getX(),c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()+half,c.getY()+sq3o2));
			captureArray.add(new VectorF2D(c.getX()+sq3o2,c.getY()+half));
			captureArray.add(new VectorF2D(c.getX()+ra,c.getY()));
			captureArray.add(new VectorF2D(c.getX()+sq3o2,c.getY()-half));
			captureArray.add(new VectorF2D(c.getX()+half,c.getY()-sq3o2));
			//captureArray.add(new VectorF2D(c.getX(),c.getY()+r));
		}
		else if(rawr == 1){
			captureArray.add(new VectorF2D(c.getX(),c.getY()-ra));
			captureArray.add(new VectorF2D(c.getX()-ra,c.getY()-ra));
			captureArray.add(new VectorF2D(c.getX()-ra,c.getY()));
			captureArray.add(new VectorF2D(c.getX()-ra,c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX(),c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()+ra,c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()+ra,c.getY()));
			captureArray.add(new VectorF2D(c.getX()+ra,c.getY()-ra));
		}
		else if(rawr == 2){
			captureArray.add(new VectorF2D(c.getX(),c.getY()-ra));
			captureArray.add(new VectorF2D(c.getX()-(ra*0.5f),c.getY()));
			captureArray.add(new VectorF2D(c.getX()-ra,c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()-(ra*0.5f),c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()+(ra*0.5f),c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()+ra,c.getY()+ra));
			captureArray.add(new VectorF2D(c.getX()+(ra*0.5f),c.getY()));
		}
		((BattleActivity)_context).populateCapture(captureArray);
	}
	
	public void updateCapture(ArrayList<VectorF2D> array){
		captureArray = array;
	}

	public void setCapStart(boolean capStart) {
		this.capStart = capStart;
	}

	public class battleTimeline {
		public int playerTime, enemyTime;
		public int playerSpeed, enemySpeed;

		public battleTimeline(Creature player, Creature enemy) {
			playerSpeed = player.getSpeedStat();
			enemySpeed = enemy.getSpeedStat();
			playerTime = 0;
			enemyTime = 0;
			if (playerSpeed >= enemySpeed) {
				CURRENT_PLAYER = 1;
			} else {
				CURRENT_PLAYER = 4;
			}

		}

		public void addTime(Move a) {
			if (CURRENT_PLAYER == 1) {
				playerTime += a.getMoveTime();
			} else {
				enemyTime += a.getMoveTime();
			}
		}

		public void getNext() {
			if (playerTime < enemyTime) {
				enemyTime -= playerTime;
				playerTime = 0;
				CURRENT_PLAYER = 1;
			} else if (enemyTime < playerTime) {
				playerTime -= enemyTime;
				enemyTime = 0;
				CURRENT_PLAYER = 4;
			} else {
				if (playerSpeed >= enemySpeed) {
					CURRENT_PLAYER = 1;
				} else {
					CURRENT_PLAYER = 4;
				}
			}
		}
	}
}