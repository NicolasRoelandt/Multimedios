package com.example.diccperso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.example.database.database;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.database;



public class Jugar extends Activity {

	private database dbInstance; 
	private ArrayList<String> list1, list2, language1, language2;
	private SQLiteDatabase db;
	private Random rand; 
	private int value;
	private TextView word;
	private EditText answer;
	private int score;
	private TextView scoreText;
	private ImageView flag1, flag2;



	void updateImage(ImageView flag, ArrayList<String> list)
	{
		Drawable res;
		switch(list.get(value))
		{
		case("Spanish"):
			res = getResources().getDrawable(R.drawable.cl);
		break;
		case("French") : 
			res = getResources().getDrawable(R.drawable.bg);
		break;
		default :
			res = getResources().getDrawable(R.drawable.uk);
		}
		flag.setImageDrawable(res);
	}

	void newWord()
	{

		if(list1.size() != 0)
		{
		
		

		

		scoreText.setText(Integer.toString(score));
		answer.setText("");
		value = rand.nextInt(list1.size());
		word.setText(list1.get(value));
		updateImage(flag1, language1);
		updateImage(flag2, language2);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jugar);

		rand = new Random();
		word = (TextView) findViewById(R.id.word);
		answer = (EditText)findViewById(R.id.answer);
		dbInstance = new database(this);
		scoreText =  (TextView) findViewById(R.id.score);
		flag1 = (ImageView) findViewById(R.id.flag1);
		flag2 = (ImageView) findViewById(R.id.flag2);



		score =0;
		db = dbInstance.getReadableDatabase();

		//Donde se guardan las palabras   
		list1 = new ArrayList<String>();
		list2 = new ArrayList<String>();
		language1 = new ArrayList<String>();
		language2 = new ArrayList<String>();
		Cursor c = db.rawQuery("SELECT* FROM palabras",null);


		// So ponen las palabras en list
		if(c.moveToFirst()){
			do{
				
				language1.add(c.getString(1));
				list1.add(c.getString(2));
				language2.add(c.getString(3));
				list2.add(c.getString(4));
			} while (c.moveToNext());
		} c.close();


		newWord();


		answer.setOnEditorActionListener(
				new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH ||
								actionId == EditorInfo.IME_ACTION_DONE ||
								event.getAction() == KeyEvent.ACTION_DOWN &&
								event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
							if (!event.isShiftPressed()) {
								// the user is done typing. 
								if (answer.getText().toString().equals(list2.get(value)))
								{
									Toast.makeText(getApplicationContext(), "Good!",Toast.LENGTH_SHORT).show();
									score++;
								}
								else 
								{
									Toast.makeText(getApplicationContext(), "Wrong",Toast.LENGTH_SHORT).show();
								}


								newWord();
								return true; // consume.
							}                
						}
						return false; // pass on to other listeners. 
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jugar, menu);
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
}
