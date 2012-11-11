package ch.master.gameproject.sprite;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
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

public class ProjectileSprite extends GenericPool<Sprite> {

	private MainActivity mainActivity;

	private TargetSprite targetSprite;
	private LinkedList projectileLL;
	private LinkedList projectilesToBeAdded;

	public ProjectileSprite(MainActivity mainActivity, TargetSprite targetSprite) {
		projectileLL = new LinkedList();
		projectilesToBeAdded = new LinkedList();
		this.mainActivity = mainActivity;
		this.targetSprite = targetSprite;
	}

	public void shootProjectile(final float pX, final float pY) {
		if (!CoolDown.sharedCoolDown().checkValidity()) {
			return;
		}
		mainActivity.player.animate(50, false);
		int offX = (int) (pX - mainActivity.player.getX() + 10);
		int offY = (int) (pY - mainActivity.player.getY() + 10);

		final Sprite projectile;
		projectile = obtainPoolItem();

		int realX = (int) (mainActivity.mCamera.getWidth() + projectile
				.getWidth() / 2.0f);
		float ratio = (float) offY / (float) offX;
		int realY = (int) ((realX * ratio) + projectile.getY());

		int offRealX = (int) (realX - projectile.getX());
		int offRealY = (int) (realY - projectile.getY());
		float length = (float) Math.sqrt((offRealX * offRealX)
				+ (offRealY * offRealY));
		float velocity = 380.0f / 1.0f; // 480 pixels / 1 sec
		float realMoveDuration = length / velocity;

		MoveByModifier movMByod = new MoveByModifier(realMoveDuration, realX,
				realY);
		LoopEntityModifier loopMod = new LoopEntityModifier(
				new RotationModifier(0.5f, 0, -360));
		final ParallelEntityModifier par = new ParallelEntityModifier(movMByod,
				loopMod);
		DelayModifier dMod = new DelayModifier(0.55f);

		SequenceEntityModifier seq = new SequenceEntityModifier(dMod, par);
		projectile.registerEntityModifier(seq);
		projectile.setVisible(false);
		mainActivity.mCurrentScene.attachChild(projectile);

		dMod.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				mainActivity.soundShooting();
				projectile.setVisible(true);
				projectile.setPosition(mainActivity.player.getX()
						+ mainActivity.player.getWidth(),
						mainActivity.player.getY());

				projectilesToBeAdded.add(projectile);

			}
		});

	}

	public IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {

			Iterator<AnimatedSprite> targets = targetSprite.getTargetLL()
					.iterator();
			AnimatedSprite _target;
			boolean hit = false;
			boolean hasTarget = false;
			while (targets.hasNext()) {
				_target = targets.next();
				if (mainActivity.player.collidesWith(_target))
					mainActivity.fail();
				hasTarget = true;
				Iterator<Sprite> projectiles = projectileLL.iterator();
				Sprite _projectile;
				while (projectiles.hasNext()) {
					_projectile = projectiles.next();

					if (_projectile.getX() >= mainActivity.mCamera.getWidth()
							|| _projectile.getY() >= mainActivity.mCamera
									.getHeight() + _projectile.getHeight()
							|| _projectile.getY() <= -_projectile.getHeight()) {
						recyclePoolItem(_projectile);
						removeSprite(_projectile, projectiles);
						continue;
					}

					if (_target.collidesWith(_projectile)) {
						recyclePoolItem(_projectile);

						removeSprite(_projectile, projectiles);
						mainActivity.hitCount++;
						mainActivity.score.setText(String
								.valueOf(mainActivity.hitCount));
						hit = true;
						break;
					}
				}

				if (hit) {
					removeSprite(_target, targets);

					hit = false;
				}

			}

			if (!hasTarget) {
				Iterator<Sprite> projectiles = projectileLL.iterator();
				Sprite _projectile;
				while (projectiles.hasNext()) {
					_projectile = projectiles.next();

					if (_projectile.getX() >= mainActivity.mCamera.getWidth()
							|| _projectile.getY() >= mainActivity.mCamera
									.getHeight() + _projectile.getHeight()
							|| _projectile.getY() <= -_projectile.getHeight()) {
						removeSprite(_projectile, projectiles);

					}
				}
			}

			projectileLL.addAll(projectilesToBeAdded);
			projectilesToBeAdded.clear();

			targetSprite.getTargetLL().addAll(
					targetSprite.getTargetsToBeAdded());
			targetSprite.getTargetsToBeAdded().clear();
			if (mainActivity.hitCount >= 4) {
				mainActivity.win();
			}
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
		projectileLL.clear();
		projectilesToBeAdded.clear();
	}

	@Override
	protected Sprite onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return new Sprite(0, 0,
				mainActivity.mProjectileTextureRegion.deepCopy(),
				mainActivity.getVertexBufferObjectManager());
	}

	/** Called when a projectile is sent to the pool */
	protected void onHandleRecycleItem(final Sprite projectile) {
		projectile.clearEntityModifiers();
		projectile.clearUpdateHandlers();
		projectile.setVisible(false);
		projectile.detachSelf();
		projectile.reset();
	}

}
