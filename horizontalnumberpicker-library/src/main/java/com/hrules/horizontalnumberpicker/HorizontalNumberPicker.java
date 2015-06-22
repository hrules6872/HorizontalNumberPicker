package com.hrules.horizontalnumberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HorizontalNumberPicker extends LinearLayout {
    private final static int DEFAULT_VALUE = 0;
    private final static int DEFAULT_MIN_VALUE = 0;
    private final static int DEFAULT_MAX_VALUE = 999;
    private final static int DEFAULT_STEP_SIZE = 1;
    private final static boolean DEFAULT_SHOW_LEADING_ZEROS = false;

    private final static int MIN_UPDATE_INTERVAL = 50;
    private final static int DEFAULT_UPDATE_INTERVAL = 100;

    private final static String DEFAULT_BUTTON_MINUS_TEXT = "-";
    private final static String DEFAULT_BUTTON_PLUS_TEXT = "+";

    private int value;
    private int maxValue;
    private int minValue;
    private int stepSize;
    private boolean showLeadingZeros;

    private Button buttonMinus;
    private Button buttonPlus;
    private TextView textValue;

    private boolean autoIncrement;
    private boolean autoDecrement;

    private int updateInterval;

    private Handler updateIntervalHandler;

    private HorizontalNumberPickerListener listener;

    public HorizontalNumberPicker(Context context) {
        this(context, null);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context);
            parseAttrs(context, attrs);
        }
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalNumberPicker);
        if (typedArray == null) {
            return;
        }

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.HorizontalNumberPicker_plusButtonText) {
                buttonPlus.setText(typedArray.getString(attr));

            } else if (attr == R.styleable.HorizontalNumberPicker_minusButtonText) {
                buttonMinus.setText(typedArray.getString(attr));

            } else if (attr == R.styleable.HorizontalNumberPicker_minValue) {
                minValue = typedArray.getInt(attr, DEFAULT_MIN_VALUE);

            } else if (attr == R.styleable.HorizontalNumberPicker_maxValue) {
                maxValue = typedArray.getInt(attr, DEFAULT_MAX_VALUE);

            } else if (attr == R.styleable.HorizontalNumberPicker_value) {
                value = typedArray.getInt(attr, DEFAULT_VALUE);
                this.setValue();

            } else if (attr == R.styleable.HorizontalNumberPicker_repeatDelay) {
                updateInterval = typedArray.getInt(attr, DEFAULT_UPDATE_INTERVAL);

            } else if (attr == R.styleable.HorizontalNumberPicker_stepSize) {
                stepSize = typedArray.getInt(attr, DEFAULT_STEP_SIZE);

            } else if (attr == R.styleable.HorizontalNumberPicker_showLeadingZeros) {
                showLeadingZeros = typedArray.getBoolean(attr, DEFAULT_SHOW_LEADING_ZEROS);
            }
        }
        typedArray.recycle();
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.horizontal_number_picker, this);


        stepSize = DEFAULT_STEP_SIZE;
        minValue = DEFAULT_MIN_VALUE;
        maxValue = DEFAULT_MAX_VALUE;
        showLeadingZeros = DEFAULT_SHOW_LEADING_ZEROS;

        autoIncrement = false;
        autoDecrement = false;

        updateInterval = DEFAULT_UPDATE_INTERVAL;
        updateIntervalHandler = new Handler();

        initButtonMinus();
        initButtonPlus();
        initTextValue();

        value = DEFAULT_VALUE;
        this.setValue();
    }

    private void initTextValue() {
        textValue = (TextView) findViewById(R.id.text_value);
    }

    private void initButtonPlus() {
        buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonPlus.setText(DEFAULT_BUTTON_PLUS_TEXT);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment();
            }
        });

        buttonPlus.setOnLongClickListener(
            new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    autoIncrement = true;
                    updateIntervalHandler.post(new repeat());
                    return false;
                }
            }
        );

        buttonPlus.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement) {
                    autoIncrement = false;
                }
                return false;
            }
        });
    }

    private void initButtonMinus() {
        buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMinus.setText(DEFAULT_BUTTON_MINUS_TEXT);

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                decrement();
            }
        });

        buttonMinus.setOnLongClickListener(
            new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    autoDecrement = true;
                    updateIntervalHandler.post(new repeat());
                    return false;
                }
            }
        );

        buttonMinus.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement) {
                    autoDecrement = false;
                }
                return false;
            }
        });
    }

    public Button getButtonMinusView() {
        return buttonMinus;
    }

    public Button getButtonPlusView() {
        return buttonPlus;
    }

    public TextView getTextValueView() {
        return textValue;
    }

    public void increment() {
        if (value < maxValue) {
            this.setValue(value + stepSize);
        }
    }

    public void decrement() {
        if (value > minValue) {
            this.setValue(value - stepSize);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value > maxValue) {
            value = maxValue;
        }
        if (value < minValue) {
            value = minValue;
        }

        this.value = value;
        this.setValue();
    }

    private void setValue() {
        String formatter = "%0" + String.valueOf(maxValue).length() + "d";
        textValue.setText(showLeadingZeros ? String.format(formatter, value) : String.valueOf(value));
        if (listener != null) {
            listener.onHorizontalNumberPickerChanged(this, value);
        }
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (maxValue < value) {
            value = maxValue;
            this.setValue();
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (minValue > value) {
            value = minValue;
            this.setValue();
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    public void setShowLeadingZeros(boolean showLeadingZeros) {
        this.showLeadingZeros = showLeadingZeros;

        String formatter = "%0" + String.valueOf(maxValue).length() + "d";
        textValue.setText(showLeadingZeros ? String.format(formatter, value) : String.valueOf(value));
    }

    public long getOnLongPressUpdateInterval() {
        return updateInterval;
    }

    public void setOnLongPressUpdateInterval(int intervalMillis) {
        if (intervalMillis < MIN_UPDATE_INTERVAL) {
            intervalMillis = MIN_UPDATE_INTERVAL;
        }
        this.updateInterval = intervalMillis;
    }

    public void setListener(HorizontalNumberPickerListener listener) {
        this.listener = listener;
    }

    class repeat implements Runnable {
        public void run() {
            if (autoIncrement) {
                increment();
                updateIntervalHandler.postDelayed(new repeat(), updateInterval);
            } else if (autoDecrement) {
                decrement();
                updateIntervalHandler.postDelayed(new repeat(), updateInterval);
            }
        }
    }
}
