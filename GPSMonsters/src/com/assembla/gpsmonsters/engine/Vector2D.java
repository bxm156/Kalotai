/*	File: Vector2D.java
 * 	Author: Rob Karam 
 */

package com.assembla.gpsmonsters.engine;

/*
 *	Encapsulates a simple 2D Vector class with basic
 *	get/set methods. Could easily be expanded to
 *	do vector arithmetic, dot product, etc.
 */
public class Vector2D
{
	private int _x;
	private int _y;
	
	public Vector2D()
	{
		_x = 0;
		_y = 0;
	}
	
	public Vector2D(int x, int y)
	{
		_x = x;
		_y = y;
	}
	
	public Vector2D(char axis)
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
	
	public void setX(int value)
	{
		_x = value;
	}
	
	public void setY(int value)
	{
		_y = value;
	}
	
	public int getX()
	{
		return _x;
	}
	
	public int getY()
	{
		return _y;
	}
	public double getAngleBetween(Vector2D vect)
	{
		double dot = this.getX() * vect.getX() + this.getY() * vect.getY();
		double a = Math.sqrt((_x * _x) + (_y * _y));
		double vectX = vect.getX();
		double vectY = vect.getY();
		double b = Math.sqrt((vectX * vectX) + (vectY * vectY));
		double c = a * b;
		double result = Math.acos(dot/c);
		
		if(vectY < 0)
		{
			result = -result;
		}
		
		return result;
	}
	public double distanceBetween(int xIn, int yIn)
	{
		double x = xIn - getX();
		x = Math.pow(x, 2);
		double y = yIn - getY();
		y = Math.pow(y, 2);
		double sum = x + y;
		sum = Math.sqrt(sum);
		return sum;
	}
	
	public boolean vEquals(Vector2D other)
	{
		return(_x == other.getX() && _y == other.getY());
	}
}