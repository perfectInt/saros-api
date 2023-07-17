package ru.saros.newsservice.views;

import lombok.Data;

@Data
public class NewsView {

    private Long id;

    private String title;

    private String description;

    private String newsDate;
}
