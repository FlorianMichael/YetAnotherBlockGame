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

import de.florianmichael.yabg.island.IslandTracker;
import de.florianmichael.yabg.island.YABGIsland;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    private final IslandTracker tracker;
    private final World world;

    public BlockBreakListener(final IslandTracker tracker, final World world) {
        this.tracker = tracker;
        this.world = world;
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        final Player player = e.getPlayer();
        if (player.getWorld() != world) {
            return;
        }
        final YABGIsland island = tracker.byOwner(player.getUniqueId());
        if (island == null) {
            return;
        }
        if (e.getBlock().getLocation().equals(island.getBlockLocation())) {
            e.setCancelled(true);
            final Item entity = (Item) player.getWorld().spawnEntity(player.getLocation(), EntityType.ITEM);
            // TODO create models to calculate a good item to give
            entity.setItemStack(new ItemStack(Material.DIAMOND_BLOCK, 64));
        }
    }

}
