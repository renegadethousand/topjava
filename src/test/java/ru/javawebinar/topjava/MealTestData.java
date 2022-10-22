package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_1 = START_SEQ + 3;
    public static final int MEAL_2 = START_SEQ + 4;
    public static final int MEAL_3 = START_SEQ + 5;
    public static final int MEAL_4 = START_SEQ + 6;

    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(MEAL_1, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_2, LocalDateTime.parse("2020-01-30T13:00:00"), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_3, LocalDateTime.parse("2020-01-30T20:00:00"), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_4, LocalDateTime.parse("2020-01-31T00:00:00"), "Еда на граничное значение", 100);

    public static Meal getNew() {
        return new Meal(LocalDateTime.parse("2020-01-30T15:00:00"), "Завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.parse("2020-01-31T15:00:00"));
        updated.setDescription("ужин");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
