package ch.master.gameproject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import ch.master.gameproject.scenes.FailScenes;
import ch.master.gameproject.scenes.PauseScenes;
import ch.master.gameproject.scenes.WinScenes;
import ch.master.gameproject.sprite.PlayerSprite;
import ch.master.gameproject.sprite.TargetSprite;
import ch.master.gameproject.sprite.ProjectileSprite;

import android.os.Bundle;
import android.app.Activity;

import android.graphics.Typeface;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity implements
		IOnSceneTouchListener {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	private Sound shootingSound;
	public Music backgroundMusic;
	

	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	private TextureRegion mParallaxLayer;

	public TargetSprite targetSprite;
	public ProjectileSprite projectileSprite;
	public PlayerSprite playerSprite;
	public Font mFont;
	public Camera mCamera;

	public TiledTextureRegion mTargetTextureRegion;
	public TextureRegion mProjectileTextureRegion;
	public TiledTextureRegion mPlayerTextureRegion;

	private BitmapTextureAtlas mBitmapTextureAtlas;

	public AnimatedSprite player;

	public TextureRegion mPausedTextureRegion;

	private PauseScenes pauseScene;
	private WinScenes winScene;
	private FailScenes failScene;

	private int cameraWidth;
	private int cameraHeight;

	private BitmapTextureAtlas sheetBitmapTextureAtlas;

	private CameraScene mResultScene;

	private boolean runningFlag = false;
	private boolean pauseFlag = false;

	public Text score;
	private BitmapTextureAtlas mFontTexture;

	public int hitCount;
	private final int maxScore = 10;

	public TextureRegion mWinTextureRegion;
	public TextureRegion mFailTextureRegion;

	// A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Display display = getWindowManager().getDefaultDisplay();
		cameraWidth = display.getWidth();
		cameraHeight = display.getHeight();
		instance = this;
		mCamera = new Camera(0, 0, cameraWidth, cameraHeight);

		targetSprite = new TargetSprite(this);
		projectileSprite = new ProjectileSprite(this, targetSprite);
		playerSprite = new PlayerSprite(this);

		winScene = new WinScenes(this);
		failScene = new FailScenes(this);
		pauseScene = new PauseScenes(this);

		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						cameraWidth, cameraHeight), mCamera);

		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);

		return engineOptions;

	}

	@Override
	public void onCreateResources() {
		mBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		sheetBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
				2048, 512);

		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(
				getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		mParallaxLayer = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this,
						"background.png", 0, 0);

		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlas, this,
						"hero.png", 0, 212, 11, 1);
	
		mTargetTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(sheetBitmapTextureAtlas, this,
						"zombieBig.png", 0, 0, 3, 1);

		mProjectileTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this, "bullet.png",
						64, 0);

		mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this, "paused.png",
						0, 64);

		mWinTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this, "win.png", 0,
						128);
		mFailTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this, "fail.png", 0,
						256);

		mFontTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont = new Font(getFontManager(), mFontTexture, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 40, true, Color.WHITE);
		mEngine.getTextureManager().loadTexture(mFontTexture);
		mEngine.getFontManager().loadFont(mFont);

		mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);
		mEngine.getTextureManager().loadTexture(sheetBitmapTextureAtlas);
		mEngine.getTextureManager().loadTexture(mAutoParallaxBackgroundTexture);
		SoundFactory.setAssetBasePath("gfx/");
		try {
			shootingSound = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), this, "pew_pew_lei.wav");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MusicFactory.setAssetBasePath("gfx/");

		try {
			backgroundMusic = MusicFactory
					.createMusicFromAsset(mEngine.getMusicManager(), this,
							"background_music_aac.wav");
			backgroundMusic.setLooping(true);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected Scene onCreateScene() {

		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new Scene();
		// background preperations
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(
				0, 0, 0, 4);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-25.0f,
				new Sprite(0, mCamera.getHeight()
						- this.mParallaxLayer.getHeight(), this.mParallaxLayer,
						getVertexBufferObjectManager())));
		mCurrentScene.setBackground(autoParallaxBackground);

		player = playerSprite.createPlayer();
		mCurrentScene.registerTouchArea(player);

		targetSprite.createSpriteSpawnTimeHandler();

		mCurrentScene.registerUpdateHandler(targetSprite.detect);

		mCurrentScene.registerUpdateHandler(projectileSprite.detect);

		mCurrentScene.setOnSceneTouchListener(this);
		pauseScene.loadPauseScene();
		winScene.loadWinScene();
		failScene.loadFailScene();

		mResultScene = new CameraScene(mCamera);
		mResultScene.attachChild(winScene.getWinSprite());
		mResultScene.attachChild(failScene.getFailSprite());
		mResultScene.setBackgroundEnabled(false);

		score = new Text(0, 0, mFont, String.valueOf(maxScore),
				getVertexBufferObjectManager());
		score.setPosition(mCamera.getWidth() - score.getWidth() - 5, 5);

		hitCount = 0;
		score.setText(String.valueOf(hitCount));
		mCurrentScene.attachChild(score);
		backgroundMusic.play();
		backgroundMusic.setVolume(3);

		return mCurrentScene;
	}

	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			final float touchX = pSceneTouchEvent.getX();
			final float touchY = pSceneTouchEvent.getY();

			projectileSprite.shootProjectile(touchX, touchY);
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_MENU
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (mEngine.isRunning()) {
				pauseMusic();
				pauseFlag = true;
				pauseScene.pauseGame();
				Toast.makeText(this, "Menu button to resume",
						Toast.LENGTH_SHORT).show();
			} else {
				pauseScene.unPauseGame();
				pauseFlag = false;

				if (!backgroundMusic.isPlaying())
					resumeMusic();
			}
			return true;
		} else if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {

			if (!mEngine.isRunning() && backgroundMusic.isPlaying()) {
				mCurrentScene.clearChildScene();
				mEngine.start();
				restart();
				return true;
			}
			return super.onKeyDown(pKeyCode, pEvent);
		}
		return super.onKeyDown(pKeyCode, pEvent);
	}

	public void soundShooting() {
		shootingSound.play();
	}

	public Engine getMEngine() {
		return mEngine;
	}

	public void restart() {

		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				resumeMusic();
				mCurrentScene.detachChildren();
				mCurrentScene.attachChild(player);
				mCurrentScene.attachChild(score);
			}
		});
		pauseMusic();
		hitCount = 0;
		runningFlag = false;
		score.setText(String.valueOf(hitCount));
		targetSprite.clear();
		projectileSprite.clear();

	}

	public void fail() {
		if (mEngine.isRunning()) {
			winScene.getWinSprite().setVisible(false);
			failScene.getFailSprite().setVisible(true);
			mCurrentScene.setChildScene(mResultScene, false, true, true);
			mEngine.stop();
			resumeMusic();
		}
	}

	public void win() {
		if (mEngine.isRunning()) {
			failScene.getFailSprite().setVisible(false);
			winScene.getWinSprite().setVisible(true);
			mCurrentScene.setChildScene(mResultScene, false, true, true);
			mEngine.stop();
			resumeMusic();
		}
	}

	public void pauseMusic() {
		if (backgroundMusic.isPlaying())
			backgroundMusic.pause();
	}

	public void resumeMusic() {
		if (!backgroundMusic.isPlaying())
			backgroundMusic.resume();
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
		if (runningFlag) {
			if (pauseFlag) {
				pauseFlag = false;
				Toast.makeText(this, "Menu button to resume",
						Toast.LENGTH_SHORT).show();
			} else {
				resumeMusic();
				// mEngine.stop();
			}
		} else {
			runningFlag = true;
		}
	}

	@Override
	protected void onPause() {
		if (runningFlag) {
			pauseMusic();
			if (mEngine.isRunning()) {
				pauseScene.pauseGame();
				pauseFlag = true;
			}
			pauseFlag = true;
		}
		super.onPause();
	}

}
