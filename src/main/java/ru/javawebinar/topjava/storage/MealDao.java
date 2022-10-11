package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    public void add(Meal meal);

    public void delete(int mealId);

    public void update(Meal meal);

    public List<Meal> getAll();

    public Meal getById(int mealId);
}
