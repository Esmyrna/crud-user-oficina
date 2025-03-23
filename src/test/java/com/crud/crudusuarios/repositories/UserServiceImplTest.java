package com.crud.crudusuarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.crudusuarios.application.services.UserServiceImpl;
import com.crud.crudusuarios.domain.entities.User;
import com.crud.crudusuarios.infra.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setNome("Esmyrna");
        user.setEmail("esmyrna@gmail.com");
    }

    @Test
    void testListAllUsers(){
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userServiceImpl.listAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("Esmyrna", users.get(0).getNome());
    }

    @Test
    void testFindById(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userServiceImpl.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("Esmyrna", foundUser.get().getNome());
    }

    @Test
    void testCreateUsers(){
        when(userRepository.save(user)).thenReturn(user);

        User createUser = userServiceImpl.createUsers(user);

        assertNotNull(createUser);
        assertEquals("Esmyrna", createUser.getNome());
        assertEquals("esmyrna@gmail.com", createUser.getEmail());
    }

    @Test
    void testUpdateUsers_Sucess(){
        User updateUser = new User();
        updateUser.setNome("Nova Esmyrna");
        updateUser.setEmail("nova@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updateUser);

        User result = userServiceImpl.updateUsers(1L, updateUser);

        assertNotNull(result);
        assertEquals("Nova Esmyrna", result.getNome());
        assertEquals("nova@email.com", result.getEmail());
    }

    @Test
    void testUpdateUsers_UserNotFound(){
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, 
        () -> userServiceImpl.updateUsers(2L, user));

        assertEquals("User with id 2 not found", exception.getMessage());
    }
  @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userServiceImpl.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_NotFound(){
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
         () -> userServiceImpl.deleteUser(2L));
    }
   

}
