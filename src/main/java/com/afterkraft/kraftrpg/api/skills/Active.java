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
package com.afterkraft.kraftrpg.api.skills;

import java.util.List;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;

/**
 * Represents a Skill that only has results on activation through command or binding. As this is an
 * active skill that requires some possible binding or targetting, usage should be included by the
 * implementation for players to understand how to use this active skill.  Recommended use of
 * this interface is {@link ActiveSkill}.  The methods {@link #getUsage()}, {@link
 * #tabComplete(com.afterkraft.kraftrpg.api.entity.SkillCaster, String[], int)}  The methods
 * that do rely on parser state shall be called in the following order: <ol> <li>{@link
 * #checkCustomRestrictions(SkillCaster, boolean)}</li> <li>{@link #parse(SkillCaster,
 * String[])}</li> <li>{@link #checkCustomRestrictions(SkillCaster, boolean)}</li> <li>If
 * checkCustomRestrictions() returns ON_WARMUP, then {@link #onWarmUp(SkillCaster)}. If it returns
 * NORMAL, then {@link #useSkill(SkillCaster)}.</li> <li>And finally, even if any throw an
 * exception, {@link #cleanState(SkillCaster)}.</li> </ol>
 */
public interface Active extends ISkill {

    /**
     * Returns the instructional usage for this skill, including various accepted arguments handled
     * in {@link #parse(com.afterkraft.kraftrpg.api.entity.SkillCaster, String[])}
     *
     * @return the instructional usage for this skill
     */
    public String getUsage();

    /**
     * Get the typed SkillArgument at the given index. This method is intended for use by the Skill,
     * as it knows what types its arguments are in advance, and they do not move.
     *
     * @param <T> The type of SkillArgument being requested.
     * @param index - index into the getSkillArguments() array
     *
     * @return a casted SkillArgument
     */
    public <T extends SkillArgument> T getArgument(int index);

    /**
     * Get the active array of SkillArguments currently in use.
     *
     * @return array of SkillArguments
     */
    public SkillArgument[] getSkillArguments();

    /**
     * Any necessary warmup process for an Active Skill should be done here. This is called after
     * parse() but before useSkill().
     *
     * @param caster caster
     */
    public void onWarmUp(SkillCaster caster);

    /**
     * Try to parse the given arguments into an internally stored state for use with other calls. If
     * this is successful, another method such as {@link #onWarmUp(SkillCaster)} or {@link
     * #useSkill(SkillCaster)} may be called, but {@link #cleanState(SkillCaster)} will
     * <b>always</b> be called before another parse() call.
     *
     * @param caster user of the skill
     * @param args   full command arguments
     *
     * @return if parse was successful - that is, the skill can be used with these arguments.
     */
    boolean parse(SkillCaster caster, String[] args);

    /**
     * Suggest tab-completions to the given caster based on the given arguments.
     *
     * @param caster     caster requesting tab completions
     * @param args       partial command arguments
     * @param startIndex index in the array to start looking
     *
     * @return A string list of available completable entries
     */
    List<String> tabComplete(SkillCaster caster, String[] args, int startIndex);

    /**
     * Without parsed state, check if the given SkillCaster is permitted to cast this skill.
     *
     * @param caster caster casting the skill
     * @param forced if the cast is involuntary (e.g. admin action)
     *
     * @return a preliminary SkillCastResult
     */
    SkillCastResult checkCustomRestrictions(SkillCaster caster, boolean forced);

    /**
     * Apply this skill using the previously parse()ed state.
     *
     * @param caster caster of the skill
     *
     * @return final SkillCastResults
     */
    SkillCastResult useSkill(SkillCaster caster);

    /**
     * Clean any parsed state. In particular, any generated objects should be available for garbage
     * collection after this call.
     *
     * @param caster caster, for validation
     */
    void cleanState(SkillCaster caster);

}
