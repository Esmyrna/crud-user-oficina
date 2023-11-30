package com.crud.crudusuarios.application.services;

import com.crud.crudusuarios.infra.repositories.UserRepository;
import com.crud.crudusuarios.application.usecases.IService;
import com.crud.crudusuarios.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUsers(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUsers(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setNome(user.getNome());
            updatedUser.setEmail(user.getEmail());
            return userRepository.save(updatedUser);
        } else {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        User existingUser = existingUserOptional.get();
        userRepository.delete(existingUser);
    }


}
