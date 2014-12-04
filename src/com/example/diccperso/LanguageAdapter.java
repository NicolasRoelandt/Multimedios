package com.example.diccperso;

import java.net.URL;







import org.json.JSONException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LanguageAdapter extends BaseAdapter{
	
	private Context context;

	
	public LanguageAdapter(Context context) {
        this.context = context;


    }


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	

	 public Object getItem(int pos) {  
         return null;  
    }  

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View view = inflater.inflate(R.layout.row, parent, false);
		    ImageView icon = (ImageView) view.findViewById(R.id.icon);
		    Drawable res;
		 
		    switch(position)
		    {
		    case 0 :  res = context.getResources().getDrawable(R.drawable.cl);
		    break;
		    case 1 :  res = context.getResources().getDrawable(R.drawable.bg);
		    break;
		    default : res = context.getResources().getDrawable(R.drawable.uk);
		    break;
		    
		    }
		   
		    icon.setImageDrawable(res);
		    return view;
		    
		
		    
		
	
	

}
}
