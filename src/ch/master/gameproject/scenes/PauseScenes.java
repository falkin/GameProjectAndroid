package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;

import ch.master.gameproject.MainActivity;

public class PauseScenes {

	private MainActivity mainActivity;

	private CameraScene mPauseScene;

	public PauseScenes(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void pauseGame() {
		mainActivity.mCurrentScene
				.setChildScene(mPauseScene, false, true, true);
		mainActivity.getMEngine().stop();
	}

	public void unPauseGame() {
		mainActivity.mCurrentScene.clearChildScene();
		mainActivity.getMEngine().start();
	}

	public void loadPauseScene() {
		mPauseScene = new CameraScene(mainActivity.mCamera);
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - mainActivity.mPausedTextureRegion
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - mainActivity.mPausedTextureRegion
				.getHeight() / 2);
		final Sprite pausedSprite = new Sprite(x, y,
				mainActivity.mPausedTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		mPauseScene.attachChild(pausedSprite);
		mPauseScene.setBackgroundEnabled(false);
	}

}
