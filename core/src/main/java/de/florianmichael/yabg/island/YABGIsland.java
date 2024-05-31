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
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class YABGIsland {

    private final UUID owner;
    private @Nullable String name;
    private final int chunkX;
    private final int chunkY;
    private final List<UUID> members;

    public YABGIsland(UUID owner, int chunkX, int chunkY) {
        this.owner = owner;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.members = new ArrayList<>();
    }

    public YABGIsland(UUID owner, int chunkX, int chunkY, @NonNull String name, List<UUID> members) {
        this.owner = owner;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.name = name;
        this.members = members;
    }

    public void prepare(final ConfigurationWrapper config) {
        final World world = BukkitPlugin.instance().world();

        world.setBlockData(middleX(config.islandSize), config.spawnY, middleZ(config.islandSize), config.islandBlock.createBlockData());
    }

    public void teleport(final Player player) {
        final int size = BukkitPlugin.instance().config().islandSize;
        final World world = BukkitPlugin.instance().world();

        player.teleport(world.getHighestBlockAt(middleX(size), middleZ(size)).getLocation().add(0.5, 1, 0.5));
    }

    public Location getBlockLocation() {
        final ConfigurationWrapper config = BukkitPlugin.instance().config();

        return new Location(BukkitPlugin.instance().world(), middleX(config.islandSize), config.spawnY, middleZ(config.islandSize));
    }

    public int middleX(final int size) {
        return chunkX * size + size / 2;
    }

    public int middleZ(final int size) {
        return chunkY * size + size / 2;
    }

    public int x(final int size) {
        return chunkX * size;
    }

    public int z(final int size) {
        return chunkY * size;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public UUID owner() {
        return owner;
    }

    public @Nullable String name() {
        return name;
    }

    public int chunkX() {
        return chunkX;
    }

    public int chunkY() {
        return chunkY;
    }

    public List<UUID> members() {
        return members;
    }
}
