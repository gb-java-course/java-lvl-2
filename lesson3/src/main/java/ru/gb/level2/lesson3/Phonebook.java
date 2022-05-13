package ru.gb.level2.lesson3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class Phonebook {

    private final Map<String, List<String>> phonesByLastName = new HashMap<>();

    public void add(String lastName, String phone) {
        if (isNull(lastName)) {
            throw new IllegalArgumentException("Last name shouldn't be null");
        }
        if (isNull(phone)) {
            throw new IllegalArgumentException("Phone shouldn't be null");
        }

        if (phonesByLastName.containsKey(lastName)) {
            phonesByLastName.get(lastName).add(phone);
            return;
        }

        phonesByLastName.put(lastName, new ArrayList<>() {{ add(phone); }});
    }

    public List<String> get(String lastName) {
        return phonesByLastName.get(lastName);
    }
}
