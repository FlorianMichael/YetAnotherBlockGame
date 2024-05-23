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

package de.florianmichael.yabg.generator;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

public final class CustomWorldFactory {

    public static World createEmptyWorld(final String name) {
        return createWorld(name, World.Environment.NORMAL, EmptyChunkGenerator.INSTANCE);
    }

    public static World createWorld(final String name, final World.Environment environment, final ChunkGenerator generator) {
        final WorldCreator creator = WorldCreator.name(name).environment(environment).generator(generator);
        return creator.createWorld();
    }

}
