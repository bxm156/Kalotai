package com.assembla.gpsmonsters.gamepanels;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

//This is the magic that makes the battle work in terms of displaying scene and allowing gesture input
//Currently hard coded for display and receipt of input but should be fixed to be more general at some point
//No reason this should need any changes other than that
public class BattleViewGroup extends ViewGroup {

	public BattleViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime){
		//this.getChildAt(0).draw(canvas);
		this.getChildAt(0).draw(canvas);
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent e){
		return this.getChildAt(0).dispatchTouchEvent(e);
	}
}
