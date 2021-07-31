package com.gmail.mxwild.mealcalories.web;


import com.gmail.mxwild.mealcalories.common.Constants;
import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.repository.MealRepository;
import com.gmail.mxwild.mealcalories.repository.inmemory.InMemoryMealRepository;
import com.gmail.mxwild.mealcalories.util.MealsUtil;
import com.gmail.mxwild.mealcalories.util.SecurityUtil;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init() {
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        switch (action == null ? "all" : action) {
            case "create":
            case "update":
                Meal meal;
                if ("create".equals(action)) {
                    meal = new Meal(LocalDateTime.now(), "", 1000);
                } else {
                    meal = repository.get(getIntegerValue(req, "mealId"), SecurityUtil.authUserId());
                }
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("mealForm.jsp").forward(req, resp);
                break;
            case "delete":
                Integer mealId = getIntegerValue(req, "mealId");
                log.info("Delete meal with mealId = {}", mealId);
                repository.delete(mealId, SecurityUtil.authUserId());
                resp.sendRedirect("meals");
                break;
            case "all":
            default:
                log.info("get all meals");
                req.setAttribute("meals", MealsUtil.getAll(
                        repository.getAll(SecurityUtil.authUserId()),
                        Constants.DEFAULT_CALORIES_PER_DAY)
                );
//        resp.sendRedirect("meals.jsp");
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mealId = req.getParameter("mealId");

        Meal meal;

        if (mealId.isEmpty()) {
            meal = new Meal(LocalDateTime.parse(req.getParameter("dateTime")),
                    req.getParameter("description"),
                    getIntegerValue(req, "calories"));
        } else {
            meal = repository.get(Integer.valueOf(mealId), SecurityUtil.authUserId());
            meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
            meal.setDescription(req.getParameter("description"));
            meal.setCalories(getIntegerValue(req, "calories"));
        }
        log.info(meal.isNew() ? "Create meal = {}" : "Update meal = {}", meal);
        repository.save(meal, SecurityUtil.authUserId());
        resp.sendRedirect("meals");
    }

    private Integer getIntegerValue(HttpServletRequest request, String parameter) {
        return Integer.valueOf(request.getParameter(parameter));
    }
}
