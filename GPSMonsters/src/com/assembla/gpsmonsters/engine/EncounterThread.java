package com.assembla.gpsmonsters.engine;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class EncounterThread extends Thread {
	private boolean 		_run;
	private long 	_timeBetween;
	private Random rand;
	private long millisLeft;
	private Handler _handler;
	private Message toUI;
	public EncounterThread(Handler handler)
	{
		_handler = handler;
		_run = false;
		_timeBetween = 60000;
		setPriority(Thread.MIN_PRIORITY);
		rand = new Random(System.currentTimeMillis());
		_timeBetween = generateNewTime();
		this.setName("EncounterThread");
		
	}
	public long generateNewTime()
	{
		int i = rand.nextInt(2); //TODO Set to 31
		i += 1; // 30 to 60 as possible values //TODO set to 30
		i *= 1000; //Miliseconds -> Seconds
		i *= 60; // Seconds -> Minutes
		return (long)i;
	}
	public void setRunning(boolean isRunning)
	{
		_run = isRunning;
	}
	public boolean isRunning()
	{
		return _run;
	}
	@Override
	public void run()
	{
	 while (_run) 
	    {

		 try {
			sleepAtLeast(_timeBetween);
			} catch (InterruptedException e) {
				e.printStackTrace();
				//Because this is dangerious if we are not sleeping...
				stop(); //STOP, NO MATTER WHAT!
			}
	    }
	 
	}
	public void sleepAtLeast(long millis) throws InterruptedException {
		 if(millisLeft < 0)
		  {
			 	try {
		 		toUI = _handler.obtainMessage();
			 	toUI.what = 1;
				_handler.sendMessage(toUI);
			 	} catch (Exception e) {
			 		e.printStackTrace();
			 	}
				
				
				
				_timeBetween  = generateNewTime();
				millis = _timeBetween;
		  }
		  long t0 = System.currentTimeMillis();
		   millisLeft = millis;
		 
		  while (millisLeft > 0) {
			  Log.d("Encounter","Sleeping for: " + millisLeft + " ms...");
		    Thread.sleep(millisLeft); 
		    long t1 = System.currentTimeMillis();
		    millisLeft = millis - (t1 - t0);
		  }
		}
}
