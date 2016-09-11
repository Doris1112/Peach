package com.doris.peach.activity.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 
 * @author Doris
 *
 * 2016年5月9日
 */
public class Rotate3dAnimation extends Animation {

	private final float mFromDegrees;
	private final float mToDegrees;
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;
	private final boolean mReverse;
	private Camera mCamera;
	private String rotate;

	private final String X = "X";
	private final String Y = "Y";

	public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ,
			boolean reverse, String rotate) {
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mCenterX = centerX;
		mCenterY = centerY;
		mDepthZ = depthZ;
		mReverse = reverse;
		this.rotate = rotate;
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float fromDegrees = mFromDegrees;

		float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();

		if (mReverse) {

			camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
		} else {

			camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
		}

		if (X.equals(rotate)) {
			camera.rotateX(degrees);
		} else if (Y.equals(rotate)) {
			camera.rotateY(degrees);
		}

		camera.getMatrix(matrix);
		camera.restore();

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}
