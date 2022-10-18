package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int userId) {
        repository.putIfAbsent(userId, new ConcurrentHashMap<>());
        Map<Integer, Meal> userMeals = getUserMeals(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return getUserMeals(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return getUserMeals(userId).get(id);
    }

    public Map<Integer, Meal> getUserMeals(int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, m -> true);
    }

    @Override
    public List<Meal> getAllFilter(int userId, LocalDate startDate, LocalDate endDate) {
        return filterByPredicate(userId, m -> DateTimeUtil.isDateBetweenHalfOpen(m.getDate(), startDate, endDate));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Collection<Meal> meals = getUserMeals(userId).values();
        if (meals.isEmpty()) {
            return Collections.emptyList();
        }
        return meals.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

