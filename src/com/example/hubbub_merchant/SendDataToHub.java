package com.example.hubbub_merchant;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hubbub.R;

public class SendDataToHub extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_data_to_hub);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		String userId = intent.getStringExtra(FullscreenActivity.EXTRA_USER_ID);
		String metricValue = intent.getStringExtra(FullscreenActivity.EXTRA_METRIC_VALUE);
		
		TextView displayUserIdView = (TextView) findViewById(R.id.display_user_id);
		TextView displayMetricView = (TextView)findViewById(R.id.display_metric);
		
		displayUserIdView.setText("Sending User ID: " + userId);
		displayMetricView.setText("Sending Metric: " + metricValue);
		
		HubApi.write(userId, metricValue);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_data_to_hub, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
