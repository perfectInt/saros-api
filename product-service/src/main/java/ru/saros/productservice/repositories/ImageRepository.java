package ru.saros.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saros.productservice.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
