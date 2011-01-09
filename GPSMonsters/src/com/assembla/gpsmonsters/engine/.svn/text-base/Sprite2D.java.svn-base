/*	File: Sprite2D.java
 * 	Authors: Kumi Sano and Rob Karam
 */

package com.assembla.gpsmonsters.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Sprite2D {
	
	protected Bitmap _bitmap;
	protected Vector2D _position;
	protected Vector2D _velocity;
	protected int _transparency;
	
	protected Matrix _transform;
	protected Matrix _translate;
	protected Matrix _rotate;
	protected Matrix _scale;
	
	public Sprite2D(Resources r, int resourceID)
	{
		this(r, resourceID, 0, 0, 0, 0);
	}

	public Sprite2D(Resources r, int resourceID, int x, int y)
	{
		this(r, resourceID, x, y, 0, 0);
	}
	
	public Sprite2D(Resources r, int resourceID, int x, int y, int vx, int vy)
	{
		_bitmap = BitmapFactory.decodeResource(r, resourceID);
		_position = new Vector2D(x, y);
		_velocity = new Vector2D(vx, vy);
		_transparency = 255;
		_transform = new Matrix();
		_translate = new Matrix();
		_rotate = new Matrix();
		_scale = new Matrix();
		
		_transform.reset();
		_translate.reset();
		_rotate.reset();
		_scale.reset();
	}
	
	public void draw(Canvas canvas)
	{
		_transform.reset();
		_transform.postConcat(_rotate);
		_transform.postConcat(_translate);
		_transform.postConcat(_scale);

		canvas.drawBitmap(_bitmap,_transform,null);
	}
	
	
	public int getTransparency()
	{
		return _transparency;
	}
	
	public void setTransparency(int alpha)
	{
		_transparency = alpha;
	}
	
	public int getHeight()
	{
		return _bitmap.getHeight();
	}
	
	public int getWidth()
	{
		return _bitmap.getWidth();
	}

	public void setRotation(float degree)
	{
		//_transform.reset();
		degree += (Math.PI / 2.0);
		degree *= (180.0)/Math.PI;
		//_transform.setTranslate(_gamePanel.getDisplayWidth()/2-_sprite.getWidth()/2, _gamePanel.getDisplayHeight()/2-_sprite.getHeight()/2);
		//_transform.setTranslate(240, 400);
		_rotate.setRotate(degree, 
				(this.getWidth() / 2.0f), 
				(this.getHeight() / 2.0f));
	
		
	}
	
	public void setTranslate(float dx, float dy)
	{
		_translate.setTranslate(dx, dy);
	}
	
	public void setScale(float factor)
	{
		_scale.setScale(factor, factor, _bitmap.getWidth() / 2.0f, _bitmap.getHeight() / 2.0f);
	}
	
	public Matrix getTransform()
	{
		return _transform;
	}
	
	public Vector2D getPosition()
	{
		return _position;
	}
	
	public Vector2D getVelocity()
	{
		return _velocity;
	}
	
	public void setVelocity(int vx, int vy)
	{
		_velocity.setX(vx);
		_velocity.setY(vy);
	}
	
	public void setPosition(Vector2D vector)
	{
		_position = vector;
	}
	
	public void setPosition(int x, int y)
	{
		_position.setX(x);
		_position.setY(y);
	}
	
	public Bitmap getImage()
	{
		return _bitmap;
	}
	
	public boolean collidedWith(Sprite2D otherSprite)
	{
		int myLeft, otherLeft, myRight, otherRight;
		int myTop, otherTop, myBottom, otherBottom;
		
		myLeft = _position.getX();
		otherLeft = otherSprite.getPosition().getX();
		myRight = myLeft + _bitmap.getWidth();
		otherRight = otherLeft + otherSprite.getImage().getWidth();
		
		myTop = _position.getY();
		otherTop = otherSprite.getPosition().getY();
		myBottom = myTop + _bitmap.getHeight();
		otherBottom = otherTop + otherSprite.getImage().getHeight();

		Rect r1 = new Rect(myLeft, myTop, myRight, myBottom);
		Rect r2 = new Rect(otherLeft, otherTop, otherRight, otherBottom);
		
		if(Rect.intersects(r1, r2))
			return true;
		
		return false;				
	}
	public void setShouldPlayAnimation(boolean flag)
	{
		return; //Empty method
	}

	public void update(long gameTime) {
		// TODO Auto-generated method stub
		
	}
}
