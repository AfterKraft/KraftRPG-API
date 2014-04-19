package com.afterkraft.kraftrpg.api.spells;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import com.afterkraft.kraftrpg.api.Manager;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.roles.Role;

/**
 * @author gabizou
 */
public interface SpellConfigManager extends Manager {

    public void reload();

    public void saveSpellConfig();

    public Configuration getClassConfig(String name);

    public void addClassSpellSettings(String className, String spellName, ConfigurationSection section);

    public void loadSpellDefaults(ISpell<? extends SpellArgument> spell);

    // Because bukkit can't handle setting defaults before sections exist
    public void setClassDefaults();
    //------------------------//
    // Data retrieval methods //
    //------------------------//

    public String getRaw(ISpell<? extends SpellArgument> spell, String setting, String def);

    public String getRaw(ISpell<? extends SpellArgument> spell, SpellSetting setting, String def);

    public Boolean getRaw(ISpell<? extends SpellArgument> spell, SpellSetting setting, boolean def);

    public Boolean getRaw(ISpell<? extends SpellArgument> spell, String setting, boolean def);

    public Set<String> getRawKeys(ISpell<? extends SpellArgument> spell, String setting);

    public Object getSetting(Role hc, ISpell<? extends SpellArgument> spell, String setting);

    public int getSetting(Role hc, ISpell<? extends SpellArgument> spell, String setting, int def);

    public double getSetting(Role hc, ISpell<? extends SpellArgument> spell, String setting, double def);

    public String getSetting(Role hc, ISpell<? extends SpellArgument> spell, String setting, String def);

    public Boolean getSetting(Role hc, ISpell<? extends SpellArgument> spell, String setting, boolean def);

    public List<String> getSetting(Role hc, ISpell<? extends SpellArgument> spell, String setting, List<String> def);

    public Set<String> getSettingKeys(Role hc, ISpell<? extends SpellArgument> spell, String setting);

    public Set<String> getUseSettingKeys(Champion champion, ISpell<? extends SpellArgument> spell, String setting);

    public List<String> getUseSettingKeys(Champion champion, ISpell<? extends SpellArgument> spell);

    public int getLevel(Champion champion, ISpell<? extends SpellArgument> spell, int def);

    public int getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, SpellSetting setting, int def, boolean lower);

    public String getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, SpellSetting setting, String def);

    public double getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, SpellSetting setting, double def, boolean lower);

    public boolean getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, SpellSetting setting, boolean def);

    public int getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, String setting, int def, boolean lower);

    public double getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, String setting, double def, boolean lower);

    public boolean getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, String setting, boolean def);

    public String getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, String setting, String def);

    public List<String> getUseSetting(Champion champion, ISpell<? extends SpellArgument> spell, String setting, List<String> def);
}