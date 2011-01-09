package com.assembla.gpsmonsters.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/*
 * 	This is where the magic happens. The normal activities cannot (as far as i know)
 * 	allow the user to arbitrarily move things on screen, so we need to create a
 * 	custom view that extends either View or SurfaceView.
 * 
 * 	SurfaceView is better for games--it allows you to draw on screen using
 * 	a background thread, and is generally more efficient at doing so, besides
 * 	the fact that the UI thread will not be tied up!
 * 
 */
public abstract class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
	
	private boolean isActive;
	protected int _viewHeight;
	protected int _viewWidth;
	protected GameManagerInterface _gameManager;
	protected Activity _context;
	private List<GamePanelComponent> _gamePanelComponents = new ArrayList<GamePanelComponent>();
	private Display _display;
	public GamePanel(Context context, GameManagerInterface gameManager) {
		super(context);
		_gameManager = gameManager;
		_context = (Activity)context;
		getHolder().addCallback(this);
		setFocusable(true);		
		WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
		Display d = wm.getDefaultDisplay();
		_display = d;
		_viewHeight = d.getHeight();
		_viewWidth = d.getWidth();
		
		
	}
	
	public int getDisplayHeight()
	{
		return _viewHeight;
	}
	
	public int getDisplayWidth()
	{
		return _viewWidth;
	}

	public Display getDisplay()
	{
		return _display;
	}
	protected void cleanup()
	{
		return;
	}
	/*
	 *	Encapsulates a Touch event. use GetAction() to see the type of event and
	 *	compare it to the constants in the MotionEvent class.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		
		
		return true;
	}
	
	/*
	 *	Draw the game screen.
	 */
	@Override
	public synchronized void draw(Canvas canvas)
	{
		//canvas.drawColor(Color.WHITE);
		
	}
	
	/*
	 * Contains update logic for the game.
	 * 
	 * Update the ball position and check for collisions.
	 */
	public synchronized void update()
	{
		
		
	
	}
	
	/*
	 *	DO NOT MODIFY
	*/
	@Override
	public final void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		
	}
	
	/*	
	 * DO NOT MODIFY
	*/
	@Override
	public final void surfaceCreated(SurfaceHolder holder) 
	{
		isActive = true;
	}
	 
	/*	
	 *	DO NOT MODIFY
	*/
	@Override
	public final void surfaceDestroyed(SurfaceHolder holder) 
	{
		isActive = false;
		_gameManager.removeGamePanel(this);
		cleanup();
	}
	
	/*
	 * Returns whether or not this panel is Active
	 */
	public final  boolean isActive()
	{
		return isActive;
	}

	public final void addGamePanelCompontent(GamePanelComponent gpc)
	{
		_gamePanelComponents.add(gpc);
		Collections.sort(_gamePanelComponents);
	}
	public final void removeGamePanelComponent(GamePanelComponent gpc)
	{
		_gamePanelComponents.remove(gpc);
		Collections.sort(_gamePanelComponents);
	}
}
