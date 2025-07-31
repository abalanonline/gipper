/*
 * Copyright (C) 2025 Aleksei Balan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ab.gpr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Prompt {

  static List<String> read(InputStream inputStream) {
    List<String> list = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder();
    for (String s : new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList())) {
      if (s.isEmpty()) {
        list.add(stringBuilder.toString().trim());
        stringBuilder.setLength(0);
      } else stringBuilder.append(s).append('\n');
    }
    if (stringBuilder.length() > 0) list.add(stringBuilder.toString().trim());
    return Collections.unmodifiableList(list);
  }

}
