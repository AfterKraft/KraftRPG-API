/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.util;

/**
 * Represents a fixed point value for Champion experience.
 */
public final class FixedPoint extends Number {
    private static final long serialVersionUID = -6313518365999400363L;

    private static final int FRAC_SIZE = 16;
    private static final int ONE = 1 << FRAC_SIZE;
    private static final int HALF = ONE >> 1;

    // 2 ^ -16
    private static final double twoPowNegSize = Math.pow(2, -FRAC_SIZE);

    public static final double ulp = twoPowNegSize;
    public static final double MAX_VALUE = (double) (Long.MAX_VALUE >>> FRAC_SIZE);

    private long val;

    public FixedPoint() {
        val = 0;
    }

    public FixedPoint(double init) {
        val = toFixed(init);
    }

    public FixedPoint(int init) {
        val = (long) init << FRAC_SIZE;
    }

    public FixedPoint(long init, boolean isFixedAlready) {
        if (isFixedAlready) {
            val = init;
        } else {
            val = init << FRAC_SIZE;
        }
    }

    private static long toFixed(double val) {
        return Math.round(val * ONE);
    }

    private static long toFixed(int val) {
        return val * ONE;
    }

    private static long toFixed(long val) {
        return val * ONE;
    }

    public void addRaw(long fixedVal) {
        this.val += fixedVal;
    }

    public void add(int val) {
        addRaw(toFixed(val));
    }

    public void add(long val) {
        addRaw(toFixed(val));
    }

    public void add(double val) {
        addRaw(toFixed(val));
    }

    public void add(FixedPoint val) {
        addRaw(val.val);
    }

    public void subRaw(long fixedVal) {
        this.val -= fixedVal;
    }

    public void sub(int val) {
        subRaw(toFixed(val));
    }

    public void sub(long val) {
        subRaw(toFixed(val));
    }

    public void sub(double val) {
        sub(toFixed(val));
    }

    public void sub(FixedPoint val) {
        subRaw(val.val);
    }

    public void multRaw(long fixedVal) {
        long t = val * fixedVal;
        t += HALF;
        val = (t >> FRAC_SIZE);
    }

    public void mult(int factor) {
        this.val *= factor;
    }

    public void mult(long factor) {
        this.val *= factor;
    }

    public void mult(double factor) {
        this.val *= factor;
    }

    public void mult(FixedPoint val) {
        multRaw(val.val);
    }

    public void divRaw(long fixedVal) {
        long t = val << FRAC_SIZE;
        t += (fixedVal >> 1);
        val = t / fixedVal;
    }

    public void div(int param) {
        this.val /= param;
    }

    public void div(long param) {
        this.val /= param;
    }

    public void div(double param) {
        this.val /= param;
    }

    public void div(FixedPoint param) {
        divRaw(param.val);
    }

    @Override
    public double doubleValue() {
        return (double) val * twoPowNegSize;
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public int intValue() {
        return (int) (val >> FRAC_SIZE);
    }

    @Override
    public long longValue() {
        return val >> FRAC_SIZE;
    }

    public long rawValue() {
        return val;
    }

    @Override
    public String toString() {
        return toString(3);
    }

    /**
     * Converts to string with custom rounding
     *
     * @param maxDecimalPlaces maximum decimal places to display
     * @return String representation of the number
     */
    public String toString(int maxDecimalPlaces) {
        boolean neg = Long.signum(val) == -1;
        long disp = val * (neg ? -1 : 1);
        int pow10 = (int) Math.pow(10, maxDecimalPlaces);
        //Round
        disp += (0x8000 / pow10);

        long decimal = disp & 0xFFFF;
        StringBuilder fracPart = new StringBuilder();
        for (int i = 0; i < maxDecimalPlaces; i++) {
            decimal *= 10;
            fracPart.append((decimal >> 16));
            decimal &= 0xFFFF;
        }

        //Remove trailing zeroes
        String str = (disp >> 16) + "." + fracPart.toString().replaceFirst("0*$", "");
        if (neg) {
            str = "-" + str;
        }
        return str;
    }
}
