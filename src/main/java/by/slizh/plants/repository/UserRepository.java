package by.slizh.plants.repository;

import by.slizh.plants.domain.user.Role;
import by.slizh.plants.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void create(User user);

    void insertUserRole(Long userId, Role role);
}
