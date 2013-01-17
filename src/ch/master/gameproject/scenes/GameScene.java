package ch.master.gameproject.scenes;

import java.util.Iterator;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.graphics.Typeface;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.MainActivity.SceneType;
import ch.master.gameproject.model.LevelManagerGame;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.sprite.BallSprite;
import ch.master.gameproject.sprite.HouseSprite;
import ch.master.gameproject.sprite.MuniSprite;
import ch.master.gameproject.sprite.PlayerSprite;
import ch.master.gameproject.sprite.ProjectileSprite;
import ch.master.gameproject.sprite.TargetSprite;
import ch.master.gameproject.sprite.TornadoSprite;

public class GameScene extends Scene implements IOnAreaTouchListener{

	private final int maxScore = 10;
	
	private AutoParallaxBackground autoParallaxBackground;
	public AnimatedSprite player;

	private MainActivity mainActivity;
	private PauseScenes pauseScene;
	private WinScenes winScene;
	boolean btTouch = false;
	private FailScenes failScene;
	public TargetSprite targetSprite;
	public HouseSprite houseSprite;
	public ProjectileSprite projectileSprite;
	public PlayerSprite playerSprite;
	public TornadoSprite tornadoSprite;
	public MuniSprite boxSprite;
	private CameraScene mResultScene;
	public Sprite life1Sprite;
	public Sprite life2Sprite;
	public Sprite life3Sprite;
	public Sprite pauseSprite;
	public Sprite muniSprite;
	public Sprite upSprite;
	public Sprite downSprite;
	public int score;
	public int hitCount;
	public int life ;
	private Text muni;
	private int muniNbr;
	private boolean projSend;
	private BallSprite ballSprite; 
	public ParallaxEntity pRevmoe ;
	public enum PlayerType
    {
			NORMAL,
			JUMP,
			DOWN
		 
	};
		
	public PlayerType currentplayer = PlayerType.NORMAL;	
	
	public GameScene(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		houseSprite = new HouseSprite(mainActivity,this);
		tornadoSprite = new TornadoSprite(mainActivity,houseSprite);
		boxSprite= new MuniSprite(mainActivity,houseSprite);
		targetSprite = new TargetSprite(mainActivity,houseSprite);
		ballSprite= new BallSprite(mainActivity,houseSprite,tornadoSprite);
		playerSprite = new PlayerSprite(mainActivity,this);
		projectileSprite = new ProjectileSprite(mainActivity, targetSprite,this,houseSprite,boxSprite,ballSprite,tornadoSprite);
		
		winScene = new WinScenes(mainActivity);
		failScene = new FailScenes(mainActivity);
		pauseScene = new PauseScenes(mainActivity);
		projSend = false;
	}
	
