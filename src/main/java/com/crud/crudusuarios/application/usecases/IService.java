package com.crud.crudusuarios.application.usecases;

import com.crud.crudusuarios.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface IService {
    List<User> listAllUsers();
    Optional<User> findById(Long id);
    User createUsers(User user);
    User updateUsers(Long id, User user);
    void deleteUser(Long id);

}
