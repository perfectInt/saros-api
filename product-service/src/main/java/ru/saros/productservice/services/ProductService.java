package ru.saros.productservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.saros.productservice.exceptions.ProductNotFoundException;
import ru.saros.productservice.mappers.ImageMapper;
import ru.saros.productservice.mappers.ProductMapper;
import ru.saros.productservice.models.Image;
import ru.saros.productservice.models.Product;
import ru.saros.productservice.repositories.ProductRepository;
import ru.saros.productservice.views.ProductView;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ImageMapper imageMapper;

    private final ProductRepository productRepository;

    public List<ProductView> getProducts(Integer page) {
        if (page == null) page = 0;
        Pageable paging = PageRequest.of(page, 9, Sort.by("title"));
        Page<Product> products = productRepository.findAll(paging);
        List<ProductView> productViews = new ArrayList<>();
        for (Product product : products) {
            ProductView productView = productMapper.toView(product);
            productViews.add(productView);
        }
        return productViews;
    }

    @Transactional
    public void saveProduct(String title, String category, MultipartFile[] files) throws IOException {
        Product product = productMapper.toEntity(title, category);
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = imageMapper.toEntity(file);
            image.setProduct(product);
            images.add(image);
        }
        images.get(0).setPreviewImage(true);
        product.setImages(images);
        Product toUpdatePreviewImage = productRepository.save(product);
        toUpdatePreviewImage.setPreviewImageId(images.get(0).getId());
        productRepository.save(toUpdatePreviewImage);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductView getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Cannot find this product"));
        return productMapper.toView(product);
    }
}
