package ru.saros.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.saros.productservice.models.Product;
import ru.saros.productservice.services.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product/create")
    @CrossOrigin
    public void createProduct(@RequestParam(name = "title") String title,
                              @RequestParam(name = "category") String category,
                              @RequestParam(name = "images[]") MultipartFile[] files) throws IOException {
        Product product = new Product();
        product.setCategory(category);
        product.setTitle(title);
        productService.saveProduct(product, files);
    }

    @DeleteMapping("/product/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
