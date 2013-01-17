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

public class TornadoSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;

	private LinkedList tornadoLL;
	
	private LinkedList tornadoLLToDel;

	private LinkedList tornadosToBeAdded;
	private HouseSprite houseSprite;
	
	public TornadoSprite(MainActivity mainActivity,HouseSprite houseSprite) {
		tornadoLL = new LinkedList();
		tornadosToBeAdded = new LinkedList();
		tornadoLLToDel = new LinkedList();
		this.mainActivity = mainActivity;
		this.houseSprite=houseSprite;
	}

	
	public LinkedList getTornadoLLToDel() {
		return tornadoLLToDel;
	}





	public void setTornadoLLToDel(LinkedList tornadoLLToDel) {
		this.tornadoLLToDel = tornadoLLToDel;
	}


	public IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
			
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			tornadoLL.addAll(tornadosToBeAdded);
			tornadosToBeAdded.clear();
		}
	};

	public void createSpriteSpawnTimeHandler() {
		TimerHandler spriteTimerHandler;
		int minDuration = 10;
		int maxDuration =16;
		Random rand = new Random();
		int effectTimeSect = rand.nextInt(maxDuration) + minDuration;
		float mEffectSpawnDelay = effectTimeSect;

		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if(!houseSprite.isAHouse())
							addTornado();
					}
				});
		
		mainActivity.mCurrentScene.registerUpdateHandler(spriteTimerHandler);
	}

	
	


	public void addTornado() {
		Random rand = new Random();
		AnimatedSprite tornado = obtainPoolItem();
		tornado.animate(25);
		mainActivity.mCurrentScene.attachChild(tornado);
		MoveXModifier mod = new MoveXModifier(8f, tornado.getX(),
				-tornado.getWidth());
		tornado.registerEntityModifier(mod.deepCopy());
		tornadosToBeAdded.add(tornado);

	}



	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		int x = (int) ((int) mainActivity.mCamera.getWidth() + InitRessources.mTornadoTextureRegion
				.getWidth());
		int minY = (int) InitRessources.mTargetTextureRegion.getHeight();
		int maxY = (int) (mainActivity.mCamera.getHeight() - InitRessources.mTornadoTextureRegion
				.getHeight());

		int y = maxY-10;
		return new AnimatedSprite(x, y,
				InitRessources.mTornadoTextureRegion.deepCopy(),
				mainActivity.getVertexBufferObjectManager());

	}

	protected void onHandleRecycleItem(final AnimatedSprite tornado) {
		tornado.clearEntityModifiers();
		tornado.clearUpdateHandlers();
		tornado.setVisible(false);
		tornado.detachSelf();
		tornado.reset();
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
	public LinkedList getTornadoLL() {
		return tornadoLL;
	}





	public void setTornadoLL(LinkedList tornadoLL) {
		this.tornadoLL = tornadoLL;
	}





	public LinkedList getTornadosToBeAdded() {
		return tornadosToBeAdded;
	}





	public void setTornadosToBeAdded(LinkedList tornadosToBeAdded) {
		this.tornadosToBeAdded = tornadosToBeAdded;
	}

}
