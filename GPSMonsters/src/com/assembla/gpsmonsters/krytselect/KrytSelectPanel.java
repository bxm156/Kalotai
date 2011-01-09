package com.assembla.gpsmonsters.krytselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;

public class KrytSelectPanel extends GamePanel{

	public KrytSelectPanel(Context context, GameManagerInterface gameManager) 
	{
		super(context, gameManager);
	}	
		
	@Override
	public void update()
	{
		super.update();
	}
	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
		canvas.drawColor(Color.RED);
		
	}
	
	
}
