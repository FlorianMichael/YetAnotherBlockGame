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

package de.florianmichael.yabg.command;

import de.florianmichael.yabg.config.ConfigurationWrapper;
import de.florianmichael.yabg.util.wrapper.WrappedCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SpawnCommand implements WrappedCommand {

    private final ConfigurationWrapper config;

    public SpawnCommand(final ConfigurationWrapper config) {
        this.config = config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final Player player = getPlayer(sender);
        if (player == null) {
            return;
        }
        if (config.positions().spawnLocation == null) {
            sender.sendMessage(config.spawnNotSetMessage);
            return;
        }
        player.teleport(config.positions().spawnLocation);
        sender.sendMessage(config.spawnMessage);
    }

}
