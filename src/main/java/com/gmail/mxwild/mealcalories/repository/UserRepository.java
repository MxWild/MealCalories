package com.gmail.mxwild.mealcalories.repository;


import com.gmail.mxwild.mealcalories.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(Integer id);

    User get(Integer id);

    User getByEmail(String email);

    List<User> getAll();
}
