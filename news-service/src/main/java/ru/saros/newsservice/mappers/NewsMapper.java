package ru.saros.newsservice.mappers;

import org.springframework.stereotype.Component;
import ru.saros.newsservice.models.News;
import ru.saros.newsservice.views.NewsView;

import java.time.format.DateTimeFormatter;

@Component
public class NewsMapper {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public NewsView toView(News news) {
        NewsView newsView = new NewsView();
        newsView.setId(news.getId());
        newsView.setTitle(news.getTitle());
        newsView.setDescription(newsView.getDescription());
        newsView.setNewsDate(news.getNewsDate().format(FORMATTER));
        return newsView;
    }
}
