package com.assembla.gpsmonsters.storyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;


import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;
import com.assembla.gpsmonsters.R;

public class StoryBoardPanel extends GamePanel {
	protected int _index=0;
	private Bitmap [] _scene;
	private float y;
	protected GameManagerInterface _gameManager;
	private Rect _dst; 

	public StoryBoardPanel(Context context, GameManagerInterface gameManager) {
		super(context, gameManager);
		
		_index =0;
		
		_scene = new Bitmap[11];
		_scene[0]= BitmapFactory.decodeResource(getResources(), R.drawable.scene1);
		_scene[1]= BitmapFactory.decodeResource(getResources(), R.drawable.scene2);
		_scene[2]= BitmapFactory.decodeResource(getResources(), R.drawable.scene3);
		_scene[3]= BitmapFactory.decodeResource(getResources(), R.drawable.scene4);
		_scene[4]= BitmapFactory.decodeResource(getResources(), R.drawable.scene5);
		_scene[5]= BitmapFactory.decodeResource(getResources(), R.drawable.scene6);
		_scene[6]= BitmapFactory.decodeResource(getResources(), R.drawable.scene7);
		_scene[7]= BitmapFactory.decodeResource(getResources(), R.drawable.scene8);
		_scene[8]= BitmapFactory.decodeResource(getResources(), R.drawable.scene9);
		_scene[9]= BitmapFactory.decodeResource(getResources(), R.drawable.scene10);
		_scene[10]= BitmapFactory.decodeResource(getResources(), R.drawable.scene11);



		_dst = new Rect();
		_dst.set(0,0,getDisplayWidth(),getDisplayHeight());
		
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
		canvas.drawBitmap(_scene[_index], null, _dst , null);
	}
	
	public void nextIndex() {
		if(_index < _scene.length-1)
			_index++;
		else
			;
	}

	public void prevIndex() {
		if(_index > 0)
			_index--;
		else
			;
	}
		
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 
		 if(event.getAction() == MotionEvent.ACTION_DOWN) {
			 y = event.getY();
		 }
		 
		 if(event.getAction() == MotionEvent.ACTION_UP) {
				 if(event.getY() < y)
					 nextIndex();
				 else
					 prevIndex();
		 }
		 
	        event.recycle(); 

	    		return true;
	    	
	}
}
	
	