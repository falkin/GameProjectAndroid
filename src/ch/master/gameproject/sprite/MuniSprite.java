package ch.master.gameproject.sprite;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.pool.GenericPool;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.ressource.InitRessources;

public class MuniSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;

	private LinkedList muniLL;
	private LinkedList munisToBeAdded;
	private HouseSprite houseSprite;
	
	public MuniSprite(MainActivity mainActivity,HouseSprite houseSprite) {
		muniLL = new LinkedList();
		munisToBeAdded = new LinkedList();
		this.mainActivity = mainActivity;
		this.houseSprite=houseSprite;
	}

	
	public IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
			
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			muniLL.addAll(munisToBeAdded);
			munisToBeAdded.clear();
		}
	};

	public void createSpriteSpawnTimeHandler() {
		TimerHandler spriteTimerHandler;
		int minDuration = 11;
		int maxDuration =20;
		Random rand = new Random();
		int effectTimeSect = rand.nextInt(maxDuration) + minDuration;
		float mEffectSpawnDelay = effectTimeSect;

		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if(!houseSprite.isAHouse())
							addMuni();
					}
				});
		
		mainActivity.mCurrentScene.registerUpdateHandler(spriteTimerHandler);
	}

	
	


	public void addMuni() {
		Random rand = new Random();
		AnimatedSprite muni = obtainPoolItem();
	
		mainActivity.mCurrentScene.attachChild(muni);
		MoveXModifier mod = new MoveXModifier(14f, muni.getX(),
				-muni.getWidth());
		muni.registerEntityModifier(mod.deepCopy());
		munisToBeAdded.add(muni);

	}



	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		int x = (int) ((int) mainActivity.mCamera.getWidth() + InitRessources.mBoxTextureRegion
				.getWidth());
		int minY = (int) InitRessources.mTargetTextureRegion.getHeight();
		int maxY = (int) (mainActivity.mCamera.getHeight() - InitRessources.mBoxTextureRegion
				.getHeight());

		int y = maxY;
		return new AnimatedSprite(x, y,
				InitRessources.mBoxTextureRegion.deepCopy(),
				mainActivity.getVertexBufferObjectManager());

	}

	protected void onHandleRecycleItem(final AnimatedSprite muni) {
		muni.clearEntityModifiers();
		muni.clearUpdateHandlers();
		muni.setVisible(false);
		muni.detachSelf();
		muni.reset();
	}
	
	public void removeSprite(final Sprite _sprite, Iterator it) {
		mainActivity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mainActivity.mCurrentScene.detachChild(_sprite);
			}
		});
		it.remove();

	}
	
	public LinkedList getMuniLL() {
		return muniLL;
	}





	public void setMuniLL(LinkedList muniLL) {
		this.muniLL = muniLL;
	}





	public LinkedList getMunisToBeAdded() {
		return munisToBeAdded;
	}





	public void setMunisToBeAdded(LinkedList munisToBeAdded) {
		this.munisToBeAdded = munisToBeAdded;
	}

}
