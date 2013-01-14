package ch.master.gameproject.sprite;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.ressource.InitRessources;

public class PlayerSprite {

	private MainActivity mainActivity;

	public PlayerSprite(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public AnimatedSprite createPlayer() {
		final int PlayerX = (int) (60);
		final int PlayerY = (int) (int) ((mainActivity.mCamera.getHeight() - InitRessources.mTargetTextureRegion
				.getHeight()) - 15);

		AnimatedSprite player = new AnimatedSprite(PlayerX, PlayerY,
				InitRessources.mPlayerTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		player.getTiledTextureRegion();
		
		/**
		 * { public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float
		 * pTouchAreaLocalX, float pTouchAreaLocalY) {
		 * this.setPosition(this.getX(), pSceneTouchEvent.getY()-
		 * this.getHeight() / 2); return true; } };
		 **/
		player.setScale(2);
		mainActivity.mCurrentScene.attachChild(player);
		return player;
	}
}
