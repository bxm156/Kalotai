package com.assembla.gpsmonsters.particleengine;


public class Path {

	public static final int LINEAR = 0;
	public static final int QUADRATIC = 1;
	public static final int SINE = 2;
		
	public static VectorF2D getParticlePosition(VectorF2D pos, VectorF2D vel, long dt, int pathType, boolean playerAttack)
	{
		float posX = pos.getX();
		float posY = pos.getY();
		float velX = vel.getX();
		float velY = vel.getY();
		
		switch(pathType)
		{
			case(LINEAR):
			{
				posY += velY;
				break;
			}
			case(QUADRATIC):
			{
				//dt *= 5;
				posY = -0.7f*(dt * dt) + 150;//-150;//velY * dt + (0.5f) * GRAVITY * dt * dt; //V0yT - 0.5 * 5 * gT^2
				break;
			}
			case(SINE):
			{
				velY *= 50;
				posY = velY * (float)Math.sin((double)(velY / (2 * Math.PI) * dt)) + 150;
				break;
			}
		}
		if(playerAttack)
			posX += velX;
		else
			posX -= velX;
		
		return new VectorF2D(posX, posY);
	}
}
