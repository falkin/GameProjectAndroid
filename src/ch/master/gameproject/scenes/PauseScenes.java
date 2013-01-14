package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.MainActivity.SceneType;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;

public class PauseScenes extends Scene  implements IOnAreaTouchListener{

	private MainActivity mainActivity;

	private CameraScene mPauseScene;
	private Sprite quitSprite;
	private Sprite resumeSprite;    
	private Sprite restartSprite;    
	
	public PauseScenes(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void pauseGame() {
		mainActivity.mCurrentScene.setChildScene(mPauseScene, false, true, true);
		clearChildScene();
	}

	public void unPauseGame() {
		mainActivity.mCurrentScene.clearChildScene();
		mainActivity.getMEngine().start();
	}

	public void loadPauseScene() {
		mPauseScene = new CameraScene(mainActivity.mCamera);
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.mPausedTextureRegion
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - InitRessources.mPausedTextureRegion
				.getHeight() / 2);
		final int xbt = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.btPlay
					.getWidth() / 2);
		final int ybt = (int) (mainActivity.mCamera.getHeight() / 1.5 - InitRessources.btPlay
					.getHeight() / 2);
			
		final Sprite pausedSprite = new Sprite(x, y,
				InitRessources.mPausedTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		resumeSprite = new Sprite(xbt, ybt,370,174,
				InitRessources.btResume,
				mainActivity.getVertexBufferObjectManager());
		restartSprite = new Sprite((xbt-InitRessources.btRestart.getWidth()-50) , ybt,370,174,
				InitRessources.btRestart,
				mainActivity.getVertexBufferObjectManager());
		quitSprite = new Sprite(xbt+InitRessources.btRestart.getWidth()+50, ybt,370,174,
				InitRessources.btQuit,
				mainActivity.getVertexBufferObjectManager());
	    mPauseScene.setBackgroundEnabled(false);
	    mPauseScene.attachChild(pausedSprite);
	    mPauseScene.attachChild(resumeSprite);
	    mPauseScene.attachChild(restartSprite);
	    mPauseScene.attachChild(quitSprite);
	    mPauseScene.registerTouchArea(resumeSprite);
	    mPauseScene.registerTouchArea(quitSprite);
	    mPauseScene.registerTouchArea(restartSprite);
	    mPauseScene.setTouchAreaBindingOnActionDownEnabled(true);
		mPauseScene.setOnAreaTouchListener(this);
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pTouchArea.equals(resumeSprite)&& pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.currentScene = SceneType.RESUMEGAME;
			 return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else if (pTouchArea.equals(quitSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.mCurrentScene.clearChildScene();
			 mainActivity.currentScene = SceneType.INITMENU;
			 return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else if (pTouchArea.equals(restartSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.currentScene = SceneType.RESTARTGAME;
		 return  mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		return false; 
	}
	
}
