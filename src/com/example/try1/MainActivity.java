package com.example.try1;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public SharedPreferences sp;
	public Object mainView = this;
	private LinearLayout mainLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainLayout = (LinearLayout) this.findViewById(R.id.loginLayout);
		mainLayout.setVisibility(LinearLayout.INVISIBLE);
		sp = getSharedPreferences("userInfo",MODE_PRIVATE);
		String preloginid = sp.getString("loginid", "0" ).toString();
		System.out.println(preloginid);
		if(!preloginid.equalsIgnoreCase("0")){
			String preauth = sp.getString("auth", "0" ).toString();
			String precode = sp.getString("code", "0" ).toString();
			ArrayList<HashMap<String, String>> trymap = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> trymap1 = new HashMap<String, String>();
		    trymap1.put("name", "action");
		    trymap1.put("value", "checklogin");
		    trymap.add(0,trymap1);
		    trymap1 = new HashMap<String, String>();
		    trymap1.put("name", "loginid");
		    trymap1.put("value", preloginid);
		    trymap.add(1,trymap1);
		    trymap1 = new HashMap<String, String>();
		    trymap1.put("name", "auth");
		    trymap1.put("value", preauth);
		    trymap.add(2,trymap1);
		    trymap1 = new HashMap<String, String>();
		    trymap1.put("name", "code");
		    trymap1.put("value", precode);
		    trymap.add(3,trymap1);
		    checkhttpRequestHandler cnhrh = new checkhttpRequestHandler();
		    cnhrh.fieldCount = 4;
		    cnhrh.map = trymap;
		    cnhrh.postUrl = "http://crmtest.appbox.com.my/login.php";
		    cnhrh.execute(new String[] {""});
		}else{
			mainLayout = (LinearLayout) this.findViewById(R.id.loginLayout);
			mainLayout.setVisibility(LinearLayout.VISIBLE);
		}
		
		
	
		
		Button button = (Button) findViewById(R.id.button_ok);
		button.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	EditText emailEditText = (EditText) findViewById(R.id.edit_UserName);
				String userEmail = emailEditText.getText().toString();
				EditText passwordEditText = (EditText) findViewById(R.id.edit_UserPassword);
				String userPassword = passwordEditText.getText().toString();
				if((userEmail.equalsIgnoreCase(""))||(userPassword.equalsIgnoreCase(""))){
					if(userEmail.equalsIgnoreCase("")){
						emailEditText.requestFocus();
					}else{
						passwordEditText.requestFocus();
					}
				}else{
					InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
					inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
					
					
					ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map1 = new HashMap<String, String>();
				    map1.put("name", "action");
				    map1.put("value", "login");
				    map.add(0,map1);
				    map1 = new HashMap<String, String>();
				    map1.put("name", "email");
				    map1.put("value", userEmail);
				    map.add(1,map1);
				    map1 = new HashMap<String, String>();
				    map1.put("name", "password");
				    map1.put("value", userPassword);
				    map.add(2,map1);
				    httpRequestHandler nhrh = new httpRequestHandler();
				    nhrh.fieldCount = 3;
				    nhrh.map = map;
				    nhrh.postUrl = "http://crmtest.appbox.com.my/login.php";
				    nhrh.execute(new String[] {""});
				}
		    }
		    
		    
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
		
	}
	
	public void toCategoryListIntent(){
		
		Intent intent = new Intent(this, CategoryList.class);
		startActivity(intent);
		
		
	}
	
	public class checkhttpRequestHandler extends AsyncTask<String, Integer, String>{ //check login
		public String postUrl;
		public ArrayList<HashMap<String, String>> map;
		public int fieldCount;
		protected String doInBackground(String... nothingHere) {
			
			try{
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(postUrl);
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for(int i = 0; i < fieldCount;i++){
					Map<String,String> currentParam =  map.get(i);
					pairs.add(new BasicNameValuePair(currentParam.get("name"), currentParam.get("value")));
				}
				post.setEntity(new UrlEncodedFormEntity(pairs));
				HttpResponse response = client.execute(post);
				String responseBody = EntityUtils.toString(response.getEntity());
				//System.out.println(responseBody);
				return responseBody;
			} catch (ClientProtocolException e) {
	            return null;
	        } catch (IOException e) {
	            return null;
	        }
		}
		@Override
	    protected void onPostExecute(String response) {
			try {
				JSONObject jsonObj = new JSONObject(response);
				System.out.println("converting JSON");
				if(jsonObj.get("logged").toString().equalsIgnoreCase("1")){
					sp = getSharedPreferences("userInfo",MODE_PRIVATE);
					String preuserid = sp.getString("userid", "0" ).toString();
					if(preuserid.equalsIgnoreCase(jsonObj.getString("userid"))){ // check userid
						// same userid
						
						Toast toast = Toast.makeText(getApplicationContext(), "hahahahahhaah.", 10000);
						toast.show();
						toCategoryListIntent();
						//Intent openStartingPoint = new Intent ("com.example.try1.CategoryList");
						//startActivity(openStartingPoint);
					}else{
						//cheating
						LinearLayout mainLayout=(LinearLayout) ((LinearLayout) mainView).findViewById(R.id.loginLayout);
						mainLayout.setVisibility(LinearLayout.VISIBLE);
						
					}
				}else{
					//remove key loginid,userid,auth,code FROM SharedPreference
					LinearLayout mainLayout=(LinearLayout) ((LinearLayout) mainView).findViewById(R.id.loginLayout);
					mainLayout.setVisibility(LinearLayout.VISIBLE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public class httpRequestHandler extends AsyncTask<String, Integer, String>{ // login
		public String postUrl;
		public ArrayList<HashMap<String, String>> map;
		public int fieldCount;
		protected String doInBackground(String... nothingHere) {
			
			try{
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(postUrl);
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for(int i = 0; i < fieldCount;i++){
					Map<String,String> currentParam =  map.get(i);
					pairs.add(new BasicNameValuePair(currentParam.get("name"), currentParam.get("value")));
				}
				post.setEntity(new UrlEncodedFormEntity(pairs));
				HttpResponse response = client.execute(post);
				String responseBody = EntityUtils.toString(response.getEntity());
				//System.out.println(responseBody);
				return responseBody;
			} catch (ClientProtocolException e) {
	            return null;
	        } catch (IOException e) {
	            return null;
	        }
		}
		@Override
	    protected void onPostExecute(String response) {
			try {
				JSONObject jsonObj = new JSONObject(response);
				System.out.println("converting JSON");
				if(jsonObj.get("logged").toString().equalsIgnoreCase("1")){
					sp = getSharedPreferences("userInfo",MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("loginid", jsonObj.get("loginid").toString());
					editor.putString("userid", jsonObj.get("userid").toString());
					editor.putString("auth", jsonObj.get("auth").toString());
					editor.putString("code", jsonObj.get("code").toString());
					editor.commit();
					//Intent nextActivity = new Intent();
					Toast toast = Toast.makeText(getApplicationContext(), "Yeah.", 10000);
					toast.show();
					System.out.println("logged=1");
					toCategoryListIntent();
				}else{
					Toast toast = Toast.makeText(getApplicationContext(), "Please smile and try again. Have a good day~ Welcome back to AppBox Technology.", 10000);
					toast.show();
					System.out.println("success=0");
					
					//Intent openStartingPoint = new Intent ("com.example.try1.CategoryList");
					//startActivity(openStartingPoint);
				}
				System.out.println("end try");
			} catch (JSONException e) {
				Toast toast = Toast.makeText(getApplicationContext(), "Please OMG.", 10000);
				toast.show();
				System.out.println("catch");
				e.printStackTrace();
			}
	    }
	}
}
