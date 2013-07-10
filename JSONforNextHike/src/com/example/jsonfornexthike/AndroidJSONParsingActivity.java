package com.example.jsonfornexthike;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidJSONParsingActivity extends ListActivity {

	// url to make request
	private static String url = "https://alpha-api.app.net/stream/0/users/1/posts";
	
	// JSON Node names
	private static final String TAG_DATA = "data"; // JSON Array 
	private static final String TAG_USER = "user"; // JSON Object
	public static final String TAG_USERNAME = "username"; //key value starts from here
	public static final String TAG_AVTAR = "avatar_image"; // Image object 
	public static final String TAG_IMAGELINK = "url"; // Key value of Image
	public static final String TAG_DESCRIPTION="description"; // Json Object for HTML
	public static final String TAG_HTML="html";// Key value for HTML
	
	
	int arraylength;  //store the length of items in JSON  Array Type  DATA

	// contacts JSONArray
	JSONObject jsonusernameobject =null;
	JSONArray jsondataarray = null;
	BitmapFactory.Options bmOptions;  // for Image
	ImageView imgView;
	String imagelink=null;  // String will be needed for image link

	HashMap<String, String> map; // HashMap will needed to show Image and Texts together
	LazyAdapter lazyAdapter; // LazyLoader is a  user defined class

	ArrayList<HashMap<String, String>> dataarray = new ArrayList<HashMap<String, String>>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imgView =(ImageView)findViewById(R.id.imageid);	
		
		new dataAsync().execute(); // execution for async class
	}
		
	
	//  I am using Aysnc Task here. so we need to create a class for it  and implements it 3 neccessary  methods 
	class dataAsync extends AsyncTask<Void, Integer, Void> {

		public final String LAYOUT_INFLATER_SERVICE = null;
		public ProgressDialog dialog;
		Context context;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new ProgressDialog(AndroidJSONParsingActivity.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(false);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Data is loading..... Please wait !");
			// dialog.setMax(100);
			// dialog.setProgress(0);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(url);

			try {
				// Getting Array of Contacts
				jsondataarray=json.getJSONArray(TAG_DATA); // TAG_DATA  contains data and it will fetch by jsondataarray			
				arraylength=jsondataarray.length(); // storing the lenght of items
				// looping through All Contacts
				for(int i = 0; i < arraylength; i++){
					JSONObject a= jsondataarray.getJSONObject(i);// creating a reference to extract other's object by array. the same are of json type
					JSONObject juserobject = a.getJSONObject(TAG_USER);//TAG_USERNAME  contains username  and other key values it will fetch by juserobject
					String username=juserobject.getString(TAG_USERNAME); // same here
					JSONObject javtarobject =juserobject.getJSONObject(TAG_AVTAR);// same here
					imagelink = javtarobject.getString(TAG_IMAGELINK);// same here
					JSONObject jdescriptionobject =juserobject.getJSONObject(TAG_DESCRIPTION);// same here
					String html =jdescriptionobject.getString(TAG_HTML);// same here
																	
					
					// creating new HashMap
					map = new HashMap<String, String>(); // a new reference of hash map			
					// adding each child node to HashMap key => value
					map.put(TAG_USERNAME,username); 
					map.put(TAG_IMAGELINK, imagelink);
					map.put(TAG_HTML,html);
					dataarray.add(map);				
					// adding HashList to ArrayList
					}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			dialog.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
			 lazyAdapter=new LazyAdapter(AndroidJSONParsingActivity.this, dataarray, null);
			setListAdapter(lazyAdapter);

		}
	}
}

