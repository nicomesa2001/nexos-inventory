package com.gnencisom.nexosinventory.service;

import com.gnencisom.nexosinventory.model.Product;
import com.gnencisom.nexosinventory.model.User;
import com.gnencisom.nexosinventory.repository.ProductRepository;
import com.gnencisom.nexosinventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Agrega un nuevo producto, validando que no exista uno con el mismo nombre
     */
    public Product addProduct(Product product) {
        // Verificar si el usuario existe antes de asociarlo al producto
        User user = userRepository.findById(product.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + product.getUser().getId() + " no existe."));

        // Ahora que sabemos que el usuario existe, podemos proceder con la inserción del producto
        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("El producto con el mismo nombre ya existe.");
        }

        // Si la fecha de ingreso es nula, establecer la fecha actual
        if (product.getDateOfEntry() == null) {
            product.setDateOfEntry(LocalDate.now());
        }

        // Validar que la fecha de ingreso no sea en el futuro
        if (product.getDateOfEntry().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha actual.");
        }

        return productRepository.save(product);
    }

    /**
     * Obtiene todos los productos
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Elimina un producto, validando que el usuario sea el mismo que lo registró
     */
    public void deleteProduct(Long id, Long userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        if (!product.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("No puedes eliminar este producto. Solo el usuario que lo registró puede hacerlo.");
        }
        productRepository.delete(product);
    }

    /**
     * Edita un producto, asegurándose de que cumpla las mismas reglas que al agregar un nuevo producto
     */
    public Product editProduct(Long id, Product newProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Validar que el nuevo nombre no ya exista en otro producto
        Optional<Product> anotherProduct = productRepository.findByName(newProduct.getName());
        if (anotherProduct.isPresent() && !anotherProduct.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe otro producto con ese nombre.");
        }

        // Si la fecha de ingreso es nula, establecer la fecha actual
        if (newProduct.getDateOfEntry() == null) {
            newProduct.setDateOfEntry(LocalDate.now());
        }

        // Validar que la fecha de ingreso no sea en el futuro
        if (newProduct.getDateOfEntry().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha actual.");
        }

        existingProduct.setName(newProduct.getName());
        existingProduct.setQuantity(newProduct.getQuantity());
        existingProduct.setDateOfEntry(newProduct.getDateOfEntry());
        existingProduct.setUser(newProduct.getUser()); // Usuario que hace la modificación

        return productRepository.save(existingProduct);
    }

    /**
     * Filtra productos por nombre, fecha o usuario
     */
    public List<Product> filterProducts(String name, LocalDate dateOfEntry, Long userId) {
        if (name != null && userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            return productRepository.findByNameAndUser(name, user);
        }
        if (name != null) {
            return productRepository.findByName(name)
                    .map(List::of)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        }
        if (dateOfEntry != null) {
            return productRepository.findByDateOfEntry(dateOfEntry);
        }
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            return productRepository.findByUser(user);
        }
        throw new IllegalArgumentException("Debes proporcionar al menos un criterio de búsqueda.");
    }
}
