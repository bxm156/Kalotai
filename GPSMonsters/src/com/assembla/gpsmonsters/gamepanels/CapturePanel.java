package com.assembla.gpsmonsters.gamepanels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.assembla.gpsmonsters.Creature;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;


public class CapturePanel extends GamePanel {

	boolean soundPlaying = false;
	public int enemyX, enemyY;
	Paint text = new Paint();
	Bitmap sky, ground, enemy;
	DatabaseModule database;
	int enemyID;
	//ParticleSystem ps1 = new ParticleSystem(new VectorF2D(50,50), new VectorF2D(100,100), 10, 2, ParticleSystem.NEUTRAL, 50, null, new Path("Y=-0.25X^2+1.5X+6"));

	Creature b;
	
	public CapturePanel(Context context, GameManagerInterface gameManager) {
		super(context, gameManager);
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
		sky = BitmapFactory.decodeResource(getResources(),
				R.drawable.grass_sky, opts);
		ground = BitmapFactory.decodeResource(getResources(),
				R.drawable.grass_flat, opts);
		enemy = BitmapFactory.decodeResource(getResources(),
				R.drawable.monster1, opts);

		sky = scaleToScreen(sky);
		ground = scaleToScreen(ground);
		enemy = scaleSprite(enemy, false);
		text.setTextAlign(Paint.Align.CENTER);
		
		enemyX = ((getDisplayWidth()  - enemy.getWidth())/2);
		enemyY = (int) (getDisplayHeight() * 0.12);
	}

	@Override
	public void draw(Canvas canvas) {
		// Do all the happy fun time drawing
		canvas.drawColor(0x7F453552);
		canvas.drawBitmap(sky, 0, 0, null);
		canvas.drawBitmap(ground, 0, 0, null);
		canvas.drawBitmap(enemy, enemyX, enemyY, null);		
	}

	@Override
	public void update() {

		super.update();
	}


	@Override
	protected void cleanup() {
		super.cleanup();
	}
	
	public void setB(Creature c){
		b = c;
		enemy = scaleSprite(b.getSprite().getImage(),false);
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

	// Scales a bitmap e.g. 2 = 1/2 scale 3 = 1/3 etc. then also applies
	// rotation if desired
	// Kind of needed on an arbitrary basis, will probably be unnecessary after
	// my crappy
	// filler art is replaced
	public Bitmap scaleNrotate(Bitmap orig, int scale, int rotate) {
		Bitmap resized;

		int width = orig.getWidth();
		int height = orig.getHeight();
		int newWidth = orig.getWidth() / scale;
		int newHeight = orig.getHeight() / scale;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(rotate);
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

		float scaleWidth = (newHeight * 0.75f) / width;
		float scaleHeight = scaleWidth;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// Flips player so they're facing opponent
		if (player)
			matrix.postScale(-1, 1);
		resized = Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);
		return resized;
	}
}