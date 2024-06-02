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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public final class PlayerMoveListener extends IslandListenerBase {

    private final Map<Player, Long> lastNotification = new HashMap<>();

    public PlayerMoveListener(BukkitPlugin instance) {
        super(instance);
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
        final YABGIsland island = getIsland(e.getPlayer());
        if (island == null) {
            return;
        }
        final Location to = e.getTo();
        final int size = instance.config().islandSize;
        if (to.x() > island.x(size) + size || to.x() < island.x(size) || to.z() > island.z(size) + size || to.z() < island.z(size)) {
            e.setCancelled(true);
            // Only send notifications every 10 seconds
            if (lastNotification.containsKey(e.getPlayer()) && System.currentTimeMillis() - lastNotification.get(e.getPlayer()) < 10_000) {
                return;
            }
            e.getPlayer().sendMessage(instance.config().notAllowedToLeaveMessage);
            lastNotification.put(e.getPlayer(), System.currentTimeMillis());
        }

        // Remove from the notification map if player has been inside his island for more than 5 seconds
        if (lastNotification.containsKey(e.getPlayer()) && System.currentTimeMillis() - lastNotification.get(e.getPlayer()) > 5000) {
            lastNotification.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        lastNotification.remove(e.getPlayer());
    }

}