	public void initGame() {

		clearChildScene();
	}
	
	
	public void loadInitScene() {
		life = 3;
		score = 4;
		hitCount = 0;
		muniNbr = 8;	
		  registerUpdateHandler(new FPSLogger());
		autoParallaxBackground = new AutoParallaxBackground(
				0, 0, 0, 4);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-0f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.mParallaxLayer.getHeight(),InitRessources.mParallaxLayer,
						mainActivity.getVertexBufferObjectManager())));
	
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-15f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.mParallaxLayerDeux.getHeight(),InitRessources.mParallaxLayerDeux,
						mainActivity.getVertexBufferObjectManager())));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.mParallaxLayerDeuxDeux.getHeight(),InitRessources.mParallaxLayerDeuxDeux,
						mainActivity.getVertexBufferObjectManager())));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.mParallaxLayerTrois.getHeight(),InitRessources.mParallaxLayerTrois,
						mainActivity.getVertexBufferObjectManager())));
		
		 pRevmoe =new ParallaxEntity(-25f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.mParallaxLayerQuatre.getHeight(),InitRessources.mParallaxLayerQuatre,
						mainActivity.getVertexBufferObjectManager()));
		autoParallaxBackground.attachParallaxEntity(pRevmoe);
		autoParallaxBackground.attachParallaxEntity( new ParallaxEntity(-25f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.initTextureRegionBackFGW.getHeight(),InitRessources.initTextureRegionBackFGW,
						mainActivity.getVertexBufferObjectManager())));
	     
 
		final int xbtt = (int) (mainActivity.mCamera.getWidth()  /2);
	    final int ybtt = (int) (mainActivity.mCamera.getHeight() -(mainActivity.mCamera.getHeight()  - 30));
		InitRessources.mFont = new Font(mainActivity.getFontManager(),InitRessources.mFontTexture, Typeface.create(
				Typeface.createFromAsset(mainActivity.getAssets(),"gfx/BD.ttf"), Typeface.BOLD),65f, true, Color.BLACK);
		mainActivity.getMEngine().getTextureManager().loadTexture(InitRessources.mFontTexture);
		mainActivity.getMEngine().getFontManager().loadFont(InitRessources.mFont);
		muni = new Text(xbtt*2-200, ybtt+30, InitRessources.mFont, String.valueOf(muniNbr),mainActivity.getVertexBufferObjectManager());
		if(LevelManagerGame.getLevelSelect()==2){
			score = 6;
			ballSprite.createSpriteSpawnTimeHandler();
			tornadoSprite.createSpriteSpawnTimeHandler();
		}
		setBackground(autoParallaxBackground);
		player = playerSprite.createPlayer();
		player.animate(new long[] {200,200,200,200},0,3, true);	
		player.setCurrentTileIndex(0);
	

		boxSprite.createSpriteSpawnTimeHandler();
		targetSprite.createSpriteSpawnTimeHandler();
		
		registerUpdateHandler(targetSprite.detect);
		registerUpdateHandler(projectileSprite.detect);
		registerUpdateHandler(houseSprite.detect);
		setOnSceneTouchListener(mainActivity);
		pauseScene.loadPauseScene();
		winScene.loadWinScene();
		
		failScene.loadFailScene();
		life1Sprite = new Sprite(xbtt*2-500, ybtt+17,91,85,
				InitRessources.btLife,
				mainActivity.getVertexBufferObjectManager());
	
		life2Sprite = new Sprite(xbtt*2-400, ybtt+17,91,85,
				InitRessources.btLife,
				mainActivity.getVertexBufferObjectManager());
		life3Sprite = new Sprite(xbtt*2-300, ybtt+17,91,85,
				InitRessources.btLife,
				mainActivity.getVertexBufferObjectManager());
		pauseSprite = new Sprite(xbtt, ybtt,141,137,
				InitRessources.btPause,
				mainActivity.getVertexBufferObjectManager());
		muniSprite= new Sprite(xbtt*2-90, ybtt+7,40,105,
				InitRessources.btMuni,
				mainActivity.getVertexBufferObjectManager());
		
		upSprite = new Sprite(xbtt*2-180, ybtt+140,141,137,
				InitRessources.btUp,
				mainActivity.getVertexBufferObjectManager());
		downSprite = new Sprite(xbtt*2-180, ybtt+280,141,137,
				InitRessources.btDown,
				mainActivity.getVertexBufferObjectManager());
		upSprite.setAlpha(0.5f);
		downSprite.setAlpha(0.5f);
		attachChild(life1Sprite);
		attachChild(life2Sprite);
		attachChild(life3Sprite);
		//attachChild(pauseSprite);
		attachChild(upSprite);
		attachChild(downSprite);
		attachChild(muniSprite);
		attachChild(muni);
		registerTouchArea(upSprite);
		registerTouchArea(downSprite);
		SoundManagerGame.startMusic(InitRessources.backgroundMusic);
		setTouchAreaBindingOnActionDownEnabled(true);
		setOnAreaTouchListener(this);		
		DelayModifier dMod = new DelayModifier(5f);
        registerEntityModifier(dMod);
	    dMod.addModifierListener(new IModifierListener<IEntity>() {
		        @Override
		        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		        }
		     
		        @Override
		        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
		        	autoParallaxBackground.detachParallaxEntity(pRevmoe);
		        	setBackground(autoParallaxBackground);
		        }
		    });
	}
	

	/**** FUNCTION ON THE GAME ********/
	
	public void shootProjectil(float touchX,float touchY){
		if(!btTouch && muniNbr > 0 && !projSend && currentplayer == PlayerType.NORMAL && player.isAnimationRunning()){
			muniNbr--;
			projSend=true;
			muni.setText(String.valueOf(muniNbr));
			if(muniNbr==0){
				muni.detachSelf();
				InitRessources.mFont = new Font(mainActivity.getFontManager(),InitRessources.mFontTexture, Typeface.create(
						Typeface.createFromAsset(mainActivity.getAssets(),"gfx/BD.ttf"), Typeface.BOLD),65f, true, Color.RED);
				mainActivity.getMEngine().getTextureManager().loadTexture(InitRessources.mFontTexture);
				mainActivity.getMEngine().getFontManager().loadFont(InitRessources.mFont);
				muni = new Text((mainActivity.mCamera.getWidth()  /2)*2-165, (mainActivity.mCamera.getHeight() -(mainActivity.mCamera.getHeight()  - 30))+30, InitRessources.mFont, String.valueOf(muniNbr),
						mainActivity.getVertexBufferObjectManager());
				
				attachChild(muni);
			}
			projectileSprite.shootProjectile(touchX, touchY);
		}
	}
	

	public void chargeMuni(){
		SoundManagerGame.startMusic(InitRessources.reloadGun);
		muniNbr+=5;
		muni.detachSelf();
		InitRessources.mFont = new Font(mainActivity.getFontManager(),InitRessources.mFontTexture, Typeface.create(
				Typeface.createFromAsset(mainActivity.getAssets(),"gfx/BD.ttf"), Typeface.BOLD),65f, true, Color.BLACK);
		mainActivity.getMEngine().getTextureManager().loadTexture(InitRessources.mFontTexture);
		mainActivity.getMEngine().getFontManager().loadFont(InitRessources.mFont);
		muni = new Text((mainActivity.mCamera.getWidth()  /2)*2-165, (mainActivity.mCamera.getHeight() -(mainActivity.mCamera.getHeight()  - 30))+30, InitRessources.mFont, String.valueOf(muniNbr),
				mainActivity.getVertexBufferObjectManager());
		attachChild(muni);
	}
	public void pauseGame(){
		pauseScene.pauseGame();
	}
	public void unPauseGame(){
		pauseScene.unPauseGame();
	
	}

	public void fail() {
		failScene.failGame();
	}

	public void win() {
		winScene.winGame();
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pTouchArea.equals(pauseSprite)&& pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
		  	// SoundManagerGame.startMusic(InitRessources.clickSound);
			// mainActivity.currentScene = SceneType.PAUSE;
			// return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else{
			if (pTouchArea.equals(upSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				btTouch=false;
				upSprite.detachSelf();
			   
				upSprite.setAlpha(0.5f);
				attachChild(upSprite);
				
			}
			else if(pTouchArea.equals(upSprite) && currentplayer== PlayerType.NORMAL){
				 mainActivity.runOnUpdateThread(new Runnable() {
			            @Override                
			            public void run() {
			            	player.detachSelf();
			            	player.stopAnimation();
			            	player.animate(new long[] {100,100,100,100},20,23, false);	
			            	attachChild(player);
			            
			            	
			            }
			           });
				btTouch=true;
				currentplayer= PlayerType.JUMP;
				upSprite.detachSelf();
				upSprite.setAlpha(1f);
				attachChild(upSprite);
				DelayModifier dMod2 = new DelayModifier(0.3f);
				player.registerEntityModifier(dMod2);
				
			    dMod2.addModifierListener(new IModifierListener<IEntity>() {
				        @Override
				        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				        }
				     
				        @Override
				        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				        	playerSprite.jump(); 
				         }
				    });
				
			}
			if (pTouchArea.equals(downSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				
				 mainActivity.runOnUpdateThread(new Runnable() {
			            @Override                
			            public void run() {
			            	player.detachSelf();
			            	player.stopAnimation();
			            	player.animate(new long[] {100,100,100,100},16,19, false);	
			            	attachChild(player);
			            }
			    });
				 
					DelayModifier dMod2 = new DelayModifier(0.6f);
					player.registerEntityModifier(dMod2);
					
				    dMod2.addModifierListener(new IModifierListener<IEntity>() {
					        @Override
					        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					        }
					     
					        @Override
					        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						   		 mainActivity.runOnUpdateThread(new Runnable() {
							            @Override                
							            public void run() {
							            	player.detachSelf();
							            	player.stopAnimation();
							            	player.animate(new long[] {200,200,200,200},0,3, true);	
							            	attachChild(player);
							            }
							    });
					         }
					    });
				currentplayer = PlayerType.NORMAL ;
				btTouch=false;
			
				downSprite.detachSelf();
				autoParallaxBackground.setParallaxChangePerSecond(4);
				downSprite.setAlpha(0.5f);
				attachChild(downSprite);
				Iterator<AnimatedSprite> houses = houseSprite.getHouseLL()
						.iterator();
				AnimatedSprite _house;
				while (houses.hasNext()) {
					_house = houses.next();
					float newDuration = (20/mainActivity.mCamera.getWidth())*_house.getX();
					MoveXModifier mod = new MoveXModifier(newDuration, _house.getX(),
							-_house.getWidth());
					_house.registerEntityModifier(mod.deepCopy());
				}
				Iterator<AnimatedSprite> munis = boxSprite.getMuniLL().iterator();
				AnimatedSprite _muni;
				while (munis.hasNext()) {
					_muni = munis.next();
					float newDuration = (14/mainActivity.mCamera.getWidth())*_muni.getX();
					MoveXModifier mod = new MoveXModifier(newDuration, _muni.getX(),
							-_muni.getWidth());
					_muni.registerEntityModifier(mod.deepCopy());
				}
			}
			
			else if(pTouchArea.equals(downSprite) && currentplayer != PlayerType.DOWN){
				 mainActivity.runOnUpdateThread(new Runnable() {
			            @Override                
			            public void run() {
			            	player.detachSelf();
			            	player.stopAnimation();
			            	player.animate(new long[] {100,100,100,100},12,15, false);	
			            	attachChild(player);
			            }
			           });
				
				btTouch=true;
				downSprite.detachSelf();
				currentplayer = PlayerType.DOWN;	
				autoParallaxBackground.setParallaxChangePerSecond(0);
				downSprite.setAlpha(1f);
				attachChild(downSprite);
				Iterator<AnimatedSprite> houses = houseSprite.getHouseLL()
						.iterator();
				AnimatedSprite _house;
				while (houses.hasNext()) {
					_house = houses.next();
					_house.clearEntityModifiers();
				}
				Iterator<AnimatedSprite> munis = boxSprite.getMuniLL().iterator();
				AnimatedSprite _muni;
				while (munis.hasNext()) {
					_muni = munis.next();
					_muni.clearEntityModifiers();
				}
			}
		}
	
		return false; 
	}
	
	public void loseLife(){
		if(life ==3){
			SoundManagerGame.startMusic(InitRessources.outch);
			life= 2;
			
			this.detachChild(life3Sprite);
		
		}
		else if(life==2){
			SoundManagerGame.startMusic(InitRessources.outch);
			life=1;
			this.detachChild(life2Sprite);
		}
		else{
			SoundManagerGame.startMusic(InitRessources.outch);
			this.detachChild(life1Sprite);
			mainActivity.currentScene = SceneType.GAMEOVER;
			mainActivity.onSceneTouchEvent(null,null);
		}
	}

	public void projSend() {
		// TODO Auto-generated method stub
		projSend=false;
	}
	
	public void finishJump(){
		 mainActivity.runOnUpdateThread(new Runnable() {
	            @Override                
	            public void run() {
	            	player.detachSelf();
	            	player.stopAnimation();
	            	player.animate(new long[] {200,200,200,200},0,3, true);	
	            	attachChild(player);
	            	currentplayer = PlayerType.NORMAL ;
	            	
	            }
	           });
		currentplayer = PlayerType.NORMAL;	
	}
	
}
