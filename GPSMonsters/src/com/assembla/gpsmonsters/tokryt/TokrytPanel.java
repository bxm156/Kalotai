package com.assembla.gpsmonsters.tokryt;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.TextView;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.database.DatabaseModule;
import com.assembla.gpsmonsters.engine.GameManagerInterface;
import com.assembla.gpsmonsters.engine.GamePanel;

public class TokrytPanel extends GamePanel {
		private GameManagerInterface _gameManager;
		private DatabaseModule _databaseModule;
		private int _id;
		
		private int _krytId;
		private String _name;
		private String _nickname;
		private String _type;
		private int _level;
		private float _health;
		private int _experience;
		private int _constitution;
		private int _strength;
		private int _endurance;
		private int _agility;
		private int _intelligence;
		private int _wisdom;
		private int _charisma;
		
		private Bitmap _profilePic;

		private Bitmap _background;
		private Paint _white;
		private Rect _srcBackground;
		private Rect _dstBackground;
		private Rect _srcProfile;
		private Rect _dstProfile;
		
		private static final int PICSIZE = 400;
		
		public TokrytPanel(Context context, GameManagerInterface gameManager) {
			super(context, gameManager);
			
			_gameManager = gameManager;
			_databaseModule = _gameManager.getDatabaseModule();
			
			_background = BitmapFactory.decodeResource(_context.getResources(),R.drawable.krytpedia_background);
			_white = new Paint();
			_white.setColor(Color.WHITE);
			_srcBackground = new Rect();
			_dstBackground = new Rect();
			_srcProfile = new Rect();
			_dstProfile = new Rect();
			_srcBackground.top = 0;
			_srcBackground.left = 0;
			_srcBackground.right = _background.getWidth();
			_srcBackground.bottom = _background.getHeight();
			
			_dstBackground.top = -1; //Ya.. it should be 0, but a 0 gives a white line
			_dstBackground.left = 0;
			_dstBackground.right = _viewWidth;
			_dstBackground.bottom = _viewHeight;
			

			
			
		}
		public void setKrytId(int id)
		{
			if(id != 0)
			{
				_id = id;
				getInformation();
			}
		}
		public void getInformation()
		{
			_databaseModule.openDataBase();
			
			ContentValues result = _databaseModule.getPlayerKrytById(_id);
			if(result.getAsBoolean("success"))
			{
				_krytId = result.getAsInteger("krytId");
				_name = result.getAsString("name");
				_nickname = result.getAsString("nickname");
				_type = result.getAsString("type");
		
				_level = result.getAsInteger("level");
				_health = result.getAsFloat("currentHealth");
				_experience = result.getAsInteger("experience");
				_constitution = result.getAsInteger("constitution");
				_strength = result.getAsInteger("strength");
				_endurance = result.getAsInteger("endurance");
				_agility = result.getAsInteger("agility");
				_intelligence = result.getAsInteger("intelligence");
				_wisdom = result.getAsInteger("wisdom");
				_charisma = result.getAsInteger("charisma");
				
			
				// Get the right image based on the naming convention
				Resources resource;
				int resourceId;
				resource = _context.getResources();
				resourceId = resource.getIdentifier("monster" + _krytId, "drawable",_context.getPackageName());
				_profilePic = BitmapFactory.decodeResource(_context.getResources(),resourceId);
				
				_srcProfile.top = 0;
				_srcProfile.left = 0;
				_srcProfile.right = _profilePic.getWidth();
				_srcProfile.bottom = _profilePic.getHeight();
				
				_dstProfile.top = 100;
				_dstProfile.left = 20;
				_dstProfile.right = 20 + PICSIZE;
				_dstProfile.bottom = 100 +PICSIZE;
				
				//Pull Font from assets
				Typeface face= Typeface.createFromAsset(_context.getAssets(), "lithosproregula.ttf"); 
				
				//Name
				TextView name;
				name = (TextView) _context.findViewById(R.id.TokrytTextViewName);
				name.setTypeface(face);
				name.setText(_name);
				name.setTextSize(40f);
				//Name
				TextView nickname;
				nickname = (TextView) _context.findViewById(R.id.TokrytTextViewNickname);
				nickname.setTypeface(face);
				nickname.setText( _nickname);
				nickname.setTextSize(18f);
				
				//Type
				TextView type;
				type = (TextView) _context.findViewById(R.id.TokrytTextViewType);
				type.setText("Type: " + _type);
				type.setTypeface(face);
				
				//Level
				TextView level;
				level = (TextView) _context.findViewById(R.id.TokrytTextViewLevel);
				level.setTypeface(face);
				level.setText("Level: " + _level);
				level.setTextSize(24f);
				
				//Current Health
				TextView health;
				health = (TextView) _context.findViewById(R.id.TokrytTextViewHealth);
				health.setText("Current Health:    " + _health);
				health.setTypeface(face);
				
				//Current Experience
				TextView experience;
				experience = (TextView) _context.findViewById(R.id.TokrytTextViewExperience);
				experience.setText("Experience:                       " + _experience);
				experience.setTypeface(face);
				
				//Current Constitution
				TextView constitution;
				constitution = (TextView) _context.findViewById(R.id.TokrytTextViewConstitution);
				constitution.setText("Constitution:                 " + _constitution);
				constitution.setTypeface(face);
				
				//Current Strength
				TextView strength;
				strength = (TextView) _context.findViewById(R.id.TokrytTextViewStrength);
				strength.setText("Strength:                       " + _strength);
				strength.setTypeface(face);
				
				//Current Endurance
				TextView endurance;
				endurance = (TextView) _context.findViewById(R.id.TokrytTextViewEndurance);
				endurance.setText("Endurance:                   " + _endurance);
				endurance.setTypeface(face);
				
				//Current Agility
				TextView agility;
				agility = (TextView) _context.findViewById(R.id.TokrytTextViewAgility);
				agility.setText("Agility:                           " + _agility);
				agility.setTypeface(face);
				
				//Current Intelligence
				TextView intelligence;
				intelligence = (TextView) _context.findViewById(R.id.TokrytTextViewIntelligence);
				intelligence.setText("Intelligence:                 " + _intelligence);
				intelligence.setTypeface(face);
				
				//Current Wisdom
				TextView wisdom;
				wisdom = (TextView) _context.findViewById(R.id.TokrytTextViewWisdom);
				wisdom.setText("Wisdom:                         " + _wisdom);
				wisdom.setTypeface(face);
				
				//Current Charisma
				TextView charisma;
				charisma = (TextView) _context.findViewById(R.id.TokrytTextViewCharisma);
				charisma.setText("Charisma:                      " + _charisma);
				charisma.setTypeface(face);
				
				/*//Description description
				TextView descrLabel;
				descrLabel = (TextView)_context.findViewById(R.id.TokrytTextViewDescription);
				descrLabel.setTypeface(face);
				
				//Description Content
				TextView description;
				description = (TextView) _context.findViewById(R.id.TokrytTextViewDescriptionContent);
				description.setText(_description);
				description.setTypeface(face);*/
				
			
				
			} else {
				System.out.print("boo");//Problem!
			}
			_databaseModule.close();
		}
		@Override
		public void draw(Canvas canvas)
		{
			super.draw(canvas);
			canvas.drawBitmap(_background, _srcBackground, _dstBackground,_white);
			canvas.drawBitmap(_profilePic, _srcProfile, _dstProfile, _white);
			
		}


}
