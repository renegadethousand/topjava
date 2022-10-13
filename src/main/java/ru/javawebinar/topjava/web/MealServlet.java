package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealDao;
import ru.javawebinar.topjava.storage.MealMemoryDao;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MealMemoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        final String mealId = request.getParameter("id");
        int caloriesPerDay = 2000;
        if (action == null) {
            action = "default";
        }
        switch (action) {
            case "delete":
                mealDao.delete(Integer.parseInt(mealId));
                response.sendRedirect("meals");
                break;
            case "edit":
                Meal meal = mealDao.getById(Integer.parseInt(mealId));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "add":
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("meals", MealsUtil.filteredByStreams(
                        mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay)
                );
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idString = request.getParameter("id");
        Meal meal = createMeal(request);
        if (idString.isEmpty()) {
            mealDao.add(meal);
        } else {
            meal.setId(Integer.parseInt(idString));
            mealDao.update(meal);
        }
        response.sendRedirect("meals");
    }

    private Meal createMeal(HttpServletRequest request) {
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories").trim());
        String dateTime = request.getParameter("dateTime");
        LocalDateTime ldt = LocalDateTime.parse(dateTime);
        return new Meal(ldt, description, calories);
    }
}
