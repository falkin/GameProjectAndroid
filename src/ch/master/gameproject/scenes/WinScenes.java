package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;

import ch.master.gameproject.MainActivity;

public class WinScenes {

	private MainActivity mainActivity;

	private Sprite winSprite;

	public WinScenes(MainActivity mainActivity) {
		this.mainActivity = mainActivity;

	}

	public Sprite getWinSprite() {
		return winSprite;
	}

	public void setWinSprite(Sprite winSprite) {
		this.winSprite = winSprite;
	}

	public void loadWinScene() {
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - mainActivity.mPausedTextureRegion
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - mainActivity.mPausedTextureRegion
				.getHeight() / 2);
		winSprite = new Sprite(x, y, mainActivity.mWinTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		winSprite.setVisible(false);
	}

}
