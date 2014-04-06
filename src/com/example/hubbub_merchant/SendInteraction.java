package com.example.hubbub_merchant;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

public class SendInteraction extends AsyncTask<Void, Void, String> {
	String clientName;
	String clientId;
	String merchantName;
	String merchantId;
	String merchantKey;
	String metric;
	FullscreenActivity act;
	TextView tView;
	EditText mView;
	
	public SendInteraction(FullscreenActivity a, TextView tv, EditText metV, String clientN, String clientI, String merchantN, String merchantI, String merchantK, String m) {
		this.act = a;
		this.tView = tv;
		this.mView = metV;
		this.clientName = clientN;
		this.clientId = clientI;
		this.merchantName = merchantN;
		this.merchantId = merchantI;
		this.merchantKey = merchantK;
		this.metric = m;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        
        HttpPost post = new HttpPost("https://hubbubapp.herokuapp.com/api/v1/interactions");
        JSONObject data = new JSONObject();
        
        try {
        	data.put("merchantName", this.merchantName);
        	data.put("merchantId", this.merchantId);
        	data.put("merchantSecretKey", this.merchantKey);
        	data.put("clientName", this.clientName);
        	data.put("clientId", this.clientId);
        	data.put("metric", metric);
        	data.put("timestamp", System.currentTimeMillis());
            
        	StringEntity se = new StringEntity(data.toString());
        	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = client.execute(post);
            
            System.out.println("Attempt to send:\n" + this.merchantName + "\n" + this.merchantId + "\n" + this.clientName + "\n" + this.clientId + "\n" + this.metric);
            
            /*Checking response */
            if(response != null){
                InputStream in = response.getEntity().getContent(); //Get the data in the entity
            }
            
        }
        catch(Exception e) {
        	PrintException.print(e);
        }

        return "fin";
	}

    protected void onProgressUpdate() {
    	
    }

    @Override
    protected void onPostExecute(String cmd) {
    	if (cmd.equals("fin")) {
    		this.tView.setText("");
    		this.mView.setText("");
    		
    		new AlertDialog.Builder(this.act)
    	      .setMessage("Your interaction was sent successfully.")
    	      .setTitle("Well done.")
    	      .setCancelable(true)
    	      .setNegativeButton("Great!",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
    	      .show();
    		
    	}
    }

}
