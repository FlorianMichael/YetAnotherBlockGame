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

package de.florianmichael.yabg.listener;

import de.florianmichael.yabg.BukkitPlugin;
import de.florianmichael.yabg.island.YABGIsland;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;

public final class BlockBreakListener extends IslandListenerBase {

    public BlockBreakListener(BukkitPlugin instance) {
        super(instance);
    }

    private void updatePhase(final Player player, final YABGIsland island) {
        if (island.phase() == null) { // Initial phase setup
            island.updatePhase(instance.config().nextPhase(null));
        }
        final Location blockLocation = island.getBlockLocation();
        final World world = player.getWorld();

        final Material material = world.getBlockData(blockLocation).getMaterial();
        if (!island.phase().contains(material)) {
            player.sendMessage("The block you tried to break is not part of the current phase. Please report to an admin.");
            return;
        }

        final Item entity = (Item) world.spawnEntity(blockLocation.clone().add(0, 1, 0), EntityType.DROPPED_ITEM);
        entity.setItemStack(new ItemStack(material, 1));

        island.blockBreaks().put(material, island.blockBreaks().getOrDefault(material, 0) + 1);
        if (island.phase().hasFinished(island.blockBreaks())) {
            island.updatePhase(instance.config().nextPhase(island.phase()));
        }

        final Material nextMaterial = island.phase().rand();
        if (nextMaterial == null) {
            player.sendMessage("An error occurred while trying to determine the next block. Please report to an admin.");
            return;
        }
        world.setBlockData(blockLocation, nextMaterial.createBlockData());
    }

    @EventHandler
    public void onBlockPhysics(final EntityChangeBlockEvent e) {
        for (YABGIsland island : instance.islandTracker().islands()) {
            if (island.getBlockLocation().equals(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        final YABGIsland island = getIsland(e.getPlayer());
        if (island == null) {
            return;
        }
        if (e.getBlock().getLocation().equals(island.getBlockLocation())) {
            e.setCancelled(true);
            updatePhase(e.getPlayer(), island);
        }
    }

}
