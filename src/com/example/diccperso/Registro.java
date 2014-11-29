package com.example.diccperso;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.database.*;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
//import android.widget.TextView; // para guardar idioma spinner
import android.widget.Toast;


	

public class Registro extends Activity {

	// Toma valores de spinner
		private String selectedItem;
		private String selectedItem2;
		
		// Toma valores de texto
		private EditText text1;
		private EditText text2;
		private String text1_value;
		private String text2_value;
		
		//Boton OK
		private Button buttonOk;
		
		//Base Datos
		private database dbInstance;
		
		
		private String photo;
		private String sound;
		private static final String TAG = "CallCamera";
		  private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		photo = null;
		
		dbInstance = new database (this);
		
								//Spinner idiomas
		
		// Sppiner 1 y 2
		Spinner spinner = (Spinner) findViewById(R.id.spinner_idiomas);	
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner_idiomas2);
		
		// Adaptador para spinner
		// Adaptador para sppiner
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.idiomas, android.R.layout.simple_spinner_item);
		
		// Implementacion de spinner y adaptador		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Obtener valores en textos
		text1   = (EditText)findViewById(R.id.editText1);
		text2   = (EditText)findViewById(R.id.editText2);
		
		// Boton OK
		
		buttonOk = (Button) findViewById(R.id.buttonOk);

		spinner.setAdapter(adapter);
		spinner2.setAdapter(adapter);
		
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				// Comprueba si la posicion la pasa a string
				//Toast.makeText(parent.getContext(),
				//	"OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
				//  Toast.LENGTH_SHORT).show();
				
			//String selectedItem = parent.getItemAtPosition(position).toString();
			//auxi = selectedItem;
				 selectedItem = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*
		 * NICOLAS
		 * */
		 
		Intent myIntent = getIntent();
		if(myIntent.getStringExtra("idioma_origen") != null)
				{
			
			
			 String[] names =  {"idioma_origen", "idioma_destino", "palabra_destino", "palabra_origen", "photo", "sound"};
			 String[] values = new String[6];
			
			 
		for(int i = 0; i<6; i++)
		{
			values[i] = myIntent.getStringExtra(names[i]);
		}
		text1.setText(values[3]);
		text2.setText(values[2]);
		spinner.setSelection(getIndex(spinner, values[0]));
		spinner2.setSelection(getIndex(spinner, values[1]));
		photo = values[4];
		
		if(photo !=null)
			{
			updateButton();
			}
			
		
		sound = values[5];
				
		
				}


		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				//String selectedItem2 = parent.getItemAtPosition(position).toString();
				 selectedItem2 = parent.getItemAtPosition(position).toString();

			}

			//Toast.makeText(getContext(),
				//"OnItemSelectedListener : " + selectedItem,
			  //Toast.LENGTH_SHORT).show();
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		buttonOk.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View v){
								
				// Aqui obtengo los valores de los edit text
				text1_value = text1.getText().toString();
				text2_value = text2.getText().toString();
			
				//Toast.makeText(getApplicationContext(), " " + text1_value, Toast.LENGTH_SHORT).show();
				
				saveData(selectedItem, text1_value, selectedItem2, text2_value, photo, null);
				} 
				
		});
	};
		
	private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		int p = 9;
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	// Acceder a diccionario reverso
	public void reverso(View view) {
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.reverso.net/"));
		startActivity(browserIntent);
		
	}
	
	
	
	// Insertar valores en base datos
	public void saveData(String spin1, String txt1, String spin2, String txt2, String photo, String sound){
		
		SQLiteDatabase db = dbInstance.getWritableDatabase();
		
		
			
		if(db != null){
			db.beginTransaction();
			try{
					 
				//db.insertOrThrow("palabras", null, valor);
				db.execSQL("INSERT OR REPLACE INTO palabras (idioma_origen, palabra_origen, idioma_destino, palabra_destino, photo, sound) " +
											"VALUES ('" + spin1 + "', '" + 
														  txt1 + "', '" + 
														  spin2 + "', '" + 
														  txt2 + "', '" + 
														  photo + "', '" +
														  sound + "')");
				
														  
			   
			} finally {
				db.setTransactionSuccessful();
			}
			db.endTransaction();
		    db.close();
		}
		Toast.makeText(getApplicationContext(), text1_value + " Guardado!", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent (this,MainActivity.class);
		startActivity (intent);
		
	} 
	
public void camera(View view) {

	 Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 File file = getOutputPhotoFile();
     photo = file.getAbsolutePath();
     Uri fileUri = Uri.fromFile(file);
     i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
     startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );
	}

private File getOutputPhotoFile() {
	  File directory = new File(Environment.getExternalStoragePublicDirectory(
	                Environment.DIRECTORY_PICTURES), getPackageName());
	  if (!directory.exists()) {
	    if (!directory.mkdirs()) {
	      Log.e(TAG, "Failed to create storage directory.");
	      return null;
	    }
	  }
	  String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
	  return new File(directory.getPath() + File.separator + "IMG_"  
	                    + timeStamp + ".jpg");
	}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
	    if (resultCode == RESULT_OK) {
	     
	      if (data == null) {
	        // A known bug here! The image should have saved in fileUri
	        Toast.makeText(this, "Image saved successfully", 
	                       Toast.LENGTH_LONG).show();
	        
	      } else {
	       
	        Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
	                       Toast.LENGTH_LONG).show();
	      }
	      // showPhoto(photoUri);
	     updateButton();
	    } else if (resultCode == RESULT_CANCELED) {
	      Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
	    } else {
	      Toast.makeText(this, "Callout for image capture failed!", 
	                     Toast.LENGTH_LONG).show();
	    }
	  }
}
	  
	  public void updateButton()
	  {
		  Bitmap myBitmap = BitmapFactory.decodeFile(photo);
	      ImageButton image = (ImageButton)  findViewById(R.id.cameraButton);
	      image.setImageBitmap(myBitmap);
	  }
	  
	


	



}
