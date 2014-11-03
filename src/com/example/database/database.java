package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class database extends SQLiteOpenHelper{

		
		private static final int DATABASE_VERSION = 1;
	    private static final String DATABASE_NAME = "EarthQuakeDB";
	    
	    private String sqlString = "CREATE TABLE 'palabras' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    												  "'idioma_origen' TEXT, " +
	    												  "'palabra_origen' TEXT, " +
	    												  "'idioma_destino' TEXT, " +
	    												  "'palabra_destino' TEXT)";
	    
	    public database(Context ctx){
	    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	    }

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(sqlString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS 'palabras'");
			onCreate(db);
		}

	
	
}
