package com.example.diccperso;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.database.database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
 
public class Revisar extends Activity {
     
    // List view
    private ListView lv;
    private database dbInstance;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputSearch;
    
    ArrayList<String> list;
    
    SQLiteDatabase db;
     
     
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar);
        
       
        
        dbInstance = new database(this);
    
        db = dbInstance.getReadableDatabase();

     // Define a projection that specifies which columns from the database
     

     
     
      
       //Donde se guardan las palabras   
        list = new ArrayList<String>();

        Cursor c = db.rawQuery("SELECT palabra_origen FROM palabras",null);
        
        
        // So ponen las palabras en list
        if(c.moveToFirst()){
            do{
            	System.out.println(c.getString(0) + " \n");
               list.add(c.getString(0));
            } while (c.moveToNext());
      } c.close();
         
     
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
         
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, list);
        lv.setAdapter(adapter);
        
        inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Revisar.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
      
        
        lv.setOnItemClickListener(new OnItemClickListener() {

        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,int position, long _id) {
        	String values = adapter.getItem(position);

        	// TODO Auto-generated method stub
        	System.out.println((String)list.get(position));
        	Intent intent = new Intent (Revisar.this,Palabra.class);
        	String palabra_origen = list.get(position);
        	String[]args=new String[]{palabra_origen};

        	
        	
        	 Cursor c = db.rawQuery("SELECT* FROM palabras WHERE palabra_origen = ?", args);
        	 c.moveToFirst();
        	 for(int i = 1; i<=4; i++)
        		 System.out.println(c.getString(i) + " \n");
        	 String idioma_origen = c.getString(1);
        	 String idioma_destino = c.getString(3);
        	 String palabra_destino = c.getString(4);
        	 
        	 
        		 
        	
        	
    		
    		intent.putExtra("idioma_origen",idioma_origen);
    		intent.putExtra("palabra_origen",palabra_origen);
    		intent.putExtra("idioma_destino",idioma_destino);
    		intent.putExtra("palabra_destino",palabra_destino);
    		startActivity(intent);
    		
        	}


        	});
         
    
    
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
     
}