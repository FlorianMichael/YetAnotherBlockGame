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

import org.bukkit.Material;

import java.util.Map;
import java.util.Random;

public record Phase(String name, WrappedMaterial[] materials) {

    private static final Random RAND = new Random(); // Random hold for phase lookups

    public boolean hasFinished(final Map<Material, Integer> blockBreaks) {
        for (final WrappedMaterial material : materials) {
            if (blockBreaks.getOrDefault(material.material(), 0) < material.amount()) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(final Material material) {
        for (final WrappedMaterial wrappedMaterial : materials) {
            if (wrappedMaterial.material().equals(material)) {
                return true;
            }
        }
        return false;
    }

    public Material rand() {
        int total = 0;
        for (final WrappedMaterial material : materials) {
            total += material.possibility();
        }
        int rand = RAND.nextInt(total);
        for (final WrappedMaterial material : materials) {
            rand -= material.possibility();
            if (rand < 0) {
                return material.material();
            }
        }
        return null;
    }

}
