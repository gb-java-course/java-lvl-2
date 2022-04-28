package ru.gb.level2.lesson3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson3 {

    public static void main(String[] args) {
        String[] words = {
                "word1", "word2", "word1", "word1",
                "word1", "word3", "word4", "word5",
                "word6", "word1", "word6", "word3"
        };
        printAndCountUniqueWords(words);
        //words counters: {word1=5, word3=2, word2=1, word5=1, word4=1, word6=2}
        //unique words: [word2, word5, word4]

        testPhonebook();
        //Ivanov: [+79998887766, +79998887711, +79998886644]
        //Fyodorov: [+79998887755]
        //Sergeev: [+79998887744]
        //Alexandrov: [+79998887733, +79998886655]
        //Mihailov: [+79998887722]
        //Alexeev: null
    }

    private static void printAndCountUniqueWords(String[] words) {
        Map<String, Integer> wordsCounters = new HashMap<>();
        for (var word : words) {
            wordsCounters.merge(word, 1, Integer::sum);
        }
        System.out.printf("words counters: %s%n", wordsCounters);

        List<String> uniqueWords = new ArrayList<>();
        for (var entry : wordsCounters.entrySet()) {
            if (entry.getValue() > 1) {
                continue;
            }
            uniqueWords.add(entry.getKey());
        }
        System.out.printf("unique words: %s%n", uniqueWords);
    }

    private static void testPhonebook() {
        Phonebook phonebook = new Phonebook();

        phonebook.add("Ivanov", "+79998887766");
        phonebook.add("Fyodorov", "+79998887755");
        phonebook.add("Sergeev", "+79998887744");
        phonebook.add("Alexandrov", "+79998887733");
        phonebook.add("Mihailov", "+79998887722");
        phonebook.add("Ivanov", "+79998887711");
        phonebook.add("Alexandrov", "+79998886655");
        phonebook.add("Ivanov", "+79998886644");

        System.out.printf("Ivanov: %s%n", phonebook.get("Ivanov"));
        System.out.printf("Fyodorov: %s%n", phonebook.get("Fyodorov"));
        System.out.printf("Sergeev: %s%n", phonebook.get("Sergeev"));
        System.out.printf("Alexandrov: %s%n", phonebook.get("Alexandrov"));
        System.out.printf("Mihailov: %s%n", phonebook.get("Mihailov"));
        System.out.printf("Alexeev: %s%n", phonebook.get("Alexeev"));
    }
}
