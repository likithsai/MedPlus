package com.likith.lockview;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Stack;

public class LockView extends FrameLayout implements ButtonView.OnPressListener {

    private Password type = Password.NUMBER;
    private int passwordLength = 4;
//    private String correctPassword = null;
    private boolean animationIsPlaying = false;
    private Stack<String> passwordStack = null;
    private TextView title;
    private Indicator indicator;
    private ButtonView[] bigButtonViews;
    private ImageView rightButton;
    private OnLeftButtonClickListener onLeftButtonClickListener;
    private OnPasswordInputListener onPasswordInputListener;

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    /**
     * Init.
     */
    private void init() {
        // number password
        LayoutInflater.from(getContext()).inflate(R.layout.lockview, this, true);

        bigButtonViews = new ButtonView[10];
        bigButtonViews[0] = (ButtonView)findViewById(R.id.button_0);
        bigButtonViews[1] = (ButtonView)findViewById(R.id.button_1);
        bigButtonViews[2] = (ButtonView)findViewById(R.id.button_2);
        bigButtonViews[3] = (ButtonView)findViewById(R.id.button_3);
        bigButtonViews[4] = (ButtonView)findViewById(R.id.button_4);
        bigButtonViews[5] = (ButtonView)findViewById(R.id.button_5);
        bigButtonViews[6] = (ButtonView)findViewById(R.id.button_6);
        bigButtonViews[7] = (ButtonView)findViewById(R.id.button_7);
        bigButtonViews[8] = (ButtonView)findViewById(R.id.button_8);
        bigButtonViews[9] = (ButtonView)findViewById(R.id.button_9);

        String[] texts = getResources().getStringArray(R.array.default_big_button_text);
        for (int i = 0; i < 10; i++) {
            bigButtonViews[i].setOnPressListener(this);
            bigButtonViews[i].setText(texts[i]);
        }


        // get screen width
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int buttonHorizontalMargin = 6;
        int buttonVerticalMargin = 24;
        int buttonWidth = (width - 11 * buttonHorizontalMargin) / 10;


        passwordStack = new Stack<>();
        Resources resources = getResources();

        indicator = (Indicator)findViewById(R.id.indicator);
        indicator.setPasswordLength(passwordLength);

        title = (TextView)findViewById(R.id.title);
        title.setTextColor(ContextCompat.getColor(getContext(), R.color.default_title_text_color));
        title.setTextSize(resources.getInteger(R.integer.default_title_text_size));


        rightButton = (ImageView) findViewById(R.id.right_button);
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordStack.size() > 0) {
                    passwordStack.pop();
                    indicator.delete();
                }
            }
        });
    }



    /**
     * Show the number keyboard smoothly or not.
     *
     * @param smoothly Smoothly or not.
     */
    private void showNumber(boolean smoothly) {
        if (animationIsPlaying) return;
        animationIsPlaying = true;
        if (smoothly) {
            ObjectAnimator.ofFloat(findViewById(R.id.layout_123), "alpha", 0f, 1f)
                    .setDuration(500).start();
            ObjectAnimator.ofFloat(findViewById(R.id.layout_456), "alpha", 0f, 1f)
                    .setDuration(500).start();
            ObjectAnimator.ofFloat(findViewById(R.id.layout_789), "alpha", 0f, 1f)
                    .setDuration(500).start();
            ObjectAnimator.ofFloat(findViewById(R.id.button_0), "alpha", 0f, 1f)
                    .setDuration(500).start();
        } else {
            findViewById(R.id.layout_123).setVisibility(VISIBLE);
            findViewById(R.id.layout_456).setVisibility(VISIBLE);
            findViewById(R.id.layout_789).setVisibility(VISIBLE);
            findViewById(R.id.button_0).setVisibility(VISIBLE);
            animationIsPlaying = false;
        }
    }

    /**
     * Set the listener.
     *
     * @param onLeftButtonClickListener Listener.
     */
    public void setOnLeftButtonClickListener(OnLeftButtonClickListener onLeftButtonClickListener) {
        this.onLeftButtonClickListener = onLeftButtonClickListener;
    }

    /**
     * Set the listener.
     *
     * @param onPasswordInputListener Listener.
     */
    public void setOnPasswordInputListener(OnPasswordInputListener onPasswordInputListener) {
        this.onPasswordInputListener = onPasswordInputListener;
    }

    /**
     * From the button views.
     *
     * @param string The string from button views.
     */
    @Override
    public void onPress(String string) {
//        if (correctPassword == null) {
//            throw new RuntimeException("The correct password has NOT been set!");
//        }

        if (passwordStack.size() >= passwordLength) return;
        passwordStack.push(string);
        indicator.add();

        StringBuilder nowPassword = new StringBuilder();
        for (String s : passwordStack) {
            nowPassword.append(s);
        }

        String nowPasswordString = nowPassword.toString();
        if (onPasswordInputListener != null)
            onPasswordInputListener.input(nowPasswordString);

//        if (correctPassword.equals(nowPasswordString)) {
//
//            // correct password
//            if (onPasswordInputListener != null)
//                    onPasswordInputListener.correct(nowPasswordString);
//            this.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
//
//        } else {
//            if (correctPassword.length() > nowPasswordString.length()) {
//
//                // input right now
//                if (onPasswordInputListener != null)
//                        onPasswordInputListener.input(nowPasswordString);
//
//            } else {
//
//                // incorrect password
//                if (onPasswordInputListener != null)
//                    onPasswordInputListener.incorrect(nowPasswordString);
//
                this.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
//                // perform the clear animation
//                indicator.clear();
//                passwordStack.clear();
//
//            }
//        }
    }

    /**
     * Prevent click 2 or above buttons at the same time.
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            if (Password.NUMBER.equals(type)) {
                for (int i = 0; i < bigButtonViews.length; i++) bigButtonViews[i].clearAnimation();
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * Set big buttons' background.
     *
     * @param id
     */
    public void setBigButtonViewsBackground(int id) {
        for (int i = 0; i < 10; i++) bigButtonViews[i].setBackground(id);
    }

    /**
     * Set big buttons' click effect.
     *
     * @param id
     */
    public void setBigButtonViewsClickEffect(int id) {
        for (int i = 0; i < 10; i++) bigButtonViews[i].setEffect(id);
    }

    /**
     * Set the click effect duration.
     *
     * @param duration
     */
    public void setBigButtonViewsClickEffectDuration(int duration) {
        for (int i = 0; i < 10; i++) bigButtonViews[i].setEffectDuration(duration);
    }



    public void setTextColor(int color) {
        if (type.equals(Password.NUMBER)) {
            for (int i = 0; i < 10; i++) {
                bigButtonViews[i].setTextColor(color);
            }
        }
        title.setTextColor(color);
    }

    /**
     * Set the length of the password.
     * Default length is 4.
     *
     * @param passwordLength
     */
    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        indicator.setPasswordLength(passwordLength);
