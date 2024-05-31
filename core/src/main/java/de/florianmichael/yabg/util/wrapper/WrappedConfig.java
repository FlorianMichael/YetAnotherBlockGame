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

package de.florianmichael.yabg.util.wrapper;

import com.google.common.base.Preconditions;
import de.florianmichael.yabg.BukkitPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;

public abstract class WrappedConfig {

    private File file;
    private FileConfiguration config;

    public WrappedConfig() {
    }

    public WrappedConfig(final FileConfiguration config) {
        this.config = config;
    }

    public abstract void read();
    public abstract void write();

    public void load(final File dataFolder, final String name) {
        Preconditions.checkArgument(file == null, "Already loaded");
        file = new File(dataFolder, name);
        config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                BukkitPlugin.instance().getLogger().log(Level.SEVERE, "Failed to create " + this.getClass().getSimpleName(), e);
            }
            save();
        } else {
            read();
        }
    }

    public void createGroup(final String name, final Consumer<ConfigurationSection> consumer) {
        consumer.accept(config.createSection(name));
    }

    public ConfigurationSection group(final String name) {
        return config.getConfigurationSection(name);
    }

    public List<ConfigurationSection> groups() {
        return config.getKeys(false).stream().map(config::getConfigurationSection).toList();
    }

    public void set(final String key, final Object value) {
        config.set(key, value);
    }

    public <T> T get(final String key, final Class<T> type) {
        return type.cast(config.get(key));
    }

    public String colorString(final String key) {
        return get(key, String.class).replace('&', ChatColor.COLOR_CHAR);
    }

    public void save() {
        write();
        try {
            config.save(file);
        } catch (IOException e) {
            BukkitPlugin.instance().getLogger().log(Level.SEVERE, "Failed to save " + this.getClass().getSimpleName(), e);
        }
    }

}
