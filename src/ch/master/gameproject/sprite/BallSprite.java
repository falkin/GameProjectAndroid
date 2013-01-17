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

public class BallSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;

	private LinkedList ballLL;
	private LinkedList ballLLToDel;
	public TornadoSprite tornadoSprite;


	private HouseSprite houseSprite;
	
	public BallSprite(MainActivity mainActivity,HouseSprite houseSprite,TornadoSprite tornadoSprite) {
		ballLL = new LinkedList();
		ballsToBeAdded = new LinkedList();
		ballLLToDel = new LinkedList();
		this.mainActivity = mainActivity;
		this.houseSprite=houseSprite;
		this.tornadoSprite=tornadoSprite;
	}

	  public LinkedList getBallLLToDel() {
			return ballLLToDel;
		}


		public void setBallLLToDel(LinkedList ballLLToDel) {
			this.ballLLToDel = ballLLToDel;
		}
	public IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
			
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			ballLL.addAll(ballsToBeAdded);
			ballsToBeAdded.clear();
		}
	};

	public void createSpriteSpawnTimeHandler() {
		TimerHandler spriteTimerHandler;
		int minDuration = 6;
		int maxDuration = 15;
		Random rand = new Random();
		int effectTimeSect = rand.nextInt(maxDuration) + minDuration;
		float mEffectSpawnDelay = effectTimeSect;

		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, false,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if(tornadoSprite.getTornadoLL().isEmpty())
							addBall();
					}
				});
		
		mainActivity.mCurrentScene.registerUpdateHandler(spriteTimerHandler);
	}

	
	public LinkedList getBallLL() {
		return ballLL;
	}





	public void setBallLL(LinkedList ballLL) {
		this.ballLL = ballLL;
	}





	public LinkedList getBallsToBeAdded() {
		return ballsToBeAdded;
	}





	public void setBallsToBeAdded(LinkedList ballsToBeAdded) {
		this.ballsToBeAdded = ballsToBeAdded;
	}


	private LinkedList ballsToBeAdded;
	


	public void addBall() {
		Random rand = new Random();
		AnimatedSprite target = obtainPoolItem();
		target.animate(100);
		mainActivity.mCurrentScene.attachChild(target);
		int minDuration = 4;
		int maxDuration = 6;
		int rangeDuration = maxDuration - minDuration;
		int actualDuration = rand.nextInt(rangeDuration) + minDuration;

		MoveXModifier mod = new MoveXModifier(actualDuration, target.getX(),
				-target.getWidth());
		target.registerEntityModifier(mod.deepCopy());
		ballsToBeAdded.add(target);
		createSpriteSpawnTimeHandler() ;

	}

	
	public void clear() {
		ballsToBeAdded.clear();
		ballLL.clear();
	}

	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		int x = (int) ((int) mainActivity.mCamera.getWidth() + InitRessources.mBallTextureRegion
				.getWidth());
		int minY = (int) InitRessources.mTargetTextureRegion.getHeight();
		int maxY = (int) (mainActivity.mCamera.getHeight() - InitRessources.mBallTextureRegion
				.getHeight()+20);

		int y = maxY - 35;
		return new AnimatedSprite(x, 610,
				InitRessources.mBallTextureRegion.deepCopy(),
				mainActivity.getVertexBufferObjectManager());

	}

	protected void onHandleRecycleItem(final AnimatedSprite ball) {
		ball.clearEntityModifiers();
		ball.clearUpdateHandlers();
		ball.setVisible(false);
		ball.detachSelf();
		ball.reset();
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
}
