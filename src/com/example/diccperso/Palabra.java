package com.example.diccperso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Palabra extends Activity {
	
	 private String[] names =  {"idioma_origen", "idioma_destino", "palabra_destino", "palabra_origen", "photo", "sound"};
	 private String[] values = new String[6];
	 private TextView[] textViews = new TextView[4]; 
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palabra);
		
		Intent myIntent = getIntent();
		for(int i = 0; i<6; i++)
		{
			values[i] = myIntent.getStringExtra(names[i]);
			int resID = getResources().getIdentifier(names[i], "id", "com.example.diccperso");
			if(i <4)
			{
			textViews[i] = (TextView) findViewById(resID);
			textViews[i].setText(values[i]);
			//System.out.println(values[i]);
			}
		
		
		
		}
		 
		
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
	
public void edit(View view) {
		
		Intent intent = new Intent (this,Registro.class);
		
		for(int i = 0; i<6; i++)
		{
		intent.putExtra(names[i],values[i]);
		}
		startActivity (intent);
		
	
}
}