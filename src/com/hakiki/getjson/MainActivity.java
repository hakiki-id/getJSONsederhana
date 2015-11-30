package com.hakiki.getjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tvShowdata; 
	Button btnAmbil; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvShowdata = (TextView) findViewById(R.id.tvShow);
		btnAmbil = (Button) findViewById(R.id.btnJSON);
		
		
		btnAmbil.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new AmbilJSON().execute("http://192.168.72.1/praktikum/ParserData.php");
				
			}
		});
		
	}
	
	private class AmbilJSON extends AsyncTask<String, Void, String> {
		ProgressDialog dialog; 
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setTitle("Connect Server...");
			dialog.setMessage("Mengambil data json...");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
		protected String doInBackground(String... url) {
			// TODO Auto-generated method stub
			
			String line="",hasil="";
			try {
				URL urls = new URL(url[0]);
				HttpURLConnection httpURLConnection = (HttpURLConnection) urls.openConnection();
				httpURLConnection.connect(); 
				
				InputStream IS = httpURLConnection.getInputStream(); 
				
				BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
				
				while ((line=BR.readLine()) != null) {
					hasil += line; 
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return hasil;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.cancel(); 
			tvShowdata.setText(result + "\n");
		}
	}

	
}
