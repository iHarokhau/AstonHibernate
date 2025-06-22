package com.coffee;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDAOTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    private static UserDAO userDAO;

    @BeforeAll
    public static void setUp() {
        System.setProperty("hibernate.connection.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgreSQLContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgreSQLContainer.getPassword());
        userDAO = new UserDAO();
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testSaveAndFindById() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());

        userDAO.save(user);

        User foundUser = userDAO.findById(user.getId());
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        assertEquals("john.doe@example.com", foundUser.getEmail());
        assertEquals(30, foundUser.getAge());
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        user1.setName("Jane Doe");
        user1.setEmail("jane.doe@example.com");
        user1.setAge(25);
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setName("Alice Smith");
        user2.setEmail("alice.smith@example.com");
        user2.setAge(35);
        user2.setCreatedAt(LocalDateTime.now());

        userDAO.save(user1);
        userDAO.save(user2);

        List<User> users = userDAO.findAll();
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setName("Bob Johnson");
        user.setEmail("bob.johnson@example.com");
        user.setAge(40);
        user.setCreatedAt(LocalDateTime.now());

        userDAO.save(user);

        user.setName("Bob Updated");
        user.setAge(45);
        userDAO.update(user);

        User updatedUser = userDAO.findById(user.getId());
        assertNotNull(updatedUser);
        assertEquals("Bob Updated", updatedUser.getName());
        assertEquals(45, updatedUser.getAge());
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setName("Charlie Brown");
        user.setEmail("charlie.brown@example.com");
        user.setAge(20);
        user.setCreatedAt(LocalDateTime.now());

        userDAO.save(user);

        userDAO.delete(user);

        User deletedUser = userDAO.findById(user.getId());
        assertNull(deletedUser);
    }
}