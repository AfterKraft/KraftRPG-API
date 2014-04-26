package com.afterkraft.kraftrpg.api.entity;

import org.bukkit.Location;

import com.afterkraft.kraftrpg.api.spells.Delayed;
import com.afterkraft.kraftrpg.api.spells.ISpell;
import com.afterkraft.kraftrpg.api.spells.SpellArgument;
import com.afterkraft.kraftrpg.api.util.SpellRequirement;

/**
 * @author gabizou
 */
public interface SpellCaster extends Mage, Insentient, PartyMember {

    public void removeSpellRequirement(SpellRequirement spellRequirement);

    public boolean isDead();

    public Location getLocation();

    /**
     * Get the key'ed cooldown. Used by Spells to mark individual cooldowns
     * @param key
     * @return
     */
    public Long getCooldown(String key);

    /**
     * Get the global cooldown
     * @return the global cooldown if not 0
     */
    public long getGlobalCooldown();

    public void setCooldown(String key, long duration);

    public void setGlobalCooldown(long duration);

    /**
     * Fetch the highest level of all active {@link com.afterkraft.kraftrpg.api.entity.roles.Role}s of the designated {@link com.afterkraft.kraftrpg.api.spells.ISpell}.
     * @param spell the spell in question
     * @return the highest level, if none, 0.
     */
    public int getHighestSpellLevel(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean canUseSpell(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean isSpellRestricted(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean canPrimaryUseSpell(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean doesPrimaryRestrictSpell(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean canSecondaryUseSpell(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean doesSecondaryRestrictSpell(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean canAdditionalUseSpell(ISpell spell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean doesAdditionalRestrictSpell(ISpell spell);

    /**
     *
     * @return
     */
    public Delayed<? extends SpellArgument> getDelayedSpell();

    public <T extends SpellArgument> boolean setDelayedSpell(Delayed<T> delayedSpell);

    /**
     *
     * @param spell
     * @return
     */
    public boolean setDelayedSpell(ISpell spell);

    /**
     *
     * @param forced whether to force cancellation of the delayed spell
     * @return true if the delayed spell was cancelled
     */
    public boolean cancelDelayedSpell(boolean forced);


}
