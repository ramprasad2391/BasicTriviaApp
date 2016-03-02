// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button startTrivia, exitButton, createQuestion, deleteQuestions;
	static ConnectivityManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        startTrivia = (Button) findViewById(R.id.startButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        createQuestion = (Button) findViewById(R.id.createButton);
        deleteQuestions = (Button) findViewById(R.id.deleteButton);
        
        startTrivia.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isNetworkConnected()){
					Intent intent = new Intent(MainActivity.this,com.example.basictriviaapp.TriviaActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this,"Internet not connected", Toast.LENGTH_SHORT).show();
				}								
			}
		});
        
        createQuestion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkConnected()){
					Intent intent = new Intent(MainActivity.this,com.example.basictriviaapp.CreateQuestionActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this,"Internet not connected", Toast.LENGTH_SHORT).show();
				}				
			}
		});
        
        
        deleteQuestions.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Delete Questions")
						.setMessage("Are you sure you want to delete all your questions?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if(isNetworkConnected()){
											DeleteQuestionAsyncTask submit = new DeleteQuestionAsyncTask(MainActivity.this);
											submit.execute("");
										}else{
											Toast.makeText(MainActivity.this,"Internet not connected", Toast.LENGTH_SHORT).show();
										}										
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).show();
			}
		});
        
        
        exitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();
			}
		});      
    }
    
    public static boolean isNetworkConnected(){
    	NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    	if(networkInfo != null && networkInfo.isConnected()){
    		return true;
    	}
    	return false;
    }
   
}
