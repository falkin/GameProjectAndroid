package ch.master.gameproject.scenes;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;

import ch.master.gameproject.MainActivity;

public class FailScenes {

	private MainActivity mainActivity;

	private Sprite failSprite;

	public Sprite getFailSprite() {
		return failSprite;
	}

	public void setFailSprite(Sprite failSprite) {
		this.failSprite = failSprite;
	}

	public FailScenes(MainActivity mainActivity) {
		this.mainActivity = mainActivity;

	}

	public void loadFailScene() {
		final int x = (int) (mainActivity.mCamera.getWidth() / 2 - mainActivity.mPausedTextureRegion
				.getWidth() / 2);
		final int y = (int) (mainActivity.mCamera.getHeight() / 2 - mainActivity.mPausedTextureRegion
				.getHeight() / 2);
		failSprite = new Sprite(x, y, mainActivity.mFailTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		failSprite.setVisible(false);
	}

}
