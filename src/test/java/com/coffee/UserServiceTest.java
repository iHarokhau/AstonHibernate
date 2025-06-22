package com.coffee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());

        doNothing().when(userDAO).save(user);

        userService.createUser(user);

        verify(userDAO, times(1)).save(user);
    }

    @Test
    public void testReadUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());

        when(userDAO.findById(1L)).thenReturn(user);

        User foundUser = userService.readUser(1L);

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        assertEquals("john.doe@example.com", foundUser.getEmail());
        assertEquals(30, foundUser.getAge());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());

        when(userDAO.findById(1L)).thenReturn(user);
        doNothing().when(userDAO).update(user);

        userService.updateUser(1L, "John Updated", "john.updated@example.com", 35);

        verify(userDAO, times(1)).update(user);
        assertEquals("John Updated", user.getName());
        assertEquals("john.updated@example.com", user.getEmail());
        assertEquals(35, user.getAge());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());

        when(userDAO.findById(1L)).thenReturn(user);
        doNothing().when(userDAO).delete(user);

        userService.deleteUser(1L);

        verify(userDAO, times(1)).delete(user);
    }

    @Test
    public void testListAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setAge(30);
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setAge(25);
        user2.setCreatedAt(LocalDateTime.now());

        when(userDAO.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.listAllUsers(0);

        assertNotNull(users);
        assertEquals(2, users.size());
    }
}