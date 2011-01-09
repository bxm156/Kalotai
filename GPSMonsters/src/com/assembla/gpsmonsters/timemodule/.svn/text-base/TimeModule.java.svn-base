// Matt DelBrocco
// Created 9/18/10
// Last Revision 9/18/10
// Used to get current time in various ways.

package com.assembla.gpsmonsters.timemodule;

import java.util.Calendar;
import java.util.Date;

public class TimeModule {
	private static String _timeString;  // 24h representation of current time in user's time zone
	private static int _minutes;
	private static int _hours;
	private static int _intTime; // used to calculate what timeOfDay we're in
	private static final int _DAWN = 0; // 0500-0629
	private static final int _MORNING = 1; // 0630-1159
	private static final int _AFTERNOON = 2; // 1200-1759
	private static final int _EVENING = 3; // 1800-2029
	private static final int _NIGHT = 4; // 2030-2399, 0000-0459
	
	// returns the current time in 24h HH:MM format
	public static String getTimeString()
	{
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		_minutes = c.get(Calendar.MINUTE);
		_hours = c.get(Calendar.HOUR_OF_DAY);
		_timeString = "" + _hours + ":" + _minutes;
		return _timeString;
	}
	
	// returns the "part" of the day we're in: morning, afternoon, etc. as in integer.
	public static int getTimeOfDay()
	{
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		_minutes = c.get(Calendar.MINUTE);
		_hours = c.get(Calendar.HOUR_OF_DAY);
		_intTime = _hours * 100 + _minutes;
		System.out.println(_intTime);
		if (_intTime < 500)
			return _NIGHT;
		else if (_intTime < 630)
			return _DAWN;
		else if (_intTime < 1200)
			return _MORNING;
		else if (_intTime < 1800)
			return _AFTERNOON;
		else if (_intTime < 2030)
			return _EVENING;
		else 
			return _NIGHT;
	}
}
