package com.gmail.mxwild.mealcalories.web.user;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.gmail.mxwild.mealcalories.util.ValidationUtil.assureIdConsistent;
import static com.gmail.mxwild.mealcalories.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class UserController {

    private static final Logger log = getLogger(UserController.class);

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    public User create(User user) {
        log.info("Create user {}", user);
        checkNew(user);
        return service.create(user);
    }

    public User get(int id) {
        log.info("get user with id {}", id);
        return service.get(id);
    }

    public User getByEmail(String email) {
        log.info("get user by email {}", email);
        return service.getByEmail(email);
    }

    public void update(User user, int id) {
        log.info("update user {} with id = {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void delete(int id) {
        log.info("delete user with id {}", id);
        service.delete(id);
    }

    public List<User> getAll() {
        log.info("get all users");
        return service.getAll();
    }
}
