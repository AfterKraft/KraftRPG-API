package com.afterkraft.kraftrpg.api;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.avaje.ebean.EbeanServer;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.easymock.EasyMock;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import com.afterkraft.kraftrpg.api.entity.CombatTracker;
import com.afterkraft.kraftrpg.api.entity.EntityManager;
import com.afterkraft.kraftrpg.api.entity.effects.EffectManager;
import com.afterkraft.kraftrpg.api.entity.party.PartyManager;
import com.afterkraft.kraftrpg.api.listeners.ListenerManager;
import com.afterkraft.kraftrpg.api.roles.RoleManager;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillConfigManager;
import com.afterkraft.kraftrpg.api.skills.SkillManager;
import com.afterkraft.kraftrpg.api.storage.StorageFrontend;
import com.afterkraft.kraftrpg.api.util.ConfigManager;
import com.afterkraft.kraftrpg.api.util.DamageManager;
import com.afterkraft.kraftrpg.api.util.Properties;

public class RPGTestPlugin implements RPGPlugin {

    private static RPGPlugin instance;

    public static RPGPlugin getInstance() {
        return instance;
    }

    static {
        RPGPlugin mockPlugin = EasyMock.createNiceMock(RPGPlugin.class);
        EasyMock.expect(mockPlugin.getServer()).andStubReturn(Bukkit.getServer());

        EasyMock.replay(mockPlugin);
        instance = mockPlugin;
    }

    @Override
    public void cancelEnable() {

    }

    @Override
    public Permission getVaultPermissions() {
        return null;
    }

    @Override
    public Economy getVaultEconomy() {
        return null;
    }

    @Override
    public SkillConfigManager getSkillConfigManager() {
        return null;
    }

    @Override
    public CombatTracker getCombatTracker() {
        return null;
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }

    @Override
    public EffectManager getEffectManager() {
        return null;
    }

    @Override
    public StorageFrontend getStorage() {
        return null;
    }

    @Override
    public ConfigManager getConfigurationManager() {
        return null;
    }

    @Override
    public DamageManager getDamageManager() {
        return null;
    }

    @Override
    public SkillManager getSkillManager() {
        return null;
    }

    @Override
    public RoleManager getRoleManager() {
        return null;
    }

    @Override
    public PartyManager getPartyManager() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public ListenerManager getListenerManager() {
        return null;
    }

    @Override
    public void log(Level level, String msg) {

    }

    @Override
    public void logSkillThrowing(ISkill skill, String action, Throwable thrown, Object extraContext) {

    }

    @Override
    public void debugLog(Level level, String msg) {

    }

    @Override
    public void debugThrow(String sourceClass, String sourceMethod, Throwable thrown) {

    }

    @Override
    public File getDataFolder() {
        return null;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return null;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public InputStream getResource(String filename) {
        return null;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void saveDefaultConfig() {

    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {

    }

    @Override
    public void reloadConfig() {

    }

    @Override
    public PluginLoader getPluginLoader() {
        return null;
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean canNag) {

    }

    @Override
    public EbeanServer getDatabase() {
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return null;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
