package com.example.diccperso;

import com.example.database.*;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Spinner;
//import android.widget.TextView; // para guardar idioma spinner
import android.widget.Toast;


	

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
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
				
				saveData(selectedItem, text1_value, selectedItem2, text2_value);
				} 
				
		});
	};
		
		
	//}


	
		//});
		
//}

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
	public void saveData(String spin1, String txt1, String spin2, String txt2){
		
		SQLiteDatabase db = dbInstance.getWritableDatabase();
		if(db != null){
			db.beginTransaction();
			try{
			    //for(EarthQuakeDataModel model : list){
					db.execSQL("INSERT INTO palabras (idioma_origen, palabra_origen, idioma_destino, palabra_destino) " +
											"VALUES ('" + spin1 + "', '" + 
														  txt1 + "', '" + 
														  spin2 + "', '" + 
														  txt2 + "')");
														  /*model.latitude + "', '" + 
														  model.longitude + "', '" + 
														  model.dateTime + "', '" + 
														  model.link + "')");*/
			   // }
			} finally {
				db.setTransactionSuccessful();
			}
			db.endTransaction();
		    db.close();
		}
		Toast.makeText(getApplicationContext(), text1_value + " Guardado!", Toast.LENGTH_SHORT).show();
		
		
	} 
	

}
