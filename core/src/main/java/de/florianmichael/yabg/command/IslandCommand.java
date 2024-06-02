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
import de.florianmichael.yabg.island.IslandTracker;
import de.florianmichael.yabg.island.YABGIsland;
import de.florianmichael.yabg.util.wrapper.WrappedCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.function.BiConsumer;

public final class IslandCommand implements WrappedCommand {

    private final Map<String, BiConsumer<Player, String[]>> subCommands = new HashMap<>();
    private final ConfigurationWrapper config;
    private final IslandTracker tracker;

    public IslandCommand(final ConfigurationWrapper config, final IslandTracker tracker) {
        this.config = config;
        this.tracker = tracker;

        subCommands.put("create", (player, args) -> {
            String name = null;
            if (args.length > 0) {
                name = de.florianmichael.yabg.util.StringUtil.stripInvalidChars(args[0]);
                if (name.isEmpty()) {
                    player.sendMessage(config.invalidNameMessage);
                    return;
                }
            }
            YABGIsland island = tracker.byOwner(player.getUniqueId());
            if (island != null) {
                player.sendMessage(config.alreadyHasIslandMessage);
                return;
            }
            island = tracker.create(player.getUniqueId(), name);
            player.sendMessage(config.islandCreatedMessage);
            island.teleport(player);
        });
        subCommands.put("setSpawn", (player, args) -> {
            final YABGIsland island = getIsland(player);
            if (island != null) {
                island.setSpawnLocation(player.getLocation());
            }
        });
        subCommands.put("delete", (player, args) -> {
        });
        subCommands.put("visit", (player, args) -> {
        });
        subCommands.put("invite", (player, args) -> {
        });
        subCommands.put("join", (player, args) -> {
            final YABGIsland island = getIsland(player);
            if (island != null) {
                island.teleport(player);
                player.sendMessage(config.teleportedToIslandMessage);
            }
        });
        subCommands.put("kick", (player, args) -> {
        });
        subCommands.put("leave", (player, args) -> {
        });
        subCommands.put("list", (player, args) -> {
        });
    }

    private YABGIsland getIsland(final Player player) {
        final YABGIsland island = tracker.byOwner(player.getUniqueId());
        if (island == null) {
            player.sendMessage(config.dontOwnIslandMessage);
        }
        return island;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(config.invalidUsageMessage);
            sender.sendMessage("");
            for (String string : subCommands.keySet()) {
                sender.sendMessage(prefixed("§c/island " + string));
            }
            return;
        }
        final Player player = getPlayer(sender);
        if (player == null) {
            return;
        }
        final BiConsumer<Player, String[]> subCommand = subCommands.get(args[0]);
        if (subCommand == null) {
            sender.sendMessage(config.invalidSubCommandMessage);
            return;
        }
        try {
            subCommand.accept(player, Arrays.copyOfRange(args, 1, args.length)); // Remove subcommand
        } catch (Exception e) { // Exceptions should only be thrown when invalid states are reached
            sender.sendMessage(String.format(config.errorOccurredMessage, e.getMessage()));
        }
    }

    @Override
    public List<String> tabComplete(String label, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], subCommands.keySet(), new ArrayList<>());
        }
        return WrappedCommand.super.tabComplete(label, args);
    }

}
