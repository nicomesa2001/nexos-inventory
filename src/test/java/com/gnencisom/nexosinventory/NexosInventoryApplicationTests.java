package com.gnencisom.nexosinventory;

import com.gnencisom.nexosinventory.controller.ProductController;
import com.gnencisom.nexosinventory.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NexosInventoryApplicationTests {

    @Autowired
    private ProductController productController;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertThat(productController).isNotNull();
        assertThat(userService).isNotNull();
    }
}
