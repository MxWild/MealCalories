package com.gmail.mxwild.mealcalories.service;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.gmail.mxwild.mealcalories.util.ValidationUtil.checkNotFoundWithId;
import static com.gmail.mxwild.mealcalories.util.ValidationUtil.checkNotFound;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email = " + email);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
