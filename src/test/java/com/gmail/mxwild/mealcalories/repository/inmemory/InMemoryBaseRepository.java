package com.gmail.mxwild.mealcalories.repository.inmemory;

import com.gmail.mxwild.mealcalories.model.BaseEntity;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.gmail.mxwild.mealcalories.common.Constants.START_SEQ;
import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryBaseRepository<T extends BaseEntity> {

    private static final Logger log = getLogger(InMemoryBaseRepository.class);

    protected final Map<Integer, T> inMemoryRepository = new ConcurrentHashMap<>();
    protected final AtomicInteger counter = new AtomicInteger(START_SEQ);

    public T save(T entity) {
        log.info("Save entity = {}", entity);
        Objects.requireNonNull(entity, "entity must not be null");
        if (entity.isNew()) {
            entity.setId(counter.incrementAndGet());
            inMemoryRepository.put(entity.getId(), entity);
            return entity;
        }
        return inMemoryRepository.computeIfPresent(entity.getId(), (id, oldUser) -> entity);
    }

    public T get(Integer id) {
        log.info("Get entity with id = {}", id);
        return inMemoryRepository.get(id);
    }

    Collection<T> getCollection() {
        return inMemoryRepository.values();
    }

    public boolean delete(Integer id) {
        log.info("Delete entity with id = {}", id);
        return inMemoryRepository.remove(id) != null;
    }
}
