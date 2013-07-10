package com.example.jsonfornexthike;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    Drawable drawableImage;
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d, Drawable drawable) {
       	 activity = a;
         data=d;
    	        
       // drawableImage=drawable;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size(); // get the length of arraylist
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    
    // all main work will in getview method. these all methods are inbuilt methods
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null); // we are using inflator in this , to inflate the layout

        TextView usernametexview = (TextView)vi.findViewById(R.id.usernameid);
        TextView htmltextview = (TextView)vi.findViewById(R.id.htmlid);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.imageid); // thumb image
        
        HashMap<String, String> hashmapdata = new HashMap<String, String>();
        hashmapdata = data.get(position);
        
        // Setting all values in listview
        usernametexview.setText(hashmapdata.get(AndroidJSONParsingActivity.TAG_USERNAME));
        htmltextview.setText(hashmapdata.get(AndroidJSONParsingActivity.TAG_HTML));       
        imageLoader.DisplayImage(hashmapdata.get(AndroidJSONParsingActivity.TAG_IMAGELINK), thumb_image);
        return vi;
    }
}