// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CreateQuestionActivity extends Activity {

	Button addButton, submitButton;
	EditText questionText, optionText, imageURL;
	RadioGroup optionsGroup;
	Question newQuestion;
	ProgressDialog progD;
	ProgressBar progBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_question);
		
		addButton = (Button) findViewById(R.id.addButton);
		submitButton = (Button) findViewById(R.id.submitButton);
		questionText = (EditText) findViewById(R.id.questionEdittext);
		optionText = (EditText) findViewById(R.id.optionEdittext);
		imageURL = (EditText) findViewById(R.id.imageEdittext);
		optionsGroup = (RadioGroup) findViewById(R.id.radioGroupOptions);
		
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (optionText.getText() == null || optionText.getText().toString().length() == 0){
					optionText.setError("Option field cannot be blank."); 
				}
				else{
					optionText.setError(null);
					optionsGroup.addView(newOption(optionText.getText().toString()));
					optionText.setText("");
				}
			}
		});
	
		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (optionsGroup.getChildCount() < 2) {
					addButton.setError("Questions must have at least 2 options.");
				}
				else{
					addButton.setError(null);
				}
				if(questionText.getText() == null || questionText.getText().toString().length() == 0)
				{
					questionText.setError("Question field cannot be blank.");
				}else{
					questionText.setError(null);
				}
				
				if(imageURL.getText() == null) { imageURL.setText(""); }
				
				if(optionsGroup.getCheckedRadioButtonId() == -1){
					submitButton.setError("Check one of the radio buttons as the correct answer");
				}
				else{
					submitButton.setError(null);
				}
				
				if(addButton.getError() == null && questionText.getError() == null && submitButton.getError() == null)
				{
					ArrayList<String> options = new ArrayList<String>();
					
					for(int i = 0; i < optionsGroup.getChildCount(); i++)
					{
						options.add(((RadioButton) optionsGroup.getChildAt(i)).getText().toString());
					}
					newQuestion = new Question(questionText.getText().toString(), options, imageURL.getText().toString(), optionsGroup.indexOfChild(findViewById(optionsGroup.getCheckedRadioButtonId())));
					CreateQuestionAsyncTask submit = new CreateQuestionAsyncTask(CreateQuestionActivity.this);
					submit.execute(newQuestion);
					try {
						Log.d("String message",newQuestion.getEncoding());
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
	}
	
	private RadioButton newOption(String text)
	{
		RadioButton option = new RadioButton(this);
		option.setText(text);
		return option;
	}
}