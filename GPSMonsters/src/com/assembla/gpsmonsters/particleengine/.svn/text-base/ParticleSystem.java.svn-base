package com.assembla.gpsmonsters.particleengine;


import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.engine.Sprite2D;
import com.assembla.gpsmonsters.particleengine.VectorF2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class ParticleSystem {
	
	public static final int NEUTRAL		= 0;
	public static final int FIRE 		= 1;
	public static final int WATER		= 2;
	public static final int ICE		= 3;
	public static final int ELECTRIC	= 4;
	public static final int VOID		= 5;
	public static final int CRAZY		= 6;
	public static final int MAX_TYPE	= 6;
	
	private Stopwatch				_systemTime;	//TODO: set this up in the code, use it for the particles
	private int						_particlePath;
	private Particle				_particle1, _particle2, _particle3;
	private Sprite2D				_sprite1;
	private Sprite2D				_sprite2;
	private Sprite2D				_sprite3;
	private VectorF2D				_position1, _position2, _position3;
	private VectorF2D				_velocity;
	boolean							_playerAttack;
	Bitmap							_particleImage;
	
	private int						_viewHeight;
	
	private float					_rotationAngle;
	
	//private List<Particle>			_particleList;

	public ParticleSystem(	Context context, int viewHeight, int viewWidth, 
							VectorF2D startPosition, VectorF2D startVelocity,
							int systemType, int pathType, boolean playerAttack)
	{
		_systemTime = new Stopwatch();
		_playerAttack = playerAttack;
		_rotationAngle = 0.0f;
		_position1 = startPosition;
		_velocity = startVelocity;
		
		//initialize all system variables here	
		if(systemType > MAX_TYPE)
			systemType = NEUTRAL;
		
		_viewHeight = viewHeight;
		
		switch(systemType)
		{
			case(NEUTRAL):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_neutral);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_neutral);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_neutral);
				break;
			}
			case(FIRE):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_fire);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_fire);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_fire);
				break;
			}
			case(WATER):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_water);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_water);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_water);
				break;
			}
			case(ICE):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_ice);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_ice);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_ice);
				break;
			}
			case(ELECTRIC):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_electric);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_electric);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_electric);
				break;
			}
			case(VOID):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_void);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_void);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_void);
				break;
			}
			case(CRAZY):
			{
				_sprite1 = new Sprite2D(context.getResources(), R.drawable.attack_fire);
				_sprite2 = new Sprite2D(context.getResources(), R.drawable.attack_electric);
				_sprite3 = new Sprite2D(context.getResources(), R.drawable.attack_ice);
				break;
			}
		}
		//_particleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.particle_red);
		//_particleList = new ArrayList<Particle>();
		/*int maxHeight = 60;
		for(int i = 0; i < 100; i++)
		{
			float startX = _position1.getX() - 32;
			float startY = _position1.getY() + 32;
			startX += 5 * rand.nextFloat();
			startY += maxHeight * (float)rand.nextGaussian();
			_particleList.add(new Particle(new VectorF2D(startX, startY), startVelocity, 5000, Color.RED));
			if(maxHeight < 10) maxHeight = 10;
			maxHeight -= 5;
		}*/
		_position1.setY(_position1.getY());
		_position2 = new VectorF2D(_position1.getX() - 64, _position1.getY() - 8);
		_position3 = new VectorF2D(_position1.getX() - 128, _position1.getY() + 4);
		
		if(!playerAttack)
		{
			_position1.setX(_viewHeight - _position1.getX());
			_position2.setX(_viewHeight - _position2.getX());
			_position3.setX(_viewHeight - _position3.getX());
		}

		_sprite1.setTranslate((float)_position1.getX(), (float)_position1.getY());
		_sprite2.setTranslate((float)_position2.getX(), (float)_position2.getY());
		_sprite3.setTranslate((float)_position3.getX(), (float)_position3.getY());
		
		if(pathType > Path.SINE)
		{
			_particlePath = Path.LINEAR;
		}
		else
		{
			_particlePath = pathType;
		}
		
		_particle1 = new Particle(_position1, startVelocity, 50000, Color.BLACK);
		_particle2 = new Particle(_position2, startVelocity, 50000, Color.BLACK);
		_particle3 = new Particle(_position3, startVelocity, 50000, Color.BLACK);
		
		_systemTime.startTimer();
	}

	public boolean isAlive()
	{
		return _particle3.isDead();
	}
	
	public void Draw(Canvas c)
	{	
		/*for(Particle p : _particleList)
		{
			c.drawBitmap(_particleImage, p.getPositionX(), p.getPositionY(), null);
		}*/
		if(!_particle1.isDead())
			_sprite1.draw(c);
		if(!_particle2.isDead())
			_sprite2.draw(c);
		if(!_particle3.isDead())
			_sprite3.draw(c);
	}
	
	public void Update()
	{
		if(_particle3.isDead())
			return;
		long elapsedTime = _systemTime.getElapsed_mS();// / 1000;
		_particle1.update(elapsedTime);
		_particle2.update(elapsedTime);
		_particle3.update(elapsedTime);
		/*int size = _particleList.size();
		Particle p;
		VectorF2D pNewPos;
		for(int i = 0; i < size; i++)
		{
			p = _particleList.get(i);
			if(_particle1.isDead())
			{
				_particleList.remove(i);
				i--;
				continue;
			}
			pNewPos = Path.getParticlePosition(p.getPosition(), p.getVelocity(), elapsedTime, Path.LINEAR, true);
			p.setPositionX(pNewPos.getX());
			p.setPositionY(pNewPos.getY());
		}*/
		
		VectorF2D newPos= Path.getParticlePosition(_position1, _velocity, elapsedTime, _particlePath, _playerAttack);
		VectorF2D newPos2 = Path.getParticlePosition(_position2, _velocity, elapsedTime, _particlePath, _playerAttack);
		VectorF2D newPos3 = Path.getParticlePosition(_position3, _velocity, elapsedTime, _particlePath, _playerAttack);
		
		_sprite1.setTranslate(newPos.getX(), newPos.getY());		
		_sprite2.setTranslate(newPos2.getX(), newPos2.getY());
		_sprite3.setTranslate(newPos3.getX(), newPos3.getY());
		
		_position1 = newPos;
		_position2 = newPos2;
		_position3 = newPos3;
		
		_sprite1.setRotation(_rotationAngle);
		_sprite2.setRotation(_rotationAngle);
		_sprite3.setRotation(_rotationAngle);
		
		_rotationAngle -= 15f;
		
		//		_particle1.setPositionX(newPos.getX());
//		_particle1.setPositionY(newPos.getY());
		
	}
	
}
