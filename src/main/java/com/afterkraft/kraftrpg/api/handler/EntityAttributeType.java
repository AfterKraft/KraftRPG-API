/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.handler;

/**
 * Standard attribute mapping for ServerInternals.
 */
public enum EntityAttributeType {
    DAMAGE,
    EXPERIENCE,
    SPAWNX,
    SPAWNY,
    SPAWNZ,
    SPAWNREASON(0, 1);

    private String attrName;
    private double minValue;
    private double maxValue;

    private EntityAttributeType() {
        this(-Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private EntityAttributeType(double minValue, double maxValue) {
        this.attrName = "kraftrpg-" + name();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private EntityAttributeType(String attributeName, double minValue, double maxValue) {
        this.attrName = attributeName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getMin() {
        return this.minValue;
    }

    public double getMax() {
        return this.maxValue;
    }

    public String getIdentifier() {
        return this.attrName;
    }
}
