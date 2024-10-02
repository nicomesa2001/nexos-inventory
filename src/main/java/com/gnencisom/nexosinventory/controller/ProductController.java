package com.gnencisom.nexosinventory.controller;

import com.gnencisom.nexosinventory.model.Product;
import com.gnencisom.nexosinventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint para agregar un nuevo producto.
     *
     * @param product Datos del producto a agregar.
     * @return El producto agregado.
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    /**
     * Endpoint para obtener todos los productos.
     *
     * @return Lista de productos.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint para eliminar un producto por su ID.
     *
     * @param id     ID del producto.
     * @param userId ID del usuario que solicita la eliminación.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestParam Long userId) {
        productService.deleteProduct(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para editar un producto por su ID.
     *
     * @param id       ID del producto.
     * @param product  Nuevos datos del producto.
     * @return El producto editado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.editProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Endpoint para filtrar productos por nombre, fecha de ingreso o usuario.
     *
     * @param name        Nombre del producto.
     * @param dateOfEntry Fecha de ingreso del producto.
     * @param userId      ID del usuario que registró el producto.
     * @return Lista de productos filtrados.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate dateOfEntry,
            @RequestParam(required = false) Long userId) {
        List<Product> products = productService.filterProducts(name, dateOfEntry, userId);
        return ResponseEntity.ok(products);
    }
}