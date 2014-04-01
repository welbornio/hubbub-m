package com.example.hubbub_merchant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.bool;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hubbub.R;

public class GetModels extends AsyncTask<String, Void, ArrayList<String>> {
	FullscreenActivity act;
	Spinner clientSpinner;
	Spinner merchantSpinner;
	boolean clients;
	
	public GetModels(FullscreenActivity a, Spinner cSpin, Spinner mSpin) {
		this.act = a;
		this.clientSpinner = cSpin;
		this.merchantSpinner = mSpin;
	}
	
	@Override
	protected ArrayList<String> doInBackground(String... params) {
		String type;
    	JSONArray array;
    	ArrayList<String> items = null;
    	String cmd = params[0];
    	
    	if (cmd.equals("getClients")) {
    		type = "clients";
    		this.clients = true;
    	}
    	else {
    		type = "merchants";
    		this.clients = false;
    	}
    	
    	HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout Limit
        HttpResponse response;
        HttpEntity httpEntity;
        
        HttpGet get = new HttpGet("https://hubbubapp.herokuapp.com/api/v1/" + type);
        
        try {
            response = client.execute(get);
            httpEntity = response.getEntity();
            
            if (httpEntity != null) {
            	InputStream inputStream = httpEntity.getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                String ligneLue = bufferedReader.readLine();
                while(ligneLue != null){
                    stringBuilder.append(ligneLue + " \n");
                    ligneLue = bufferedReader.readLine();
                }
                bufferedReader.close();

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                array = jsonArray;
                
                items = new ArrayList<String>();
                
                try {
        	        for(int n = 0; n < array.length(); n++) {
        	            JSONObject object = array.getJSONObject(n);
        	            if (type.equals("merchants")) {
        	            	act.merchantMap.put(object.getString("username"), new Model(object.getString("username"), "example", object.getString("_id")));
        	            }
        	            else {
        	            	act.clientMap.put(object.getString("username"), new Model(object.getString("username"), "example", object.getString("_id")));
        	            }
        	            items.add(object.getString("username"));
        	        }
                }
                catch (Exception e) {
                	StringWriter sw = new StringWriter();
                	e.printStackTrace(new PrintWriter(sw));
                	String exceptionAsString = sw.toString();
                	System.out.println("Error out: " + exceptionAsString);
                }
            }
        }
        catch (Exception e){
        	StringWriter sw = new StringWriter();
        	e.printStackTrace(new PrintWriter(sw));
        	String exceptionAsString = sw.toString();
        	System.out.println("Error out: " + exceptionAsString);
        }
        
        return items;
	}

    protected void onProgressUpdate() {
    	
    }

    protected void onPostExecute(ArrayList<String> items) {
    	// Client spinner
        Spinner dropdown = null;
        if (!this.clients) {
        	dropdown = this.merchantSpinner;
        }
        else {
        	dropdown = this.clientSpinner;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.act, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        dropdown.setAdapter(adapter);
    }

}
