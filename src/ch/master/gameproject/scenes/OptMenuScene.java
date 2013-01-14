package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.MainActivity.SceneType;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.sprite.PlayerSprite;

public class OptMenuScene extends Scene   implements IOnAreaTouchListener{



	private MainActivity mainActivity;
    private Sprite enableSoundSprite;
    private Sprite backSprite;    
	private CameraScene mInitScene;
    private Text sound;
	
	public OptMenuScene(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void initMenu() {
		this.setChildScene(mInitScene, false, true, true);
	}

	public void loadInitScene() {
		
		mInitScene = new CameraScene(mainActivity.mCamera);
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - InitRessources.initTextureRegionBackOpt
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - InitRessources.initTextureRegionBackOpt
				.getHeight() / 2);
	    SpriteBackground bg = new SpriteBackground(new Sprite(x, y-1,   InitRessources.initTextureRegionBackOpt ,mainActivity.getVertexBufferObjectManager()));
		
	    final int xbt = (int) (mainActivity.mCamera.getWidth() / 1.4 - InitRessources.btPlay.getWidth() / 2);
		final int ybt = (int) (mainActivity.mCamera.getHeight() / 2.2- InitRessources.btPlay.getHeight() / 2);
	
	    final int xbte = (int) (mainActivity.mCamera.getWidth() - (mainActivity.mCamera.getWidth() -50));
		final int ybte = (int) (mainActivity.mCamera.getHeight() -(mainActivity.mCamera.getHeight()  - 30));
		
		
		if(SoundManagerGame.isSoundEnable() == 0){
			loadingSpriteSoundEnable(xbt, ybt);
		}
		else{
			loadingSpriteSoundDisable(xbt, ybt);
		}

		backSprite= new Sprite(xbte, ybte,141,137,
				InitRessources.btBack,
				mainActivity.getVertexBufferObjectManager()) ;
		
		sound = new Text(0, 0, InitRessources.mFontBD, "Sound:",
				mainActivity.getVertexBufferObjectManager());
		sound.setPosition((mainActivity.mCamera.getWidth()/2)-200  , ybt+45);
		
		mInitScene.setBackgroundEnabled(true);
		mInitScene.setBackground(bg);
		
	
		mInitScene.attachChild(backSprite);
		mInitScene.attachChild(sound);
		mInitScene.registerTouchArea(enableSoundSprite);
		mInitScene.registerTouchArea(backSprite);
		mInitScene.setTouchAreaBindingOnActionDownEnabled(true);
		mInitScene.setTouchAreaBindingOnActionMoveEnabled(true);
		mInitScene.setOnAreaTouchListener(this);		
	}
	

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pTouchArea.equals(backSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			 SoundManagerGame.startMusic(InitRessources.clickSound);
			 mainActivity.currentScene = SceneType.INITMENU;
			 return mainActivity.onSceneTouchEvent(this,pSceneTouchEvent);
		}
		
		else if (pTouchArea.equals(enableSoundSprite) && pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			
			
		    final int xbt = (int) (mainActivity.mCamera.getWidth() / 1.4 - InitRessources.btPlay.getWidth() / 2);
			final int ybt = (int) (mainActivity.mCamera.getHeight() / 2.2- InitRessources.btPlay.getHeight() / 2);
			enableSoundSprite.detachSelf();
			if(SoundManagerGame.isSoundEnable() == 0){
				 SoundManagerGame.pauseMusic(InitRessources.backgroundMusicMenu);
				SoundManagerGame.saveHighScore(1);
				loadingSpriteSoundDisable(xbt, ybt);
				
			}
			else{
				
				
				SoundManagerGame.saveHighScore(0);
				loadingSpriteSoundEnable(xbt, ybt);
				 SoundManagerGame.startMusic(InitRessources.clickSound);
				 SoundManagerGame.startMusic(InitRessources.backgroundMusicMenu);
				
			}
			mInitScene.registerTouchArea(enableSoundSprite);
			return  true;
		}
		
		return false;
	}

	private void loadingSpriteSoundEnable(int x, int y){
		
		enableSoundSprite = new Sprite(x, y,141,137,
				InitRessources.btSoundOn,
		mainActivity.getVertexBufferObjectManager()) ;
		mInitScene.attachChild(enableSoundSprite);
	}
	
	private void loadingSpriteSoundDisable(int x, int y){
		
		enableSoundSprite = new Sprite(x, y,141,137,
				InitRessources.btSoundOff,
		mainActivity.getVertexBufferObjectManager()) ;
		mInitScene.attachChild(enableSoundSprite);
	}
}
