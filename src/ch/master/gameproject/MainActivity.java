package ch.master.gameproject;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;

import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.scenes.GameScene;
import ch.master.gameproject.scenes.InitMenuScene;
import ch.master.gameproject.scenes.LevelMenuScene;
import ch.master.gameproject.scenes.OptMenuScene;
import ch.master.gameproject.scenes.StyleMenuScene;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity implements
		IOnSceneTouchListener {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	private Scene splashScene ;
	private Sprite splash;
	public Camera mCamera;

	private int cameraWidth;
	private int cameraHeight;

	// A reference to the current scene
	public GameScene mCurrentScene;
	public InitMenuScene initScene;
	public OptMenuScene optMenuScene;
	public StyleMenuScene worldMenuScene;
	public LevelMenuScene levelMenuScene;
	public static BaseActivity instance;
    
	
	// ************* Images texture ********************
	private BitmapTextureAtlas splashTextureAtlas;
	private ITextureRegion splashTextureRegion;
	
	// ************* Scenes modes ********************
	public enum SceneType
    {
			SPLASH,
			INITMENU,
			STARTGAME,
		    RESUMEGAME,
		    RESTARTGAME,
			LOADGAME,
			OPTIONS,
			WORLD_SELECTION,
			LEVEL_SELECTION,
			PAUSE,
			GAMEOVER,
			WIN
	}
		
	public SceneType currentScene = SceneType.SPLASH;	
	
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Display display = getWindowManager().getDefaultDisplay();
		cameraWidth = display.getWidth();
		cameraHeight = display.getHeight();
		instance = this;
		mCamera = new Camera(0, 0, cameraWidth, cameraHeight);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						cameraWidth, cameraHeight), mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)     throws Exception {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas =  new BitmapTextureAtlas(
				getTextureManager(), 1280, 720, TextureOptions.DEFAULT);
		splashTextureRegion =BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.splashTextureAtlas, this,
						"loadingBack.png", 0, 0);
		
		InitRessources.sheetBitmapTextureAtlass = new BitmapTextureAtlas(getTextureManager(),
				2048, 512);
		InitRessources.loadingTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(InitRessources.sheetBitmapTextureAtlass, this,
						"LoadingCircle.png",0,0  ,8, 1);
		
		splashTextureAtlas.load();
		InitRessources.sheetBitmapTextureAtlass.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
	 throws Exception {
		initSplashScene();
        pOnCreateSceneCallback.onCreateSceneFinished(this.splashScene);
	}

	
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

	
			switch (currentScene)
	    	{
	    		case SPLASH:
	    			break;
	    		case INITMENU:
	    			SoundManagerGame.startMusic(InitRessources.backgroundMusicMenu);
	    			mEngine.getScene().detachSelf();
	    			loadInitScenes();
    			    mEngine.setScene(initScene);    
    			    initScene.initMenu();
	    			break;
	    		case WORLD_SELECTION:
	    			mEngine.getScene().detachSelf();
	    			 loadWorldScenes();
    			    mEngine.setScene(worldMenuScene);    
    			    worldMenuScene.initMenu();
	    			break;
	    		case LEVEL_SELECTION:
	    			mEngine.getScene().detachSelf();
	    			loadLevelScenes();
   			        mEngine.setScene(levelMenuScene);    
   			        levelMenuScene.initMenu();
	    			break;
	    		case LOADGAME:
	    			    SoundManagerGame.pauseMusic(InitRessources.backgroundMusicMenu);
	    			    mEngine.getScene().detachSelf();
	    			    loadGameScenes();
	    			    mEngine.setScene(mCurrentScene);    
	    			    mCurrentScene.initGame();
	    			    currentScene = SceneType.STARTGAME;
	    			    
	    			break;
	    		case STARTGAME:
	    			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
	    				final float touchX = pSceneTouchEvent.getX();
	    				final float touchY = pSceneTouchEvent.getY();
	    				mCurrentScene.shootProjectil(touchX,touchY);
	    			}
	    			break;
	    		case OPTIONS:
	    			mEngine.getScene().detachSelf();
    			    loadOptScenes();
    			    mEngine.setScene(optMenuScene);    
    			    optMenuScene.initMenu();
    			break;
	    		case RESUMEGAME:
	    			 SoundManagerGame.pauseMusic(InitRessources.backgroundMusicMenu);
					mCurrentScene.unPauseGame();
				    if (!InitRessources.backgroundMusic.isPlaying())
						SoundManagerGame.startMusic(InitRessources.backgroundMusic);
				    currentScene = SceneType.STARTGAME;
				    getMEngine().start();
	    			break;
	    		case RESTARTGAME:
	    			 SoundManagerGame.pauseMusic(InitRessources.backgroundMusicMenu);
	    			 mEngine.stop();
	    	    	 mEngine.getScene().detachSelf();			
	    			 loadGameScenes();
	    			 mEngine.setScene(mCurrentScene);    
	    			 mCurrentScene.initGame();
	    			 currentScene = SceneType.STARTGAME;
	    			 getMEngine().start();
	    			break;
	    		case PAUSE:
	    				SoundManagerGame.pauseMusic(InitRessources.backgroundMusic);
	    				
	    				mCurrentScene.pauseGame();
	    							
	    			break;
	    		case GAMEOVER:
	    			SoundManagerGame.stopMusic(InitRessources.backgroundMusic);
	    			
	    			mCurrentScene.fail();
	    			
	    			
	    			break;
	    		case WIN:
	    			SoundManagerGame.stopMusic(InitRessources.backgroundMusic);
	    			
	    		    mCurrentScene.win();
	    		  
	    			break;
	    		default :
	    			break;
	    	}
		return true;
	}

	@Override
	public boolean onKeyUp(final int pKeyCode, final KeyEvent pEvent) {
		  if (pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_UP) {
			 if(currentScene == SceneType.STARTGAME ||  currentScene == SceneType.PAUSE){
				 if (mEngine.isRunning()) {
					 
					SoundManagerGame.startMusic(InitRessources.clickSound);
				    currentScene = SceneType.PAUSE;
					return onSceneTouchEvent(null,null);
				} 
				else {
					 SoundManagerGame.startMusic(InitRessources.clickSound);
					currentScene = SceneType.RESUMEGAME;
					return onSceneTouchEvent(null,null);
				}
			}			 
			return true;
		} else if (pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_UP) {
			switch (currentScene)
	    	{
	    		case SPLASH:
	    			break;
	    		case INITMENU:
	    			 SoundManagerGame.startMusic(InitRessources.clickSound);
	    			finihGame();
	    			break;
	    		case WORLD_SELECTION:
	    			 SoundManagerGame.startMusic(InitRessources.clickSound);
	    			currentScene = SceneType.INITMENU;
	    			return onSceneTouchEvent(null,null);
	    			
	    		case LEVEL_SELECTION:
	    			 SoundManagerGame.startMusic(InitRessources.clickSound);
	    			currentScene = SceneType.WORLD_SELECTION;
	    			return onSceneTouchEvent(null,null);
	    			
	    		/*case STARTGAME:
	    			if (mEngine.isRunning() && InitRessources.backgroundMusic.isPlaying()) {
	    				mCurrentScene.clearChildScene();
	    				mEngine.start();
	    				restart();
	    				retusrn true;
	    			}
	    			break;*/
	    		case OPTIONS:
	    			 SoundManagerGame.startMusic(InitRessources.clickSound);
	    			currentScene = SceneType.INITMENU;
	    			return onSceneTouchEvent(null,null);
	    		default :
	    		break;
	    	
	    	}
			return true;	
		}
		else if (pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			return false;
		}
		
		return true;
	}
	


	public Engine getMEngine() {
		return mEngine;
	}

	@Override
	protected void onResume() {
		if (currentScene != SceneType.STARTGAME && currentScene != SceneType.SPLASH) {
			SoundManagerGame.resumeMusic(InitRessources.backgroundMusicMenu);
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		if(currentScene != SceneType.SPLASH)	
			SoundManagerGame.pauseMusic(InitRessources.backgroundMusicMenu);
		if (currentScene == SceneType.STARTGAME) {
			currentScene = SceneType.PAUSE;
			onSceneTouchEvent(null,null);
		}
		super.onPause();
	}
	
	
	
	
	private void initSplashScene()
	{
	    splashScene = new Scene();
	    splash = new Sprite(0, 0, splashTextureRegion, mEngine.getVertexBufferObjectManager())
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera)
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    };
	    int x =   (int)((cameraWidth - 102) * 0.5);
		int y = (int)((cameraHeight - 102) * 0.5);
	    AnimatedSprite s= new AnimatedSprite(x, y,InitRessources.loadingTextureRegion.deepCopy(),getVertexBufferObjectManager());
	    s.getTiledTextureRegion();
	    s.animate(120);
	    splash.attachChild(s);
	    splash.setPosition((cameraWidth - splash.getWidth()) * 0.5f, (cameraHeight - splash.getHeight()) * 0.5f);
	    splashScene.attachChild(splash);
	}

	
	@Override
	public void onPopulateScene(Scene pScene,OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
	
		mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	mEngine.unregisterUpdateHandler(pTimerHandler);
            	loadRessouces();    
                splashScene.detachSelf();  
                loadInitScenes();
                mEngine.setScene(initScene);
                initScene.initMenu();
                SoundManagerGame.startMusic(InitRessources.backgroundMusicMenu);
                currentScene = SceneType.INITMENU;
                getMEngine().start();
            }
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();	
	}
	
	private void loadRessouces()
	{
		InitRessources.initRessources(this);
		SoundManagerGame.loadHighScore();
		mEngine.getTextureManager().loadTexture(InitRessources.mFontTexture);
		mEngine.getFontManager().loadFont(InitRessources.mFont);
		mEngine.getTextureManager().loadTexture(InitRessources.mFontTextureBD);
		mEngine.getFontManager().loadFont(InitRessources.mFontBD);
		mEngine.getTextureManager().loadTexture(InitRessources.mBitmapTextureAtlas);
		mEngine.getTextureManager().loadTexture(InitRessources.sheetBitmapTextureAtlas);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBack);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtExit);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtPlay);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtTools);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtSoundOff);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtSoundOn);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtBack);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtNext);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtWW);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtLvl1);
	    mEngine.getTextureManager().loadTexture(InitRessources.mBtQuit);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtResume);
		mEngine.getTextureManager().loadTexture(InitRessources.mBtRestart);
		mEngine.getTextureManager().loadTexture(InitRessources.mAutoParallaxBackgroundTexture);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBackOpt);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBackWorld);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBackLevel);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBackPause);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBackWin);
		mEngine.getTextureManager().loadTexture(InitRessources.splashTextureAtlasBackLose);
	}
	
	private void loadInitScenes()
	{
	    initScene  = new InitMenuScene(this);	
		initScene.loadInitScene();
	}
	
	private void loadWorldScenes()
	{
		worldMenuScene  = new StyleMenuScene(this);	
		worldMenuScene.loadInitScene();
	}
	private void loadLevelScenes()
	{
		levelMenuScene  = new LevelMenuScene(this);	
		levelMenuScene.loadInitScene();
	}
	private void loadOptScenes()
	{
		optMenuScene  = new OptMenuScene(this);	
		optMenuScene.loadInitScene();
	}
	
	private void loadGameScenes()
	{
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene  = new GameScene(this);	
		mCurrentScene.loadInitScene();
    
	}

	public void finihGame() {
		mEngine.stop();
		mEngine.clearDrawHandlers();
		System.exit(0);
	}

}
