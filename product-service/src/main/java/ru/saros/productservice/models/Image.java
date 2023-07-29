package ru.saros.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    private boolean isPreviewImage;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] bytes;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Product product;
}
