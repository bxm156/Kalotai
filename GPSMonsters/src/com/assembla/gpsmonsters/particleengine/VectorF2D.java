package com.assembla.gpsmonsters.particleengine;

public class VectorF2D {
	/*
	 *	Encapsulates a simple 2D Vector class with basic
	 *	get/set methods using floating point variables for
	 *	vector positions.
	 */
	
	private float _x;
	private float _y;
		
		public VectorF2D()
		{
			_x = 0;
			_y = 0;
		}
		
		public VectorF2D(float x, float y)
		{
			_x = x;
			_y = y;
		}
		
		public VectorF2D(char axis)
		{
			if(axis == 'x')
			{
				_x = 1;
				_y = 0;
			}
			if(axis == 'y')
			{
				_x = 0;
				_y = 1;
			}
		}
		
		public void setX(float value)
		{
			_x = value;
		}
		
		public void setY(float value)
		{
			_y = value;
		}
		
		public float getX()
		{
			return _x;
		}
		
		public float getY()
		{
			return _y;
		}
}