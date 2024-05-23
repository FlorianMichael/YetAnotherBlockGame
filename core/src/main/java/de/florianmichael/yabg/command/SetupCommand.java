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
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class SetupCommand implements WrappedCommand {

    private final Map<String, BiConsumer<Player, String[]>> subCommands = new HashMap<>();
    private final ConfigurationWrapper config;

    public SetupCommand(final ConfigurationWrapper config) {
        this.config = config;

        subCommands.put("setSpawn", this::setSpawn);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(prefixed("§cInvalid usage!"));
            sender.sendMessage("");
            for (String string : subCommands.keySet()) {
                sender.sendMessage(prefixed("§c/setup " + string));
            }
            return;
        }
        final Player player = getPlayer(sender);
        if (player == null || !hasPermission(player, "yabg.setup")) {
            return;
        }
        final BiConsumer<Player, String[]> subCommand = subCommands.get(args[0]);
        if (subCommand == null) {
            sender.sendMessage(prefixed("§cInvalid subcommand!"));
            return;
        }
        subCommand.accept(player, args);
    }

    private void setSpawn(Player player, String[] args) {
        config.positions().spawnLocation = player.getLocation();
        player.sendMessage(prefixed("§aSpawn location set!"));
    }

    @Override
    public List<String> tabComplete(String label, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], subCommands.keySet(), new ArrayList<>());
        }
        return WrappedCommand.super.tabComplete(label, args);
    }
}
