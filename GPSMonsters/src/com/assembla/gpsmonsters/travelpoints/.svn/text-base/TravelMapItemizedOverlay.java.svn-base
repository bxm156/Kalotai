package com.assembla.gpsmonsters.travelpoints;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class TravelMapItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private OverlayItem mapOverlays;// = new ArrayList<OverlayItem>();
	private TravelActivity _context;
	
	public TravelMapItemizedOverlay(Drawable defaultMarker, TravelActivity context) {
		super(boundCenterBottom(defaultMarker));
		  _context = context;
		}
	
	public TravelMapItemizedOverlay(Drawable defaultMarker) {
		//super(defaultMarker);
		super(boundCenterBottom(defaultMarker));
	}

	public void addOverlay(OverlayItem overlay) {
	    mapOverlays=overlay;
	    populate();
	}
	
	@Override
	protected boolean onTap(int index) {
	  
	  OverlayItem item = mapOverlays;
	  AlertDialog.Builder dialog = new AlertDialog.Builder(_context);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage("Do you want to travel here?");
	  dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
  	  {
			@Override
			public void onClick(DialogInterface dialog, int button)
			{
				_context.end(mapOverlays.getPoint().getLatitudeE6(),mapOverlays.getPoint().getLongitudeE6());
			}
  	});
  	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() 
  	{
			@Override
			public void onClick(DialogInterface dialog, int button)
			{
			}
  	});
	  dialog.show();
	  
	  return true;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mapOverlays;
	}
	
	public OverlayItem getItem()
	{
	return mapOverlays;
	}
	@Override
	public int size() {
		  return 1;
	}

}
