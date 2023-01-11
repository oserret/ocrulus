package com.oserret.ocrulus.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Utilities {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String[] COLORS = { "White", "Black", "Pink", "Yellow", "Blue" };
    private static final String[] ANIMALS = { "Parrots", "Horses", "Rabbits", "Eagles", "Elaphants" };
    private static final String[] ADJECTIVES = { "Gracefully", "Neatly", "Wonderfully", "Enjoyably", "Dreamily" };
    private static final String[] VERBS = { "Swim", "Fly", "Dive", "Move", "Run", "Relax" };

    private static Random rand = new Random();

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        objectMapper.setDateFormat(dateFormat);
        return objectMapper.writeValueAsString(object);
    }

    public static String generateDicPassword() {
        StringBuilder pwd = new StringBuilder();

        pwd.append((int) (rand.nextInt(10) + 10));
        pwd.append(COLORS[rand.nextInt(COLORS.length)]);
        pwd.append(ANIMALS[rand.nextInt(ANIMALS.length)]);
        pwd.append(ADJECTIVES[rand.nextInt(ADJECTIVES.length)]);
        pwd.append(VERBS[rand.nextInt(VERBS.length)]);
        return pwd.toString();
    }

}
