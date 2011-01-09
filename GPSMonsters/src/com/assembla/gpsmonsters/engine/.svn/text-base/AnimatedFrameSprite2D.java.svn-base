package com.assembla.gpsmonsters.engine;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class AnimatedFrameSprite2D extends Sprite2D {
	private int _totalFrames;
	private int _currentFrame;
	private double _timeBetweenFrames; //In milliseconds
	
	private boolean _shouldPlayAnimation;
	private int _staticFrame; //If not playing animation, this frame is drawn
	
	private double _timeElapsed;
	
	//private Rect _bitmapRect;
	
	private Bitmap[] _bitmapFrames;

	public AnimatedFrameSprite2D(Resources resource, int resourceID, Context _context, String baseName,int totalFrames, double timeBetweenFrames) {
		super(resource, resourceID, 0, 0, 0, 0);
		_shouldPlayAnimation = false;
		_timeElapsed = 0;
		_bitmapFrames = new Bitmap[totalFrames];
		_totalFrames = totalFrames;
		_currentFrame = 0;
		_staticFrame = 0;
		_timeBetweenFrames = timeBetweenFrames;
		
		for(int i = 0; i < totalFrames; i++)
		{
			int resourceId;
			resourceId = resource.getIdentifier(baseName + i, "drawable",_context.getPackageName());
			_bitmapFrames[i] = BitmapFactory.decodeResource(_context.getResources(),resourceId);
		}
		

	}
	public void recycle()
	{
		for(int i = 0; i < _totalFrames; i++)
		{
			_bitmapFrames[i].recycle();
		}
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
		if(_shouldPlayAnimation) 
		{
			canvas.drawBitmap(_bitmapFrames[_currentFrame], _transform, null);
		} else {
			canvas.drawBitmap(_bitmapFrames[_staticFrame],_transform,null);
		}
	}

}
