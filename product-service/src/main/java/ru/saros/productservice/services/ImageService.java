package ru.saros.productservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.saros.productservice.exceptions.ImageNotFoundException;
import ru.saros.productservice.models.Image;
import ru.saros.productservice.repositories.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("Cannot find this image"));
    }
}
