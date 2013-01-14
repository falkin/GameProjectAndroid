package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.sprite.PlayerSprite;
import ch.master.gameproject.sprite.ProjectileSprite;
import ch.master.gameproject.sprite.TargetSprite;

public class GameScene extends Scene {

	private final int maxScore = 10;
	
	private AutoParallaxBackground autoParallaxBackground;
	public AnimatedSprite player;
	private MainActivity mainActivity;
	private PauseScenes pauseScene;
	private WinScenes winScene;
	private FailScenes failScene;
	public TargetSprite targetSprite;
	public ProjectileSprite projectileSprite;
	public PlayerSprite playerSprite;
	private CameraScene mResultScene;
	public Text score;
	public int hitCount;
	
	public GameScene(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		targetSprite = new TargetSprite(mainActivity);
		playerSprite = new PlayerSprite(mainActivity);
		projectileSprite = new ProjectileSprite(mainActivity, targetSprite,this);
		playerSprite = new PlayerSprite(mainActivity);
		winScene = new WinScenes(mainActivity);
		failScene = new FailScenes(mainActivity);
		pauseScene = new PauseScenes(mainActivity);
	}
	
	public void initGame() {
		this.setChildScene(mResultScene, false, true, true);
		clearChildScene();
	}
	
	
	public void loadInitScene() {
		autoParallaxBackground = new AutoParallaxBackground(
				0, 0, 0, 4);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-25.0f,
				new Sprite(0, mainActivity.mCamera.getHeight() - InitRessources.mParallaxLayer.getHeight(),InitRessources.mParallaxLayer,
						mainActivity.getVertexBufferObjectManager())));
		
		setBackground(autoParallaxBackground);
		player = playerSprite.createPlayer();
		registerTouchArea(player);
		targetSprite.createSpriteSpawnTimeHandler();
		registerUpdateHandler(targetSprite.detect);
		registerUpdateHandler(projectileSprite.detect);
		setOnSceneTouchListener(mainActivity);
		pauseScene.loadPauseScene();
		winScene.loadWinScene();
		failScene.loadFailScene();
		mResultScene = new CameraScene(mainActivity.mCamera);
		mResultScene.setBackgroundEnabled(false);
		
		score = new Text(0, 0, InitRessources.mFont, String.valueOf(maxScore),
				mainActivity.getVertexBufferObjectManager());
		score.setPosition(mainActivity.mCamera.getWidth() - score.getWidth() - 5, 5);

		hitCount = 0;
		score.setText(String.valueOf(hitCount));
		attachChild(score);
		SoundManagerGame.startMusic(InitRessources.backgroundMusic);

		
	}
	

	/**** FUNCTION ON THE GAME ********/
	
	public void shootProjectil(float touchX,float touchY){
		projectileSprite.shootProjectile(touchX, touchY);
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
	
	
}
