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

import org.spongepowered.api.data.DataManipulator;

import com.afterkraft.kraftrpg.api.effect.Effect;
import com.afterkraft.kraftrpg.api.role.Role;
import com.afterkraft.kraftrpg.api.skill.Skill;
import com.afterkraft.kraftrpg.api.storage.ComponentStorage;

/**
 * A {@link Component} is a more customized {@link DataManipulator} for
 * <strong>RpgCommon-API</strong> such that components can not be misunderstood for external {@link
 * DataManipulator}s. While the common use of {@link Component}s is for rudimentary use of custom
 * data related to {@link Effect}s, {@link Skill}s, {@link Role}s, etc., the power of the {@link
 * Component} system is that a {@link Component} will always have an attached {@link
 * ComponentStorage}.
 *
 * <p>The benefits of segregating external {@link DataManipulator}s from {@link Component}s is that
 * a {@link Component}'s {@link ComponentStorage} will always be able to serialize and deserialize
 * the {@link Component} to the storage strategy provided at runtime. Advanced use of {@link
 * Component}s in tandem with {@link DataManipulator}s from <strong>SpongeAPI</strong> unleashes the
 * ultimat compatibility with both API's.</p>
 *
 * @param <T> The type of component, for comparison reasons
 */
public interface Component<T extends Component<T>> extends DataManipulator<T> {

    /**
     * Gets the identifiable name of this {@link Component}.
     *
     * @return The identifiable name of this component
     */
    String getId();

}
