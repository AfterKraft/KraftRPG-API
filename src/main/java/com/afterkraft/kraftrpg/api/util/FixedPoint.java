package com.afterkraft.kraftrpg.api.util;

/**
 * Represents a fixed point value for percision values such as Experience and
 * cooldowns=
 */
public final class FixedPoint {

    private static final int FRAC_SIZE = 16;
    private static final int ONE = 1 << FRAC_SIZE;
    private static final int HALF = ONE >> 1;

    private static final double twoPowNegSize = Math.pow(2, -FRAC_SIZE);

    private long val;

    public FixedPoint() {

    }

    public FixedPoint(double init) {
        val = toFixed(init);
    }

    public static long toFixed(double val) {
        return Math.round(val * ONE);
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

    public void add(int val) {
        this.val += (val << FRAC_SIZE);
    }

    public void sub(int val) {
        this.val -= (val << FRAC_SIZE);
    }

    public void mult(int val) {
        this.val *= val;
    }

    public void div(int val) {
        this.val /= val;
    }

    public void add(FixedPoint val) {
        add(val.val);
    }

    public void add(long fixedVal) {
        this.val += fixedVal;
    }

    public void sub(FixedPoint val) {
        sub(val.val);
    }

    public void sub(long fixedVal) {
        this.val -= fixedVal;
    }

    public void mult(FixedPoint val) {
        mult(val.val);
    }

    public void mult(long fixedVal) {
        long t = val * fixedVal;
        t += HALF;
        val = (t >> FRAC_SIZE);
    }

    public void div(FixedPoint val) {
        div(val.val);
    }

    public void div(long fixedVal) {
        long t = val << FRAC_SIZE;
        t += (fixedVal >> 1);
        val = t / fixedVal;
    }

    public void add(double val) {
        add(toFixed(val));
    }

    public void sub(double val) {
        sub(toFixed(val));
    }

    public void mult(double val) {
        mult(toFixed(val));
    }

    public void div(double val) {
        div(toFixed(val));
    }

    public double asDouble() {
        return (double) val * twoPowNegSize;
    }

    public long getByteValue() {
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
