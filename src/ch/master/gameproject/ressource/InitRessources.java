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
	public static BitmapTextureAtlas mBtLvl2Over;
	public static TextureRegion btLvl2Over;
	public static BitmapTextureAtlas mBtLvl1Over;
	public static TextureRegion btLvl1Over;
	public static BitmapTextureAtlas mBtLvl2;
	public static TextureRegion btLvl2;
	public static BitmapTextureAtlas mBtMuni;
	public static TextureRegion btMuni;
	public static BitmapTextureAtlas mBtResume;
	public static TextureRegion btResume;
	public static BitmapTextureAtlas mBtRestart;
	public static TextureRegion btRestart;
	public static BitmapTextureAtlas mBtQuit;
	public static TextureRegion btQuit;
	public static BitmapTextureAtlas mBtNext;
	public static TextureRegion btNext;
	public static BitmapTextureAtlas mBtPause;
	public static TextureRegion btPause;
	public static BitmapTextureAtlas mBtLife;
	public static TextureRegion btLife;
	public static BitmapTextureAtlas mBtDown;
	public static TextureRegion btDown;
	public static BitmapTextureAtlas mBtUp;
	public static TextureRegion btUp;
	public static TextureRegion mWinTextureRegion;
	public static TextureRegion mFailTextureRegion;
	public static TextureRegion initTextureRegion;
	public static TextureRegion initTextureRegionBackOpt;
	public static TextureRegion initTextureRegionBackWorld;
	public static TextureRegion initTextureRegionBackLevelUn;
	public static TextureRegion initTextureRegionBackFGW;
	public static BitmapTextureAtlas sheetBitmapTextureAtlassLoad;
	public static BitmapTextureAtlas mFontTexture;
	public static BitmapTextureAtlas mFontTextureBD;
	public static BitmapTextureAtlas sheetBitmapTextureAtlas;
	public static BitmapTextureAtlas sheetBitmapTextureAtlass;
	public static BitmapTextureAtlas sheetBitmapTextureAtlasHouse;
	public static BitmapTextureAtlas sheetBitmapTextureAtlasTarget;
	public static BitmapTextureAtlas sheetBitmapTextureAtlasBall;
	public static BitmapTextureAtlas splashTextureAtlasBack;
	public static BitmapTextureAtlas splashTextureAtlasBackOpt;
	public static BitmapTextureAtlas splashTextureAtlasBackWorld;
	public static BitmapTextureAtlas splashTextureAtlasBackLevel;
	public static BitmapTextureAtlas splashTextureAtlasBackPause;
	public static BitmapTextureAtlas splashTextureAtlasBackLose;
	public static BitmapTextureAtlas splashTextureAtlasBackWin;
	public static BitmapTextureAtlas  sheetBitmapTextureAtlasBox;
	public static BitmapTextureAtlas  sheetBitmapTextureAtlasTornado;
	public static BitmapTextureAtlas  sheetBitmapTextureAtlasShoot;
	public static BitmapTextureAtlas  sheetBitmapTextureAtlasPUp;
	public static BitmapTextureAtlas  sheetBitmapTextureAtlasPDown;
	public static BitmapTextureAtlas sheetBitmapTextureAtlasPJump;
	public static BitmapTextureAtlas mBitmapTextureAtlas;
	public static TextureRegion mPausedTextureRegion;
	public static TiledTextureRegion mTargetTextureRegion;
	public static TiledTextureRegion mHouseTextureRegion;
	public static TiledTextureRegion loadingTextureRegion;
	public static TiledTextureRegion mProjectileTextureRegion;
	public static TiledTextureRegion mPlayerTextureRegion;
	public static TiledTextureRegion mBoxTextureRegion;
	public static TiledTextureRegion mBallTextureRegion;
	public static TiledTextureRegion mTornadoTextureRegion;
	public static TiledTextureRegion mShootTextureRegion;
	public static TiledTextureRegion mPUpTextureRegion;
	public static TiledTextureRegion mPDownTextureRegion;
	public static TiledTextureRegion mPJumpTextureRegion;
	public static TextureRegion mParallaxLayer;
	public static TextureRegion mParallaxLayerDeux;
	public static TextureRegion mParallaxLayerTrois;
	public static TextureRegion mParallaxLayerDeuxDeux;
	public static TextureRegion mParallaxLayerQuatre;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTextureDeux;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTextureTrois;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTextureDeuxDeux;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTextureQuatre;
	public static BitmapTextureAtlas splashTextureAtlasBackFGW;
	public static Sound shootingSound;
	public static Sound clickSound;
	public static Sound winSound;
	public static Sound loseSound;
	public static Sound pauseSound;
	public static Sound reloadGun;
	public static Sound jump;
	public static Sound outchEn;
	public static Sound outch;
	public static Music backgroundMusic;
	public static Music backgroundMusicMenu;
	public static  Typeface fontDB;
	
	
	private static final String SOUND_DB = "EN_SOUND";
	private static final String LVL_DB = "EN_LVL";
	public static SharedPreferences mScoreDb ;
	public static SharedPreferences.Editor mScoreDbEditor;
		
	public static SharedPreferences mLvlDb ;
	public static SharedPreferences.Editor mLvlDbEditor;
	
	public static void initRessources(MainActivity mainActivity){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		mScoreDb = mainActivity.getSharedPreferences(SOUND_DB, Context.MODE_PRIVATE);
		mScoreDbEditor = mScoreDb.edit();
		
		mLvlDb = mainActivity.getSharedPreferences(LVL_DB, Context.MODE_PRIVATE);
		mLvlDbEditor = mLvlDb.edit();
		
		
		mBitmapTextureAtlas = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		sheetBitmapTextureAtlas = new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 2048);
		sheetBitmapTextureAtlasHouse = new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 512);
		sheetBitmapTextureAtlasBox= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 512);
		sheetBitmapTextureAtlasTarget = new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 512);
		sheetBitmapTextureAtlasBall = new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 512);
		splashTextureAtlasBackPause= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		splashTextureAtlasBackWin= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		splashTextureAtlasBackFGW= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		splashTextureAtlasBackLose= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 1024);
		sheetBitmapTextureAtlasTornado= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				2048, 2048);
		mBtPlay = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		sheetBitmapTextureAtlasShoot= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 1024);
		sheetBitmapTextureAtlasPUp= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 1024);
		sheetBitmapTextureAtlasPDown= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 1024);
		sheetBitmapTextureAtlasPJump= new BitmapTextureAtlas(mainActivity.getTextureManager(),
				1024, 1024);
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
		mBtDown  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtUp = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtMuni= new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtSoundOff  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtLvl1  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtLvl2Over  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtLvl1Over  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtLvl2  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtPause  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBtLife  = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		splashTextureAtlasBackOpt =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		splashTextureAtlasBackLevel =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		splashTextureAtlasBackWorld =  new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		mAutoParallaxBackgroundTextureDeux = new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 2048, 720, TextureOptions.DEFAULT);
		mAutoParallaxBackgroundTextureTrois = new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1882, 720, TextureOptions.DEFAULT);
		mAutoParallaxBackgroundTextureDeuxDeux = new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1882, 720, TextureOptions.DEFAULT);
		mAutoParallaxBackgroundTextureQuatre = new BitmapTextureAtlas(
				mainActivity.getTextureManager(), 1882, 720, TextureOptions.DEFAULT);
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
						"backgroundwest.png", 0, 0);
		
		mParallaxLayerDeux = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTextureDeux, mainActivity,
						"backgroundwest2.png", 0, 0);
		mParallaxLayerTrois = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTextureTrois, mainActivity,
						"backgroundwest3.png", 0, 0);
		mParallaxLayerDeuxDeux = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTextureDeuxDeux, mainActivity,
						"backgroundMountainBig.png", 0, 0);
		mParallaxLayerQuatre = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTextureQuatre, mainActivity,
						"backgroundHouse.png", 0, 0);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlas, mainActivity,
						"Player.png", 87, 150, 4, 6);
		mBallTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasBall, mainActivity,
						"ball.png", 0, 0, 6, 1);
		mTargetTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasTarget, mainActivity,
						"zombieBig.png", 0, 0, 3, 1);
		mHouseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasHouse, mainActivity,
						"houseSprite.png", 0, 0, 1, 1);
		mBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasBox, mainActivity,
						"muniBox.png", 0, 0, 1, 1);
		mProjectileTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBitmapTextureAtlas, mainActivity,
						"Projectile.png", 0, 0, 3, 1);
		mTornadoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasTornado, mainActivity,
						"tornado.png", 0, 0, 4, 3);
		mShootTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasShoot, mainActivity,
						"shoot.png", 0, 0, 7, 1);
		mPUpTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasPUp, mainActivity,
						"getUp.png", 0, 0, 4, 1);
		mPJumpTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasPJump, mainActivity,
						"jump.png", 0, 0, 4, 1);
		mPDownTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlasPDown, mainActivity,
						"getDown.png", 0, 0, 4, 1);
		
		mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackPause, mainActivity, "backPause.png",
						0, 64);

		mWinTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackWin, mainActivity, "back_win.png", 0,
						128);
		initTextureRegionBackFGW = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackFGW, mainActivity, "frogroundWEst.png", 0,
						128);
		mFailTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlasBackLose, mainActivity, "back_lose.png", 0,
						256);
		btLife = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtLife, mainActivity, "life.png", 0,
						256);
		btPause = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtPause, mainActivity, "bt_pause.png", 0,
						256);
		btPlay = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtPlay, mainActivity, "bt_play.png", 0,
						256);
		btMuni = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtMuni, mainActivity, "muni.png", 0,
						256);
		btExit = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtExit, mainActivity, "bt_exit.png", 0,
						256);
		btTools = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtTools, mainActivity, "bt_tools.png", 0,
						256);
		btUp = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtUp, mainActivity, "bt_up.png", 0,
						256);
		btDown = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtDown, mainActivity, "bt_down.png", 0,
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
		btLvl1Over = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtLvl1Over, mainActivity, "bt_lvl1_over.png", 0,
						256);
		btLvl2 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtLvl2, mainActivity, "bt_lvl2.png", 0,
						256);
		btLvl2Over = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtLvl2Over, mainActivity, "bt_lvl2_over.png", 0,
						256);
		btNext = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBtNext, mainActivity, "bt_next.png", 0,
						256);
		mFontTexture = new BitmapTextureAtlas(mainActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		mFontTextureBD = new BitmapTextureAtlas(mainActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    FontFactory.setAssetBasePath("gfx/");
	//fontDB = Typeface.crcreateFromAsset(mainActivity.getMEngine().getFontManager(),"BD.ttf" );
		mFontBD = new Font(mainActivity.getFontManager(), mFontTextureBD, Typeface.create(
				Typeface.createFromAsset(mainActivity.getAssets(),"gfx/BD.ttf"), Typeface.BOLD), 40, true, Color.BLACK);
		
		mFont= new Font(mainActivity.getFontManager(), mFontTexture, Typeface.create(
				Typeface.createFromAsset(mainActivity.getAssets(),"gfx/BD.ttf"), Typeface.BOLD),65f, true, Color.BLACK);
		
		SoundFactory.setAssetBasePath("gfx/");
		try {
			shootingSound = SoundFactory.createSoundFromAsset(
				mainActivity.getMEngine().getSoundManager(), mainActivity, "shootgun.mp3");
			clickSound = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "click.mp3");
			winSound = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "win.mp3");
			loseSound = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "lose.mp3");
			pauseSound = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "pause.mp3");
			outchEn = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "outch.mp3");
			outch = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "boyCry.mp3");
			reloadGun = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "reloadGun.mp3");
			jump = SoundFactory.createSoundFromAsset(
					mainActivity.getMEngine().getSoundManager(), mainActivity, "jump.mp3");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MusicFactory.setAssetBasePath("gfx/");

		try {
			backgroundMusic = MusicFactory
					.createMusicFromAsset(mainActivity.getMEngine().getMusicManager(), mainActivity,
							"Western.mp3");
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
