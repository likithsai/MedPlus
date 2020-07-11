package com.example.numberpicker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class ArrowNumberPicker extends FrameLayout {


    private EditText tvPicker;
    private ImageButton tvPlus, tvMinus;
    private int selectedValue = 0, maxValue = 0;
    private Context context;
    // listener -->
    private PickerChangeListener listener;



    public interface PickerChangeListener {
        void onPickerChanged(int newValue);
    }




    public ArrowNumberPicker(Context context) {
        super(context);
    }


    public ArrowNumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        this.listener = null;
    }


    public ArrowNumberPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    private void init(Context context, @Nullable AttributeSet attrs){
        this.context = context;
        LayoutInflater layoutInflater =  LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.arrow_number_picker, this);

        tvPicker = findViewById(R.id.tv_picker);
        tvPlus = findViewById(R.id.tv_plus);
        tvMinus = findViewById(R.id.tv_minus);

//        setAttrs(attrs);

        tvPicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    selectedValue = Integer.parseInt(s.toString());

                    if (listener != null) {
                        listener.onPickerChanged(selectedValue);
                    }
                }
            }
        });

        tvPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( selectedValue <= maxValue ) {
                    selectedValue++;
                    updateCounter();
                }

                performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            }
        });

        tvMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedValue > 0){
                    selectedValue--;
                    updateCounter();
                }

                performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            }
        });
    }

//    private void setAttrs(@Nullable AttributeSet attrs){
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArrowNumberPicker, 0, 0);
//        a.recycle();
//    }


    private void updateCounter(){
        tvPicker.setText(String.valueOf(selectedValue));
        if(listener != null){
            listener.onPickerChanged(selectedValue);
        }
    }



    public int getValue(){
        return selectedValue;
    }


    public void setValue(int newValue){
        this.selectedValue = newValue;
        updateCounter();
    }



    public void setMaxValue(int value) {
        this.maxValue = value;
        updateCounter();
    }





    public void setPickerChangedListener(PickerChangeListener l){
        listener = l;
    }
    // listener <--


}
