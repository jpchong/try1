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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.try1.MainActivity.checkhttpRequestHandler;



import android.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CategoryList extends ListActivity{
	public SharedPreferences sp;
	public Object view = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.category_list);
		Intent intent = getIntent();
		sp = getSharedPreferences("userInfo",MODE_PRIVATE);
		String preloginid = sp.getString("loginid", "0" ).toString();
		String preauth = sp.getString("auth", "0" ).toString();
		String precode = sp.getString("code", "0" ).toString();
		ArrayList<HashMap<String, String>> trymap = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> trymap1 = new HashMap<String, String>();
	    trymap1.put("name", "action");
	    trymap1.put("value", "category");
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
	    cnhrh.postUrl = "http://crmtest.appbox.com.my/products.apps.php";
	    cnhrh.execute(new String[] {""});
	}
	/*
	public class ListViewLoader extends ListActivity {
	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setListAdapter(new ArrayAdapter<String>(CategoryList.this, android.R.layout.simple_list_item_1, 0));
	        
	        //Intent intent = getIntent();
	        ProgressBar progressBar = new ProgressBar(this);
	        progressBar.setLayoutParams(new LayoutParams (LayoutParams.WRAP_CONTENT));
	        progressBar.setIndeterminate(true);
	        getListView().setEmptyView(progressBar);
	        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	        root.addView(progressBar);
	        
	        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
	                R.layout.person_name_and_number, cursor, fromColumns, toViews, 0);
	        ListView listView = getListView();
	        listView.setAdapter(adapter);
	        
	        ArrayList<HashMap<String, String>> myArrayList;
		}
	}
	*/
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
				System.out.println(response);
				if(jsonObj.get("success").toString().equalsIgnoreCase("1")){
				    JSONArray categorylistarray = jsonObj.getJSONArray("category");
				    ArrayList<String> stringArrayList = new ArrayList<String>();
				    for (int i = 0; i < categorylistarray.length(); i++) {
				    	JSONObject currentrow = (JSONObject) categorylistarray.get(i);
				    	stringArrayList.add(currentrow.getString("categoryname"));
				    }
				    String[] adapterarray = stringArrayList.toArray(new String[stringArrayList.size()]);
				    ((ListActivity) view).setListAdapter( new ArrayAdapter(CategoryList.this,R.layout.simple_list_item_1 ,R.id.text1, adapterarray));
				}else{
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
}

		