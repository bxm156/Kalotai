package com.assembla.gpsmonsters.particleengine;

public class Stopwatch {
    
    private long _startTime = 0;
    private long _endTime = 0;
    private boolean _isRunning = false;

    public void startTimer() 
    {
        _startTime = System.currentTimeMillis();
        _isRunning = true;
    }

    
    public void stopTimer() 
    {
        _endTime = System.currentTimeMillis();
        _isRunning = false;
    }

    public long getElapsed_mS() 
    {
        long elapsed = 0;
        
        if (_isRunning) 
        {
             elapsed = (System.currentTimeMillis() - _startTime);
        }
        else 
        {
            elapsed = (_endTime - _startTime);
        }
        
        return elapsed;
    }

}