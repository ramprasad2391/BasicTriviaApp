// Group9a_HW03
// Aaron Maisto
// Ram Prasad Narayanaswamy
// Anusha Srivastava

package com.example.basictriviaapp;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Question {
	String question;
	ArrayList<String> options;
	String imageUrl;
	int correctAnswer;
	
	public Question()
	{
		options = new ArrayList<String>();
	}
	
	public Question(String question, ArrayList<String> options,
			String imageUrl, int correctAnswer) {
		super();
		this.question = question;
		this.options = options;
		this.imageUrl = imageUrl;
		this.correctAnswer = correctAnswer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ArrayList<String> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	@Override
	public String toString() {
		return "Question [question=" + question + ", options=" + options
				+ ", imageUrl=" + imageUrl + ", correctAnswer=" + correctAnswer
				+ "]";
	}
	
	public String getEncoding() throws UnsupportedEncodingException {
		
		//String result = "gid=WbjvemgPyzmf0JtcwIlm&q=" + URLEncoder.encode(this.question,"UTF-8") + ";";
		//String result = URLEncoder.encode(this.question,"UTF-8") + ";";
		/*for(String option : this.options){
			result += URLEncoder.encode(option,"UTF-8") + ";";
		}
		result += URLEncoder.encode(this.imageUrl,"UTF-8") + ";" + this.correctAnswer;*/
		String result = this.question + ";";
		
		for(String option : this.options){
			result += option + ";";
		}
		result += this.imageUrl + ";" + this.correctAnswer;
		
		return result;
	}
	
	
	
	
	
}