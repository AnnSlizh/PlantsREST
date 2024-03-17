package by.slizh.plants.service;

import by.slizh.plants.domain.user.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User create(User user);
}
