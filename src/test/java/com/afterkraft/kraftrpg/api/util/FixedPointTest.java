package com.afterkraft.kraftrpg.api.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class FixedPointTest {
    private static final double exact = 0;
    private static final double ulp = FixedPoint.ulp;
    private static final double ulp2 = 2 * ulp;

    @Test
    public void testValueConversions() {
        FixedPoint num;

        num = new FixedPoint(0);
        assertEquals(0, num.intValue());
        assertEquals(0, num.longValue());
        assertEquals(0, num.floatValue(), exact);
        assertEquals(0, num.doubleValue(), exact);

        num = new FixedPoint(1);
        assertEquals(1, num.intValue());
        assertEquals(1, num.longValue());
        assertEquals(1, num.floatValue(), exact);
        assertEquals(1, num.doubleValue(), exact);

        num = new FixedPoint(1.00123);
        assertEquals(1, num.intValue());
        assertEquals(1, num.longValue());
        assertEquals(1.00123, num.floatValue(), ulp);
        assertEquals(1.00123, num.doubleValue(), ulp);

        num = new FixedPoint(600.00123);
        assertEquals(600, num.intValue());
        assertEquals(600, num.longValue());
        assertEquals(600.00123, num.floatValue(), ulp);
        assertEquals(600.00123, num.doubleValue(), ulp);

        num = new FixedPoint(-600.00123);
        assertEquals(-601, num.intValue());
        assertEquals(-601, num.longValue());
        assertEquals(-600.00123, num.floatValue(), ulp);
        assertEquals(-600.00123, num.doubleValue(), ulp);

        num = new FixedPoint(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, num.intValue());
        assertEquals((long) Integer.MAX_VALUE, num.longValue());
        assertEquals((float) Integer.MAX_VALUE, num.floatValue(), ulp);
        assertEquals((double) Integer.MAX_VALUE, num.doubleValue(), ulp);

        num = new FixedPoint(FixedPoint.MAX_VALUE);
        // assertEquals(??, num.intValue());
        assertEquals((long) FixedPoint.MAX_VALUE, num.longValue());
        assertEquals((float) FixedPoint.MAX_VALUE, num.floatValue(), ulp);
        assertEquals((double) FixedPoint.MAX_VALUE, num.doubleValue(), ulp);

        double temp = FixedPoint.MAX_VALUE + FixedPoint.ulp;

        // Truncated at max

        num = new FixedPoint(temp);
        // assertEquals(??, num.intValue());
        assertEquals((long) FixedPoint.MAX_VALUE, num.longValue());
        assertEquals((float) FixedPoint.MAX_VALUE, num.floatValue(), ulp);
        assertEquals((double) FixedPoint.MAX_VALUE, num.doubleValue(), ulp);
    }

    @Test
    public void addFixedPoints() {
        FixedPoint num, num2;

        num = new FixedPoint(5);
        num2 = new FixedPoint(7);
        num.add(num2);
        assertEquals(12, num.doubleValue(), exact);

        num2 = new FixedPoint(-30);
        num.add(num2);
        assertEquals(-18, num.doubleValue(), exact);

        num = new FixedPoint(5);
        num.add(7);
        assertEquals(12, num.doubleValue(), exact);
        num.add(-30);
        assertEquals(-18, num.doubleValue(), exact);
    }

    @Test
    public void addDoubles() {
        FixedPoint num, num2;

        // Adding doubles
        num = new FixedPoint(2.125);

        num.add(3D);
        assertEquals(5.125, num.doubleValue(), ulp);
        num.add(0.1);
        assertEquals(5.225, num.doubleValue(), ulp);
        num.add(0.1);
        assertEquals(5.325, num.doubleValue(), ulp);
        num.add(0.1);
        assertEquals(5.425, num.doubleValue(), ulp2);
        num.add(0.1);
        assertEquals(5.525, num.doubleValue(), ulp2);
        num.add(0.1);
        assertEquals(5.625, num.doubleValue(), ulp2);

        num.add(30.3);
        assertEquals(35.925, num.doubleValue(), ulp * 3);

        num2 = new FixedPoint(16777216f);
        num.add(num2);
        assertEquals(16777216 + 35.925, num.doubleValue(), ulp * 3);
    }

    @Test
    public void addingUlps() {
        FixedPoint num = new FixedPoint(123.456);

        // Adding an ulp changes the Raw by one
        long prevRaw = num.rawValue();

        num.add(ulp);
        assertEquals(++prevRaw, num.rawValue());
        assertEquals(123.456 + ulp, num.doubleValue(), ulp / 2);

        num.add(ulp);
        assertEquals(++prevRaw, num.rawValue());
        assertEquals(123.456 + ulp + ulp, num.doubleValue(), ulp / 2);

        num.add(ulp);
        assertEquals(++prevRaw, num.rawValue());
        assertEquals(123.456 + ulp + ulp + ulp, num.doubleValue(), ulp / 2);

        // Adding half an ulp either increases or stays the same
        num.add(ulp / 2);
        if (num.rawValue() != prevRaw && num.rawValue() != prevRaw + 1) {
            fail();
        }

        // Adding a quarter of an ulp stays the same
        prevRaw = num.rawValue();
        num.add(ulp / 4);
        assertEquals(prevRaw, num.rawValue());
    }

    @Test
    public void subFixedPoints() {
        FixedPoint num, num2;

        num = new FixedPoint(5);
        num2 = new FixedPoint(7);
        num.sub(num2);
        assertEquals(-2, num.doubleValue(), exact);

        num2 = new FixedPoint(-30);
        num.sub(num2);
        assertEquals(28, num.doubleValue(), exact);

        num = new FixedPoint(5);
        num.sub(7);
        assertEquals(-2, num.doubleValue(), exact);
        num.sub(-30);
        assertEquals(28, num.doubleValue(), exact);
    }

    @Test
    public void multFixedPoints() {
        FixedPoint num, num2;

        num = new FixedPoint(5);
        num2 = new FixedPoint(7);
        num.mult(num2);
        assertEquals(35, num.doubleValue(), exact);

        num2 = new FixedPoint(-30);
        num.mult(num2);
        assertEquals(-1050, num.doubleValue(), exact);

        num = new FixedPoint(5);
        num.mult(7);
        assertEquals(35, num.doubleValue(), exact);
        num.mult(-30);
        assertEquals(-1050, num.doubleValue(), exact);
    }

    @Test
    public void divFixedPoints() {
        FixedPoint num, num2;

        num = new FixedPoint(-5);
        num.div(-5);
        assertEquals(1, num.doubleValue(), ulp);

        num = new FixedPoint(5);
        num.div(num);
        assertEquals(1, num.doubleValue(), ulp);

        num = new FixedPoint(5);
        num2 = new FixedPoint(7);
        num.div(num2);
        assertEquals(5 / 7.0, num.doubleValue(), ulp);

        num = new FixedPoint(-30);
        num.div(5 / 7.0);
        assertEquals(-30 * 7 / 5.0, num.doubleValue(), ulp);
    }
}
