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

package de.florianmichael.yabg.config;

import de.florianmichael.yabg.BukkitPlugin;
import de.florianmichael.yabg.island.IslandTracker;
import de.florianmichael.yabg.util.wrapper.WrappedConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * Wrapper to handle settings stored in config.yml, also stores other config files
 */
public final class ConfigurationWrapper extends WrappedConfig {

    private final PositionsSave positions;
    private final IslandsSave islands;

    public String chatFormat;
    public String worldName;

    public String spawnMessage;
    public String spawnNotSetMessage;
    public String playerOnlyCommandMessage;
    public String missingPermissionMessage;

    public int islandSize;
    public Material islandBlock;
    public int spawnY;

    public ConfigurationWrapper(final FileConfiguration config, final IslandTracker tracker) {
        super(config);
        this.positions = new PositionsSave();
        this.islands = new IslandsSave(tracker);
    }

    @Override
    public void read() {
        // Main options
        chatFormat = colorString("chat-format");
        worldName = get("world-name", String.class);

        // Messages
        spawnMessage = message("spawn-teleport");
        spawnNotSetMessage = message("spawn-not-set");
        playerOnlyCommandMessage = message("player-only-command");
        missingPermissionMessage = message("missing-permission");

        islandSize = get("island.size", Integer.class);
        islandBlock = Material.valueOf(get("island.block", String.class));
        spawnY = get("island.spawn-y", Integer.class);

        // Internal save files
        final File folder = BukkitPlugin.instance().getDataFolder();
        this.positions.load(folder, "positions.yml");
        this.islands.load(folder, "islands.yml");
    }

    public String message(final String key) {
        return chatFormat + colorString("messages." + key);
    }

    @Override
    public void write() {
        this.positions.save();
        this.islands.save();
    }

    public PositionsSave positions() {
        return positions;
    }

}
