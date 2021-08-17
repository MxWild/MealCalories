package com.gmail.mxwild.mealcalories.repository.jpa;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.MealRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.mxwild.mealcalories.common.Constants.USER_ID;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, Integer userId) {
        meal.setUser(entityManager.getReference(User.class, userId));
        if (meal.isNew()) {
            entityManager.persist(meal);
            return meal;
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }
        return entityManager.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(Integer id, Integer userId) {
        return entityManager.createQuery(
                "DELETE FROM " + Meal.class.getSimpleName() + " m WHERE m.id=:id AND m.user.id=:userId")
                .setParameter("id", id)
                .setParameter(USER_ID, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        Meal meal = entityManager.find(Meal.class, id);
        return meal != null && meal.getUser().getId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return entityManager
                .createQuery("SELECT m FROM "
                        + Meal.class.getSimpleName()
                        + " m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
                .setParameter(USER_ID, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createQuery(
                "SELECT m FROM " + Meal.class.getSimpleName()
                        + " m WHERE m.user.id=:userId " +
                        "AND m.dateTime >= :startDateTime " +
                        "AND m.dateTime < :endDateTime " +
                        "ORDER BY m.dateTime DESC")
                .setParameter(USER_ID, userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}
