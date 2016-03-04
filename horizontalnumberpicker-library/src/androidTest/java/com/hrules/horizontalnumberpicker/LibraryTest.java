package com.hrules.horizontalnumberpicker;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

public class LibraryTest extends AndroidTestCase {
  // TODO: rewrite tests

  @SmallTest public void testDefaultGetButtonMinusView() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    assertTrue(horizontalNumberPicker.getButtonMinusView().getText().toString().equals("-"));
  }

  @SmallTest public void testDefaultGetButtonPlusView() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    assertTrue(horizontalNumberPicker.getButtonPlusView().getText().toString().equals("+"));
  }

  @SmallTest public void testDefaultGetTextValueView() {
    Context context = this.getContext();

    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    assertTrue(horizontalNumberPicker.getTextValueView().getText().toString().equals("0"));
  }

  @SmallTest public void testDefaultStepSize() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    assertTrue(horizontalNumberPicker.getStepSize() == 1);
  }

  @SmallTest public void testDecrementFromZero() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.decrement();
    assertTrue(horizontalNumberPicker.getValue() == 0);
  }

  @SmallTest public void testIncrementFromZero() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.increment();
    assertTrue(horizontalNumberPicker.getValue() == 1);
  }

  @SmallTest public void testDecrementBigStep() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setValue(horizontalNumberPicker.getMaxValue());
    horizontalNumberPicker.setStepSize(horizontalNumberPicker.getMaxValue() * 2);
    horizontalNumberPicker.decrement();
    assertTrue(horizontalNumberPicker.getValue() == 0);
  }

  @SmallTest public void testIncrementBigStep() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setValue(horizontalNumberPicker.getMinValue());
    horizontalNumberPicker.setStepSize(horizontalNumberPicker.getMaxValue() * 2);
    horizontalNumberPicker.increment();
    assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMaxValue());
  }

  @SmallTest public void testSetValueMax() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setValue(horizontalNumberPicker.getMaxValue() * 2);
    assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMaxValue());
  }

  @SmallTest public void testSetValueMin() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setValue(horizontalNumberPicker.getMinValue() - 1);
    assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMinValue());
  }

  @SmallTest public void testSetInvalidMinValue() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setMinValue(horizontalNumberPicker.getMinValue() + 1);
    assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMinValue());
  }

  @SmallTest public void testSetInvalidMaxValue() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setValue(horizontalNumberPicker.getMaxValue());
    horizontalNumberPicker.setMaxValue(horizontalNumberPicker.getMaxValue() - 1);
    assertTrue(horizontalNumberPicker.getValue() == horizontalNumberPicker.getMaxValue());
  }

  @SmallTest public void testShowLeadingZeros() {
    Context context = this.getContext();
    HorizontalNumberPicker horizontalNumberPicker = new HorizontalNumberPicker(context);
    horizontalNumberPicker.setMaxValue(99);
    horizontalNumberPicker.setShowLeadingZeros(true);
    assertTrue(horizontalNumberPicker.getTextValueView().getText().toString().equals("00"));
  }
}
