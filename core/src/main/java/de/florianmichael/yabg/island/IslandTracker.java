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

package de.florianmichael.yabg.island;

import de.florianmichael.yabg.BukkitPlugin;
import de.florianmichael.yabg.config.ConfigurationWrapper;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class IslandTracker {

    private final List<YABGIsland> islands = new ArrayList<>();

    public @Nullable YABGIsland byOwner(final UUID owner) {
        return islands.stream().filter(island -> island.owner().equals(owner)).findFirst().orElse(null);
    }

    public YABGIsland create(final UUID owner, final String name) {
        final ConfigurationWrapper config = BukkitPlugin.instance().config();
        final double maxWorldSize = BukkitPlugin.instance().world().getWorldBorder().getMaxSize(); // HMMMMMMMMMMMMMM

        int chunkX = 0;
        int chunkZ = 0;

        int finalChunkX = chunkX;
        int finalChunkY = chunkZ;
        while (islands.stream().anyMatch(island -> island.chunkX() == finalChunkX && island.chunkY() == finalChunkY)) {
            chunkX++;
            if ((chunkX * config.islandSize) + config.islandSize >= maxWorldSize) {
                chunkX = 0;
                chunkZ++;
            }
            if ((chunkZ * config.islandSize) + config.islandSize >= maxWorldSize) {
                throw new IllegalStateException(config.noSpaceForIslandsMessage);
            }
        }

        final YABGIsland island = new YABGIsland(owner, chunkX, chunkZ);
        island.setName(name);
        islands.add(island);

        island.initialize(config);
        return island;
    }

    public List<YABGIsland> islands() {
        return islands;
    }

}
