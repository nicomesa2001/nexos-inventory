package com.gnencisom.nexosinventory.service;

import com.gnencisom.nexosinventory.model.User;
import com.gnencisom.nexosinventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Agrega un nuevo usuario y establece la fecha y hora de creación
     */
    public User addUser(User user) {
        // Validar que el nombre no sea nulo o vacío
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        }

        // Validar que la edad sea un número positivo
        if (user.getAge() <= 0) {
            throw new IllegalArgumentException("La edad del usuario debe ser mayor que 0.");
        }

        // Validar que el rol no sea nulo
        if (user.getRole() == null) {
            throw new IllegalArgumentException("El rol del usuario no puede estar vacío.");
        }

        // Validar si el usuario ya existe (usando el nombre como ejemplo)
        Optional<User> existingUser = userRepository.findByName(user.getName());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El usuario con el nombre '" + user.getName() + "' ya existe.");
        }

        // Establecer la fecha y hora actual del sistema
        user.setJoiningDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }
}
