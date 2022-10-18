package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            User user = adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            User user2 = adminUserController.create(new User(null, "anotherUserName", "anotherEmail@mail.ru", "password", Role.USER));

            List<User> users = adminUserController.getAll();
            List<Meal> meals = new ArrayList<>(MealsUtil.meals);

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            SecurityUtil.setAuthUserId(user.getId());
            for (Meal meal : meals) {
                mealRestController.update(meal, meal.getId());
            }
            SecurityUtil.setAuthUserId(user2.getId());
            for (Meal meal : meals) {
                mealRestController.update(meal, meal.getId());
            }
        }
    }
}
