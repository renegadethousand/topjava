package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealDao;
import ru.javawebinar.topjava.storage.MealDaoMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealDao mealDao = new MealDaoMemory();

    @Override
    public void init() throws ServletException {
        for (Meal meal : MealsUtil.meals) {
            mealDao.add(meal);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        final String action = request.getParameter("action");
        final String mealId = request.getParameter("id");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.filteredByStreams(
                    mealDao.getAll(), LocalTime.of(1, 0), LocalTime.of(23, 0), 10000)
            );
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                mealDao.delete(Integer.parseInt(mealId));
                request.setAttribute("meals", MealsUtil.filteredByStreams(
                        mealDao.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59, 59), 10000)
                );
                response.sendRedirect("meals");
        }
    }
}
