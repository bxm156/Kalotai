package com.assembla.gpsmonsters.tokryt;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.engine.GameActivity;


public class TokrytActivity extends GameActivity {
		private TokrytPanel _gamePanel;
		private int _krytId = 0; 
		
		
	    @Override
		 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle myBundle = getIntent().getExtras();
			 _krytId = myBundle.getInt("krytId");
				setContentView(R.layout.tokryt);
		}
	    @Override
		public void onLoadGameManager()
	    {
	    	_gamePanel = new TokrytPanel(this,_gameManager);
	    	_gameManager.addGamePanel(_gamePanel);
	    	setContentView(_gamePanel);
	    	View textOverlay = getLayoutInflater().inflate(R.layout.tokryt,null,false);
	    	ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
	    	addContentView(textOverlay,layoutParams);
	    	_gamePanel.setKrytId(_krytId);
	    }
	}


