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

import de.florianmichael.yabg.BukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Make Bukkit API less disgusting and add utils for chat messages
public interface WrappedCommand extends TabExecutor {

    void execute(CommandSender sender, String[] args);

    @Override
    default boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, strings);
        return true;
    }

    default List<String> tabComplete(String label, String[] args) {
        return List.of();
    }

    @Override
    default @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return tabComplete(s, strings);
    }

    // ----------------------------------------------------------------------

    default Player getPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player) sender;
        } else {
            sender.sendMessage(BukkitPlugin.instance().config().playerOnlyCommandMessage);
            return null;
        }
    }

    default boolean hasPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage(BukkitPlugin.instance().config().missingPermissionMessage);
            return false;
        }
    }

    default String prefixed(final String message) {
        return BukkitPlugin.instance().config().chatFormat + message;
    }

}
