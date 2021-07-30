package com.gmail.mxwild.mealcalories.repository.inmemory;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static final Logger log = getLogger(InMemoryUserRepository.class);

    @Override
    public User save(User user) {
        log.info("Save user = {}", user);
        return user;
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Delete user with id = {}", id);
        return false;
    }

    @Override
    public User get(Integer id) {
        log.info("Get user with id = {}", id);
        return null;
    }

    @Override
    public User getByEmail(String email) {
        log.info("Get user with email = {}", email);
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("Get all users");
        return null;
    }
}
