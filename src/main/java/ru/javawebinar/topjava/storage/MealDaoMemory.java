package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoMemory implements MealDao{

    private Map<Integer, Meal> storage;

    public MealDaoMemory() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public Meal add(Meal meal) {
        storage.put(meal.getId(), meal);
        return getById(meal.getId());
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        storage.putIfAbsent(meal.getId(), meal);
        return getById(meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal getById(int mealId) {
        return storage.get(mealId);
    }
}
