// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;








import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TriviaActivity extends Activity {
	ProgressDialog progressDialog;
	ArrayList<Question> questionsArray;
	TextView tv1, tv2, tv3;
	static int questioncounter;
	RadioGroup rg;
	ScrollView sv;
	static CountDownTimer countDownTimer;
	ProgressBar progressBar;
	ImageView img;
	Question qn;
	int resultCounter;
	Button nextButton,quit;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trivia);
		questionsArray = new ArrayList<Question>();
		questioncounter = 0;
		resultCounter = 0;
		img = (ImageView) findViewById(R.id.imageView1);
		tv3 = (TextView) findViewById(R.id.timer);
		tv2 = (TextView) findViewById(R.id.questionNum);
		tv1 = (TextView) findViewById(R.id.questionDisplay);
		new DoWork().execute("");
				
		quit = (Button) findViewById(R.id.quitButton);	
		quit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TriviaActivity.this.finish();
			}
		});		
		
		nextButton = (Button) findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("answer",rg.getCheckedRadioButtonId()+"");
				if(!(rg.getCheckedRadioButtonId() == -1)){
					int checkedButton = rg.getCheckedRadioButtonId();
					int checkedIndex = rg.indexOfChild(findViewById(checkedButton));
					Log.d("questionchoice",qn.getCorrectAnswer()+"");
					Log.d("answerchoice",checkedIndex+"");
					if(checkedIndex == qn.getCorrectAnswer())
					{
						resultCounter = resultCounter + 1;
					}
				}
				questioncounter++;				
				if(questioncounter < questionsArray.size()){				
					rg.removeAllViews();
					tv3.setText("");
					countDownTimer.cancel();
					displayQuestion(questioncounter);
				}
				else{
					intent = new Intent(TriviaActivity.this,com.example.basictriviaapp.ResultsActivity.class);
					intent.putExtra("Result Counter", resultCounter);
					intent.putExtra("Max Size", questionsArray.size());
					startActivityForResult(intent,0);
				}
			}
		});

	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 0){
			TriviaActivity.this.finish();
		}
		if(resultCode == 1){
			Intent intent = getIntent();
			TriviaActivity.this.finish();
			startActivity(intent);
		}
	}

	
	public void displayQuestion(int questioncounter)
	{		
		int questionNumber = questioncounter +1;
		tv2.setText("Q"+questionNumber);
		qn = questionsArray.get(questioncounter);		
		tv1.setText(qn.getQuestion());
		int noofoptions = qn.getOptions().size();
		
		sv = (ScrollView) findViewById(R.id.scrollViewoptions);
		rg = new RadioGroup(TriviaActivity.this);
		LinearLayout ll = (LinearLayout) sv.getChildAt(0);
		ll.addView(rg);
		for(int i =0;i<noofoptions;i++)
		{
			RadioButton rb = new RadioButton(TriviaActivity.this);
			rb.setText(qn.getOptions().get(i));
			rg.addView(rb);
		}
		if(qn.getImageUrl().length() != 0){
			new ShowImageAsyncTask(TriviaActivity.this).execute(qn.getImageUrl());
		}
		else{
			new ShowImageAsyncTask(TriviaActivity.this).execute("http://www.schoolsforyou.in/assets/image-unavailable.jpg");
		}
						
	}

	
	class DoWork extends AsyncTask<String, Integer, ArrayList<Question>> {
		// runs in the child thread
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
			progressBar = (ProgressBar) findViewById(R.id.progressBar1);
			img = (ImageView) findViewById(R.id.imageView1);
			progressBar.setVisibility(ProgressBar.INVISIBLE);
			img.setVisibility(ImageView.INVISIBLE);

			progressDialog = new ProgressDialog(TriviaActivity.this);
			progressDialog.setMax(100);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading..");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
		}
		
		@Override
		protected void onPostExecute(ArrayList<Question> result) {					
			
			if(result != null){
				progressDialog.dismiss();
				nextButton.setVisibility(Button.VISIBLE);
				questionsArray = result;
				displayQuestion(questioncounter);
			}
			else{
				progressDialog.dismiss();
				nextButton.setVisibility(Button.INVISIBLE);
				Toast.makeText(TriviaActivity.this,"Internet not connected", Toast.LENGTH_SHORT).show();
			}
						
		}
	
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			progressDialog.setProgress(values[0]);
		}
		
		@Override
		protected ArrayList<Question> doInBackground(String... params) {

			BufferedReader reader = null;
			URL url;
			if(MainActivity.isNetworkConnected()){
				try {
					url = new URL("http://dev.theappsdr.com/apis/trivia/getAll.php");
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					con.setRequestMethod("GET");
					reader = new BufferedReader(new InputStreamReader(
							con.getInputStream()));

					String line = "";
					while ((line = reader.readLine()) != null) {
							String[] qnarr = null;
							qnarr = line.split(";");
							boolean isOptionEmpty = false;
							for(int j=1;j<qnarr.length-2;j++){
								if(qnarr[j].length() == 0){
									isOptionEmpty = true;
									break;
								}
							}
							if (qnarr.length >= 5 && qnarr[0].length() > 0 && qnarr[qnarr.length - 1].matches("\\d+") && !isOptionEmpty) {
								qn = new Question();
								qn.setQuestion(qnarr[0]);
								qn.setCorrectAnswer(Integer
										.parseInt(qnarr[qnarr.length - 1]));
								qn.setImageUrl(qnarr[qnarr.length - 2]);
								ArrayList<String> optionsarr = new ArrayList<String>();
								for (int i = 1; i < qnarr.length - 2; i++) {
									optionsarr.add(qnarr[i]);
								}
								qn.setOptions(optionsarr);
								questionsArray.add(qn);
								publishProgress(questionsArray.size());
							}
					}
					return questionsArray;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			return null;
		}
	}		
}