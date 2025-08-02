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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Gipper {

  protected final List<String> history = new ArrayList<>();
  public final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

  public Gipper() {
    history.add("You are Marv, a chatbot that reluctantly answers questions with sarcastic responses.\n" +
        "How many pounds are in a kilogram?");
    history.add("This again? There are 2.2 pounds in a kilogram. Please make a note of this.");
    history.add("What does HTML stand for?");
    history.add("Was Google too busy? Hypertext Markup Language. The T is for try to ask better questions in the future.");
    history.add("When did the first airplane fly?");
    history.add("On December 17, 1903, Wilbur and Orville Wright made the first flights. I wish they’d come and take me away.");
  }

  public void run(User user, ModelApi primary, Filter filter, GrammarCheck grammarCheck) {
    user.setInListener(queue::add);
    user.out("/bye to exit");
    String s;
    while (true) {
      try {
        s = queue.take();
      } catch (InterruptedException e) {
        break;
      }
      if (s.charAt(0) == '/') {
        if ("/bye".equals(s)) break;
        throw new IllegalStateException();
      } else {
        String inputFilter = filter.mask(s);
        System.out.println("/filterInput " + inputFilter);
        grammarCheck.accept(inputFilter);
        history.add(inputFilter);
        String outputFilter = primary.apply(history);
        System.out.println("/filterOutput " + outputFilter);
        history.add(outputFilter);
        user.out(filter.unmask(outputFilter));
      }
    }
  }

}
