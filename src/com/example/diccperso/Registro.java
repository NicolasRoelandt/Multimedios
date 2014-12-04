package com.example.diccperso;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.database.*;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
		private Button recordOn;
		private Button recordOff;
		
		//Base Datos
		private database dbInstance;
		
		//Grabar audio
		private MediaRecorder mRecorder = null;
		private static final String LOG_TAG = "AudioRecordTest";
	    //private static String audioFile = null;
	    private MediaPlayer   mPlayer = null;
	       
		
		private String photo;
		private String sound = null;
		private static final String TAG = "CallCamera";
		private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
		
		//imagenes spinner
		int total_images[] = { R.drawable.cl, R.drawable.uk, R.drawable.bg};
		
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
		
		int[] array = getResources().getIntArray(R.array.idiomas);
        String [] objects = new String[array.length];
        for(int i = 0; i != array.length; i++){
            objects[i] = "" + array[i];
        }

        spinner.setAdapter(new MyAdapter(Registro.this, R.id.spinner_idiomas, objects)); 

    
		
		// Adaptador para sppiner
	//	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		//        R.array.idiomas, android.R.layout.simple_spinner_item);
		
		
		// Implementacion de spinner y adaptador		
	//	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Obtener valores en textos
		text1   = (EditText)findViewById(R.id.editText1);
		text2   = (EditText)findViewById(R.id.editText2);
		
		// Boton OK
		
		buttonOk = (Button) findViewById(R.id.buttonOk);
		

		/*
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
		}); */
		
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


	/*	spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

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
			
		}); */
		
		buttonOk.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View v){
								
				// Aqui obtengo los valores de los edit text
				text1_value = text1.getText().toString();
				text2_value = text2.getText().toString();
			
				//Toast.makeText(getApplicationContext(), " " + text1_value, Toast.LENGTH_SHORT).show();
				
				saveData(selectedItem, text1_value, selectedItem2, text2_value, photo, sound);
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

	class MyAdapter extends ArrayAdapter<String>{

        String[] objects;
        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false); 

            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            TypedArray imgs = getResources().obtainTypedArray(R.array.idiomas);
            icon.setImageResource(imgs.getResourceId(position, -1));

            return row;
         }
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
	
	public void check_folder(){
		
		File folder = new File(Environment.getExternalStorageDirectory() + "/diccPerso");
		boolean success = true;
		if (!folder.exists()) {
		    success = folder.mkdir();
		}
		if (success) {
		    // Do something on success
		} else {
		    // Do something else on failure 
		}
	}
	
	public void check_file(String file){
		
		File folder = new File(Environment.getExternalStorageDirectory() + "/diccPerso"+file);
		boolean success = true;
		if (!folder.exists()) {
		    success = folder.mkdir();
		}
		if (success) {
		    // Do something on success
		} else {
		    // Do something else on failure 
		}
	}
	//guardar audio
	public void start_recording(View view){

			//mFileName = Environment.getDataDirectory().getAbsolutePath() + "/text1_value.3gpp";
			String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

			File folder = new File(Environment.getExternalStorageDirectory() + "/diccPerso");
			check_folder();
			//audioFile = timeStamp +".3gpp";
			sound = timeStamp +".3gpp";

			mRecorder = new MediaRecorder();
	        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	        mRecorder.setOutputFile("sdcard/diccPerso/"+sound);
     

	        try {	        	
	        	  mRecorder.prepare();
	        	  mRecorder.start();
	        	 } catch (IllegalStateException e) {
	        	
	               // start:it is called before prepare()
	               // prepare: it is called after start() or before setOutputFormat()
	                  e.printStackTrace();
	            	       } catch (IOException e) {
	           	           // prepare() fails
	        	        	           e.printStackTrace();
	              	        }

	        Toast.makeText(getApplicationContext(), "Start recording...",Toast.LENGTH_SHORT).show();
		
		
	} 
		
	
	//parar guardar audio
	public void stop_recording(View view) {
			 
			           mRecorder.stop();
			           mRecorder.release();
			           mRecorder  = null;
			           Toast.makeText(getApplicationContext(), "Stop recording...",Toast.LENGTH_SHORT).show();
			

    }
	
	 public void play(View view) {
	       
		 String audioFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/diccPerso/"+sound;
		 
		 check_file(audioFile);
		 
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
	

	/*  File directory = new File(Environment.getExternalStoragePublicDirectory(
	                Environment.DIRECTORY_PICTURES), getPackageName()); */
	File directory = new File(Environment.getExternalStorageDirectory() + "/diccPerso");
	check_folder();

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
