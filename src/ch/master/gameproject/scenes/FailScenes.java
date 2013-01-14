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

public class FailScenes extends Scene  implements IOnAreaTouchListener{

	private MainActivity mainActivity;

	private CameraScene mFailScene;
	private Sprite quitSprite; 
	private Sprite restartSprite;    
	
	public FailScenes(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void failGame() {
		mainActivity.mCurrentScene.setChildScene(mFailScene, false, true, true);
		clearChildScene();
	}

	public void loadFailScene() {
		mFailScene = new CameraScene(mainActivity.mCamera);
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.mFailTextureRegion
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - InitRessources.mFailTextureRegion
				.getHeight() / 2);
		final int xbt = (int) (mainActivity.mCamera.getWidth() / 4);
		final int ybt = (int) (mainActivity.mCamera.getHeight() / 1.5 - InitRessources.btPlay
					.getHeight() / 2);
			
		final Sprite pausedSprite = new Sprite(x, y,
				InitRessources.mFailTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		
		restartSprite = new Sprite(xbt-100 , ybt,370,174,
				InitRessources.btRestart,
				mainActivity.getVertexBufferObjectManager());
		quitSprite = new Sprite((int)(xbt+(InitRessources.btRestart.getWidth()*0.9)), ybt,370,174,
				InitRessources.btQuit,
				mainActivity.getVertexBufferObjectManager());
		mFailScene.setBackgroundEnabled(false);
		mFailScene.attachChild(pausedSprite);
		
		mFailScene.attachChild(restartSprite);
		mFailScene.attachChild(quitSprite);
	   
	    mFailScene.registerTouchArea(quitSprite);
	    mFailScene.registerTouchArea(restartSprite);
	    mFailScene.setTouchAreaBindingOnActionDownEnabled(true);
	    mFailScene.setOnAreaTouchListener(this);
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		 if (pTouchArea.equals(quitSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			 mainActivity.mCurrentScene.clearChildScene();
			 mainActivity.currentScene = SceneType.INITMENU;
			 return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else if (pTouchArea.equals(restartSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.mCurrentScene.clearChildScene();
			mainActivity.currentScene = SceneType.RESTARTGAME;
		 return  mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		return false; 
	}
	
}
