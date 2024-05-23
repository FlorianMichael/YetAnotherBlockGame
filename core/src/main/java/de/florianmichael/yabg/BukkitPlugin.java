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

import de.florianmichael.yabg.command.SetupCommand;
import de.florianmichael.yabg.command.SpawnCommand;
import de.florianmichael.yabg.config.ConfigurationWrapper;
import de.florianmichael.yabg.generator.CustomWorldFactory;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

// TODO move out into a separate class and keep this only for bootstrapping
public final class BukkitPlugin extends JavaPlugin {

    private ConfigurationWrapper config;
    private World world;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new ConfigurationWrapper(getConfig());
        config.read(); // Load done by bukkit

        registerCommand("spawn", new SpawnCommand(config));
        registerCommand("setup", new SetupCommand(config));

        if (getServer().getWorld(config.worldName) != null) {
            world = getServer().getWorld(config.worldName);
        } else {
            getLogger().info("Creating OneBlock world...");
            world = CustomWorldFactory.createEmptyWorld(config.worldName);
        }
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

    public static BukkitPlugin instance() {
        return JavaPlugin.getPlugin(BukkitPlugin.class);
    }

    public ConfigurationWrapper config() {
        return config;
    }

    public World world() {
        return world;
    }

}
