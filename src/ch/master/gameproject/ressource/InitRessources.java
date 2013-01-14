package ch.master.gameproject.ressource;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import ch.master.gameproject.MainActivity;

public class InitRessources {

	public static Font mFont;
	public static Font mFontBD;
	public static BitmapTextureAtlas mBtPlay;
	public static TextureRegion btPlay;
	public static BitmapTextureAtlas mBtExit;
	public static TextureRegion btExit;
	public static BitmapTextureAtlas mBtTools;
	public static TextureRegion btTools;
	public static BitmapTextureAtlas mBtBack;
	public static TextureRegion btBack;
	public static BitmapTextureAtlas mBtSoundOn;
	public static TextureRegion btSoundOn;
	public static BitmapTextureAtlas mBtSoundOff;
	public static TextureRegion btSoundOff;
	public static BitmapTextureAtlas mBtWW;
	public static TextureRegion btWW;
	public static BitmapTextureAtlas mBtLvl1;
	public static TextureRegion btLvl1;
	public static BitmapTextureAtlas mBtResume;
	public static TextureRegion btResume;
	public static BitmapTextureAtlas mBtRestart;
	public static TextureRegion btRestart;
	public static BitmapTextureAtlas mBtQuit;
	public static TextureRegion btQuit;
	public static BitmapTextureAtlas mBtNext;
	public static TextureRegion btNext;
	public static TextureRegion mWinTextureRegion;
	public static TextureRegion mFailTextureRegion;
	public static TextureRegion initTextureRegion;
	public static TextureRegion initTextureRegionBackOpt;
	public static TextureRegion initTextureRegionBackWorld;
	public static TextureRegion initTextureRegionBackLevelUn;
	public static BitmapTextureAtlas mFontTexture;
	public static BitmapTextureAtlas mFontTextureBD;
	public static BitmapTextureAtlas sheetBitmapTextureAtlas;
	public static BitmapTextureAtlas sheetBitmapTextureAtlass;
	public static BitmapTextureAtlas splashTextureAtlasBack;
	public static BitmapTextureAtlas splashTextureAtlasBackOpt;
	public static BitmapTextureAtlas splashTextureAtlasBackWorld;
	public static BitmapTextureAtlas splashTextureAtlasBackLevel;
	public static BitmapTextureAtlas splashTextureAtlasBackPause;
	public static BitmapTextureAtlas splashTextureAtlasBackLose;
	public static BitmapTextureAtlas splashTextureAtlasBackWin;
	public static BitmapTextureAtlas mBitmapTextureAtlas;
	public static TextureRegion mPausedTextureRegion;
	public static TiledTextureRegion mTargetTextureRegion;
	public static TiledTextureRegion loadingTextureRegion;
	public static TextureRegion mProjectileTextureRegion;
	public static TiledTextureRegion mPlayerTextureRegion;
	public static TextureRegion mParallaxLayer;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	public static Sound shootingSound;
	public static Sound clickSound;
	public static Music backgroundMusic;
	public static Music backgroundMusicMenu;
	public static  Typeface fontDB;
	
	
	private static final String SOUND_DB = "EN_SOUND";
	
	public static SharedPreferences mScoreDb ;
	public static SharedPreferences.Editor mScoreDbEditor;
		
	public static void initRessources(MainActivity mainActivity){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		mScoreDb = mainActivity.getSharedPreferences(SOUND_DB, Context.MODE_PRIVATE);
		mScoreDbEditor = mScoreDb.edit();
		
		
		mBitmapTextureAtlas = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		sheetBitmapTextureAtlas = new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 512);
		splashTextureAtlasBackPause= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		splashTextureAtlasBackWin= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		splashTextureAtlasBackLose= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		mBtPlay = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		
		mBtExit  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mBtTools  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtBack = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mBtWW = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtQuit = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtRestart = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtResume = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
						512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtNext = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		mBtSoundOn  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mBtSoundOff  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtLvl1  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		splashTextureAtlasBackOpt =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		splashTextureAtlasBackLevel =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		splashTextureAtlasBackWorld =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);
		
		splashTextureAtlasBack =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		
		initTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBack, mainActivity, "back.png", 0,
						0);
		initTextureRegionBackOpt  = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackOpt, mainActivity, "back_opt.png", 0,
						0);
		initTextureRegionBackWorld = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackWorld, mainActivity, "backworld.png", 0,
						0);
		initTextureRegionBackLevelUn = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackLevel, mainActivity, "back_level.png", 0,
						0);
		mParallaxLayer = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture, mainActivity,
						"background.png", 0, 0);
		
		
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlas, mainActivity,
						"hero.png", 0, 212, 11, 1);
	
		mTargetTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlas, mainActivity,
						"zombieBig.png", 0, 0, 3, 1);

		mProjectileTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, mainActivity, "bullet.png",
						64, 0);

		mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackPause, mainActivity, "backPause.png",
						0, 64);

		mWinTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackWin, mainActivity, "back_win.png", 0,
						128);
		mFailTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackLose, mainActivity, "back_lose.png", 0,
						256);
		
		btPlay = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtPlay, mainActivity, "bt_play.png", 0,
						256);
		btExit = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtExit, mainActivity, "bt_exit.png", 0,
						256);
		btTools = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtTools, mainActivity, "bt_tools.png", 0,
						256);
		btWW = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtWW, mainActivity, "bt_western.png", 0,
						256);
		btBack = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtBack, mainActivity, "bt_back.png", 0,
						256);
		btQuit = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtQuit, mainActivity, "bt_quit.png", 0,
						256);
		btResume = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtResume, mainActivity, "bt_resume.png", 0,
						256);
		btRestart = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtRestart, mainActivity, "bt_restart.png", 0,
						256);
		btSoundOn = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtSoundOn, mainActivity, "bt_soundOn.png", 0,
						256);
		btSoundOff = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtSoundOff, mainActivity, "bt_soundOff.png", 0,
						256);
		btLvl1 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtLvl1, mainActivity, "bt_lvl1.png", 0,
						256);
		btNext = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtNext, mainActivity, "bt_next.png", 0,
						256);
		mFontTexture = new BitmapTextureAtlas(mainActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mFontTextureBD = new BitmapTextureAtlas(mainActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    FontFactory.setAssetBasePath("gfx/");
	//fontDB = Typeface.crcreateFromAsset(mainActivity.getMEngine().getFontManager(),"BD.ttf" );
		mFontBD = new Font(mainActivity.getFontManager(), mFontTextureBD, Typeface.create(
				Typeface.createFromAsset(mainActivity.getAssets(),"gfx/BD.ttf"), Typeface.BOLD), 40, true, Color.BLACK);
		
		mFont = new Font(mainActivity.getFontManager(), mFontTexture, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 40, true, Color.WHITE);
		
		SoundFactory.setAssetBasePath("gfx/");
		try {
			shootingSound = SoundFactory.createSoundFromAsset(
				mainActivity.getMEngine().getSoundManager(), mainActivity, "pew_pew_lei.wav");
			clickSound = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "click.mp3");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MusicFactory.setAssetBasePath("gfx/");

		try {
			backgroundMusic = MusicFactory
					.createMusicFromAsset(mainActivity.getMEngine().getMusicManager(), mainActivity,
							"background_music_aac.wav");
			backgroundMusic.setLooping(true);
			backgroundMusicMenu = MusicFactory
					.createMusicFromAsset(mainActivity.getMEngine().getMusicManager(), mainActivity,
							"menuMusik.mp3");
			backgroundMusicMenu.setLooping(true);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
