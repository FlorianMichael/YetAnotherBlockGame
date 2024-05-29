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

import de.florianmichael.yabg.BukkitPlugin;
import de.florianmichael.yabg.util.wrapper.WrappedCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

// Test-only command, not to be loaded later on
public final class DebugCommand implements WrappedCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
//        getPlayer(sender).teleport(BukkitPlugin.instance().world().getSpawnLocation());
//
//        System.out.println(getPlayer(sender).getWorld().getName());

        final Location loc = getPlayer(sender).getLocation();
        getPlayer(sender).getWorld().setBlockData(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + 2, Material.DIRT.createBlockData());
    }

}
