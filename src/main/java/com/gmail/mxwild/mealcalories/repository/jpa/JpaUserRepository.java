package com.gmail.mxwild.mealcalories.repository.jpa;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.UserRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User get(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = entityManager
                .createQuery("SELECT u FROM " + User.class.getSimpleName() + " u LEFT JOIN FETCH u.roles WHERE u.email = ?1")
                .setParameter(1, email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return entityManager
                .createQuery("SELECT u FROM " + User.class.getSimpleName() + " u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
                .getResultList();
    }
}
