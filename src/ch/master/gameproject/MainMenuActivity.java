package ch.master.gameproject;

import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

public class MainMenuActivity extends Activity{

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	public void onClickButtonPlay(View v){
		startActivity(new Intent(this, LevelSelectionMenuActivity.class));
	}
	
	public void onClickButtonHighScores(View v){
		//startActivity(new Intent(this, HighScoreActivity.class));
	}
	
	public void onClickButtonOption(View v){
		startActivity(new Intent(this, OptionMenuActivity.class));
	}

}
