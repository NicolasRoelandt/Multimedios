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

	//Spinners
	private Spinner spinner;
	private Spinner spinner2;


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


	String getLanguage(Spinner spinner)
	{
		int i = spinner.getSelectedItemPosition();
		switch (i) {
		case 0:  return "Spanish";

		case 1:  return "French";

		default:  return "English";

		}


	}



	void setSpinner(Spinner spinner, String language)
	{

		switch(language){
		case "Spanish" : 
			spinner.setSelection(0);
			break;
		case "French" : 
			spinner.setSelection(1);
			break;
		default : 
			spinner.setSelection(2);
			break;
		} 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		setTitle("New word");
		photo = null;

		dbInstance = new database (this);

		//Spinner idiomas

		// Sppiner 1 y 2
		spinner = (Spinner) findViewById(R.id.spinner_idiomas);	
		spinner2 = (Spinner) findViewById(R.id.spinner_idiomas2);




		spinner.setAdapter(new LanguageAdapter(Registro.this)); 
		spinner2.setAdapter(new LanguageAdapter(Registro.this)); 



	

		// Obtener valores en textos
		text1   = (EditText)findViewById(R.id.editText1);
		text2   = (EditText)findViewById(R.id.editText2);

		// Boton OK

		buttonOk = (Button) findViewById(R.id.buttonOk);
		setSpinner(spinner2, "French");





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
			setSpinner(spinner, values[0]);
			setSpinner(spinner2, values[1]);
			photo = values[4];

			
			if (!photo.equals("null") && photo != null)
			{
			
			try{
				updateButton();
			} catch (Exception e){}
			}
			

			sound = values[5];
			
			setTitle("Edit " + values[3]);


		}



		buttonOk.setOnClickListener(new OnClickListener() {

			public void onClick(View v){

				// Aqui obtengo los valores de los edit text
				text1_value = text1.getText().toString();
				text2_value = text2.getText().toString();
				String language1 = getLanguage(spinner);
				String language2 = getLanguage(spinner2);




			
               if(text1_value.length() != 0 && text2_value.length() != 0)
				saveData(language1, text1_value, language2, text2_value, photo, sound);
			} 

		});

	};

	

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
		
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	// Acceder a WR
	public void reverso(View view) {
		String url = "http://www.wordreference.com/";

		
			switch(spinner.getSelectedItemPosition())
			{
			case 0:
				url += "es";
				break;
			case 1:
				url += "fr";
				break;
			default :
				url +="en";
			}
			
			switch(spinner2.getSelectedItemPosition())
			{
			case 0:
				url += "es";
				break;
			case 1:
				url += "fr";
				break;
			default :
				url +="en";
			}
		
		url += "/" + text1.getText().toString();

		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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
		Toast.makeText(getApplicationContext(), text1_value + " saved!", Toast.LENGTH_SHORT).show();

		onBackPressed();

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
				try{updateButton();} catch (Exception e){}
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Callout for image capture failed!", 
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void updateButton() throws Exception
	{
		
		Bitmap myBitmap = BitmapFactory.decodeFile(photo);
		ImageButton image = (ImageButton)  findViewById(R.id.cameraButton);
		image.setImageBitmap(myBitmap);

	}








}
