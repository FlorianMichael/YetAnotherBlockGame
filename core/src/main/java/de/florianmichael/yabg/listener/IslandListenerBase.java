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
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public abstract class IslandListenerBase implements Listener {

    protected final BukkitPlugin instance;

    public IslandListenerBase(final BukkitPlugin instance) {
        this.instance = instance;
    }

    public void call(final Player player, final Consumer<YABGIsland> consumer) {
        if (player.getWorld() == instance.world()) {
            final YABGIsland island = instance.islandTracker().byOwner(player.getUniqueId());
            if (island != null) {
                consumer.accept(island);
            }
        }
    }

}
