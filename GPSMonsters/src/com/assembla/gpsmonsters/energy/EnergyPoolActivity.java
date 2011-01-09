package com.assembla.gpsmonsters.energy;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.engine.GameActivity;

public class EnergyPoolActivity extends GameActivity {
private EnergyPoolPanel _gamePanel;

private EnergyGestureListener _gestureListener;
private GestureDetector _gestureDetector;
private Dialog dialog;

private Vibrator _vibe;

private static final int ASSIGNWHITEDIALOG = 1;
    @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_gestureListener = new EnergyGestureListener();
		_gestureDetector = new GestureDetector(this,_gestureListener);
		_vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);

	}
    @Override
	public void onLoadGameManager()
    {
    	_gamePanel = new EnergyPoolPanel(this,_gameManager);
    	_gameManager.addGamePanel(_gamePanel);
    	setContentView(_gamePanel);
    	View textOverlay = getLayoutInflater().inflate(R.layout.energylayout,null,false);
    	ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
    	addContentView(textOverlay,layoutParams);
    	_gamePanel.setupLayout();
    	//Setup Gesture Listening

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent e){
    super.dispatchTouchEvent(e);
    return _gestureDetector.onTouchEvent(e);
    }

	
	public class EnergyGestureListener extends SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				
					_gamePanel.nextEnergy();
				
				return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					_gamePanel.prevEnergy();
					return true;
				}
			
			return false;
		}
		
		@Override
		public boolean onDoubleTap(MotionEvent e)
		{
			if (e.getX() > 100 && e.getX() < 400 && e.getY() > 100 && e.getY() < 400 && _gamePanel.getCurrentTank() == EnergyTank.WHITE) {
				showDialog(ASSIGNWHITEDIALOG);
				return true;
			}
			return false;
		}
		@Override
		public void onLongPress (MotionEvent e)
		{
			_vibe.vibrate(300);
			onDoubleTap(e);
		}
	}
	@Override
	protected Dialog onCreateDialog(int id) {
	   dialog =  new Dialog(this);
	    switch(id) {
	    case ASSIGNWHITEDIALOG:
	        // do the work to define the pause Dialog
	    	dialog.setOwnerActivity(this);
	    	dialog.setContentView(R.layout.assignwhiteenergydialog);
	    	dialog.setTitle("Convert Energy");
	    	ButtonListener bl = new ButtonListener();
	    	Spinner s = (Spinner) dialog.findViewById(R.id.energySpinner);
	    	Button plus = (Button) dialog.findViewById(R.id.energyconvertplusbutton);
	    	Button minus = (Button) dialog.findViewById(R.id.energyconvertminusbutton);
	    	Button cancel = (Button) dialog.findViewById(R.id.energyconvertcancelbutton);
	    	Button convert = (Button) dialog.findViewById(R.id.energyconvertbutton);
	    	
	    	plus.setOnClickListener(bl);
	    	minus.setOnClickListener(bl);
	    	cancel.setOnClickListener(bl);
	    	convert.setOnClickListener(bl);
	    	
	    	String[] arraySpinner = new String[] {"Health","Attack","Defense"};

			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,arraySpinner);
	    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	s.setAdapter(adapter);


	        break;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}
	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
		
			if(v == dialog.findViewById(R.id.energyconvertplusbutton)) {
				TextView t = (TextView) dialog.findViewById(R.id.energyamounteditabletext);
				int i = Integer.parseInt( t.getText().toString());
				
				if(i < _gamePanel.getMiscEnergyAmount())
				{
					i++;
				}
				t.setText("" + i);
			}
			if(v == dialog.findViewById(R.id.energyconvertminusbutton)) {
				TextView t = (TextView) dialog.findViewById(R.id.energyamounteditabletext);
				int i = Integer.parseInt( t.getText().toString());
				i--;
				if(i <= 0) {
					i = 1;
				}
				t.setText("" + i);
			}
			if(v == dialog.findViewById(R.id.energyconvertcancelbutton))
			{
				dialog.cancel();
			}
			if(v == dialog.findViewById(R.id.energyconvertbutton))
			{
				//Do the conversion!
				TextView t = (TextView) dialog.findViewById(R.id.energyamounteditabletext);
				int i = Integer.parseInt( t.getText().toString());
				
				if(i > _gamePanel.getMiscEnergyAmount() || i <= 0) {
					return;
				}
				
			 	Spinner s = (Spinner) dialog.findViewById(R.id.energySpinner);
			 	String result = (String) s.getSelectedItem();
			 	if(result == "Health"){
			 		_gamePanel.updateHealth(i);
			 		_gamePanel.updateMisc(-i);
			 	} else if(result == "Attack")
			 	{
			 		_gamePanel.updateAttack(i);
			 		_gamePanel.updateMisc(-i);
			 	} else if(result == "Defense")
			 	{
			 		_gamePanel.updateDefense(i);
			 		_gamePanel.updateMisc(-i);
			 	}
			 	else {
			 		
			 	}
			 	dialog.dismiss();
			}
		}
	}
	
}
