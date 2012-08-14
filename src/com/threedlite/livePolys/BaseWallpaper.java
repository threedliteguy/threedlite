/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.threedlite.livePolys;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


public abstract class BaseWallpaper extends WallpaperService {

	private final Handler mHandler = new Handler();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new BaseEngine();
	}

	class BaseEngine extends Engine {

		private final Paint mPaint = new Paint();
		private float mOffset;
		private float mTouchX = -1;
		private float mTouchY = -1;
		private long mStartTime;
		private float mCenterX;
		private float mCenterY;

		private final Runnable mDraw = new Runnable() {
			public void run() {
				drawFrame();
			}
		};
		private boolean mVisible;

		BaseEngine() {
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			// By default we don't get touch events, so enable them.
//			setTouchEventsEnabled(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mDraw);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mDraw);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			reinit(width);
			mCenterX = width/2.0f;
			mCenterY = height/2.0f;
			drawFrame();
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mDraw);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xStep, float yStep, int xPixels, int yPixels) {
			mOffset = xOffset;
			drawFrame();
		}

		/*
		 * Store the position of the touch event so we can use it for drawing later
		 */
		@Override
		public void onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				mTouchX = event.getX();
				mTouchY = event.getY();
			} else {
				mTouchX = -1;
				mTouchY = -1;
			}
			super.onTouchEvent(event);
		}

		/*
		 * Draw one frame of the animation. This method gets called repeatedly
		 * by posting a delayed Runnable. You can do any drawing you want in
		 * here. This example draws a wireframe cube.
		 */
		void drawFrame() {

			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					drawScreen(c);
				}
			} finally {
				if (c != null) holder.unlockCanvasAndPost(c);
			}

			// Reschedule the next redraw
			mHandler.removeCallbacks(mDraw);
			if (mVisible) {
				mHandler.postDelayed(mDraw, 1000 / 25);
			}
		}


		void drawScreen(Canvas c) {
			c.save();
			//c.translate(mCenterX, mCenterY);
			c.drawColor(0xff000000);
			draw(c);
			c.restore();
		}



	}

	protected abstract void draw(Canvas c);

	protected abstract void reinit(int disperse);

}
