// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
	TriviaActivity activity;
	ProgressBar progressBar;
	ImageView img;
	TextView progress;
	

	public ShowImageAsyncTask(TriviaActivity activity) {
		this.activity = activity;
	}
	

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressBar = (ProgressBar) activity.findViewById(R.id.progressBar1);
		img = (ImageView) activity.findViewById(R.id.imageView1);
		progress = (TextView) activity.findViewById(R.id.textView1);
		progressBar.setMax(100);
		progressBar.setVisibility(ProgressBar.VISIBLE);
		img.setVisibility(ImageView.INVISIBLE);
		progress.setVisibility(TextView.VISIBLE);
		progress.setText("Loading Image..");
		activity.nextButton.setClickable(false);
	}
	
	protected Bitmap doInBackground(String... params) {
		
		if(MainActivity.isNetworkConnected()){
			try {
				URL url = new URL((String) params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				int statusCode = con.getResponseCode();
				if(statusCode == HttpURLConnection.HTTP_OK){
					Log.d("demo",params[0]);
					return BitmapFactory.decodeStream(con.getInputStream());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]);
	}

	protected void onPostExecute(Bitmap image) {
		// TODO Auto-generated method stub
		
		progressBar.setVisibility(ProgressBar.INVISIBLE);			
		if(image != null){
			progress.setVisibility(TextView.INVISIBLE);	
			img.setImageBitmap(image);
			img.setVisibility(ImageView.VISIBLE);
		}
		else{
			progress.setVisibility(TextView.VISIBLE);
			progress.setText("Image not loaded due to internet connectivity problem");
			img.setVisibility(ImageView.INVISIBLE);
		}
		
		activity.nextButton.setClickable(true);

		TriviaActivity.countDownTimer = new CountDownTimer(24000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				activity.tv3.setText((millisUntilFinished / 1000) + " Seconds");
			}

			@Override
			public void onFinish() {
				
				Log.d("answer",activity.rg.getCheckedRadioButtonId()+"");
				if(!(activity.rg.getCheckedRadioButtonId() == -1)){
					int checkedButton = activity.rg.getCheckedRadioButtonId();
					int checkedIndex = activity.rg.indexOfChild(activity.findViewById(checkedButton));
					if(checkedIndex == activity.qn.getCorrectAnswer())
					{
						activity.resultCounter++;
					}
				}
				TriviaActivity.questioncounter++;				
				if(TriviaActivity.questioncounter < activity.questionsArray.size()){				
					activity.rg.removeAllViews();
					activity.tv3.setText("");
					//TriviaActivity.countDownTimer.cancel();
					activity.displayQuestion(TriviaActivity.questioncounter);
				}
				else{
					activity.finish();
					Intent intent = new Intent(activity,com.example.basictriviaapp.ResultsActivity.class);
					intent.putExtra("Result Counter", activity.resultCounter);
					intent.putExtra("Max Size", activity.questionsArray.size());
					activity.startActivityForResult(intent,0);					
					Log.d("result",activity.resultCounter+"");
				}
			}
		}.start();
		}
	
	
}
