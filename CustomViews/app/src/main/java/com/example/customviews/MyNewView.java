package com.example.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.ArrayList;

public class MyNewView extends View {
    /* ------------------------*/
    /*    member variables     */
    private int mCanvasWidth;
    private int mCanvasHeight;
    private Rect mCanvasBounds;

    private Paint mPaint;
    private Paint mPaintGround;
    private Paint mPaintSun;

    private final static int SCALE_FACTOR = 20;

    private Bitmap mFlower;
    private int mFlowerWidth;
    private int mFlowerHeight;

    private ArrayList<Flower> mFlowerList;


    /* ------------------------*/
    /*    constructor          */

    public MyNewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
        setupFlower();
    }

    /* ------------------------*/
    /*    class methods        */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean result = true;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                addFlower((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                (mFlowerList.get(mFlowerList.size()-1)).startAnimation();
                break;
        }
        return result;
    }

    /* --------------------------------*/
    /*    lifecycle method             */

    @Override
    protected void onSizeChanged(int w, int h, int prevW, int prevH) {
        super.onSizeChanged(w, h, prevW, prevH);

        // get the width and height
        mCanvasWidth = w;
        mCanvasHeight = h;

        mCanvasBounds = new Rect(0, 0, mCanvasWidth, mCanvasHeight);
    }

    /* --------------------------------*/
    /*    protected draw operation     */

    @Override
    protected void onDraw(Canvas canvas) {

        if (!mFlowerList.isEmpty()) {
            for (Flower thisFlower : mFlowerList) {
                canvas.drawBitmap(mFlower, null, thisFlower.getmPostionRect(), mPaint);
            }
        }
    }

    /* --------------------------------*/
    /*    custom operations methods    */

    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(80);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void setupFlower() {

        mFlower       = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
        mFlowerWidth  = mFlower.getWidth()/SCALE_FACTOR;
        mFlowerHeight = mFlower.getHeight()/SCALE_FACTOR;

        mFlowerList = new ArrayList<Flower>();
    }

    private void addFlower(int x, int y) {
        mFlowerList.add(new Flower(x, y));
        postInvalidate();
    }

    /* --------------------------------*/
    /*    custom class                 */

    public class Flower implements Runnable{

        private static final int VELOCITY_X = 0;
        private static final int VELOCITY_Y = 3;

        private Handler mFlowerAnimationHandler = new Handler();

        private Rect mPostionRect =  new Rect(0,0,mFlowerWidth, mFlowerHeight);

        public Flower(int xPos, int yPos) {
            mPostionRect.offset(xPos - mFlowerWidth/2, yPos - mFlowerHeight/2);
        }

        public Rect getmPostionRect() {
            return mPostionRect;
        }

        /* --------------------------------*/
        /*    custom operations methods    */

        public void startAnimation() {
            mFlowerAnimationHandler.postDelayed(this, 50);
        }

        public void stopAnimation() {
            mFlowerAnimationHandler.removeCallbacks(this);
        }

        /* --------------------------------*/
        /*    interface methods            */

        @Override
        public void run() {
            mPostionRect.offset(VELOCITY_X, VELOCITY_Y);
            postInvalidate();

            if (mCanvasBounds.contains(mPostionRect)) {
                this.startAnimation();
            } else {
                this.stopAnimation();
            }
        }
    }
}
