package com.assembla.gpsmonsters.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.assembla.gpsmonsters.DebugSettings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DatabaseModule extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "kalotaiDatabase.sqlite";
	private static final String DATABASE_PATH = "/data/data/com.assembla.gpsmonsters/databases/";
	// private String DATABASE_PATH;
	private SQLiteDatabase _db;
	private final Context _context;

	public DatabaseModule(Context context) {
		super(context, DATABASE_NAME, null, 1);
		_context = context;
		// DATABASE_PATH = _context.getDatabasePath(DATABASE_NAME);;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (DebugSettings.DEBUG && DebugSettings.ALWAYSCOPYNEWDATABASE) {
			dbExist = false;
		}

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}
		this.close();

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = _context.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DATABASE_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public synchronized void openDataBase() throws SQLException {
		try {
		// Open the database
		String myPath = DATABASE_PATH + DATABASE_NAME;
		_db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		_db.setLockingEnabled(true);
		
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return;

	}

	@Override
	public synchronized void close() {
try {
		if (_db != null)
			_db.close();
} catch (Exception e) {
	e.printStackTrace();
	
} finally {
		super.close();
}
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/* Query Methods */
	/*
	 * KRYT QUERIES
	 */
	public synchronized ContentValues getKrytByID(int id) {
		String[] columns = new String[] { DatabaseFields.KRYTS.NAME,
				DatabaseFields.KRYTS.TYPE, DatabaseFields.KRYTS.DESCRIPTION,
				DatabaseFields.KRYTS.CONSTITUTION,
				DatabaseFields.KRYTS.STRENGTH, DatabaseFields.KRYTS.ENDURANCE,
				DatabaseFields.KRYTS.AGILITY,
				DatabaseFields.KRYTS.INTELLIGENCE, DatabaseFields.KRYTS.WISDOM,
				DatabaseFields.KRYTS.CHARISMA, DatabaseFields.KRYTS.MOVE1,
				DatabaseFields.KRYTS.MOVE2, DatabaseFields.KRYTS.MOVE3,
				DatabaseFields.KRYTS.MOVE4, DatabaseFields.KRYTS.MOVE5,
				DatabaseFields.KRYTS.CRY, DatabaseFields.KRYTS.WALKSPEED,
				DatabaseFields.KRYTS.ISTOTEM, DatabaseFields.KRYTS.expYield

		};
		Cursor c = null;
		ContentValues result = new ContentValues();
		try {
			_db.beginTransaction();
			c = _db.query(DatabaseFields.TABLE_KRYTS, columns,
					DatabaseFields.KRYTS.ID + "=" + id, null, null, null, null,
					"1");
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result.put("success", true);
				result.put("name", c.getString(0));
				result.put("type", c.getString(1));
				result.put("description", c.getString(2));
				result.put("constitution", c.getInt(3));
				result.put("strength", c.getInt(4));
				result.put("endurance", c.getInt(5));
				result.put("agility", c.getInt(6));
				result.put("intelligence", c.getInt(7));
				result.put("wisdom", c.getInt(8));
				result.put("charisma", c.getInt(9));
				result.put("move1", c.getInt(10));
				result.put("move2", c.getInt(11));
				result.put("move3", c.getInt(12));
				result.put("move4", c.getInt(13));
				result.put("move5", c.getInt(14));
				result.put("cry", c.getInt(15));
				result.put("walkSpeed", c.getInt(16));
				result.put("isTotem", c.getInt(17));
				result.put("expYield", c.getInt(18));

			} else {
				result.put("success", false);
			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
			result.put("success", false);

		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;
	}
	public  synchronized  ArrayList<ContentValues> getKryts() {
		String[] columns = new String[] { DatabaseFields.KRYTS.NAME,
				DatabaseFields.KRYTS.TYPE, DatabaseFields.KRYTS.DESCRIPTION,
				DatabaseFields.KRYTS.CONSTITUTION,
				DatabaseFields.KRYTS.STRENGTH, DatabaseFields.KRYTS.ENDURANCE,
				DatabaseFields.KRYTS.AGILITY,
				DatabaseFields.KRYTS.INTELLIGENCE, DatabaseFields.KRYTS.WISDOM,
				DatabaseFields.KRYTS.CHARISMA, DatabaseFields.KRYTS.MOVE1,
				DatabaseFields.KRYTS.MOVE2, DatabaseFields.KRYTS.MOVE3,
				DatabaseFields.KRYTS.MOVE4, DatabaseFields.KRYTS.MOVE5,
				DatabaseFields.KRYTS.CRY, DatabaseFields.KRYTS.WALKSPEED,
				DatabaseFields.KRYTS.ISTOTEM, DatabaseFields.KRYTS.expYield,DatabaseFields.KRYTS.ID

		};
		Cursor c = null;
		ArrayList<ContentValues> array = new ArrayList<ContentValues>();
		try {
			_db.beginTransaction();
			c = _db.query(DatabaseFields.TABLE_KRYTS, columns,
					null, null, null, null, null,null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
				ContentValues result = new ContentValues();
				result.put("id", c.getInt(19));
				result.put("name", c.getString(0));
				result.put("type", c.getString(1));
				result.put("description", c.getString(2));
				result.put("constitution", c.getInt(3));
				result.put("strength", c.getInt(4));
				result.put("endurance", c.getInt(5));
				result.put("agility", c.getInt(6));
				result.put("intelligence", c.getInt(7));
				result.put("wisdom", c.getInt(8));
				result.put("charisma", c.getInt(9));
				result.put("move1", c.getInt(10));
				result.put("move2", c.getInt(11));
				result.put("move3", c.getInt(12));
				result.put("move4", c.getInt(13));
				result.put("move5", c.getInt(14));
				result.put("cry", c.getInt(15));
				result.put("walkSpeed", c.getInt(16));
				result.put("isTotem", c.getInt(17));
				result.put("expYield", c.getInt(18));
				array.add(result);
				} while (c.moveToNext());
			} else {
				
			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());

		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return array;
	}
	/*
	 * GPS QUERIES
	 */
	public  synchronized void insertGPSTerrain(float latitude, float longitude, String terrain) {
		ContentValues values = new ContentValues();
		values.put(DatabaseFields.GPSTERRAIN.LAT, ""+latitude);
		values.put(DatabaseFields.GPSTERRAIN.LONG, ""+longitude);
		values.put(DatabaseFields.GPSTERRAIN.TERRAIN, terrain);
		try {
			_db.beginTransaction();
			long i = _db.insert(DatabaseFields.TABLE_GPSTERRAIN, null, values);
			Log.d("sqlError",""+i);
			_db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
		}

	}


	public  synchronized  String getGPSTerrain(float latitude, float longitude) {
		String[] columns = new String[] { DatabaseFields.GPSTERRAIN.TERRAIN };
		String selection = DatabaseFields.GPSTERRAIN.LAT + "=" + latitude
				+ " AND " + DatabaseFields.GPSTERRAIN.LONG + "=" + longitude;
		Cursor c = null;
		String result = "";
		try {
			_db.beginTransaction();
			c = _db.query(DatabaseFields.TABLE_GPSTERRAIN, columns, selection,
					null, null, null, null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result = c.getString(0);

			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;

	}

	/*
	 * ENERGY QUERIES
	 */
	public  synchronized  int getEnergy(int type) {
		String[] columns = new String[] { DatabaseFields.ENERGY.VALUE };
		String selection = DatabaseFields.ENERGY.TYPE + "=" + type;
		Cursor c = null;
		int result = 0;
		try {
			_db.beginTransaction();
			c = _db.query(DatabaseFields.TABLE_ENERGY, columns, selection,
					null, null, null, null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result = c.getInt(0);
			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;
	}

	public synchronized void updateEnergy(int type, int value) {
		ContentValues values = new ContentValues();
		values.put(DatabaseFields.ENERGY.VALUE, value);
		String whereClause = DatabaseFields.ENERGY.TYPE + "=" + type;
		//
		try {
			_db.beginTransaction();
			_db.update(DatabaseFields.TABLE_ENERGY, values, whereClause, null);
			_db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());

		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}

		}
		//

	}

	/*
	 * User Kryts
	 */
	public synchronized  ArrayList<ContentValues> getPlayerKryts() {
		String tables = DatabaseFields.TABLE_PLAYERKRYTS + ","
				+ DatabaseFields.TABLE_KRYTS;
		String[] columns = new String[] {
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.ID,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.KRYT_ID,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.NAME,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.TYPE,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE1,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE2,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE3,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE4,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE5,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.CRY,
				DatabaseFields.TABLE_KRYTS + "."
						+ DatabaseFields.KRYTS.WALKSPEED,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ISTOTEM,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.LEVEL,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CURRENT_HEATH,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.SELECTED,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.NICKNAME,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.EXPERIENCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CONSTITUTION,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.STRENGTH,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.ENDURANCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.AGILITY,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.INTELLIGENCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.WISDOM,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CHARISMA };
		String where = DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.KRYT_ID + "="
				+ DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ID;
		String orderBy = DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.ID + " DESC";
		String query = SQLiteQueryBuilder.buildQueryString(false, tables,
				columns, where, null, null, orderBy, null);
		ArrayList<ContentValues> result = new ArrayList<ContentValues>();
		Cursor c = null;

		try {
			_db.beginTransaction();
			c = _db.rawQuery(query, null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				do {
					ContentValues row = new ContentValues();
					row.put("success", true);
					row.put("id", c.getInt(0));
					row.put("krytId", c.getInt(1));
					row.put("name", c.getString(2));
					row.put("type", c.getString(3));
					row.put("move1", c.getInt(4));
					row.put("move2", c.getInt(5));
					row.put("move3", c.getInt(6));
					row.put("move4", c.getInt(7));
					row.put("move5", c.getInt(8));
					row.put("cry", c.getInt(9));
					row.put("walkSpeed", c.getInt(10));
					row.put("isTotem", c.getInt(11));
					row.put("level", c.getInt(12));
					row.put("currentHealth", c.getFloat(13));
					row.put("selected", c.getInt(14));
					row.put("nickname", c.getString(15));
					row.put("experience", c.getInt(16));
					row.put("constitution", c.getInt(17));
					row.put("strength", c.getInt(18));
					row.put("endurance", c.getInt(19));
					row.put("agility", c.getInt(20));
					row.put("intelligence", c.getInt(21));
					row.put("wisdom", c.getInt(22));
					row.put("charisma", c.getInt(23));
					result.add(row);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
			
		}
		return result;
	}

	public synchronized  ContentValues getPlayerSelectedKryt() {
		String tables = DatabaseFields.TABLE_PLAYERKRYTS + ","
				+ DatabaseFields.TABLE_KRYTS;
		String[] columns = new String[] {
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.ID,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.KRYT_ID,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.NAME,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.TYPE,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE1,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE2,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE3,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE4,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE5,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.CRY,
				DatabaseFields.TABLE_KRYTS + "."
						+ DatabaseFields.KRYTS.WALKSPEED,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ISTOTEM,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.LEVEL,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CURRENT_HEATH,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.SELECTED,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.NICKNAME,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.EXPERIENCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CONSTITUTION,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.STRENGTH,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.ENDURANCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.AGILITY,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.INTELLIGENCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.WISDOM,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CHARISMA };
		String where = DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.KRYT_ID + "="
				+ DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ID;
		where = where + " AND " + DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.SELECTED + " = '1' ";
		String orderBy = DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.ID + " DESC";
		String query = SQLiteQueryBuilder.buildQueryString(false, tables,
				columns, where, null, null, orderBy, null);

		ContentValues result = new ContentValues();
		Cursor c = null;

		try {
			_db.beginTransaction();
			c = _db.rawQuery(query, null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result.put("success", true);
				result.put("id", c.getInt(0));
				result.put("krytId", c.getInt(1));
				result.put("name", c.getString(2));
				result.put("type", c.getString(3));
				result.put("move1", c.getInt(4));
				result.put("move2", c.getInt(5));
				result.put("move3", c.getInt(6));
				result.put("move4", c.getInt(7));
				result.put("move5", c.getInt(8));
				result.put("cry", c.getInt(9));
				result.put("walkSpeed", c.getInt(10));
				result.put("isTotem", c.getInt(11));
				result.put("level", c.getInt(12));
				result.put("currentHealth", c.getFloat(13));
				result.put("selected", c.getInt(14));
				result.put("nickname", c.getString(15));
				result.put("experience", c.getInt(16));
				result.put("constitution", c.getInt(17));
				result.put("strength", c.getInt(18));
				result.put("endurance", c.getInt(19));
				result.put("agility", c.getInt(20));
				result.put("intelligence", c.getInt(21));
				result.put("wisdom", c.getInt(22));
				result.put("charisma", c.getInt(23));
			} else {
				result.put("success", false);

			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
			result.put("success", false);
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
		
		}
		return result;
	}

	public  synchronized ContentValues getPlayerKrytById(int playerKrytId) {
		String tables = DatabaseFields.TABLE_PLAYERKRYTS + ","
				+ DatabaseFields.TABLE_KRYTS;
		String[] columns = new String[] {
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.ID,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.KRYT_ID,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.NAME,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.TYPE,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE1,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE2,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE3,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE4,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE5,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.CRY,
				DatabaseFields.TABLE_KRYTS + "."
						+ DatabaseFields.KRYTS.WALKSPEED,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ISTOTEM,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.LEVEL,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CURRENT_HEATH,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.SELECTED,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.NICKNAME,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.EXPERIENCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CONSTITUTION,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.STRENGTH,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.ENDURANCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.AGILITY,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.INTELLIGENCE,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.WISDOM,
				DatabaseFields.TABLE_PLAYERKRYTS + "."
						+ DatabaseFields.PLAYERKRYTS.CHARISMA };
		String where = DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.KRYT_ID + "="
				+ DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ID;
		where = where + " AND " + DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.ID + " = '" + playerKrytId + "'";
		String orderBy = DatabaseFields.TABLE_PLAYERKRYTS + "."
				+ DatabaseFields.PLAYERKRYTS.ID + " DESC";
		String query = SQLiteQueryBuilder.buildQueryString(false, tables,
				columns, where, null, null, orderBy, null);

		ContentValues result = new ContentValues();
		Cursor c = null;

		try {
			_db.beginTransaction();
			c = _db.rawQuery(query, null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result.put("success", true);
				result.put("id", c.getInt(0));
				result.put("krytId", c.getInt(1));
				result.put("name", c.getString(2));
				result.put("type", c.getString(3));
				result.put("move1", c.getInt(4));
				result.put("move2", c.getInt(5));
				result.put("move3", c.getInt(6));
				result.put("move4", c.getInt(7));
				result.put("move5", c.getInt(8));
				result.put("cry", c.getInt(9));
				result.put("walkSpeed", c.getInt(10));
				result.put("isTotem", c.getInt(11));
				result.put("level", c.getInt(12));
				result.put("currentHealth", c.getFloat(13));
				result.put("selected", c.getInt(14));
				result.put("nickname", c.getString(15));
				result.put("experience", c.getInt(16));
				result.put("constitution", c.getInt(17));
				result.put("strength", c.getInt(18));
				result.put("endurance", c.getInt(19));
				result.put("agility", c.getInt(20));
				result.put("intelligence", c.getInt(21));
				result.put("wisdom", c.getInt(22));
				result.put("charisma", 23);
			} else {
				result.put("success", false);

			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
			result.put("success", false);
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;
	}
	public  synchronized int getAnyPlayerKrytId() {
		String[] columns = new String[] {
				DatabaseFields.PLAYERKRYTS.ID };
		Cursor c = null;
		int result = 0;
		try {
			_db.beginTransaction();
			c = _db.query(DatabaseFields.TABLE_PLAYERKRYTS, columns, null, null, null, null, null, "1");
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result = c.getInt(0);
			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
				c.close();	
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;
	}

	public synchronized  void setPlayerKryAsSelected(int playerKrytId) {
		ContentValues selectedOff, selectedOn;
		selectedOff = new ContentValues();
		selectedOn = new ContentValues();
		selectedOff.put("selected", 0);
		selectedOn.put("selected", 1);
		String whereOn = DatabaseFields.PLAYERKRYTS.SELECTED + " = '1'";
		String whereKryt = DatabaseFields.PLAYERKRYTS.ID + " = '"
				+ playerKrytId + "'";
		try {
			_db.beginTransaction();
			_db.update(DatabaseFields.TABLE_PLAYERKRYTS, selectedOff, whereOn,
					null);
			_db.update(DatabaseFields.TABLE_PLAYERKRYTS, selectedOn, whereKryt,
					null);
			_db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
		}
	}
	public synchronized  void deletePlayerSelectedKryt()
	{
		try {
			_db.beginTransaction();
			_db.delete(DatabaseFields.TABLE_PLAYERKRYTS, DatabaseFields.PLAYERKRYTS.SELECTED  + " = '1'", null);		
			_db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
		}
		int newKryt = this.getAnyPlayerKrytId();
		this.setPlayerKryAsSelected(newKryt);
	}
	/*
	 * Encounters
	 */
	public  synchronized long insertEncounter(ContentValues content)
	{
		long rowId = -1;
		try {
			_db.beginTransaction();
			rowId = _db.insert(DatabaseFields.TABLE_ENCOUNTERKRYTS, null, content);
			_db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("sqlError",e.getMessage());
		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
		}
		return rowId; //returns -1 on failure
	}
	public synchronized  void emptyEncounterTable()
	{
		String query = "DELETE FROM " + DatabaseFields.TABLE_ENCOUNTERKRYTS;
		try {
			_db.beginTransaction();
			_db.rawQuery(query, null);
			_db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("sqlError",e.getMessage());
		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
		}
	}
	public  synchronized ContentValues getEncounteredKrytById(int encounterId) {
		String tables = DatabaseFields.TABLE_ENCOUNTERKRYTS + ","
				+ DatabaseFields.TABLE_KRYTS;
		String[] columns = new String[] {
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.ID,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.KRYT_ID,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.NAME,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.TYPE,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE1,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE2,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE3,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE4,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.MOVE5,
				DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.CRY,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.LEVEL,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.CURRENT_HEATH,
				DatabaseFields.TABLE_KRYTS + "."
						+ DatabaseFields.KRYTS.expYield,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.CONSTITUTION,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.STRENGTH,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.ENDURANCE,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.AGILITY,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.INTELLIGENCE,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.WISDOM,
				DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
						+ DatabaseFields.ENCOUNTERKRYTS.CHARISMA

		};
		String where = DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
				+ DatabaseFields.ENCOUNTERKRYTS.KRYT_ID + "="
				+ DatabaseFields.TABLE_KRYTS + "." + DatabaseFields.KRYTS.ID;
		where = where + " AND " + DatabaseFields.TABLE_ENCOUNTERKRYTS + "."
				+ DatabaseFields.ENCOUNTERKRYTS.ID + " = '" + encounterId + "'";
		String query = SQLiteQueryBuilder.buildQueryString(false, tables,
				columns, where, null, null, null, null);

		ContentValues result = new ContentValues();
		Cursor c = null;

		try {
			_db.beginTransaction();
			c = _db.rawQuery(query, null);
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result.put("success", true);
				result.put("id", c.getInt(0));
				result.put("krytId", c.getInt(1));
				result.put("name", c.getString(2));
				result.put("experience",0);
				result.put("walkSpeed", 0);
				result.put("type", c.getString(3));
				result.put("move1", c.getInt(4));
				result.put("move2", c.getInt(5));
				result.put("move3", c.getInt(6));
				result.put("move4", c.getInt(7));
				result.put("move5", c.getInt(8));
				result.put("cry", c.getInt(9));
				result.put("level", c.getInt(10));
				result.put("currentHealth", c.getFloat(11));
				result.put("expYield", c.getInt(12));
				result.put("constitution", c.getInt(13));
				result.put("strength", c.getInt(14));
				result.put("endurance", c.getInt(15));
				result.put("agility", c.getInt(16));
				result.put("intelligence", c.getInt(17));
				result.put("wisdom", c.getInt(18));
				result.put("charisma", c.getInt(19));
			} else {
				result.put("success", false);

			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
			result.put("success", false);
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;
	}
	public  synchronized int getRandomEncounterId()
	{
		String[] columns = new String[] {
				DatabaseFields.ENCOUNTERKRYTS.ID };
		String selection = DatabaseFields.ENCOUNTERKRYTS.ID + ">=random() % (SELECT max("+DatabaseFields.ENCOUNTERKRYTS.ID+") FROM "+DatabaseFields.TABLE_ENCOUNTERKRYTS+")";
		Cursor c = null;
		int result = 0;
		try {
			_db.beginTransaction();
			c = _db.query(DatabaseFields.TABLE_ENCOUNTERKRYTS, columns, selection, null, null, null, null, "1");
			_db.setTransactionSuccessful();
			if (c.moveToFirst()) {
				result = c.getInt(0);
			}
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
				c.close();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return result;
	}
	public  synchronized void removeEncounter(int encounterId)
	{
		String where = DatabaseFields.ENCOUNTERKRYTS.ID + " = " + encounterId;
		try {
			_db.beginTransaction();
			_db.delete(DatabaseFields.TABLE_ENCOUNTERKRYTS, where, null);
			_db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("sqlError", e.getMessage());
		} finally {
			try {
				_db.endTransaction();
			} catch (Exception e)
			{
				Log.e("sqlError", e.getMessage());
			}
			
		}
		return;
	}
	public synchronized boolean addPlayerKryt(ContentValues content)
	{
		long result = -1;
		try {
			_db.beginTransaction();
			result = _db.insertOrThrow(DatabaseFields.TABLE_PLAYERKRYTS, null, content);
			_db.setTransactionSuccessful();
			
		} catch (Exception e)
		{
			Log.e("sqlError",e.getMessage());
		} finally 
		{
			try {
				_db.endTransaction();
				
			} catch (Exception e)
			{
				Log.e("sqlError",e.getMessage());
			}
		}
		if (result >= 0)
		{
			return true;
		} else {
			return false;
		}
	}
}
