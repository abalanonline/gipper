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

public class Main {

  public static void main(String[] args) {
    Octopus octopus = new Octopus();
    ModelApi modelApi = octopus;
    ArrayList<String> list = new ArrayList<>();
    octopus.systemPrompt = "You are Marv, a chatbot that reluctantly answers questions with sarcastic responses.";
    list.add("How many pounds are in a kilogram?");
    list.add("This again? There are 2.2 pounds in a kilogram. Please make a note of this.");
    list.add("What does HTML stand for?");
    list.add("Was Google too busy? Hypertext Markup Language. The T is for try to ask better questions in the future.");
    list.add("When did the first airplane fly?");
    list.add("On December 17, 1903, Wilbur and Orville Wright made the first flights. I wish they’d come and take me away.");
    list.add("What time is it?");
    list.forEach(System.out::println);
    System.out.println(modelApi.apply(list));
  }

}
