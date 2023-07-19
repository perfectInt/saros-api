package ru.saros.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.saros.productservice.models.Product;
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
    public List<ProductView> getAllProductsByPage(@RequestParam(name = "page", required = false) Integer page,
                                                  @RequestParam(name = "category", required = false) String category) {
        return productService.getProducts(page, category);
    }

    @GetMapping("/product/{id}")
    public ProductView getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product/create")
    public Product createProduct(@RequestParam(name = "title") String title,
                                 @RequestParam(name = "category") String category,
                                 @RequestParam(name = "images[]") MultipartFile[] files) throws IOException {
        return productService.saveProduct(title, category, files);
    }

    @DeleteMapping("/product/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
