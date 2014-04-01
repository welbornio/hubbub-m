package com.example.hubbub_merchant;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hubbub.R;

public class FullscreenActivity extends Activity {
	
	public final static String EXTRA_USER_ID = "com.example.hubbub.USER_ID";
	public final static String EXTRA_METRIC_VALUE = "com.example.hubbub.METRIC_VALUE";
	
	public static Map<String, Model> clientMap = new HashMap();
	public static Map<String, Model> merchantMap = new HashMap();
	
	private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        
        new GetModels(this, (Spinner)findViewById(R.id.spinner_client), (Spinner)findViewById(R.id.spinner_merchant)).execute("getClients");
        new GetModels(this, (Spinner)findViewById(R.id.spinner_client), (Spinner)findViewById(R.id.spinner_merchant)).execute("getMerchants");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fullscreen, menu);
        return true;
    }
    
    public void sendData(View view) {
    	TextView displaySending = (TextView)findViewById(R.id.display_sending);
    	displaySending.setText("Sending...");
    	
    	Spinner clientSpinner = (Spinner)findViewById(R.id.spinner_client);
    	Spinner merchantSpinner = (Spinner)findViewById(R.id.spinner_merchant);
    	EditText metricView = (EditText) findViewById(R.id.metric);
    	
    	String clientName = clientSpinner.getSelectedItem().toString();
    	String merchantName = merchantSpinner.getSelectedItem().toString();
    	String metricValue = metricView.getText().toString();
    	
    	System.out.println("Selected client is: " + clientName + "\nAnd Selected merchant: " + merchantName);
    	
    	Intent intent = new Intent(this, FullscreenActivity.class);
    	try{
    		HttpResponse response;
    		response = HubApi.write(clientName, "1", merchantName, "1", metricValue);
    		Thread.sleep(2000);
//    		intent.putExtra(EXTRA_USER_ID, userId);
//        	intent.putExtra(EXTRA_METRIC_VALUE, metricValue);
    		System.out.println(response.toString());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		startActivity(intent);
    	}
    }
    
}
