package example.kingja.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time:2017/6/10 13:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ZoomDialogActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    private LinearLayout ll_root;
    private View dialogView;
    private int dialogX;
    private int dialogY;
    private int measuredDialogWidth;
    private int measuredDialogHeight;
    private TextView tv_title;
    private EditText et_name;
    private EditText et_password;
    private Button confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_zoomdialog);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
//        dialogView = View.inflate(ZoomDialogActivity.this, R.layout.layout_login, null);
//        ll_root.addView(dialogView);



//        EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);
//        EditText et_password = (EditText) dialogView.findViewById(R.id.et_password);
//
//        dialogView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        int dialogX = getDialogX(ll_root, dialogView);
//        int dialogY = getDialogY(ll_root, dialogView);


//                ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
//                layoutParams.width=view_open.getMeasuredWidth();
//                layoutParams.height=view_open.getMeasuredHeight();
//                dialogView.setLayoutParams(layoutParams);


//
//
//        dialogView.setX(dialogX);
//        dialogView.setY(dialogY);

//        doAnimation(dialogView, dialogX, dialogY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, "onWindowFocusChanged: ");
        dialogView = View.inflate(ZoomDialogActivity.this, R.layout.layout_login, null);
        ll_root.addView(dialogView);
        dialogView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        measuredDialogWidth = dialogView.getMeasuredWidth();
        measuredDialogHeight = dialogView.getMeasuredHeight();
        Log.e(TAG, "measuredDialogHeight: "+measuredDialogHeight );
        dialogX = getDialogX(ll_root, dialogView);
        dialogY = getDialogY(ll_root, dialogView);


        tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        et_name = (EditText) dialogView.findViewById(R.id.et_name);
        et_password = (EditText) dialogView.findViewById(R.id.et_password);
        confirm = (Button) dialogView.findViewById(R.id.confirm);
        et_name.setVisibility(View.GONE);
        et_password.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doAnimation(dialogView, dialogX, dialogY);

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnimation(dialogView, 0, 0);
            }
        });
    }

    private void doAnimation(final View dialogView, int dialogX, int dialogY) {
//        et_name.setVisibility(View.VISIBLE);
//        et_password.setVisibility(View.VISIBLE);
//        confirm.setVisibility(View.VISIBLE);
        ValueAnimator heightAnimator = ValueAnimator.ofInt(measuredDialogHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "height: " + (int) animation.getAnimatedValue());
                ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
                layoutParams.height= (int) animation.getAnimatedValue();
                dialogView.setLayoutParams(layoutParams);
            }
        });
        ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(dialogView, View.X, dialogX);
        ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(dialogView, View.Y, dialogY);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(dialogView, View.SCALE_Y, 1.0f);
        moveYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.e(TAG, "onAnimationUpdate: " + ((float) animation.getAnimatedValue()));
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(moveXAnimator, moveYAnimator,scaleYAnimator,heightAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    private int getDialogX(View rootView, View view) {
        Log.e(TAG, "rootView.getMeasuredWidth(): " + rootView.getMeasuredWidth());
        Log.e(TAG, "view.getMeasuredWidth(): " + view.getMeasuredWidth());
        return (rootView.getMeasuredWidth() - view.getMeasuredWidth()) / 2;
    }

    private int getDialogY(View rootView, View view) {
        Log.e(TAG, "rootView.getMeasuredHeight(): " + rootView.getMeasuredHeight());
        Log.e(TAG, "view.getMeasuredHeight(): " + view.getMeasuredHeight());
        return (rootView.getMeasuredHeight() - view.getMeasuredHeight()) / 2;
    }
}
