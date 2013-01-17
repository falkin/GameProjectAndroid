package ch.master.gameproject.sprite;

import java.util.LinkedList;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import ch.master.gameproject.MainActivity;
import ch.master.gameproject.model.SoundManagerGame;
import ch.master.gameproject.ressource.InitRessources;
import ch.master.gameproject.scenes.GameScene;

public class PlayerSprite extends GenericPool<AnimatedSprite> {

	private MainActivity mainActivity;
	private LinkedList TargetsToBeAdded;
	private AnimatedSprite player;
	private AnimatedSprite playerJump;
	private AnimatedSprite playerJumpp;
	private AnimatedSprite playerDown;
	private AnimatedSprite playerUp;
	public GameScene gameScene;
	
	public PlayerSprite(MainActivity mainActivity,GameScene gameScenes) {
		this.mainActivity = mainActivity;
		TargetsToBeAdded = new LinkedList();
		this.gameScene = gameScenes;
	}

	public AnimatedSprite createPlayer() {
		final int PlayerX = (int) (100);
		final int PlayerY = (int) (int) ((mainActivity.mCamera.getHeight() - InitRessources.mPlayerTextureRegion
				.getHeight()-15));

		 player = new AnimatedSprite(PlayerX, PlayerY,
		         InitRessources.mPlayerTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		
		
		
		mainActivity.mCurrentScene.attachChild(player);
		return player;
	}

	public AnimatedSprite createShootPlayer() {
		final int PlayerX = (int) (100);
		final int PlayerY = (int) (int) ((mainActivity.mCamera.getHeight() - InitRessources.mPlayerTextureRegion
				.getHeight()-15));

		
		 playerJump = new AnimatedSprite(PlayerX, PlayerY,
					InitRessources.mShootTextureRegion,
					mainActivity.getVertexBufferObjectManager());
		
		
		
		return playerJump;
	}

	
	public void jump() {
		SoundManagerGame.startMusic(InitRessources.jump);
		//playerJumpp.animate(70,false);
		
		MoveYModifier mod = new MoveYModifier(0.7f,player.getY(), player.getY()-210);
		player.registerEntityModifier(mod);
		DelayModifier dMod = new DelayModifier(0.7f);
		DelayModifier dMod2 = new DelayModifier(1.5f);
	    MoveYModifier mod2  = new MoveYModifier(0.7f, player.getY()-210,
	    		player.getY());
		SequenceEntityModifier seq = new SequenceEntityModifier(dMod, mod2);
		player.registerEntityModifier(seq);
		player.registerEntityModifier(dMod2);

	    dMod2.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
			        gameScene.finishJump();

			}
		});

	}
	
	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
