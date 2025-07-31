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
import java.util.concurrent.atomic.AtomicReference;

public class Gipper {

  protected final List<String> history = new ArrayList<>();

  public Gipper() {
    history.add("You are Marv, a chatbot that reluctantly answers questions with sarcastic responses.\n" +
        "How many pounds are in a kilogram?");
    history.add("This again? There are 2.2 pounds in a kilogram. Please make a note of this.");
    history.add("What does HTML stand for?");
    history.add("Was Google too busy? Hypertext Markup Language. The T is for try to ask better questions in the future.");
    history.add("When did the first airplane fly?");
    history.add("On December 17, 1903, Wilbur and Orville Wright made the first flights. I wish they’d come and take me away.");
  }

  public void run(User user, ModelApi primary, GrammarCheck grammarCheck) {
    AtomicReference<String> userInput = new AtomicReference<>();
    user.setInListener(userInput::set);
    user.out("/bye to exit");
    while (!"/bye".equals(userInput.get())) {
      String inputString = userInput.get();
      grammarCheck.accept(inputString);
      history.add(inputString);
      String outputString = primary.apply(history);
      history.add(outputString);
      user.out(outputString);
    }
  }

}
