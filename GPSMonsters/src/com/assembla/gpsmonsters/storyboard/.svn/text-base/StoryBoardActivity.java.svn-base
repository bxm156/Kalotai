package com.assembla.gpsmonsters.storyboard;

import android.os.Bundle;


import com.assembla.gpsmonsters.engine.GameActivity;


public class StoryBoardActivity extends GameActivity{

	private StoryBoardPanel _gamePanel;
	
    @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   }
	    
	    @Override
		public void onLoadGameManager()
	    {
	    	_gamePanel = new StoryBoardPanel(this,_gameManager);
	    	_gameManager.addGamePanel(_gamePanel);
	    	setContentView(_gamePanel);
	    }



}





