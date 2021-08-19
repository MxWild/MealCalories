package com.gmail.mxwild.mealcalories.web;


import com.gmail.mxwild.mealcalories.common.Profiles;
import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.web.meal.MealRestController;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.gmail.mxwild.mealcalories.util.DateTimeUtil.parseLocalDate;
import static com.gmail.mxwild.mealcalories.util.DateTimeUtil.parseLocalTime;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private ClassPathXmlApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
        mealController = springContext.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
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
                    meal = mealController.get(getIntegerValue(req, "id"));
                }
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("mealForm.jsp").forward(req, resp);
                break;
            case "delete":
                Integer mealId = getIntegerValue(req, "id");
                log.info("Delete meal with mealId = {}", mealId);
                mealController.delete(mealId);
                resp.sendRedirect("meals");
                break;
            case "filter":
                LocalDate startDate = parseLocalDate(req.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(req.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(req.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(req.getParameter("startTime"));
                req.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
            case "all":
            default:
                log.info("get all meals");
                req.setAttribute("meals", mealController.getAll());
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mealId = req.getParameter("id");

        Meal meal;

        if (mealId.isEmpty()) {
            meal = new Meal(LocalDateTime.parse(req.getParameter("dateTime")),
                    req.getParameter("description"),
                    getIntegerValue(req, "calories"));
            mealController.create(meal);
        } else {
            meal = mealController.get(getIntegerValue(req, "id"));
            meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
            meal.setDescription(req.getParameter("description"));
            meal.setCalories(getIntegerValue(req, "calories"));
            mealController.update(meal, getIntegerValue(req, "id"));
        }
        resp.sendRedirect("meals");
    }

    private Integer getIntegerValue(HttpServletRequest request, String parameter) {
        return Integer.valueOf(request.getParameter(parameter));
    }
}
