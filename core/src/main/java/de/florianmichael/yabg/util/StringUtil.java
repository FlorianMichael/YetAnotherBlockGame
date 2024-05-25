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

package de.florianmichael.yabg.util;

// Thank you Mojang, very cool
public class StringUtil {

    public static boolean isValidChar(char c) {
        return c != 167 && c >= ' ' && c != 127;
    }

    public static String stripInvalidChars(String string) {
        return stripInvalidChars(string, false);
    }

    public static String stripInvalidChars(String string, boolean allowLinebreak) {
        StringBuilder stringBuilder = new StringBuilder();

        for(char c : string.toCharArray()) {
            if (isValidChar(c)) {
                stringBuilder.append(c);
            } else if (allowLinebreak && c == '\n') {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

}
