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
public final class FixedPoint extends Number implements Cloneable {
    private static final long serialVersionUID = -6313518365999400363L;

    private static final int FRAC_SIZE = 16;
    private static final int ONE = 1 << FRAC_SIZE;
    private static final int HALF = ONE >> 1;
    private static final int MAX_FRAC_VAL = ONE - 1;

    // 2 ^ -16
    private static final double twoPowNegSize = Math.pow(2, -FRAC_SIZE);

    public static final double ulp = twoPowNegSize;
    public static final long MAX_VALUE = Long.MAX_VALUE >>> FRAC_SIZE;

    private long val;

    public FixedPoint() {
        this(0);
    }

    private FixedPoint(long init) {
        this.val = init;
    }

    public static FixedPoint valueOf(int number) {
        return fromRaw(toFixed(number));
    }

    private static long toFixed(int param) {
        return param * (long) ONE;
    }

    public static FixedPoint valueOf(long number) {
        return fromRaw(toFixed(number));
    }

    private static long toFixed(long param) {
        return param * ONE;
    }

    public static FixedPoint valueOf(double number) {
        return fromRaw(toFixed(number));
    }

    private static long toFixed(double param) {
        return Math.round(param * ONE);
    }

    public static FixedPoint fromRaw(long raw) {
        return new FixedPoint(raw);
    }

    @Override
    public FixedPoint clone() {
        try {
            return (FixedPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public FixedPoint add(int param) {
        return addRaw(toFixed(param));
    }

    public FixedPoint addRaw(long fixedVal) {
        return fromRaw(this.val + fixedVal);
    }

    public FixedPoint add(long param) {
        return addRaw(toFixed(param));
    }

    public FixedPoint add(double param) {
        return addRaw(toFixed(param));
    }

    public FixedPoint add(FixedPoint param) {
        return addRaw(param.val);
    }

    public FixedPoint sub(int param) {
        return subRaw(toFixed(param));
    }

    public FixedPoint subRaw(long fixedVal) {
        return fromRaw(this.val - fixedVal);
    }

    public FixedPoint sub(double param) {
        return sub(toFixed(param));
    }

    public FixedPoint sub(long param) {
        return subRaw(toFixed(param));
    }

    public FixedPoint sub(FixedPoint param) {
        return subRaw(param.val);
    }

    public FixedPoint mult(int param) {
        return fromRaw(this.val * param);
    }

    public FixedPoint mult(long param) {
        return fromRaw(this.val * param);
    }

    public FixedPoint mult(double param) {
        return multRaw(toFixed(param));
    }

    public FixedPoint mult(FixedPoint param) {
        return multRaw(param.val);
    }

    public FixedPoint multRaw(long fixedVal) {
        long t = this.val * fixedVal;
        t += HALF;
        return fromRaw(t >> FRAC_SIZE);
    }

    public FixedPoint div(int param) {
        return fromRaw(this.val / param);
    }

    public FixedPoint div(long param) {
        return fromRaw(this.val / param);
    }

    public FixedPoint div(double param) {
        return divRaw(toFixed(param));
    }

    public FixedPoint div(FixedPoint param) {
        return divRaw(param.val);
    }

    public FixedPoint divRaw(long fixedVal) {
        long t = this.val << FRAC_SIZE;
        t += (fixedVal >> 1);
        return fromRaw(t / fixedVal);
    }

    public FixedPoint setRaw(long fixedVal) {
        return fromRaw(fixedVal);
    }

    public FixedPoint set(int param) {
        return fromRaw(toFixed(param));
    }

    public FixedPoint set(long param) {
        return fromRaw(toFixed(param));
    }

    public FixedPoint set(double param) {
        return fromRaw(toFixed(param));
    }

    public FixedPoint set(FixedPoint param) {
        return fromRaw(param.val);
    }

    @Override
    public int intValue() {
        return (int) (this.val >> FRAC_SIZE);
    }

    @Override
    public long longValue() {
        return this.val >> FRAC_SIZE;
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return (double) this.val * twoPowNegSize;
    }

    public long rawValue() {
        return this.val;
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
        long disp = Math.abs(this.val);
        int pow10 = (int) Math.pow(10, maxDecimalPlaces);
        //Round
        disp += (HALF / pow10);

        long decimal = disp & MAX_FRAC_VAL;
        StringBuilder fracPart = new StringBuilder();
        for (int i = 0; i < maxDecimalPlaces; i++) {
            decimal *= 10;
            fracPart.append((decimal >> FRAC_SIZE));
            decimal &= MAX_FRAC_VAL;
        }

        //Remove trailing zeroes
        String str = (disp >> FRAC_SIZE) + "." + fracPart.toString().replaceFirst("0*$", "");
        if (this.val < 0) {
            str = "-" + str;
        }
        return str;
    }
}
