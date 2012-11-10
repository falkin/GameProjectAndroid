package ch.master.gameproject;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Menu;

public class MainActivity extends SimpleBaseGameActivity {

	
	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	
	public Font mFont;
	public Camera mCamera;
	
	//A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Display display = getWindowManager().getDefaultDisplay();
		int cameraWidth = display.getWidth();
		int cameraHeight = display.getHeight();
	    instance = this;
	    mCamera = new Camera(0, 0, cameraWidth, cameraHeight);

	    return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(cameraWidth, cameraHeight), mCamera);
	}

	@Override
	protected void onCreateResources() {
		    mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		    mFont.load();

	}
	@Override
	protected Scene onCreateScene() {
	    mEngine.registerUpdateHandler(new FPSLogger());
	    mCurrentScene = new Scene();
	    mCurrentScene.setBackground(new Background(0.09804f, 0.7274f, 0.8f));
	    return mCurrentScene;
	}



}
