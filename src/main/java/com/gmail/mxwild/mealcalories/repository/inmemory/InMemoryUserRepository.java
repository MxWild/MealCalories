package com.gmail.mxwild.mealcalories.repository.inmemory;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    private static final Logger log = getLogger(InMemoryUserRepository.class);

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
