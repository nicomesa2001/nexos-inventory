package com.gnencisom.nexosinventory.service;

import com.gnencisom.nexosinventory.model.Product;
import com.gnencisom.nexosinventory.model.User;
import com.gnencisom.nexosinventory.repository.ProductRepository;
import com.gnencisom.nexosinventory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks

        user = new User();
        user.setId(1L);
        user.setName("Juan Pérez");

        product = new Product();
        product.setName("Producto 1");
        product.setQuantity(10);
        product.setDateOfEntry(LocalDate.now());
        product.setUser(user);
    }

    @Test
    void addProduct_validProduct_shouldSaveProduct() {
        // Simular que el usuario existe
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // Simular que no hay productos con el mismo nombre
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        // Simular que el producto es guardado correctamente
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.addProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Producto 1", savedProduct.getName());
        assertEquals(10, savedProduct.getQuantity());
        assertEquals(user, savedProduct.getUser());  // Verifica que el usuario es el correcto
    }

    @Test
    void addProduct_userNotFound_shouldThrowException() {
        // Simular que el usuario no existe
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(product);
        });

        assertEquals("El usuario con ID 1 no existe.", exception.getMessage());
    }

    @Test
    void addProduct_duplicateName_shouldThrowException() {
        // Simular que el usuario existe
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // Simular que ya existe un producto con el mismo nombre
        when(productRepository.findByName(product.getName())).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(product);
        });

        assertEquals("El producto con el mismo nombre ya existe.", exception.getMessage());
    }

    @Test
    void addProduct_futureDate_shouldThrowException() {
        // Establecer una fecha futura
        product.setDateOfEntry(LocalDate.now().plusDays(1));
        // Simular que el usuario existe
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // Simular que no existe un producto con el mismo nombre
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(product);
        });

        assertEquals("La fecha de ingreso no puede ser posterior a la fecha actual.", exception.getMessage());
    }
}
