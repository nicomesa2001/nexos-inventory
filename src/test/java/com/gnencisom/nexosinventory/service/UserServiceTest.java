package com.gnencisom.nexosinventory.service;

import com.gnencisom.nexosinventory.model.Role;
import com.gnencisom.nexosinventory.model.User;
import com.gnencisom.nexosinventory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks
        user = new User();
        user.setName("Juan Pérez");
        user.setAge(30);
        user.setRole(new Role(1L, "Asesor de ventas"));
    }

    @Test
    void addUser_emptyName_shouldThrowException() {
        // Arrange
        user.setName("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("El nombre del usuario no puede estar vacío.", exception.getMessage());
    }

    @Test
    void addUser_invalidAge_shouldThrowException() {
        // Arrange
        user.setAge(-1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("La edad del usuario debe ser mayor que 0.", exception.getMessage());
    }
}
