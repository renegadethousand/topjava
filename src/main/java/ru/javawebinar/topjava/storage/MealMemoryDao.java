package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryDao implements MealDao {

    private Map<Integer, Meal> storage;

    private AtomicInteger idCount = new AtomicInteger(0);

    public MealMemoryDao() {
        this.storage = new ConcurrentHashMap<>();
        for (Meal meal : MealsUtil.meals) {
            add(meal);
        }
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(idCount.getAndIncrement());
        storage.put(meal.getId(), meal);
        return getById(meal.getId());
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        storage.put(meal.getId(), meal);
        return getById(meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal getById(int id) {
        return storage.get(id);
    }
}
