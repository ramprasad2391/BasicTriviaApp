// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		Bundle b = getIntent().getExtras();
		int max = b.getInt("Max Size");		
		int result = b.getInt("Result Counter");
		
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setMax(max);
		progressBar.setProgress(result);
		
		float resPercent = ((float)result/max) * 100;
		int resultPercent = Math.round(resPercent);
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setText(resultPercent+"%");
		
		TextView tv = (TextView) findViewById(R.id.messageTextview);
		if(result == max){
			tv.setText(getResources().getString(R.string.resultmessage2));
		}else{			
			tv.setText(getResources().getString(R.string.resultmessage1));
		}
		
		
		Button quit = (Button) findViewById(R.id.quitButton1);
		Button tryAgain = (Button) findViewById(R.id.tryAgainButton);
		
		quit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				setResult(0);
				ResultsActivity.this.finish();
			}
		});
		
		tryAgain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(1);
				ResultsActivity.this.finish();
				//intent = new Intent(ResultsActivity.this,com.example.group9a_hw3.TriviaActivity.class);				
				//startActivity(intent);			
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		setResult(0);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setResult(0);
	}
}