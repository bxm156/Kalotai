package com.assembla.gpsmonsters.engine;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

/*	
 * Contains the main game loop in a separate thread.
 * The SurfaceView can accept drawings from this thread, while
 * a simple View cannot. This is more efficient for drawing,
 * and does not tie up the UI thread.
 */
public class DrawingThread extends Thread
{	
	private GameManager		_gameManager;
	private GamePanel[] 	_gamePanels;
	private GamePanel		gp;
	private SurfaceHolder	_surfaceHolder;
	private boolean 		_run;
	
	public DrawingThread(GameManager gameManager)
	{
		_gameManager = gameManager;
		this.setName("DrawingThread");
		_run = false;
	}
	
	public void setRunning(boolean isRunning)
	{
		_run = isRunning;
	}
	public boolean isRunning()
	{
		return _run;
	}
	//the main game loop
	//since we're writing to a screen buffer (contained in the surfaceholder),
	//we must first lock it and keep it in sync with the surfaceholder
	//object as we work
	@Override
	public void run()
	{
		Canvas c;
	    while (_run) 
	    {
	        c = null;
        	//_gamePanels = _gameManager.getGamePanels();
        	gp = _gameManager.getCurrentPanel();
        	if(gp == null)
        		break;
        	//for(GamePanel gp : _gamePanels)
        	//{
        		try 
        		{	
        			if(gp == null)
        				continue;
        			
	        		if(!gp.isActive())
	        			continue;
	        		
	        		
	        		
	        		  if(gp.getHolder() == null)
	        		  {
	        			  return;
	        		  }
	        		c = gp.getHolder().lockCanvas();
	        		//GL10 gl = (GL10) c.getGL();
		           try {
			            synchronized ( gp.getHolder()) 
			            {
			            	
			            	gp.update();
			                gp.draw(c);
			            }
		           } catch (Exception ex)
			        {
		        	   	Log.d("DT", "Exception caught in DrawingThread.java: " + ex.getMessage());
			        }
	        	}
        		catch(Exception ex){
        			Log.d("DT", "Exception caught in DrawingThread.java: " + ex.getMessage());
        			}
        		//we don't care if an error occurs, no matter what happens,
    	        //we want to unlock the canvas and display it
        		finally 
    	        {
        			_gamePanels = null;
    	            if (c != null) 
    	            {
    	            	try {
    	            		gp.getHolder().unlockCanvasAndPost(c);
    	            	} catch (Exception e)
    	            	{
    	            	}
    	            }
    	        }
        	//}
	    }
	}
}