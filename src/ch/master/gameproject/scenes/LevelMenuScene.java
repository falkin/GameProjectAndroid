package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.MainActivity.SceneType;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.sprite.PlayerSprite;

public class LevelMenuScene extends Scene   implements IOnAreaTouchListener{



	private MainActivity mainActivity;
    private Sprite playSprite;
    private Sprite backSprite;       
	private CameraScene mInitScene;

	
	public LevelMenuScene(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void initMenu() {
		this.setChildScene(mInitScene, false, true, true);
	}

	public void loadInitScene() {
		
		mInitScene = new CameraScene(mainActivity.mCamera);
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.initTextureRegionBackLevelUn
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - InitRessources.initTextureRegionBackLevelUn
				.getHeight() / 2);
	    SpriteBackground bg = new SpriteBackground(new Sprite(x, y-1,   InitRessources.initTextureRegionBackLevelUn,mainActivity.getVertexBufferObjectManager()));
		
	    
	    final int xbte = (int) (mainActivity.mCamera.getWidth() - (mainActivity.mCamera.getWidth() -50));
		final int ybte = (int) (mainActivity.mCamera.getHeight() -(mainActivity.mCamera.getHeight()  - 30));
		
		final int xbtt = (int) (mainActivity.mCamera.getWidth()  - 180);
	    final int ybtt = (int) (mainActivity.mCamera.getHeight() -(mainActivity.mCamera.getHeight()  - 30));
		
		playSprite = new Sprite(446,208,105,102,
				InitRessources.btLvl1,
		mainActivity.getVertexBufferObjectManager()) ;
		backSprite= new Sprite(xbte, ybte,141,137,
				InitRessources.btBack,
				mainActivity.getVertexBufferObjectManager()) ;
		
		mInitScene.setBackgroundEnabled(true);
		mInitScene.setBackground(bg);
		
		mInitScene.attachChild(playSprite);
		mInitScene.attachChild(backSprite);
		
		mInitScene.registerTouchArea(playSprite);
		mInitScene.registerTouchArea(backSprite);
	
		mInitScene.setTouchAreaBindingOnActionDownEnabled(true);
		mInitScene.setOnAreaTouchListener(this);		
	}
	

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pTouchArea.equals(backSprite)&& pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.currentScene = SceneType.WORLD_SELECTION;
		 return  mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		else if (pTouchArea.equals(playSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			mainActivity.currentScene = SceneType.LOADGAME;
		 return  mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		return false; 
	}

}
