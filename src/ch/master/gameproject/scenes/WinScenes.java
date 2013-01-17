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
import ch.master.gameproject.model.LevelManagerGame;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;

public class WinScenes extends Scene  implements IOnAreaTouchListener{

	private MainActivity mainActivity;

	private CameraScene mWinScene;
	private Sprite quitSprite;
	private Sprite resumeSprite;    
	private Sprite nextSprite;    
	
	public WinScenes(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void winGame() {
		mainActivity.mCurrentScene.setChildScene(mWinScene, false, true, true);
		clearChildScene();
	}


	public void loadWinScene() {
		mWinScene = new CameraScene(mainActivity.mCamera);
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.mWinTextureRegion
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - InitRessources.mWinTextureRegion
				.getHeight() / 2);
		final int xbt = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.btPlay
					.getWidth() / 2);
		final int ybt = (int) (mainActivity.mCamera.getHeight() / 1.5 - InitRessources.btPlay
					.getHeight() / 2);
			
		final Sprite pausedSprite = new Sprite(x, y,
				InitRessources.mWinTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		resumeSprite = new Sprite(xbt, ybt,370,174,
				InitRessources.btRestart,
				mainActivity.getVertexBufferObjectManager());
		nextSprite = new Sprite((xbt-InitRessources.btRestart.getWidth()-50) , ybt,370,174,
				InitRessources.btNext,
				mainActivity.getVertexBufferObjectManager());
		quitSprite = new Sprite(xbt+InitRessources.btRestart.getWidth()+50, ybt,370,174,
				InitRessources.btQuit,
				mainActivity.getVertexBufferObjectManager());
		mWinScene.setBackgroundEnabled(false);
		mWinScene.attachChild(pausedSprite);
		mWinScene.attachChild(resumeSprite);
		mWinScene.attachChild(nextSprite);
		mWinScene.attachChild(quitSprite);
		mWinScene.registerTouchArea(resumeSprite);
		mWinScene.registerTouchArea(quitSprite);
	    mWinScene.registerTouchArea(nextSprite);
	    mWinScene.setTouchAreaBindingOnActionDownEnabled(true);
	    mWinScene.setOnAreaTouchListener(this);
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if (pTouchArea.equals(resumeSprite)&& pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.mCurrentScene.clearChildScene();
			 mainActivity.currentScene = SceneType.RESTARTGAME;
			 return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else if (pTouchArea.equals(quitSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){ 
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.mCurrentScene.clearChildScene();
			 mainActivity.currentScene = SceneType.INITMENU;
			 return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else if (pTouchArea.equals(nextSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			if(LevelManagerGame.getLevelNow()==3){
				 SoundManagerGame.startMusic(InitRessources.clickSound);
				 mainActivity.mCurrentScene.clearChildScene();
				 mainActivity.currentScene = SceneType.LEVEL_SELECTION;
			     return  mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
				
			}
			else{
				LevelManagerGame.setLevelSelect(LevelManagerGame.getLevelNow());
				SoundManagerGame.startMusic(InitRessources.clickSound);
				mainActivity.currentScene = SceneType.LOADGAME;
				return  mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
			}
			
		}
		return false; 
	}
	
}
