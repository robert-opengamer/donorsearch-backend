package ru.donorsearch.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donorsearch.backend.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    @Override
    boolean existsById(Long aLong);
}
