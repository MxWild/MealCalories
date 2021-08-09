package com.gmail.mxwild.mealcalories.repository.inmemory;

import com.gmail.mxwild.mealcalories.UserTestData;
import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.mxwild.mealcalories.UserTestData.ADMIN;
import static com.gmail.mxwild.mealcalories.UserTestData.USER;
import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    private static final Logger log = getLogger(InMemoryUserRepository.class);

    public void init() {
        inMemoryRepository.clear();
        inMemoryRepository.put(UserTestData.USER_ID, USER);
        inMemoryRepository.put(UserTestData.ADMIN_ID, ADMIN);
        counter.getAndSet(UserTestData.ADMIN_ID + 1);
    }

    @Override
    public List<User> getAll() {
        log.info("Get all users");
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("Get user with email = {}", email);
        return getCollection()
                .stream()
                .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
