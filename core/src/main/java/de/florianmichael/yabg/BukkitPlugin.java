/*
 * This file is part of YetAnotherBlockGame - https://github.com/FlorianMichael/YetAnotherBlockGame
 * Copyright (C) 2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.yabg;

import de.florianmichael.yabg.command.DebugCommand;
import de.florianmichael.yabg.command.IslandCommand;
import de.florianmichael.yabg.command.SetSpawnCommand;
import de.florianmichael.yabg.command.SpawnCommand;
import de.florianmichael.yabg.config.ConfigurationWrapper;
import de.florianmichael.yabg.generator.CustomWorldFactory;
import de.florianmichael.yabg.island.IslandTracker;
import de.florianmichael.yabg.listener.BlockBreakListener;
import de.florianmichael.yabg.listener.PlayerJoinListener;
import de.florianmichael.yabg.listener.PlayerMoveListener;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

// TODO move out into a separate class and keep this only for bootstrapping (expose interfaces to api module)
public final class BukkitPlugin extends JavaPlugin {

    private IslandTracker islandTracker;
    private ConfigurationWrapper config;

    private World world;

    @Override
    public void onEnable() {
        islandTracker = new IslandTracker();

        saveDefaultConfig();
        config = new ConfigurationWrapper(getConfig(), islandTracker);
        config.read(); // Load done by bukkit

        registerCommand("spawn", new SpawnCommand(config));
        registerCommand("setspawn", new SetSpawnCommand(config));
        registerCommand("island", new IslandCommand(config, islandTracker));

        registerCommand("debug", new DebugCommand());

        if (getServer().getWorld(config.worldName) != null) {
            world = getServer().getWorld(config.worldName);
        } else {
            getLogger().info("Creating OneBlock world...");
            world = CustomWorldFactory.createEmptyWorld(config.worldName);
        }

        registerEvent(new PlayerJoinListener(config));
        registerEvent(new BlockBreakListener(this));
        registerEvent(new PlayerMoveListener(this));
    }

    @Override
    public void onDisable() {
        config.write();
    }

    private void registerCommand(final String name, final TabExecutor implementation) {
        final PluginCommand command = getCommand(name);
        if (command == null) {
            throw new IllegalArgumentException("Add command to plugin.yml: " + name);
        }
        command.setExecutor(implementation);
        command.setTabCompleter(implementation);
    }

    private void registerEvent(final Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static BukkitPlugin instance() {
        return JavaPlugin.getPlugin(BukkitPlugin.class);
    }

    public IslandTracker islandTracker() {
        return islandTracker;
    }

    public ConfigurationWrapper config() {
        return config;
    }

    public World world() {
        return world;
    }

}
