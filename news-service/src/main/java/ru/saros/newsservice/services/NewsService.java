package ru.saros.newsservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.saros.newsservice.exceptions.NewsNotFoundException;
import ru.saros.newsservice.mappers.NewsMapper;
import ru.saros.newsservice.models.News;
import ru.saros.newsservice.views.NewsView;
import ru.saros.newsservice.repositories.NewsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public News createNews(News news) {
        news.setNewsDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    public List<NewsView> getNewsByPages(Integer page) {
//        if (page == null) page = 0;
        Pageable paging = PageRequest.of(page, 10, Sort.by("newsDate"));
        Page<News> news = newsRepository.findAll(paging);
        List<NewsView> newsViews = new ArrayList<>();
        for (News n : news) {
            NewsView newsView = newsMapper.toView(n);
            newsViews.add(newsView);
        }
        return newsViews;
    }

    public NewsView getNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Cannot find this news"));
        return newsMapper.toView(news);
    }

    public void updateNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
