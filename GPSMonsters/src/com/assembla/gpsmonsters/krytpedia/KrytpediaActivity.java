package com.assembla.gpsmonsters.krytpedia;

import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;


import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameActivity;

public class KrytpediaActivity extends GameActivity {
	private int _krytId = 0; 
	private DatabaseModule _databaseModule;
	private String _name;
	private String _description;
	private String _type;
	
	
    @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle myBundle = getIntent().getExtras();
		 _krytId = myBundle.getInt("krytId");
		setContentView(R.layout.krytpedia);

	}
    @Override
	public void onLoadGameManager()
    {
    	_databaseModule = _gameManager.getDatabaseModule();
    	getInformation();
    	
    }
    
    public void getInformation()
	{
		_databaseModule.openDataBase();
		
		ContentValues result = _databaseModule.getKrytByID(_krytId);
		if(result.getAsBoolean("success"))
		{
			_name = result.getAsString("name");
			_type = result.getAsString("type");
			_description = result.getAsString("description");
			
			// Get the right image based on the naming convention
			Resources resource;
			int resourceId;
			resource = getResources();
			resourceId = resource.getIdentifier("monster" + _krytId, "drawable",getPackageName());
			ImageView image;
			image = (ImageView) findViewById(R.id.CreatureImage);
			image.setImageResource(resourceId);			
			
			//Pull Font from assets
			Typeface face= Typeface.createFromAsset(getAssets(), "lithosproregula.ttf"); 
			
			//Title
			TextView title;
			title = (TextView) findViewById(R.id.KrytpediaTextViewTitle);
			title.setTypeface(face);
			title.setTextSize(40f);
			
			//Name
			TextView name;
			name = (TextView) findViewById(R.id.KrytpediaTextViewName);
			name.setTypeface(face);
			name.setText(_name);
			name.setTextSize(24f);
			
			//Type
			TextView type;
			type = (TextView) findViewById(R.id.KrytpediaTextViewType);
			type.setText("Type: " + _type);
			type.setTypeface(face);
			
			
			//Description label
			TextView descrLabel;
			descrLabel = (TextView) findViewById(R.id.KrytpediaTextViewDescription);
			descrLabel.setTypeface(face);
			
			//Description Content
			TextView description;
			description = (TextView)  findViewById(R.id.KrytpediaTextViewDescriptionContent);
			description.setText(_description);
			description.setTypeface(face);
			
		} else {
			//Problem!
		}
		_databaseModule.close();
	}
       
    
}
