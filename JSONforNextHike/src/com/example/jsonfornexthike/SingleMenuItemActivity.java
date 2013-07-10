package com.example.jsonfornexthike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// JSON node keys
	//private static final String TAG_BOARDID = "BOARDID";
	public static final String TAG_USERNAME = "username";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
      //  String name = in.getStringExtra(TAG_BOARDID);
        String cost = in.getStringExtra(TAG_USERNAME);
       
        
        // Displaying all values on the screen
      //  TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblCost = (TextView) findViewById(R.id.email_label);
       // TextView lblDesc = (TextView) findViewById(R.id.mobile_label);
        
     //   lblName.setText(name);
        lblCost.setText(cost);
      
    }
}
