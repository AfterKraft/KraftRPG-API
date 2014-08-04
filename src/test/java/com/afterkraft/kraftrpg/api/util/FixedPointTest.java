package com.afterkraft.kraftrpg.api.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FixedPointTest {
    private static final double exact = 0;
    private static final double ulp = FixedPoint.ulp;
    private static final double ulp2 = 2 * ulp;

    @Test
    public void testValueConversions() {
        FixedPoint num;

        num = FixedPoint.valueOf(0);
        assertEquals(0, num.intValue());
        assertEquals(0, num.longValue());
        assertEquals(0, num.floatValue(), exact);
        assertEquals(0, num.doubleValue(), exact);

        num = FixedPoint.valueOf(1);
        assertEquals(1, num.intValue());
        assertEquals(1, num.longValue());
        assertEquals(1, num.floatValue(), exact);
        assertEquals(1, num.doubleValue(), exact);

        num = FixedPoint.valueOf(1.00123);
        assertEquals(1, num.intValue());
        assertEquals(1, num.longValue());
        assertEquals(1.00123, num.floatValue(), ulp);
        assertEquals(1.00123, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(600.00123);
        assertEquals(600, num.intValue());
        assertEquals(600, num.longValue());
        assertEquals(600.00123, num.floatValue(), ulp);
        assertEquals(600.00123, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(-600.00123);
        assertEquals(-601, num.intValue());
        assertEquals(-601, num.longValue());
        assertEquals(-600.00123, num.floatValue(), ulp);
        assertEquals(-600.00123, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, num.intValue());
        assertEquals((long) Integer.MAX_VALUE, num.longValue());
        assertEquals((float) Integer.MAX_VALUE, num.floatValue(), ulp);
        assertEquals((double) Integer.MAX_VALUE, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(FixedPoint.MAX_VALUE);
        // assertEquals(??, num.intValue());
        assertEquals((long) FixedPoint.MAX_VALUE, num.longValue());
        assertEquals((float) FixedPoint.MAX_VALUE, num.floatValue(), ulp);
        assertEquals((double) FixedPoint.MAX_VALUE, num.doubleValue(), ulp);

        double temp = FixedPoint.MAX_VALUE + FixedPoint.ulp;

        // Test: truncated at max

        num = FixedPoint.valueOf(temp);
        // assertEquals(??, num.intValue());
        assertEquals((long) FixedPoint.MAX_VALUE, num.longValue());
        assertEquals((float) FixedPoint.MAX_VALUE, num.floatValue(), ulp);
        assertEquals((double) FixedPoint.MAX_VALUE, num.doubleValue(), ulp);
    }

    @Test
    public void addFixedPoints() {
        FixedPoint num, num2;

        num = FixedPoint.valueOf(5);
        num2 = FixedPoint.valueOf(7);
        num = num.add(num2);
        assertEquals(12, num.doubleValue(), exact);

        num2 = FixedPoint.valueOf(-30);
        num = num.add(num2);
        assertEquals(-18, num.doubleValue(), exact);

        num = FixedPoint.valueOf(5);
        num = num.add(7);
        assertEquals(12, num.doubleValue(), exact);
        num = num.add(-30);
        assertEquals(-18, num.doubleValue(), exact);
    }

    @Test
    public void addDoubles() {
        FixedPoint num, num2;

        // Adding doubles
        num = FixedPoint.valueOf(2.125);

        num = num.add(3D);
        assertEquals(5.125, num.doubleValue(), ulp);
        num = num.add(0.1);
        assertEquals(5.225, num.doubleValue(), ulp);
        num = num.add(0.1);
        assertEquals(5.325, num.doubleValue(), ulp);
        num = num.add(0.1);
        assertEquals(5.425, num.doubleValue(), ulp2);
        num = num.add(0.1);
        assertEquals(5.525, num.doubleValue(), ulp2);
        num = num.add(0.1);
        assertEquals(5.625, num.doubleValue(), ulp2);

        num = num.add(30.3);
        assertEquals(35.925, num.doubleValue(), ulp * 3);

        num2 = FixedPoint.valueOf(16777216f);
        num = num.add(num2);
        assertEquals(16777216 + 35.925, num.doubleValue(), ulp * 3);
    }

    @Test
    public void addingUlps() {
        FixedPoint num = FixedPoint.valueOf(123.456);

        // Adding an ulp changes the Raw by one
        long prevRaw = num.rawValue();

        num = num.add(ulp);
        assertEquals(++prevRaw, num.rawValue());
        assertEquals(123.456 + ulp, num.doubleValue(), ulp / 2);

        num = num.add(ulp);
        assertEquals(++prevRaw, num.rawValue());
        assertEquals(123.456 + ulp + ulp, num.doubleValue(), ulp / 2);

        num = num.add(ulp);
        assertEquals(++prevRaw, num.rawValue());
        assertEquals(123.456 + ulp + ulp + ulp, num.doubleValue(), ulp / 2);

        // Adding less than half an ulp stays the same
        num = num.add(ulp / 8 * 3);
        assertEquals(prevRaw, num.rawValue());

        // Adding more than half an ulp increases
        num = num.add(ulp / 8 * 5);
        assertEquals(++prevRaw, num.rawValue());
    }

    @Test
    public void subFixedPoints() {
        FixedPoint num, num2;

        num = FixedPoint.valueOf(5);
        num2 = FixedPoint.valueOf(7);
        num = num.sub(num2);
        assertEquals(-2, num.doubleValue(), exact);

        num2 = FixedPoint.valueOf(-30);
        num = num.sub(num2);
        assertEquals(28, num.doubleValue(), exact);

        num = FixedPoint.valueOf(5);
        num = num.sub(7);
        assertEquals(-2, num.doubleValue(), exact);
        num = num.sub(-30);
        assertEquals(28, num.doubleValue(), exact);
    }

    @Test
    public void multFixedPoints() {
        FixedPoint num, num2;

        num = FixedPoint.valueOf(5);
        num2 = FixedPoint.valueOf(7);
        num = num.mult(num2);
        assertEquals(35, num.doubleValue(), exact);

        num2 = FixedPoint.valueOf(-30);
        num = num.mult(num2);
        assertEquals(-1050, num.doubleValue(), exact);

        num = FixedPoint.valueOf(5);
        num = num.mult(7);
        assertEquals(35, num.doubleValue(), exact);
        num = num.mult(-30);
        assertEquals(-1050, num.doubleValue(), exact);
    }

    @Test
    public void divFixedPoints() {
        FixedPoint num, num2;

        num = FixedPoint.valueOf(-5);
        num = num.div(-5);
        assertEquals(1, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(5);
        num = num.div(num);
        assertEquals(1, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(5);
        num2 = FixedPoint.valueOf(7);
        num = num.div(num2);
        assertEquals(5 / 7.0, num.doubleValue(), ulp);

        num = FixedPoint.valueOf(-30);
        num = num.mult(7.0).div(5);
        assertEquals(-30 * 7 / 5.0, num.doubleValue(), ulp);
    }
}
