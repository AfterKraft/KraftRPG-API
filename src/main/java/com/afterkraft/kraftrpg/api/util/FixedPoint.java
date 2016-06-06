/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api.util;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;

import com.afterkraft.kraftrpg.api.RpgQueries;

/**
 * Represents a fixed point value for Champion experience.
 */
public final class FixedPoint extends Number implements Cloneable, DataSerializable {
    private static final long serialVersionUID = -6313518365999400363L;

    private static final int FRAC_SIZE = 16;
    public static final long MAX_VALUE = Long.MAX_VALUE >>> FRAC_SIZE;
    private static final int ONE = 1 << FRAC_SIZE;
    private static final int HALF = ONE >> 1;
    private static final int MAX_FRAC_VAL = ONE - 1;
    // 2 ^ -16
    private static final double twoPowNegSize = Math.pow(2, -FRAC_SIZE);
    public static final double ulp = twoPowNegSize;
    private final long val;

    public static final FixedPoint ZERO = new FixedPoint();

    private FixedPoint() {
        this(0);
    }

    private FixedPoint(long init) {
        this.val = init;
    }

    public static FixedPoint valueOf(int number) {
        return fromRaw(toFixed(number));
    }

    public static FixedPoint fromRaw(long raw) {
        return new FixedPoint(raw);
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

    @Override
    public FixedPoint clone() {
        try {
            return (FixedPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return toString(3);
    }

    /**
     * Converts to string with custom rounding
     *
     * @param maxDecimalPlaces maximum decimal places to display
     *
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
        String str = (disp >> FRAC_SIZE) + "." + fracPart.toString()
                .replaceFirst("0*$", "");
        if (this.val < 0) {
            str = "-" + str;
        }
        return str;
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

    public FixedPoint multRaw(long fixedVal) {
        long t = this.val * fixedVal;
        t += HALF;
        return fromRaw(t >> FRAC_SIZE);
    }

    public FixedPoint mult(FixedPoint param) {
        return multRaw(param.val);
    }

    /**
     * Gets a new {@link FixedPoint} where the underlying value is the result of the passed int
     * parameter.
     *
     * @param param The number to divide
     *
     * @return The new fixed point of the result
     */
    public FixedPoint div(int param) {
        return fromRaw(this.val / param);
    }

    /**
     * Gets a new {@link FixedPoint} where the underlying value is the result of the passed int
     * parameter.
     *
     * @param param The number to divide
     *
     * @return The new fixed point of the result
     */
    public FixedPoint div(long param) {
        return fromRaw(this.val / param);
    }

    /**
     * Gets a new {@link FixedPoint} where the underlying value is the result of the passed int
     * parameter.
     *
     * @param param The number to divide
     *
     * @return The new fixed point of the result
     */
    public FixedPoint div(double param) {
        return divRaw(toFixed(param));
    }

    /**
     * Gets a new {@link FixedPoint} where the underlying value is the result
     *
     * @param fixedVal The number to divide
     *
     * @return The new fixed point of the result
     */
    public FixedPoint divRaw(long fixedVal) {
        long t = this.val << FRAC_SIZE;
        t += (fixedVal >> 1);
        return fromRaw(t / fixedVal);
    }

    public FixedPoint div(FixedPoint param) {
        return divRaw(param.val);
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

    /**
     * @param param
     *
     * @return
     */
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
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(Queries.CONTENT_VERSION, getContentVersion())
                .set(RpgQueries.FIXED_POINT_RAW, this.val);
    }
}
