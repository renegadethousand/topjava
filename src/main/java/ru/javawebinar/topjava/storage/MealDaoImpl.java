package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao{

    private Map<Integer, Meal> storage;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    public MealDaoImpl(Map<Integer, Meal> storage) {
        this.storage = storage;
    }

    @Override
    public void add(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public void update(Meal meal) {
        storage.putIfAbsent(meal.getId(), meal);
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
