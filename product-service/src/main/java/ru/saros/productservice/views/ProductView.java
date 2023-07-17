package ru.saros.productservice.views;

import lombok.Data;

import java.util.List;

@Data
public class ProductView {
    private String title;
    private String description;
    private String category;
    private Double price;
    private List<Long> imagesIds;
    private Long previewImageId;
}
