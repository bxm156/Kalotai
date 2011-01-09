package com.assembla.gpsmonsters.krytselect;

import java.util.ArrayList;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

public class KrytSelectActivity extends GameActivity
{
	int counter=0;
	Button btn;
	String testText;
	private int id;
	private DatabaseModule _databaseModule;
	private ArrayList<ContentValues> creatureValues;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) 
	 {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.krytselecttable);

	 }
	 
	 @Override
	public void onLoadGameManager()
	 {
			_databaseModule = _gameManager.getDatabaseModule();
			addRows();		 
	 }
	 
	 @Override
	 public void onDestroy()
	 {
	    super.onDestroy();   	
	 }
	    
	 @Override
	 public void onRestart()
	 {
	    super.onRestart();	
	 }
	 		
	public void addRows(){
		_databaseModule.openDataBase();
		creatureValues = _databaseModule.getPlayerKryts();
	
		
				 	TableLayout table = (TableLayout) findViewById(R.id.krytTable);
				 	table.removeAllViews();
			        for(ContentValues value : creatureValues)
					{
			        	id = Integer.parseInt(value.getAsString("id"));
			        	int krytId = Integer.parseInt(value.getAsString("krytId"));
			        		
			        	TableRow row = (TableRow)getLayoutInflater().inflate(R.layout.krytrow, table, false);
			        	
			        	int resourceId;
						Resources resource;
						resource = this.getResources();
						resourceId = resource.getIdentifier("monster" + krytId, "drawable",this.getPackageName());
						Bitmap bitmapOrg = BitmapFactory.decodeResource(this.getResources(),resourceId);  
					        int width = bitmapOrg.getWidth();
					        int height = bitmapOrg.getHeight();
					        int newWidth = 100;
					        int newHeight = 100;
					        float scaleWidth = ((float) newWidth) / width;
					        float scaleHeight = ((float) newHeight) / height;
					        Matrix matrix = new Matrix();
					        matrix.postScale(scaleWidth, scaleHeight);
//					        matrix.postRotate(45);
					    Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
					    BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
			        	
			        	ImageView krytpic = (ImageView)row.findViewById(R.id.krytpic);
				        krytpic.setImageDrawable(bmd);

//			        	krytpic.setImageResource(resourceId);
			        	
			        	TextView krytname = (TextView)row.findViewById(R.id.krytname);
			        	krytname.setText(value.getAsString("name"));
			        	
			        	TextView krytlevel = (TextView)row.findViewById(R.id.krytlevel);
				        krytlevel.setText(" Level:" + value.getAsString("level"));
				 
				        TextView kryth = (TextView)row.findViewById(R.id.kryth);
				        kryth.setText(" H:" + value.getAsString("currentHealth"));
				 
				        TextView kryta = (TextView)row.findViewById(R.id.kryta);
				        kryta.setText(" S:" + value.getAsString("strength"));
				 
				        TextView krytd = (TextView)row.findViewById(R.id.krytd);
				        krytd.setText(" E:" + value.getAsString("endurance"));
			        	
				        row.setId(id);
				        row.setOnLongClickListener(new View.OnLongClickListener() {
	                        @Override
							public boolean onLongClick(View v) {
	                        		
	                        	longClick(v.getId());
	                        	
	                        	return true;
	                        	
	                        	}
				        });
			 
			        // add the TableRow to the TableLayout
			        table.addView(row);//,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					}
					_databaseModule.close();

			 
		}
	
	public void longClick(int id)
	{
		this.runSelectedKryt(id);
		
	}
	
	public void getCreatures()
	{		
		_databaseModule.openDataBase();
		creatureValues = _databaseModule.getPlayerKryts();
		
		_databaseModule.close();
	}

	public void close(View v)
    {
		//setResult(Activity.RESULT_OK, null);
		finish();
    }
	
    public void krytSelect(View v)
    {
		id = v.getId();
		int krytId=0;
		_databaseModule.openDataBase();
		creatureValues = _databaseModule.getPlayerKryts();
		
		_databaseModule.close();
		
		
		for(ContentValues value : creatureValues)
		{
			if(id==Integer.parseInt(value.getAsString("id")))
					krytId = Integer.parseInt(value.getAsString("krytId"));
			else
				;
		}
		
		Resources resource;
		resource = this.getResources();
		int resourceId = resource.getIdentifier("monster" + krytId, "drawable",this.getPackageName());
		
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    	alertDialog.setTitle("Move Kryt?");
    	alertDialog.setIcon(resourceId);
    	alertDialog.setMessage("Do you want to make this your active Kryt?");
    	alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
    	{
			@Override
			public void onClick(DialogInterface dialog, int button)
			{
				Intent resultIntent = new Intent();
				resultIntent.putExtra("selectedId", id);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
    	});
    	alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() 
    	{
			@Override
			public void onClick(DialogInterface dialog, int button)
			{
			}
    	});
    	
    	AlertDialog ad = alertDialog.create();
        ad.show();
    }
    
	 
	 }

