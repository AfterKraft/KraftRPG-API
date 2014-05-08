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
    public static final long MAX_VALUE = Long.MAX_VALUE >>> FRAC_SIZE;

    private long val;

    public FixedPoint() {
        this(0);
    }

    private FixedPoint(long init) {
        val = init;
    }

    private static long toFixed(double param) {
        return Math.round(param * ONE);
    }

    private static long toFixed(int param) {
        return param * ONE;
    }

    private static long toFixed(long param) {
        return param * ONE;
    }

    public static FixedPoint valueOf(int number) {
        return new FixedPoint(toFixed(number));
    }

    public static FixedPoint valueOf(long number) {
        return new FixedPoint(toFixed(number));
    }

    public static FixedPoint valueOf(double number) {
        return new FixedPoint(toFixed(number));
    }

    public static FixedPoint fromRaw(long raw) {
        return new FixedPoint(raw);
    }

    public void addRaw(long fixedVal) {
        this.val += fixedVal;
    }

    public void add(int param) {
        addRaw(toFixed(param));
    }

    public void add(long param) {
        addRaw(toFixed(param));
    }

    public void add(double param) {
        addRaw(toFixed(param));
    }

    public void add(FixedPoint param) {
        addRaw(param.val);
    }

    public void subRaw(long fixedVal) {
        this.val -= fixedVal;
    }

    public void sub(int param) {
        subRaw(toFixed(param));
    }

    public void sub(long param) {
        subRaw(toFixed(param));
    }

    public void sub(double param) {
        sub(toFixed(param));
    }

    public void sub(FixedPoint param) {
        subRaw(param.val);
    }

    public void multRaw(long fixedVal) {
        long t = val * fixedVal;
        t += HALF;
        val = (t >> FRAC_SIZE);
    }

    public void mult(int param) {
        this.val *= param;
    }

    public void mult(long param) {
        this.val *= param;
    }

    public void mult(double param) {
        this.val *= param;
    }

    public void mult(FixedPoint param) {
        multRaw(param.val);
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

    public void setRaw(long fixedVal) {
        val = fixedVal;
    }

    public void set(int param) {
        this.val = toFixed(param);
    }

    public void set(long param) {
        this.val = toFixed(param);
    }

    public void set(double param) {
        this.val = toFixed(param);
    }

    public void set(FixedPoint param) {
        this.val = param.val;
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
