package ch.master.gameproject.model;


import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;

import ch.master.gameproject.ressource.InitRessources;

public class LevelManagerGame {
	
	private static int levelNow;

	public static int getLevelNow() {
		return levelNow;
	}

	public static int getLevelSelect() {
		return levelSelect;
	}

	public static void setLevelSelect(int levelSelect) {
		LevelManagerGame.levelSelect = levelSelect;
	}

	private static int levelSelect;
	private static final String LEVEL_LABEL = "level";
	
	
	public static void saveLevel(int level) {
		    levelNow = level;
			InitRessources.mLvlDbEditor.putInt(LEVEL_LABEL, level);
	        InitRessources.mLvlDbEditor.commit();
	}
	 
	public static void loadLevel() {
		levelNow  =	InitRessources.mLvlDb.getInt(LEVEL_LABEL, 0);
		if(levelNow!=1){
			saveLevel(1);
		}
	}
	
	
	
	
}
