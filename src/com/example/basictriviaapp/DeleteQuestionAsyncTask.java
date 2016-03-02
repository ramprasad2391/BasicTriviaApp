// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

public class DeleteQuestionAsyncTask extends AsyncTask<String, Void, Boolean> {
	ProgressDialog dialog;
	MainActivity activity;
	
	public DeleteQuestionAsyncTask(MainActivity activity)
	{
		dialog = new ProgressDialog(activity);
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {	
		dialog.setTitle("Deleting Questions");
		dialog.setMessage("Deleting...");
        dialog.show();
        dialog.setCancelable(false);
	}

	protected void onPostExecute(Boolean isDeleted) {	
        if(isDeleted){
        	dialog.dismiss();
        }
        else{
        	dialog.setMessage("Internet not connected....Cannot delete the questions");
        	dialog.dismiss();
        }		      
	}

	@Override
	protected Boolean doInBackground(String... params) {
		AndroidHttpClient httpClient = null;		
		if(MainActivity.isNetworkConnected()){
			try{	
				httpClient = AndroidHttpClient.newInstance("AndroidAgent");
				HttpPost request = new HttpPost("http://dev.theappsdr.com/apis/trivia/deleteAll.php");
				ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(1);
			    nameValuePairs.add(new BasicNameValuePair("gid","WbjvemgPyzmf0JtcwIlm"));  
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
