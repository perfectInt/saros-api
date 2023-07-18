package ru.saros.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.saros.productservice.services.ProductService;
import ru.saros.productservice.views.ProductView;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductView> getAllProductsByPage(@RequestParam(name = "page", required = false) Integer page) {
        return productService.getProducts(page);
    }

    @GetMapping("/product/{id}")
    public ProductView getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    @CrossOrigin
    public void createProduct(@RequestParam(name = "title") String title,
                              @RequestParam(name = "category") String category,
                              @RequestParam(name = "images[]") MultipartFile[] files) throws IOException {
        productService.saveProduct(title, category, files);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
