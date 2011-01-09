package com.assembla.gpsmonsters.engine;

import com.assembla.gpsmonsters.R;

import android.app.Activity;
import android.content.Context;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.*;

public class GameSlidingDrawer implements OnDrawerCloseListener {

	private Activity activity;
	private SlidingDrawer slidingDrawer;
	
	public GameSlidingDrawer(Context context)
	{
		activity = (Activity)context;
        this.slidingDrawer= (SlidingDrawer) activity.findViewById(R.id.GameSlidingDrawer_slidingDrawer);
	}
	
	public SlidingDrawer getSlidingDrawer()
	{
		return slidingDrawer;
	}
	
	@Override
    public void onDrawerClosed() 
	{
		this.slidingDrawer.bringToFront();
    }
   
	/*
	private SlidingDrawer slidingDrawer;		// the picture sliding drawer
    private Button slideHandleButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.slidingdrawer);
    	slideHandleButton = (Button) findViewById(R.id.GameSlidingDrawer_handle);
    	slidingDrawer = (SlidingDrawer) findViewById(R.id.GameSlidingDrawer_slidingDrawer);

    	slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

    	@Override
    	public void onDrawerOpened() 
    	{
    	slideHandleButton.setBackgroundResource(R.drawable.testdrawer);
    	}
    	
    	});

    	slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

    	@Override
    	public void onDrawerClosed() 
    	{
    	slideHandleButton.setBackgroundResource(R.drawable.testdrawer);
    	}

    	});
	
    }
    */
}
