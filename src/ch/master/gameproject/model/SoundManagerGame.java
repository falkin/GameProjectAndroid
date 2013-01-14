package ch.master.gameproject.model;


import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;

import ch.master.gameproject.ressource.InitRessources;

public class SoundManagerGame {
	
	private static int isSoundEnable;
	private static final String SOUND_LABEL = "sound";
	
	
	public static void saveHighScore(int isEnable) {
		    isSoundEnable = isEnable;
			InitRessources.mScoreDbEditor.putInt(SOUND_LABEL, isSoundEnable);
	        InitRessources.mScoreDbEditor.commit();
	}
	 
	public static void loadHighScore() {
		     isSoundEnable=	InitRessources.mScoreDb.getInt(SOUND_LABEL, 0);	      
	}
	
	public static int isSoundEnable(){
		  return isSoundEnable;
	}


	public static void startMusic(Music music) {
		if(isSoundEnable == 0){
			music.play();
			music.setVolume(3);
		}
	}

	public static void pauseMusic(Music music) {
		if (music.isPlaying())
			music.pause();
	}
	public static void stopMusic(Music music) {
		if (music.isPlaying())
			music.stop();
	}
	public static void resumeMusic(Music music) {
		if (!music.isPlaying())
			music.resume();
	}
	
	
	public static void startMusic(Sound music) {
		if(isSoundEnable == 0){
			music.play();
			music.setVolume(3);
		}
	}
}
