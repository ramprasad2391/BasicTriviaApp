// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

public class CreateQuestionAsyncTask extends AsyncTask<Question, Integer, Boolean>{

	ProgressDialog dialog;
	CreateQuestionActivity activity;
	
	public CreateQuestionAsyncTask(CreateQuestionActivity activity)
	{
		dialog = new ProgressDialog(activity);
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {		
		dialog.setMessage("Uploading question...");
        dialog.show();
        dialog.setCancelable(false);
	}

	protected void onPostExecute(Boolean isCreated) {
		if(isCreated){
        	dialog.dismiss();
        	activity.finish();
        }
        else{
        	dialog.setMessage("Internet not connected....Cannot create the question");
        	dialog.dismiss();
        	activity.finish();
        }
	}
		
	protected Boolean doInBackground(Question... params) {
		AndroidHttpClient httpClient = null;
		if(MainActivity.isNetworkConnected()){
			try{	
				httpClient = AndroidHttpClient.newInstance("AndroidAgent");
				HttpPost request = new HttpPost("http://dev.theappsdr.com/apis/trivia/saveNew.php");
				ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(1);
			    nameValuePairs.add(new BasicNameValuePair("gid","WbjvemgPyzmf0JtcwIlm"));
			    nameValuePairs.add(new BasicNameValuePair("q",params[0].getEncoding()));  
			    request.setEntity(new UrlEncodedFormEntity(nameValuePairs));	    
			    HttpResponse response = httpClient.execute(request);
			    String str = EntityUtils.toString(response.getEntity());
			    return true;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				httpClient.close();
			}
		}				
		return false;
	}	
}