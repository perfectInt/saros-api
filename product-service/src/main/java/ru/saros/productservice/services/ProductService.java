package ru.saros.productservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    private final ProductRepository productRepository;

    public List<ProductView> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductView> productViews = new ArrayList<>();
        for (Product product : products) {
            ProductView productView = new ProductView();
            productView.setTitle(product.getTitle());
            productView.setCategory(product.getCategory());
            productView.setDescription(product.getDescription());
            productView.setPreviewImageId(product.getPreviewImageId());
            productView.setPrice(product.getPrice());
            List<Long> ids = new ArrayList<>();
            for (Image image : product.getImages()) {
                ids.add(image.getId());
            }
            productView.setImagesIds(ids);
            productViews.add(productView);
        }
        return productViews;
    }

    @Transactional
    public void saveProduct(Product product, MultipartFile[] files) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = toImageEntity(file);
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
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}
