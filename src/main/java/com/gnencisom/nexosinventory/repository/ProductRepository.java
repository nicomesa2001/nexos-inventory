package com.gnencisom.nexosinventory.repository;

import com.gnencisom.nexosinventory.model.Product;
import com.gnencisom.nexosinventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Método para buscar un producto por nombre (para evitar duplicados al registrar)
    Optional<Product> findByName(String name);

    // Método para buscar productos por fecha de entrada
    List<Product> findByDateOfEntry(LocalDate dateOfEntry);

    // Método para buscar productos por el usuario que los registró
    List<Product> findByUser(User user);

    // Método para buscar productos por nombre y usuario (consulta compuesta)
    List<Product> findByNameAndUser(String name, User user);
}
