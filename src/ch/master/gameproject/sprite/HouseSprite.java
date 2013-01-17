package ch.master.gameproject.sprite;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import ch.master.gameproject.CoolDown;
import ch.master.gameproject.MainActivity;
import ch.master.gameproject.MainActivity.SceneType;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.scenes.GameScene;

public class HouseSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;

	private LinkedList houseLL;
	

	private LinkedList houseToBeAdded;
	public GameScene gameScene;
	public boolean oneHouse;
	public HouseSprite(MainActivity mainActivity,GameScene gameScene) {
		houseLL = new LinkedList();
		houseToBeAdded = new LinkedList();
		this.mainActivity = mainActivity;
		this.gameScene = gameScene;
		oneHouse=false;
	}

	public void addHouse() {
		oneHouse =true;
		AnimatedSprite target = obtainPoolItem();
		mainActivity.mCurrentScene.attachChild(target);
	
		MoveXModifier mod = new MoveXModifier(16f, target.getX(),
				-target.getWidth());
		target.registerEntityModifier(mod.deepCopy());
	
		houseToBeAdded.add(target);
	}

	public IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			houseLL.addAll(houseToBeAdded);
			houseToBeAdded.clear();
		}
	};

	public void removeSprite(final Sprite _sprite, Iterator it) {
		mainActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mainActivity.mCurrentScene.detachChild(_sprite);
			}
		});
		it.remove();
	}

	public void clear() {
		houseLL.clear();
		houseToBeAdded.clear();
	}

	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		int x = (int) ((int) mainActivity.mCamera.getWidth() + InitRessources.mHouseTextureRegion
				.getWidth());
		int y = (int) 440;
		return new AnimatedSprite(x, y,
				InitRessources.mHouseTextureRegion.deepCopy(),
				mainActivity.getVertexBufferObjectManager());

	}
	/** Called when a projectile is sent to the pool */
	protected void onHandleRecycleItem(final Sprite house) {
		house.clearEntityModifiers();
		house.clearUpdateHandlers();
		house.setVisible(false);
		house.detachSelf();
		house.reset();
	}
	public LinkedList getHouseLL() {
		return houseLL;
	}

	public void setHouseLL(LinkedList houseLL) {
		this.houseLL = houseLL;
	}

	public LinkedList getHouseToBeAdded() {
		return houseToBeAdded;
	}

	public void setHouseToBeAdded(LinkedList houseToBeAdded) {
		this.houseToBeAdded = houseToBeAdded;
	}
	
	public boolean isAHouse(){
		return oneHouse;	
	}

}
