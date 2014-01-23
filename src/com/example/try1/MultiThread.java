package com.example.try1;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.try1.MainActivity.httpRequestHandler;

import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
	
	public class MultiThread extends Activity{
		Handler hand = new Handler();
	    Button clickme;
	    
	

	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        final Button button = (Button) findViewById(R.id.button_ok);
	        EditText emailEditText = (EditText) findViewById(R.id.edit_UserName);
	        EditText passwordEditText = (EditText) findViewById(R.id.edit_UserPassword);
	        //hand.postDelayed(run, 1000);
	        
	        new Thread(new Runnable() { 
	            public void run(){

	        button.setText("Thread!!");


	            }
	    }).start();

	    } 

	}
	/*package com.nkm.thread;
 
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
 
public class MultiThreadingActivity extends Activity {
    Handler hand = new Handler();
    Button clickme;
    TextView timer;
 
    @Override
    public void onCreate(Bundle_savedInstance) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        timer = (TextView) findViewById(R.id_timer);
        clickme = (Button) findViewById(R.id_clickme);
        hand.postDelayed(run, 1000);
    }
 
    Runnable run = new Runnable() {
        @Override
        public void run() {
            updateTime();
 
        }
    };
 
    public void updateTime() {
        timer.setText("" + (Integer.parseInt(timer.getText().toString()) - 1));
        if (Integer.parseInt(timer.getText().toString()) == 0) {
            clickme.setVisibility(0);
        } else {
            hand.postDelayed(run, 1000);
        }
    }
}


public class ReadWebpageAsyncTask extends Activity {
		  private TextView textView;

		  @Override
		  public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.main);
		    textView = (TextView) findViewById(R.id.TextView01);
		  }

		  private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		    @Override
		    protected String doInBackground(String... urls) {
		      String response = "";
		      for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          InputStream content = execute.getEntity().getContent();

		          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		          String s = "";
		          while ((s = buffer.readLine()) != null) {
		            response += s;
		          }

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		      return response;
		    }

		    @Override
		    protected void onPostExecute(String result) {
		      textView.setText(result);
		    }
		  }

		  public void onClick(View view) {
		    DownloadWebPageTask task = new DownloadWebPageTask();
		    task.execute(new String[] { "http://www.vogella.com" });

		  }
		} 
	public void onClick(View v) {
	    new Thread(new Runnable() {
	        public void run() {ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map1 = new HashMap<String, String>();
		    map1.put("name", "action");
		    map1.put("value", "login");
		    map.add(0,map1);
		    map1 = new HashMap<String, String>();
		    map1.put("name", "email");
		    map1.put("value", userEmail);
		    map.add(0,map1);
		    map1 = new HashMap<String, String>();
		    map1.put("name", "password");
		    map1.put("value", userPassword);
		    map.add(0,map1);
	        	httpRequestHandler nhrh = new httpRequestHandler();
			    String response = nhrh.doInBackground("http://crmtest.appbox.com.my/login.php", map, 3);
	            mHandler.post(new Runnable() {
	                public void run() {
	                    mhandler.httpRequestHandler(bitmap);
	                }
	            });
	        }
	    }).start();*/
	
	
	