package com.example.diccperso;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import com.example.database.database;


public class Palabra extends Activity {
	
	 private String[] names =  {"idioma_origen", "idioma_destino", "palabra_destino", "palabra_origen", "photo", "sound"};
	 private String[] values = new String[6];
	 private TextView[] textViews = new TextView[4]; 
	 private String sound = null;
     private MediaPlayer   mPlayer = null;
     private static final String LOG_TAG = "AudioRecordTest";
     private database dbInstance; 
 	private SQLiteDatabase db;



 
     void updateImage(ImageView flag, int number)
     {
    	 Drawable res;
    	if (values[number].equals("Spanish"))
    	{
    		res = getResources().getDrawable(R.drawable.cl);
    	}
    	else if (values[number].equals("French"))
    	{
    		res = getResources().getDrawable(R.drawable.bg);
    	}
    	else 
    	{
    		res = getResources().getDrawable(R.drawable.uk);
    	}
    	flag.setImageDrawable(res);
    	
    			
    	 
     }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palabra);
		dbInstance = new database(this);
		db = dbInstance.getWritableDatabase();
		
		Intent myIntent = getIntent();
		for(int i = 0; i<6; i++)
		{
			values[i] = myIntent.getStringExtra(names[i]);
			int resID = getResources().getIdentifier(names[i], "id", "com.example.diccperso");
			if(i == 2 || i == 3)
			{
			textViews[i] = (TextView) findViewById(resID);
			textViews[i].setText(values[i]);
			//System.out.println(values[i]);
			}
		
		
		
		}
		
		ImageView flag1 = (ImageView) findViewById(R.id.language1);
		ImageView flag2 = (ImageView) findViewById(R.id.language2);
		
		//languages
		
		updateImage(flag1, 0);
		updateImage(flag2, 1);
		
		
		
	
		
		 
		sound = values[5];
				
		String photo = values[4];
		if(photo != null)
		{
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		 Bitmap myBitmap = BitmapFactory.decodeFile(photo);
		 image.setImageBitmap(myBitmap);
		}
		
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.palabra, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void audio(View view){
		
		String audioFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/diccPerso/"+sound;
			 mPlayer = new MediaPlayer();
	        try {
	            mPlayer.setDataSource(audioFile);
	            mPlayer.prepare();
	            mPlayer.start();
		        Toast.makeText(getApplicationContext(), "play...",Toast.LENGTH_SHORT).show();

	        } catch (IOException e) {
	            Log.e(LOG_TAG, "prepare() failed");
	        }
	}
	public void edit(View view) {
		
		Intent intent = new Intent (this,Registro.class);
		
		for(int i = 0; i<6; i++)
		{
		intent.putExtra(names[i],values[i]);
		}
		startActivity (intent);
		
	
	}
	
public void delete(View view) {
		
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	String[]args=new String[]{values[3]};
	        	db.beginTransaction();
				try{
	        	db.execSQL("DELETE FROM palabras WHERE palabra_origen = ?", args);
				}
				finally {
					db.setTransactionSuccessful();
				}

			db.endTransaction();
			db.close();
			
			onBackPressed();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Do you really want to delete this word?").setPositiveButton("Yes", dialogClickListener)
	    .setNegativeButton("No", dialogClickListener).show();
	
	}
}