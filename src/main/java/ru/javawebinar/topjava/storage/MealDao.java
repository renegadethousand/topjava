package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    public Meal add(Meal meal);

    public void delete(int id);

    public Meal update(Meal meal);

    public List<Meal> getAll();

    public Meal getById(int mealId);
}
