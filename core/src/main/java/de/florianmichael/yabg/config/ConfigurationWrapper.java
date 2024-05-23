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
import de.florianmichael.yabg.util.wrapper.WrappedConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * Wrapper to handle settings stored in config.yml, also stores other config files
 */
public final class ConfigurationWrapper extends WrappedConfig {

    private final PositionsConfig positions;

    public String chatFormat;

    public String spawnMessage;
    public String spawnNotSetMessage;
    public String playerOnlyCommand;

    public ConfigurationWrapper(final FileConfiguration config) {
        super(config);
        this.positions = new PositionsConfig();
    }

    @Override
    public void read() {
        chatFormat = colorString("chat-format");

        spawnMessage = message("spawn-message");
        spawnNotSetMessage = message("spawn-not-set-message");
        playerOnlyCommand = message("player-only-command");

        final File folder = BukkitPlugin.instance().getDataFolder();
        this.positions.load(folder, "positions.yml");
    }

    public String message(final String key) {
        return chatFormat + colorString(key);
    }

    @Override
    public void write() {
        throw new IllegalStateException("Not implemented");
    }

    public PositionsConfig positions() {
        return positions;
    }

}
