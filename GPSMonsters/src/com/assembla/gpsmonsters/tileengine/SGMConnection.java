package com.assembla.gpsmonsters.tileengine;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.location.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

public class SGMConnection extends Thread {
	
	private String _sgmURL;
	private boolean _hasGMap = false;
	//private char[][] _currentGameMap;
	private GameManagerInterface _gameManager;
	private LocationModule _locationModule;
	private DatabaseModule _databaseModule;
	private MapFactory _mapFactory;
	private DecimalFormat _df;
	
	private boolean _isRunning = false;
	
	public SGMConnection(GameManagerInterface gm, MapFactory mapFactory)
	{
		this.setName("SGM Thread");
		_sgmURL = "http://maps.google.com/maps/api/staticmap?zoom=" +
					"18&size=400x400&maptype=satellite&sensor=true&format" + 
					"=jpp&center=";
		
		_gameManager = gm;
		_locationModule = _gameManager.getLocationModule();
		_databaseModule = _gameManager.getDatabaseModule();
		_mapFactory = mapFactory;
		_df = new DecimalFormat("#0.0000");
	}

	@Override
	public void run()
	{
		while(_isRunning)
		{
			//TODO: ELAPSE TIME BETWEEN CHECKING THE DATABASE
			
			
			/// DO NOT ELAPSE TIME HERE!
			if(!_hasGMap) // && NOT IN DATABASE
			{
 				_hasGMap = tryGetMap(_locationModule.getLocationString());
				//_isRunning = !_hasGMap;
			}
			if(_locationModule._needsNewMap)
			{
				_hasGMap = false;
				_locationModule._needsNewMap = false;
			}
		}
	}
	
	public void setRunning(boolean run)
	{
		_isRunning = run;
	}
	
	public boolean tryGetMap(String locationString)
	{
		if(locationString.equals(""))
			return false;
		
		double lat = _locationModule.getLatitude();
		double lon = _locationModule.getLongitude();		

		
		String sLat = _df.format(lat);
		String sLon = _df.format(lon);
		
		float fLat = Float.parseFloat(sLat);
		float fLon = Float.parseFloat(sLon);
		
		_databaseModule.openDataBase();
		String terrain = _databaseModule.getGPSTerrain(fLat, fLon);
		_databaseModule.close();
		if(terrain != null && terrain.length() > 0)
		{
			String[] sRows = terrain.split("0");
			int length = sRows.length;
			char[][] map = new char[length][length];
			for(int i = 0; i < length; i++)
			{
				String row = sRows[i];
				for(int j = 0; j < length; j++)
				{
					map[i][j] = row.charAt(j);
				}
				
			}
			_mapFactory.setMap(map);

			return true;
		}
		Bitmap maptile = null;
		
		String locString = _sgmURL + locationString;
		
		try 
		{
			@SuppressWarnings("unused")
			InputStream inputStream = null;
			 URL url = new URL(locString);
			 URLConnection conn = url.openConnection();

			  HttpURLConnection httpConn = (HttpURLConnection)conn;
			  httpConn.setRequestMethod("GET");
			  httpConn.connect();

			  if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
			  {
				  inputStream = httpConn.getInputStream();
			  }
			  else
				  return false;
			 
			//InputStream mapStream = (InputStream) new URL(locString).getContent();
			  
			 
			maptile = BitmapFactory.decodeStream(httpConn.getInputStream());
			
			/*String path = Environment.getExternalStorageDirectory().toString();
			FileOutputStream fileOutputStream = new FileOutputStream(path + "/testgmap.jpg");
			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
			
	        maptile.compress(Bitmap.CompressFormat.JPEG, 85, bos);
	        bos.flush();
	        bos.close();
			*/
	        processImage(maptile);
			
			_locationModule._mapCenterLat = _locationModule.getLatitude();
			_locationModule._mapCenterLon = _locationModule.getLongitude();
			
			Log.d("MAP", "Got map at (" + _locationModule._mapCenterLat + ", " + _locationModule._mapCenterLon + ")");
			
			return true;
		} 
		catch (MalformedURLException e) { return false; } 
		catch (IOException e) 
		{ 
		
			Log.d("trygetmap", "Problem" + e.getMessage());
			return false;
		}
	}
	
	private void processImage(Bitmap inMap)
	{
 		int stride = 19;
		int width, height, color;
		float tempR, tempG, tempB;
		
		
		width = inMap.getWidth();
		height = inMap.getHeight();
		color = 0;
		tempR = tempG = tempB = 0;
		
		int r, g, b;
		r = g = b = 0;
	
		char[][] mapMatrix = new char[width / stride][height / stride];
		
		for(int i = 0; i < width - stride; i += stride)
		{
			for(int j = 0; j < height - stride; j += stride)
			{
				int y = 0;
				for(int x = 0; x < stride; x++)
				{
					color = inMap.getPixel(i, j);//result[x][y];//

					tempR += Color.red(color);
					tempG += Color.green(color);// * 1.1f;
					tempB += Color.blue(color);// * 1.16f;
					
					y++;
				}
				
				tempR /= y;
				tempG /= y;
				tempB /= y;
				
				if(tempR > tempG + 5 && tempR > tempB + 5)
				{
					mapMatrix[i / stride][j / stride] = 'r';
					r++;
				}
				else if (tempG > tempR - 5 && tempG > tempB - 5)
				{
					mapMatrix[i / stride][j / stride] = 'g';
					g++;
				}
				else
				{
					mapMatrix[i / stride][j / stride] = 'b';
					b++;
				}
				tempR = tempG = tempB = 0;
			}
		}
		
		String terrain = "";
		for(int i = 0; i < mapMatrix.length; i++)
		{
			char[] row = mapMatrix[i];
			for(int j = 0; j < row.length; j++)
			{
				terrain += mapMatrix[i][j];
			}
			terrain += "0";
		}
		String sLat = _df.format(_locationModule.getLatitude());
		String sLon = _df.format(_locationModule.getLongitude());
		float fLat = Float.parseFloat(sLat);
		float fLon = Float.parseFloat(sLon);
		
		_databaseModule.openDataBase();
		_databaseModule.insertGPSTerrain(fLat, fLon, terrain);
		_databaseModule.close();
		Log.d("terrain", terrain);
		_mapFactory.setMap(mapMatrix);
		
	}
	
	public void reInitMap()
	{
		_isRunning = true;
		_hasGMap = false;
	}
}
