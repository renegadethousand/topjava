package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    Meal add(Meal meal);

    void delete(int id);

    Meal update(Meal meal);

    List<Meal> getAll();

    Meal getById(int id);
}
