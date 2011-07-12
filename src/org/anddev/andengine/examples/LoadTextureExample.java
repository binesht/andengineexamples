package org.anddev.andengine.examples;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.widget.Toast;

/**
 * @author Nicolas Gramlich
 * @since 12:14:29 - 30.06.2010
 */
public class LoadTextureExample extends BaseExample {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private BitmapTexture mBitmapTexture;
	private Scene mScene;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		Toast.makeText(this, "Touch the screen to load a completely new Texture in a random location with every touch!", Toast.LENGTH_LONG).show();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		/* Nothing done here. */
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mScene = new Scene();
		this.mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		this.mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
				if(pSceneTouchEvent.isActionDown()) {
					LoadTextureExample.this.loadNewTexture();
				}

				return true;
			}
		});

		return this.mScene;
	}

	@Override
	public void onLoadComplete() {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void loadNewTexture() {
		BitmapTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTexture = new BitmapTexture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final TextureRegion faceTextureRegion = BitmapTextureRegionFactory.createFromAsset(this.mBitmapTexture, this, "ace_box.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mBitmapTexture);


		final float x = (CAMERA_WIDTH - faceTextureRegion.getWidth()) * MathUtils.RANDOM.nextFloat();
		final float y = (CAMERA_HEIGHT - faceTextureRegion.getHeight()) * MathUtils.RANDOM.nextFloat();
		final Sprite clickToUnload = new Sprite(x, y, faceTextureRegion);
		this.mScene.attachChild(clickToUnload);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
