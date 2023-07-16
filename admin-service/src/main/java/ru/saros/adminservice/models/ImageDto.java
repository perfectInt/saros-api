package ru.saros.adminservice.models;

import lombok.Data;

@Data
public class ImageDto {
    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    private boolean isPreviewImage;

    private byte[] bytes;
}
