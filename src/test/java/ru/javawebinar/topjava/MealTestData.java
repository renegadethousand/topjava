package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID_1 = START_SEQ + 3;
    public static final int MEAL_ID_2 = START_SEQ + 4;
    public static final int MEAL_ID_3 = START_SEQ + 5;
    public static final int MEAL_ID_4 = START_SEQ + 6;

    public static final int NOT_FOUND = 100999;

    public static final Meal meal1 = new Meal(MEAL_ID_1,
            LocalDateTime.of(2020, 1, 30, 10, 0, 0),
            "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID_2,
            LocalDateTime.of(2020, 1, 30, 13, 0, 0),
            "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID_3,
            LocalDateTime.of(2020, 1, 30, 20, 0, 0),
            "Ужин", 500);

    public static final Meal meal4 = new Meal(MEAL_ID_4,
            LocalDateTime.of(2020, 1, 31, 0, 0, 0),
            "Еда на граничное значение", 100);

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
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
