package example.kingja.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:TODO
 * Create Time:2017/6/8 15:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RandomDragBall extends AppCompatTextView {

    private final String TAG = getClass().getSimpleName();
    private ViewGroup.LayoutParams layoutParams;
    private int startX;
    private int startY;
    private int distanceX;
    private int distanceY;
    private float mParentWidth;
    private float mParentHeight;
    private boolean mDragable;
    private ViewGroup parentView;
    private float parentX;
    private float parentY;
    private float speed = 0.35f;

    private List<RandomPosition> randomPositions = new ArrayList<>();

    public RandomDragBall(Context context) {
        super(context);
        initDragBall(context);
    }

    public RandomDragBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDragBall(context);
    }


    public RandomDragBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDragBall(context);
    }

    private void initDragBall(Context context) {
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        System.out.println("heigth2 : " + dm2.heightPixels);
        System.out.println("width2 : " + dm2.widthPixels);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        parentView = (ViewGroup) getParent();
        mParentWidth = parentView.getWidth();
        mParentHeight = parentView.getHeight();
        parentX = parentView.getX();
        parentY = parentView.getY();


        RandomPosition topLinePosition = new RandomPosition(Orientation.TOP, new PositionRange(0, mParentWidth -
                getWidth())
                .getRandomPosition(), 0);
        RandomPosition bottomLinePosition = new RandomPosition(Orientation.BOTTOM, new PositionRange(0, mParentWidth
                - getWidth())
                .getRandomPosition()
                , mParentHeight - getHeight());

        RandomPosition leftLinePosition = new RandomPosition(Orientation.LEFT, 0, new PositionRange(0, mParentHeight
                - getHeight())
                .getRandomPosition
                        ());
        RandomPosition rightLinePosition = new RandomPosition(Orientation.RIGHT, mParentWidth - getWidth(), new
                PositionRange(0,
                mParentHeight -
                        getHeight())
                .getRandomPosition());

        randomPositions.add(topLinePosition);
        randomPositions.add(bottomLinePosition);
        randomPositions.add(rightLinePosition);
        randomPositions.add(leftLinePosition);
    }

    public RandomPosition getRandomPosition(Orientation orientation) {
        RandomPosition result = null;
        switch (orientation) {
            case LEFT:
                result = new RandomPosition(Orientation.LEFT, 0, new PositionRange(0, mParentHeight - getHeight())
                        .getRandomPosition
                                ());
                break;
            case TOP:
                result = new RandomPosition(Orientation.TOP, new PositionRange(0, mParentWidth - getWidth())
                        .getRandomPosition(), 0);
                break;
            case RIGHT:
                result = new RandomPosition(Orientation.RIGHT, mParentWidth - getWidth(), new PositionRange(0,
                        mParentHeight -
                                getHeight())
                        .getRandomPosition());
                break;
            case BOTTOM:
                result = new RandomPosition(Orientation.BOTTOM, new PositionRange(0, mParentWidth - getWidth())
                        .getRandomPosition()
                        , mParentHeight - getHeight());
                break;

        }
        return result;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private boolean setDragable(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();
                distanceX = moveX - startX;
                distanceY = moveY - startY;

                int left = getLeft() + distanceX;
                int top = getTop() + distanceY;
                int right = getRight() + distanceX;
                int bottom = getBottom() + distanceY;

                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }
                if (right > mParentWidth) {
                    right = (int) mParentWidth;
                    left = right - getWidth();
                }
                if (bottom > mParentHeight) {
                    bottom = (int) mParentHeight;
                    top = bottom - getHeight();
                }

                layout(left, top, right, bottom);
                startX = moveX;
                startY = moveY;

                break;
            case MotionEvent.ACTION_UP:
                distanceX = 0;
                distanceY = 0;
                break;

        }
        return true;
    }

    public void startAnimation() {
        getPositionAndStart();
    }

    private void getPositionAndStart() {
        Log.e(TAG, "getX" + getX());
        Log.e(TAG, "getY" + getY());
        float currentPositionX = getX();
        float currentPositionY = getY();
        RandomPosition nextPosition = randomPositions.remove(0);
        long distance = (long) Math.sqrt((Math.pow((currentPositionX - nextPosition.getX()), 2) + Math.pow(
                (currentPositionY -
                        nextPosition.getY()), 2)));
        long costTime = (long) (distance / (speed*1L));
        Log.e(TAG, "distance" + distance);

        Log.e(TAG, "costTime" + costTime);
        int newPosition = 1 + new Random().nextInt(randomPositions.size());
        RandomPosition newRandomPosition = getRandomPosition(nextPosition.getOrientation());
        randomPositions.add(newPosition, newRandomPosition);
        doAnimation(nextPosition.getX(), nextPosition.getY(), costTime);
    }

    private void doAnimation(float x, float y, long costTime) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this, View.X, x), ObjectAnimator.ofFloat(this, View.Y, y));
        animatorSet.setDuration(costTime);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                float randomX = getRandomX();
                float randomY = getRandomY();
                Log.e(TAG, "(" + randomX + "," + randomY + ")");
                getPositionAndStart();
            }
        });
        animatorSet.start();
    }

    private float getRandomX() {
        return new Random().nextInt((int) (mParentWidth - getWidth()));
    }

    private float getRandomY() {
        return new Random().nextInt((int) (mParentHeight - getHeight()));
    }

}
