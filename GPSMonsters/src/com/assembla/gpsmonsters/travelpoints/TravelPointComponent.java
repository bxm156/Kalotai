package com.assembla.gpsmonsters.travelpoints;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import java.lang.Math;

import com.assembla.gpsmonsters.engine.GamePanel;
import com.assembla.gpsmonsters.engine.GamePanelComponent;
import com.assembla.gpsmonsters.travelpoints.TravelpointsModule;


public class TravelPointComponent extends GamePanelComponent {
	private Rect _rect;
	private Paint _blackPaint;
	private Paint _whitePaint;
	private TravelpointsModule _travelpointsModule;
	private int _travelpoints;
	
	@SuppressWarnings("unused")
	private GamePanel _parentPanel;
	public TravelPointComponent(GamePanel panel, TravelpointsModule module)
	{
		super();
		_parentPanel = panel;
		_travelpointsModule = module;
		_rect = new Rect();
		_rect.left = 300;
		_rect.top = 0;
		_rect.right = 425;
		_rect.bottom = 40;
		_blackPaint = new Paint();
		_whitePaint = new Paint();
		_blackPaint.setColor(Color.BLACK);
		_whitePaint.setColor(Color.WHITE);
		
		_travelpoints = _travelpointsModule.getTravelPoints();
		

		
	}
	@Override
	public void update()
	{
		_travelpoints = _travelpointsModule.getTravelPoints();
	}
	
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(_rect, _blackPaint);
		//canvas.drawText("Travel Points: " + _travelpointsModule.getTravelPoints(), 310, 30, _whitePaint);
		canvas.drawText( "#: " + _travelpointsModule.getOldLatitude() + " / " +  _travelpointsModule.getLatitude() + " / " +  _travelpointsModule.getOldLongitude() + " / " + _travelpointsModule.getLongitude() + " / " +  _travelpointsModule.getTravelPoints(), 0, 30, _whitePaint);
		//canvas.drawText("Travel Points: 1337", 310, 30, _whitePaint);
	}
}
