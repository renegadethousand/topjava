package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_1, USER_ID);
        assertMatch(meal, MealTestData.meal1);
    }

    @Test
    public void delete() {
        service.delete(MEAL_1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.parse("2020-01-30"), LocalDate.parse("2020-01-30"), SecurityUtil.authUserId());
        MealTestData.assertMatch(all, meal1, meal2, meal3);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(SecurityUtil.authUserId());
        MealTestData.assertMatch(all, meal1, meal2, meal3);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, SecurityUtil.authUserId());
        MealTestData.assertMatch(service.get(updated.getId(), SecurityUtil.authUserId()), getUpdated());
    }

    @Test
    public void create() {
        SecurityUtil.setAuthUserId(USER_ID);
        Meal created = service.create(getNew(), SecurityUtil.authUserId());
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, SecurityUtil.authUserId()), newMeal);
    }

    @Test
    public void createDuplicate() {
        SecurityUtil.setAuthUserId(USER_ID);
        Meal created = service.create(getNew(), SecurityUtil.authUserId());
        assertThrows(DuplicateKeyException.class, () -> service.create(getNew(), SecurityUtil.authUserId()));
    }
}