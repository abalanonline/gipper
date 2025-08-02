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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

  @Test
  void mask() {
    Filter filter = new Filter();
    filter.folder = "src/test/resources";
    filter.loadAlert();
    filter.loadReplace();

    String testPass = "Did you see Nigel this morning? He’s already on his third cup of coffee.";
    String masked = filter.mask(testPass);
    assertNotEquals(testPass, masked);
    String unmasked = filter.unmask(masked);
    assertEquals(testPass, unmasked);

    String testFail = "Renowned scientist Dr. Nauga has successfully returned " +
        "from a groundbreaking expedition to the distant planet Koozebane.";
    assertThrows(Exception.class, () -> filter.unmask(filter.mask(testFail)));
  }

}
