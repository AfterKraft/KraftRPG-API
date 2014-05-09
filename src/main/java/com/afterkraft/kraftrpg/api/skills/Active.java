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
package com.afterkraft.kraftrpg.api.skills;

import java.util.List;

import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.util.SkillRequirement;

/**
 * Represents a Skill that only has results on activation through command or
 * binding. As this is an active skill that requires some possible binding or
 * targetting, usage should be included by the implementation for players to
 * understand how to use this active skill.
 * <p>
 * Recommended use of this interface is {@link ActiveSkill}.
 * <p>
 * The methods {@link #getUsage()},
 * {@link #tabComplete(SkillCaster, String[])} ,
 * {@link #checkCustomRestrictions(SkillCaster, boolean)},
 * {@link #getSkillRequirement(SkillCaster)},
 * {@link #hasSkillRequirement(SkillRequirement, SkillCaster)}, or
 * {@link #grantsExperienceOnCast()} should not rely on parser state.
 *
 * The methods that do rely on parser state shall be called in the following
 * order:
 * <ol>
 * <li>{@link #checkCustomRestrictions(SkillCaster, boolean)}</li>
 * <li>{@link #parse(SkillCaster, String[])}</li>
 * <li>One of these, or possibly none of them (e.g. if parse returned false,
 * but possibly other cases):
 * <ul>
 * <li>{@link #onWarmUp(SkillCaster)}</li>
 * <li>{@link #useSkill(SkillCaster)}</li>
 * </ul>
 * </li>
 * <li>{@link #cleanState(SkillCaster)}</li>
 * </ol>
 */
public interface Active extends ISkill {

    /**
     * Returns the instructional usage for this skill, including various
     * accepted arguments handled in
     * {@link #parse(com.afterkraft.kraftrpg.api.entity.SkillCaster, String[])}
     *
     * @return the instructional usage for this skill
     */
    public String getUsage();

    /**
     * Get the typed SkillArgument at the given index. This method is intended
     * for use by the Skill, as it knows what types its arguments are in
     * advance, and they do not move.
     *
     * @param index - index into the getSkillArguments() array
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
     * Any necessary warmup process for an Active Skill should be done here.
     * This is called after parse() but before useSkill().
     *
     * @param caster caster
     */
    public void onWarmUp(SkillCaster caster);

    /**
     * Try to parse the given arguments into an internally stored state for
     * use with other calls. If this is successful, another method such as
     * {@link #onWarmUp(SkillCaster)} or {@link #useSkill(SkillCaster)} may be
     * called, but {@link #cleanState(SkillCaster)} will <b>always</b> be
     * called before another parse() call.
     *
     * @param caster user of the skill
     * @param args full command arguments
     * @return if parse was successful - that is, the skill can be used with
     *         these arguments.
     */
    public boolean parse(SkillCaster caster, String[] args);

    /**
     * Suggest tab-completions to the given caster based on the given
     * arguments.
     *
     * @param caster caster requesting tab completions
     * @param args partial command arguments
     * @param startIndex index in the array to start looking
     * @return
     */
    public List<String> tabComplete(SkillCaster caster, String[] args, int startIndex);

    /**
     * Without parsed state, check if the given SkillCaster is permitted to
     * cast this skill.
     *
     * @param caster caster casting the skill
     * @param forced if the cast is involuntary (e.g. admin action)
     * @return a preliminary SkillCastResult
     */
    public SkillCastResult checkCustomRestrictions(SkillCaster caster, boolean forced);

    /**
     * Apply this skill using the previously parse()ed state.
     *
     * @param caster caster of the skill
     * @return final SkillCastResults
     */
    public SkillCastResult useSkill(SkillCaster caster);

    /**
     * Clean any parsed state. In particular, any generated objects should be
     * available for garbage collection after this call.
     *
     * @param caster caster, for validation
     */
    public void cleanState(SkillCaster caster);

    /**
     * Return the calculated SkillRequirement for the queried
     * {@link com.afterkraft.kraftrpg.api.entity.Champion}. This can be
     * overriden to use different requirements dependent on various things,
     * such as Attributes, the experience level of the Champion, and other
     * things.
     *
     * @param caster the Champion to check
     * @return a new SkillRequirement for the queried champion
     */
    public SkillRequirement getSkillRequirement(SkillCaster caster);

    /**
     * Check if using this Skill grants the
     * {@link com.afterkraft.kraftrpg.api.entity.Champion} experience on skill
     * casts.
     *
     * @return if exp is granted
     */
    public boolean grantsExperienceOnCast();

}
