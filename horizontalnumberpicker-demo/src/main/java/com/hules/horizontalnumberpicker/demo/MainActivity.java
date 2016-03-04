package com.hules.horizontalnumberpicker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;
import com.hrules.horizontalnumberpicker.HorizontalNumberPickerListener;
import com.hules.horizontalnumberpicker.demo.commons.DebugLog;

public class MainActivity extends AppCompatActivity implements HorizontalNumberPickerListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HorizontalNumberPicker horizontalNumberPicker1 = (HorizontalNumberPicker) findViewById(R.id.horizontal_number_picker1);
        HorizontalNumberPicker horizontalNumberPicker2 = (HorizontalNumberPicker) findViewById(R.id.horizontal_number_picker2);
        HorizontalNumberPicker horizontalNumberPicker3 = (HorizontalNumberPicker) findViewById(R.id.horizontal_number_picker3);

        horizontalNumberPicker1.setMaxValue(5);

        horizontalNumberPicker2.getButtonMinusView().setText("<");
        horizontalNumberPicker2.getButtonPlusView().setText(">");
        horizontalNumberPicker2.setShowLeadingZeros(true);
        horizontalNumberPicker2.setValue(23);

        horizontalNumberPicker3.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        horizontalNumberPicker3.getTextValueView().setTextColor(getResources().getColor(android.R.color.holo_red_light));
        horizontalNumberPicker3.getTextValueView().setTextSize(22);

        horizontalNumberPicker1.setListener(this);
        horizontalNumberPicker2.setListener(this);
        horizontalNumberPicker3.setListener(this);
    }

    @Override
    public void onHorizontalNumberPickerChanged(HorizontalNumberPicker horizontalNumberPicker, int value) {
        switch (horizontalNumberPicker.getId()) {
            case R.id.horizontal_number_picker1:
                DebugLog.d("horizontal_number_picker1 current value:" + value);
                break;

            case R.id.horizontal_number_picker2:
                DebugLog.d("horizontal_number_picker2 current value: " + value);
                break;

            case R.id.horizontal_number_picker3:
                DebugLog.d("horizontal_number_picker3 current value: " + value);
                break;
        }
    }
}
