package ch.master.gameproject;

import ch.master.gameproject.model.Media;
import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class OptionMenuActivity extends Activity{

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		//
		
		return super.onCreateView(name, context, attrs);
	}
	//TODO quelles options ?

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	public void onCBMusic(View v){
		boolean checked = false;
		if(checked)
			Media.music_on();
		else
			Media.music_off();
	}

}