//        passwordStack.clear();
//        correctPassword = null;
    }


    public int getPasswordLength() {
        return this.passwordLength;
    }

    /**
     * Get the password type.
     *
     * @return
     */
    public Password getType() {
        return type;
    }

    /**
     * Set the password type.
     *
     * @param type Number or text.
     */
    public void setType(Password type, boolean smoothly) {
        if (animationIsPlaying) return;
        this.type = type;
        indicator.clear();
//        passwordStack.clear();
        if (Password.NUMBER.equals(type)) {
            showNumber(smoothly);
        }
    }


    public void clear() {
        indicator.clear();
        passwordStack.clear();
    }

    /**
     * Set the title text.
     *
     * @param string
     */
    public void setTitle(String string) {
        title.setText(string);
    }

//    /**
//     * Set the target password.
//     *
//     * @param correctPassword The target password.
//     */
//    public void setCorrectPassword(String correctPassword) {
//        setPasswordLength(correctPassword.length());
////        this.correctPassword = correctPassword;
//    }


    public interface OnPasswordInputListener {
//        void correct(String inputPassword);
//        void incorrect(String inputPassword);
        void input(String inputPassword);
    }

    public interface OnLeftButtonClickListener {
        void onClick();
    }

    /**
     * Get the title.
     * @return
     */
    public TextView getTitle() {
        return title;
    }

    /**
     * Get the numbers.
     * @return
     */
    public ButtonView[] getBigButtonViews() {
        return bigButtonViews;
    }

}