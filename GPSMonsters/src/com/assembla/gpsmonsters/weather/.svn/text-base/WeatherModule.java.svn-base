/*
 * WeatherModule.java
 * Created By Edward Yanosik 
 * 
 * Interfaces with National Weather Service's online API 
 */




package com.assembla.gpsmonsters.weather;

import com.assembla.gpsmonsters.engine.GameManager;
import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class WeatherModule{
	
	private String condition; 
	 
/***  These are all of the possibilities from Google, will break into necessary groups.
	private static final int CLEAR =1;
	private static final int CLOUDY =2;
	private static final int FOG =3;
	private static final int HAZE =4;
	private static final int LIGHT_RAIN =5;
	private static final int MOSTLY_CLOUDY =6;
	private static final int OVERCAST =7;
	private static final int PARTLY_CLOUDY =8;
	private static final int RAIN =9;
	private static final int RAIN_SHOWERS =10;
	private static final int SHOWERS =11;
	private static final int THUNDERSTORM =12; 
	private static final int CHANCE_OF_SHOWERS =13; 
	private static final int CHANCE_OF_SNOW =14;
	private static final int CHANCE_OF_STORM =15;
	private static final int MOSTLY_SUNNY = 16;
	private static final int PARTLY_SUNNY =17;
	private static final int SCATTERED_SHOWERS =18;
	private static final int SUNNY =19;
	private static final int OTHER = 20; //used in case return is of some unforeseen condition
***/
	int currentWeather;
	String zipCode;
	public  static final int CLEAR=1;
	public  static final int RAIN=2;
	public static final int SNOW=3;
	private GameManager _gameManager;
	
	public WeatherModule(GameManager gameManager)
	{
		_gameManager = gameManager;
		currentWeather = 1;	
		zipCode = Integer.toString(_gameManager.getLocationModule().getZipcode());
	}
	
	private void findCondition(String zipCode) throws SAXException, IOException, ParserConfigurationException 
	{
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder;

		 builder =  factory.newDocumentBuilder();
		 URL url = new URL("http://www.google.com/ig/api?weather=" + zipCode);
		 InputStream inputStream = url.openStream();
		 Document document= builder.parse(inputStream);
		 
		 NodeList nodeLst = document.getElementsByTagName("current_conditions");
		 Node firstNode = nodeLst.item(0);
		 NamedNodeMap map = firstNode.getFirstChild().getAttributes();
		 condition = map.item(0).getNodeValue();
		 
		 if((condition.equals("Light Rain"))||(condition.equals("Rain"))||(condition.equals("Rain Showers"))||(condition.equals("Showers"))||(condition.equals("Thunderstorm"))||(condition.equals("Chance of Showers"))||(condition.equals("Scattered Showers")))
		 {
			
			currentWeather = RAIN; 
		 }
		 else if((condition.equals("Chance of Snow"))||(condition.equals("Snow")) || (condition.equals("Flurries")) || (condition.equals("Sleet")) || (condition.equals("Icy")))
		 {
			currentWeather = SNOW; 
		 }
		 else
		 {
			currentWeather = CLEAR;
		 }
	 }
	
	public int getCondition()
	{
		try
		{
			this.zipCode = Integer.toString(_gameManager.getLocationModule().getZipcode());
			findCondition(this.zipCode);
		}
		catch (Exception e) 
		{
			e.printStackTrace(); 
			return 0;

		}
		return currentWeather;
	}
	
}
