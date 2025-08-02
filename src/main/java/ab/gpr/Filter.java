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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Filter {
  public static final String ALERT_FILE = "FilterAlert.txt";
  public static final String REPLACE_FILE = "FilterReplace.txt";
  protected String folder = ".";
  protected Set<String> alert = Collections.emptySet();
  protected Map<String, String> mapMask = Collections.emptyMap();

  public Filter() {
    for (Runnable runnable : new Runnable[]{this::loadAlert, this::loadReplace}) {
      try {
        runnable.run();
      } catch (UncheckedIOException e) {
        if (!(e.getCause() instanceof NoSuchFileException)) throw e;
      }
    }
  }

  public String mask(String s) {
    for (Map.Entry<String, String> entry : mapMask.entrySet()) s = s.replace(entry.getKey(), entry.getValue());
    for (String a : alert) if (s.contains(a)) throw new IllegalStateException(a + " word alert. " + s);
    return s;
  }

  public String unmask(String s) {
    for (Map.Entry<String, String> entry : mapMask.entrySet()) s = s.replace(entry.getValue(), entry.getKey());
    return s;
  }

  protected String readString(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  protected void writeString(Path path, String s) {
    try {
      Files.writeString(path, s);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  protected void loadAlert() {
    alert = Arrays.stream(readString(Paths.get(folder, ALERT_FILE)).split("\r?\n"))
        .filter(s -> !s.isEmpty()).collect(Collectors.toCollection(LinkedHashSet::new));
  }

  protected void saveAlert() {
    writeString(Paths.get(folder, ALERT_FILE), alert.stream().map(s -> s + "\n").collect(Collectors.joining()));
  }

  protected void loadReplace() {
    Map<String, String> mapMask = new LinkedHashMap<>();
    String key = null;
    for (String v : readString(Paths.get(folder, REPLACE_FILE)).split("\r?\n")) {
      if (key == null) {
        key = v;
      } else {
        mapMask.put(key, v);
        key = null;
      }
    }
    this.mapMask = mapMask;
  }

  protected void saveReplace() {
    writeString(Paths.get(folder, REPLACE_FILE),
        mapMask.entrySet().stream().map(e -> e.getKey() + "\n" + e.getValue() + "\n").collect(Collectors.joining()));
  }

}
