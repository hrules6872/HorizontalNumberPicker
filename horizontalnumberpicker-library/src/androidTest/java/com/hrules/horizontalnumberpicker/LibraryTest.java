package com.hrules.horizontalnumberpicker;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import org.junit.Before;

public class LibraryTest extends AndroidTestCase {
    Context context;

    @Before
    public void setUp() {
        context = this.getContext();
    }

    @SmallTest
    public void testDefaultGetButtonMinusView() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        assertTrue(horizontalNumberPicker.getButtonMinusView().getText().toString().equals("-"));
    }

    @SmallTest
    public void testDefaultGetButtonPlusView() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        assertTrue(horizontalNumberPicker.getButtonPlusView().getText().toString().equals("+"));
    }

    @SmallTest
    public void testDefaultGetTextValueView() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        assertTrue(horizontalNumberPicker.getTextValueView().getText().toString().equals("0"));
    }

    @SmallTest
    public void testDefaultStepSize() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        assertTrue(horizontalNumberPicker.getStepSize() == 1);
    }

    @SmallTest
    public void testDecrementFromZero() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.decrement();
        assertTrue(horizontalNumberPicker.getValue() == 0);
    }

    @SmallTest
    public void testIncrementFromZero() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.increment();
        assertTrue(horizontalNumberPicker.getValue() == 1);
    }

    @SmallTest
    public void testDecrementBigStep() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setValue(horizontalNumberPicker.getMaxValue());
        horizontalNumberPicker.setStepSize(horizontalNumberPicker.getMaxValue() * 2);
        horizontalNumberPicker.decrement();
        assertTrue(horizontalNumberPicker.getValue() == 0);
    }

    @SmallTest
    public void testIncrementBigStep() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setValue(horizontalNumberPicker.getMinValue());
        horizontalNumberPicker.setStepSize(horizontalNumberPicker.getMaxValue() * 2);
        horizontalNumberPicker.increment();
        assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMaxValue());
    }

    @SmallTest
    public void testSetValueMax() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setValue(horizontalNumberPicker.getMaxValue() * 2);
        assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMaxValue());
    }

    @SmallTest
    public void testSetValueMin() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setValue(horizontalNumberPicker.getMinValue() - 1);
        assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMinValue());
    }

    @SmallTest
    public void testSetInvalidMinValue() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setMinValue(horizontalNumberPicker.getMinValue() + 1);
        assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMinValue());
    }

    @SmallTest
    public void testSetInvalidMaxValue() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setValue(horizontalNumberPicker.getMaxValue());
        horizontalNumberPicker.setMaxValue(horizontalNumberPicker.getMaxValue() - 1);
        assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMaxValue());
    }

    @SmallTest
    public void testShowLeadingZeros() {
        HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
        horizontalNumberPicker.setMaxValue(99);
        horizontalNumberPicker.setShowLeadingZeros(true);
        assertTrue(horizontalNumberPicker.getTextValueView().getText().toString().equals("00"));
    }
}
