package com.hrules.horizontalnumberpicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HorizontalNumberPicker extends LinearLayout {
  private final static int MIN_UPDATE_INTERVAL = 50;

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
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public HorizontalNumberPicker(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (isInEditMode()) {
      return;
    }

    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    layoutInflater.inflate(R.layout.horizontal_number_picker, this);

    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.HorizontalNumberPicker);
    Resources res = getResources();

    String buttonPlusText = typedArray.getString(R.styleable.HorizontalNumberPicker_plusButtonText);
    initButtonPlus(
        buttonPlusText != null ? buttonPlusText : res.getString(R.string.defaultButtonPlus));

    String buttonMinusText =
        typedArray.getString(R.styleable.HorizontalNumberPicker_minusButtonText);
    initButtonMinus(
        buttonMinusText != null ? buttonMinusText : res.getString(R.string.defaultButtonMinus));

    minValue = typedArray.getInt(R.styleable.HorizontalNumberPicker_minValue,
        res.getInteger(R.integer.default_minValue));
    maxValue = typedArray.getInt(R.styleable.HorizontalNumberPicker_maxValue,
        res.getInteger(R.integer.default_maxValue));

    updateInterval = typedArray.getInt(R.styleable.HorizontalNumberPicker_repeatDelay,
        res.getInteger(R.integer.default_updateInterval));
    stepSize = typedArray.getInt(R.styleable.HorizontalNumberPicker_stepSize,
        res.getInteger(R.integer.default_stepSize));
    showLeadingZeros = typedArray.getBoolean(R.styleable.HorizontalNumberPicker_showLeadingZeros,
        res.getBoolean(R.bool.default_showLeadingZeros));

    initTextValue();
    value = typedArray.getInt(R.styleable.HorizontalNumberPicker_value,
        res.getInteger(R.integer.default_value));
    typedArray.recycle();

    this.setValue();

    autoIncrement = false;
    autoDecrement = false;

    updateIntervalHandler = new Handler();
  }

  private void initTextValue() {
    textValue = (TextView) findViewById(R.id.text_value);
  }

  private void initButtonPlus(String text) {
    buttonPlus = (Button) findViewById(R.id.button_plus);
    buttonPlus.setText(text);

    buttonPlus.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        increment();
      }
    });

    buttonPlus.setOnLongClickListener(new View.OnLongClickListener() {
      public boolean onLongClick(View v) {
        autoIncrement = true;
        updateIntervalHandler.post(new repeat());
        return false;
      }
    });

    buttonPlus.setOnTouchListener(new View.OnTouchListener() {
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement) {
          autoIncrement = false;
        }
        return false;
      }
    });
  }

  private void initButtonMinus(String text) {
    buttonMinus = (Button) findViewById(R.id.button_minus);
    buttonMinus.setText(text);

    buttonMinus.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        decrement();
      }
    });

    buttonMinus.setOnLongClickListener(new View.OnLongClickListener() {
      public boolean onLongClick(View v) {
        autoDecrement = true;
        updateIntervalHandler.post(new repeat());
        return false;
      }
    });

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

  public int getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(int maxValue) {
    this.maxValue = maxValue;
    if (maxValue < value) {
      value = maxValue;
      this.setValue();
    }
  }

  public int getMinValue() {
    return minValue;
  }

  public void setMinValue(int minValue) {
    this.minValue = minValue;
    if (minValue > value) {
      value = minValue;
      this.setValue();
    }
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

  private class repeat implements Runnable {
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
