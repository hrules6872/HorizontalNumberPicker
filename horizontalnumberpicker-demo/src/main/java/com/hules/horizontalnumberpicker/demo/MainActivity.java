package com.hules.horizontalnumberpicker.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HorizontalNumberPicker horizontalNumberPicker2 = (HorizontalNumberPicker) findViewById(R.id.horizontal_number_picker2);
        HorizontalNumberPicker horizontalNumberPicker3 = (HorizontalNumberPicker) findViewById(R.id.horizontal_number_picker3);

        horizontalNumberPicker2.getButtonMinusView().setText("<");
        horizontalNumberPicker2.getButtonPlusView().setText(">");
        horizontalNumberPicker2.setShowLeadingZeros(true);
        horizontalNumberPicker2.setValue(23);

        horizontalNumberPicker3.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        horizontalNumberPicker3.getTextValueView().setTextColor(getResources().getColor(android.R.color.holo_red_light));
        horizontalNumberPicker3.getTextValueView().setTextSize(22);

    }
}
