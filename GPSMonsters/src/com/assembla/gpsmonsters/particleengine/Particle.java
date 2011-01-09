package com.assembla.gpsmonsters.particleengine;

//import android.graphics.Color;

public class Particle {
	
	private VectorF2D 	_position;
	private VectorF2D	_initialPosition;
	private VectorF2D 	_velocity;
	
	private long		_lifetime;
	private long		_age;
	
	private boolean		_isDead;
	
//	private int			_color;
	
	private int			_alpha;
			
	public Particle(float posX, float posY, float velX, float velY, long lifetime, int color)
	{
		this(new VectorF2D(velX, velY), new VectorF2D(posX, posY), lifetime, color);
	}
	
	public Particle(VectorF2D position, VectorF2D velocity, long lifetime, int color)
	{
		_position 	= position;
		_initialPosition = _position;
		_velocity 	= velocity;
		_lifetime 	= lifetime;
		//_color 		= color;
		_isDead		= false;
		_age		= 0;
	}
	
	public float getPositionX()
	{
		return _position.getX();
	}
	
	public float getPositionY()
	{
		return _position.getY();
	}
	
	public float getVelocityX()
	{
		return _velocity.getX();
	}
	
	public float getVelocityY()
	{
		return _velocity.getY();
	}
	
	public void setPositionX(float x)
	{
		_position.setX(x);
	}
	
	public void setPositionY(float y)
	{
		_position.setY(y);
	}
	
	public void setVelocityX(float x)
	{
		_velocity.setX(x);
	}
	
	public void setVelocityY(float y)
	{
		_velocity.setY(y);
	}
	
	public boolean isDead()
	{
		return _isDead;
	}
	
	public void kill()
	{
		_isDead = true;
	}
	public long getAge()
	{
		return _age;
	}
	
	public VectorF2D getPosition()
	{
		return _position;
	}
	
	public VectorF2D getVelocity()
	{
		return _velocity;
	}
	
	public float getInitialY()
	{
		return _initialPosition.getY();
	}
	
	public float getInitialX()
	{
		return _initialPosition.getX();
	}
	
	public int getAlpha()
	{
		return _alpha;
	}
	
	public void update(long elapsedTime)
	{
		_age += elapsedTime;
		
		//double percentLifetime = 1 - ((double) _age / (double) _lifetime);
		
		_alpha = 255;//(int) (255 * percentLifetime);
		
		if(_age >= _lifetime)
			_isDead = true;
	}
}
