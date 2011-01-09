package com.assembla.gpsmonsters.krytpedia;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameActivity;

public class KrytpediaMainActivity extends GameActivity {
	
	private DatabaseModule _databaseModule;
	private TableLayout table;
	private TextView story;
	private Typeface _face;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Bundle myBundle = getIntent().getExtras();
		 //_krytId = myBundle.getInt("krytId");
			setContentView(R.layout.krytpediahome);
			_face= Typeface.createFromAsset(getAssets(), "lithosproregula.ttf"); 
	}
    @Override
	public void onLoadGameManager()
    {
		//_databaseModule = _gameManager.getDatabaseModule();
		table = (TableLayout) findViewById(R.id.krytpediaTable);
        story = (TextView)findViewById(R.id.KrytpediaStory);
    	story.setTextSize(16);
    	story.setTypeface(_face);
    	
    	TextView title = (TextView)findViewById(R.id.KrytpediaTitle);
    	title.setTypeface(_face);
    	
    	Button creatures = (Button)findViewById(R.id.creaturesButton);
    	creatures.setTypeface(_face);
    	
    	Button story = (Button)findViewById(R.id.storyButton);
    	story.setTypeface(_face);
    	
    	Button tutorial = (Button)findViewById(R.id.tutorialButton);
    	tutorial.setTypeface(_face);

    }
    
    @Override
	 public void onDestroy()
	 {
    	super.onDestroy();
	 }
    
    public void kryts(View v)
    {    	
    	story.setVisibility(8);
    	table.setVisibility(0);
    	
    	_databaseModule = _gameManager.getDatabaseModule();
		_databaseModule.openDataBase();
		 ArrayList<ContentValues> creatureValues = _databaseModule.getKryts();
	 	_databaseModule.close();
		 
	   table.removeAllViews();
       for(ContentValues value : creatureValues)
		{
        	int id = Integer.parseInt(value.getAsString("id"));
        		
        	TableRow row = (TableRow)getLayoutInflater().inflate(R.layout.krytrow, table, false);
        	
        	int resourceId;
			
        	Resources resource;
			resource = this.getResources();
			resourceId = resource.getIdentifier("monster" + id, "drawable",this.getPackageName());
			Bitmap bitmapOrg = BitmapFactory.decodeResource(this.getResources(),resourceId);  
		        int width = bitmapOrg.getWidth();
		        int height = bitmapOrg.getHeight();
		        int newWidth = 75;
		        int newHeight = 75;
		        float scaleWidth = ((float) newWidth) / width;
		        float scaleHeight = ((float) newHeight) / height;
		        Matrix matrix = new Matrix();
		        matrix.postScale(scaleWidth, scaleHeight);
//		        matrix.postRotate(45);
		    Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		    BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        	
        	ImageView krytpic = (ImageView)row.findViewById(R.id.krytpic);
	        krytpic.setImageDrawable(bmd);

//        	krytpic.setImageResource(resourceId);
        	
        	TextView krytname = (TextView)row.findViewById(R.id.krytname);
        	krytname.setText(value.getAsString("name"));
        	krytname.setTypeface(_face);
        	
        	TextView krytlevel = (TextView)row.findViewById(R.id.krytlevel);
	        krytlevel.setText("");
	        krytlevel.setTypeface(_face);
	 
	        TextView kryth = (TextView)row.findViewById(R.id.kryth);
	        kryth.setText("");
	        kryth.setTypeface(_face);
	 
	        TextView kryta = (TextView)row.findViewById(R.id.kryta);
	        kryta.setText("");
	        kryta.setTypeface(_face);
	 
	        TextView krytd = (TextView)row.findViewById(R.id.krytd);
	        krytd.setText("");
	        krytd.setTypeface(_face);
        	
	        row.setId(id);
	        
 
        // add the TableRow to the TableLayout
	        table.addView(row);//,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
		
		}
    }
    
    
    public void krytSelect(View v)
    {
    	this.runKrytpedia(v.getId());
    }
    
    public void story(View v)
    {

    	story.setVisibility(0);
    	table.setVisibility(8);
    	story.setText("Long ago, there existed an ancient and advanced race of beings known as the Kalotai.  This mysterious race was around long before the rise of the Greeks, Egyptians, or Aztecs.  The Kalotai existed in between our physical world and an ethereal world overlapping ours called Kalotera.  For reasons still unknown, the Kalotai vanished, in an event known as the Pannu.  They left few traces in the physical world, though some clues of their existence did leak through, appearing in cultures around the world, influencing language, architecture, and other areas.  They also left behind the Kryts.");

    }
    
    public void tutorial(View v)
    {
    	this.runTutorial();
    }

}
