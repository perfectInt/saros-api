package ru.saros.adminservice.models;

import lombok.Data;

@Data
public class ProductDto {
    private String title;
    private String category;
    private ImageDto image;
}
