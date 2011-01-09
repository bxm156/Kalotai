package com.assembla.gpsmonsters.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/********************
 * 
 *
 * @author Bryan
 *
 *
 *		How to use this class:
 *
 *		In a constructor:
 *
 *		_animatedSprite = new AnimatedSprite2D(getResources(),R.drawable.spritesheet);
		_animatedSprite.setAnimationSettings(36, 40, 10, 0, 0, 100);
		_animatedSprite.setShouldPlayAnimation(true);
		_animatedSprite.getTransform().setTranslate(200, 200); //Move it to somewhere on screen
 
 *		In the update method:
 *
 *		_animatedSprite.update(System.currentTimeMillis());
 *
 *
 *		In the draw method:
 *		_animatedSprite.draw(canvas);
 *
 */
public class AnimatedSprite2D extends Sprite2D {

	private int _frameWidth;
	private int _frameHeight;
	private int _totalFrames;
	private int _currentFrame;
	private double _timeBetweenFrames; //In milliseconds
	
	private boolean _shouldPlayAnimation;
	private int _staticFrame; //If not playing animation, this frame is drawn
	
	private double _timeElapsed;
	
	public boolean isMoving;
	
	//private Rect _bitmapRect;
	
	private Bitmap[] _bitmapFrames;
	
	public AnimatedSprite2D(Resources r, int resourceID) {
		this(r, resourceID,0,0,0,0);
	}

	public AnimatedSprite2D(Resources r, int resourceID, int x, int y) {
		this(r, resourceID,x,y,0,0);
	}

	public AnimatedSprite2D(Resources r, int resourceID, int x, int y, int vx, int vy) {
		super(r, resourceID, x, y, vx, vy);
		_shouldPlayAnimation = false;
		_timeBetweenFrames = 500; // 500 ms = .5 s Default
		_timeElapsed = 0;
		//_bitmapRect = new Rect(0,0,0,0);
		_bitmapFrames = new Bitmap[0];
		//Normally, we would need to set up the frame information,
		//but we are going to make the user do that in by them calling a method
	}
	public void setAnimationSettings(int frameWidth, int frameHeight, int totalFrames, int startingFrame, int staticFrame,double timeBetweenFrames)
	{
		_frameWidth = frameWidth;
		_frameHeight = frameHeight;
		_totalFrames = totalFrames;
		_currentFrame = startingFrame;
		_staticFrame = staticFrame;
		_timeBetweenFrames = timeBetweenFrames;
		
		

		/*
		 * The following would work, but since we are using Matrix, there is no method
		 * for defining a Source and Dest Rectangle
		 * _bitmapRect.top=0;
		 * _bitmapRect.bottom = _frameHeight;
		 * _bitmapRect.left = _currentFrame * _frameWidth;
		 * _bitmapRect.left = _bitmapRect.left + _frameWidth;
		 * 
		 */
		_bitmapFrames = new Bitmap[totalFrames];
		//this is kind of ugly, but i don't see any other way if we are to use matrixes
		for(int i = 0; i < totalFrames; i++)
		{
			_bitmapFrames[i] = Bitmap.createBitmap(_bitmap, i * _frameWidth, 0, _frameWidth, _frameHeight);
		}
		
		
		
	}
	@Override
	public int getWidth()
	{
		return _frameWidth;
	}
	@Override
	public int getHeight()
	{
		return _frameHeight;
	}
	@Override
	public void setShouldPlayAnimation(boolean flag)
	{
		_shouldPlayAnimation = flag;
	}
	@Override
	public void update(long gameTime)
	{
		//Updates what frame we are on
		if(gameTime > _timeElapsed + _timeBetweenFrames )
		{
			_timeElapsed = gameTime;
			_currentFrame += 1;
			if(_currentFrame >= _totalFrames) {
				_currentFrame = 0;
			}
		}
		/*
		 * The following would work, but since we are using Matrix, there is no method
		 * for defining a Source and Dest Rectangle
		 * _bitmapRect.left = _currentFrame * _frameWidth;
		 * _bitmapRect.left = _bitmapRect.left + _frameWidth;
		 * 
		 */
	}
	@Override
	public void draw(Canvas canvas)
	{
		_transform.reset();
		_transform.postConcat(_rotate);
		_transform.postConcat(_translate);
		//_transform.preConcat(_scale);
		
		if(_shouldPlayAnimation) 
		{
			canvas.drawBitmap(_bitmapFrames[_currentFrame], _transform, null);
		} else {
			canvas.drawBitmap(_bitmapFrames[_staticFrame],_transform,null);
		}
	}
	/*
	@Override
	public void setRotation(float degree)
	{
		//_transform.reset();
		degree += (Math.PI / 2.0);
		degree *= (180.0)/Math.PI;
		//_transform.setTranslate(_gamePanel.getDisplayWidth()/2-_sprite.getWidth()/2, _gamePanel.getDisplayHeight()/2-_sprite.getHeight()/2);
		//_transform.setTranslate(240, 400);
		_rotate.setRotate(degree, 
				(float)(this.getWidth() / 2.0f), 
				(float)(this.getHeight() / 2.0f));
	
		
	}
	*/
	

}
