/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.entity.component;

import com.afterkraft.kraftrpg.api.util.FixedPoint;

/**
 * A component that marks a being as granting a reward, or {@link FixedPoint} experience to the
 * killer upon death.
 */
public interface RewardingComponent extends Component<RewardingComponent> {

    /**
     * Return the {@link com.afterkraft.kraftrpg.api.util.FixedPoint} this being should grant as a
     * reward for being killed by a {@link com.afterkraft.kraftrpg.api.entity.Sentient} being.
     *
     * @return the customized experience to grant to a killer
     */
    FixedPoint getRewardExperience();

    /**
     * Set the rewarding experience this being will grant to its killer.
     *
     * @param experience to grant
     */
    void setRewardExperience(FixedPoint experience);

}
