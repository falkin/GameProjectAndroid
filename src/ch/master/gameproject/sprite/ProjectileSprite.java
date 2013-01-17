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
import ch.master.gameproject.MainActivity.SceneType;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.scenes.GameScene;
import ch.master.gameproject.scenes.GameScene.PlayerType;

public class ProjectileSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;

	private TargetSprite targetSprite;
	private LinkedList projectileLL;
	private LinkedList projectilesToBeAdded;
	public GameScene gameScene;
	private HouseSprite houseSprite;
	private MuniSprite muniSprite;
	private BallSprite ballSprite; 
	public TornadoSprite tornadoSprite;
	public ProjectileSprite(MainActivity mainActivity, TargetSprite targetSprite,GameScene gameScene,HouseSprite houseSprite, MuniSprite muniSprite,BallSprite ballSprite, TornadoSprite tornadoSprite) {
		projectileLL = new LinkedList();
		projectilesToBeAdded = new LinkedList();
		this.mainActivity = mainActivity;
		this.targetSprite = targetSprite;
		this.gameScene = gameScene;
		this.muniSprite = muniSprite;
		this.ballSprite = ballSprite;
		this.houseSprite = houseSprite;
		this.tornadoSprite = tornadoSprite;
	}

	public void shootProjectile(final float pX, final float pY) {
		if (!CoolDown.sharedCoolDown().checkValidity()) {
			return;
		}
		
		 mainActivity.runOnUpdateThread(new Runnable() {
	            @Override                
	            public void run() {
	            	gameScene.player.detachSelf();
	            	gameScene.player.stopAnimation();
	            	gameScene.player.animate(new long[] {100,100,100,100,100,100,100},4,10, false);	
	            	gameScene.attachChild(gameScene.player);
	            }
	    });
		 
		DelayModifier dMod2 = new DelayModifier(0.6f);
		gameScene.player.registerEntityModifier(dMod2);
		
	    dMod2.addModifierListener(new IModifierListener<IEntity>() {
		        @Override
		        public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		        }
		     
		        @Override
		        public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
			   		 mainActivity.runOnUpdateThread(new Runnable() {
				            @Override                
				            public void run() {
				            	gameScene.player.detachSelf();
				            	gameScene.player.stopAnimation();
				            	gameScene.player.animate(new long[] {200,200,200,200},0,3, true);	
				            	gameScene.attachChild(gameScene.player);
				            }
				    });
			   		gameScene.projSend();
		         }
		    });
	    
		
		int offX = (int) (pX - gameScene.player.getX() + 10);
		int offY = (int) (pY - gameScene.player.getY() + 10);

		final AnimatedSprite projectile;
		projectile = obtainPoolItem();
		projectile.animate(200);
		
		int realX = (int) (mainActivity.mCamera.getWidth() + projectile
				.getWidth() );
		float ratio = (float) offY / (float) offX;
		int realY = (int) (((float)realX * (ratio)));

		int offRealX = (int) (realX - projectile.getX());
		int offRealY = (int) (realY - projectile.getY());

		if(offRealX >=1000 ){
			offRealX = 200;
		}
		if(offRealY >=1000 ){
			offRealY = 200;
		}
		float length = (float) Math.sqrt((offRealX * offRealX)
				+ (offRealY * offRealY));
		float velocity = 390.0f / 1.0f; // 480 pixels / 1 sec
		float realMoveDuration = (float) 4;
		System.out.println("test : "+ pX +" "  +pY +" " +realX+" "+realY+"R"+ratio+"   "+offX+" "+offY+projectile.getY());
		
		/******rotation effect*****************/
		if(realY > 100){
			realY= 100;
		}
		MoveByModifier movMByod = new MoveByModifier(realMoveDuration, realX,
				realY);
	
		DelayModifier dMod = new DelayModifier(0.4f);

		SequenceEntityModifier seq = new SequenceEntityModifier(dMod, movMByod);
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
				SoundManagerGame.startMusic(InitRessources.shootingSound);
				projectile.setVisible(true);
				projectile.setPosition(gameScene.player.getX()
						+ gameScene.player.getWidth(),
						gameScene.player.getY()+65);

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
				hasTarget = true;
				Iterator<AnimatedSprite> projectiles = projectileLL.iterator();
				AnimatedSprite _projectile;
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
						gameScene.hitCount++;
						hit = true;
						break;
					}
				}

				if (hit) {
					SoundManagerGame.startMusic(InitRessources.outchEn);
					removeSprite(_target, targets);
					hit = false;
				}
				else if (gameScene.player.collidesWith(_target)){
					gameScene.loseLife();
					removeSprite(_target, targets);
				}
				else if (_target.getX() <= -_target.getWidth()) {
					removeSprite(_target, targets);
				}

			}

			if (!hasTarget) {
				Iterator<AnimatedSprite> projectiles = projectileLL.iterator();
				AnimatedSprite _projectile;
				while (projectiles.hasNext()) {
					_projectile = projectiles.next();

					if (_projectile.getX() >= mainActivity.mCamera.getWidth()
							|| _projectile.getY() >= mainActivity.mCamera
									.getHeight() + _projectile.getHeight()
							|| _projectile.getY() <= -_projectile.getHeight()) {
						recyclePoolItem(_projectile);
						removeSprite(_projectile, projectiles);
					
					}
				}
			}
			Iterator<AnimatedSprite> houses = houseSprite.getHouseLL()
					.iterator();
			AnimatedSprite _house;
			while (houses.hasNext()) {
				_house = houses.next();
				if (gameScene.player.collidesWith(_house)){
					gameScene.detachChild(gameScene.player);
					mainActivity.currentScene = SceneType.WIN;
					mainActivity.onSceneTouchEvent(null,null);
				}
			
			}
			Iterator<AnimatedSprite> munis = muniSprite.getMuniLL().iterator();
			AnimatedSprite _muni;
			while (munis.hasNext()) {
				_muni = munis.next();
				if (gameScene.player.collidesWith(_muni)){
					gameScene.chargeMuni();
					removeSprite(_muni, munis);
				}
				else if (_muni.getX() <= -_muni.getWidth()) {
						removeSprite(_muni, munis);
					
				}
			
			}
			
			Iterator<AnimatedSprite> balls = ballSprite.getBallLL()
					.iterator();
			AnimatedSprite _ball;
			while (balls.hasNext()) {
				_ball = balls.next();
				
				if (gameScene.player.collidesWith(_ball) &&  gameScene.currentplayer != PlayerType.JUMP ){
					gameScene.loseLife();
					ballSprite.getBallLLToDel().add(_ball);
					balls.remove();
					
				}
				else if (_ball.getX() <= -_ball.getWidth()) {
					removeSprite(_ball, balls);
				
		    	}
			
			}
			Iterator<AnimatedSprite> ballsD = ballSprite.getBallLLToDel()
					.iterator();
			AnimatedSprite _ballD;
			while (ballsD.hasNext()) {
				_ballD = ballsD.next();
				 if (_ballD.getX() <= -_ballD.getWidth()) {
					removeSprite(_ballD, ballsD);
				
		    	}
			
			}
			
			Iterator<AnimatedSprite> tornados = tornadoSprite.getTornadoLL()
					.iterator();
			AnimatedSprite _tornado;
			while (tornados.hasNext()) {
				_tornado = tornados.next();
				
				if (gameScene.player.collidesWith(_tornado) &&  gameScene.currentplayer != PlayerType.DOWN){
					gameScene.loseLife();
					tornadoSprite.getTornadoLLToDel().add(_tornado);
					tornados.remove();
				}
				else if (_tornado.getX() <= -_tornado.getWidth()) {
					removeSprite(_tornado, tornados);
				
			}
				
			Iterator<AnimatedSprite> tornadosD = tornadoSprite.getTornadoLLToDel()
						.iterator();
				AnimatedSprite _tornadoD;
				while (tornadosD.hasNext()) {
					_tornadoD = tornadosD.next();
					 if (_tornadoD.getX() <= -_tornadoD.getWidth()) {
						removeSprite(_tornadoD, tornadosD);
					
			    	}
				
				}
			
			}
			
			projectileLL.addAll(projectilesToBeAdded);
			projectilesToBeAdded.clear();

			targetSprite.getTargetLL().addAll(targetSprite.getTargetsToBeAdded());
			targetSprite.getTargetsToBeAdded().clear();
			
			muniSprite.getMuniLL().addAll(muniSprite.getMunisToBeAdded());
			muniSprite.getMunisToBeAdded().clear();
			
			if (gameScene.hitCount >= gameScene.score && !houseSprite.isAHouse() ) {
				houseSprite.addHouse();
			}
			
			houseSprite.getHouseLL().addAll(houseSprite.getHouseToBeAdded());
			houseSprite.getHouseToBeAdded().clear();
			ballSprite.getBallLL().addAll(ballSprite.getBallsToBeAdded());
			ballSprite.getBallsToBeAdded().clear();
			tornadoSprite.getTornadoLL().addAll(tornadoSprite.getTornadosToBeAdded());
			tornadoSprite.getTornadosToBeAdded().clear();
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
	protected AnimatedSprite onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return new AnimatedSprite(0, 0,
				InitRessources.mProjectileTextureRegion.deepCopy(),
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
