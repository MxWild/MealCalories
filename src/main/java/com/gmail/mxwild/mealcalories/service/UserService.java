package com.gmail.mxwild.mealcalories.service;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User create(User user) {
        return repository.save(user);
    }

    public void update(User user) {
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        return checkNotFound(repository.getByEmail(email), "email = " + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
