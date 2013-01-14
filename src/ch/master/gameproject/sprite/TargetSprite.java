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

public class TargetSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;

	private LinkedList targetLL;

	private LinkedList TargetsToBeAdded;

	public TargetSprite(MainActivity mainActivity) {
		targetLL = new LinkedList();
		TargetsToBeAdded = new LinkedList();
		this.mainActivity = mainActivity;
	}

	public IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {

			Iterator<AnimatedSprite> targets = targetLL.iterator();
			AnimatedSprite _target;

			while (targets.hasNext()) {
				_target = targets.next();
				if (_target.getX() <= -_target.getWidth()) {
					recyclePoolItem(_target);
					removeSprite(_target, targets);
				}
			}

			targetLL.addAll(TargetsToBeAdded);
			TargetsToBeAdded.clear();
		}
	};

	public void createSpriteSpawnTimeHandler() {
		TimerHandler spriteTimerHandler;
		int minDuration = 3;
		int maxDuration = 8;
		Random rand = new Random();
		int effectTimeSect = rand.nextInt(maxDuration) + minDuration;
		float mEffectSpawnDelay = effectTimeSect;

		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						addTarget();
					}
				});

		mainActivity.getEngine().registerUpdateHandler(spriteTimerHandler);
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

	public void addTarget() {
		Random rand = new Random();

		AnimatedSprite target = obtainPoolItem();
		target.animate(300);
		mainActivity.mCurrentScene.attachChild(target);
		int minDuration = 5;
		int maxDuration = 10;
		int rangeDuration = maxDuration - minDuration;
		int actualDuration = rand.nextInt(rangeDuration) + minDuration;

		MoveXModifier mod = new MoveXModifier(actualDuration, target.getX(),
				-target.getWidth());
		target.registerEntityModifier(mod.deepCopy());

		TargetsToBeAdded.add(target);

	}

	public LinkedList getTargetLL() {
		return targetLL;
	}

	public void setTargetLL(LinkedList targetLL) {
		this.targetLL = targetLL;
	}

	public LinkedList getTargetsToBeAdded() {
		return TargetsToBeAdded;
	}

	public void setTargetsToBeAdded(LinkedList targetsToBeAdded) {
		TargetsToBeAdded = targetsToBeAdded;
	}

	public void clear() {
		TargetsToBeAdded.clear();
		targetLL.clear();
	}

	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		int x = (int) ((int) mainActivity.mCamera.getWidth() + InitRessources.mTargetTextureRegion
				.getWidth());
		int minY = (int) InitRessources.mTargetTextureRegion.getHeight();
		int maxY = (int) (mainActivity.mCamera.getHeight() - InitRessources.mTargetTextureRegion
				.getHeight());

		int y = maxY - 35;
		return new AnimatedSprite(x, y,
				InitRessources.mTargetTextureRegion.deepCopy(),
				mainActivity.getVertexBufferObjectManager());

	}

	protected void onHandleRecycleItem(final AnimatedSprite target) {
		target.clearEntityModifiers();
		target.clearUpdateHandlers();
		target.setVisible(false);
		target.detachSelf();
		target.reset();
	}
}
