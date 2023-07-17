package ru.saros.newsservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.saros.newsservice.models.News;
import ru.saros.newsservice.services.NewsService;
import ru.saros.newsservice.views.NewsView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news/{id}")
    public NewsView getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    @GetMapping("/news")
    public List<NewsView> getNewsByPage(@RequestParam(name = "page", required = false) Integer page) {
        return newsService.getNewsByPages(page);
    }

    @PostMapping("/news/create")
    public News createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    @PutMapping("/news/update")
    public void updateNews(@RequestBody News news) {
        newsService.updateNews(news);
    }

    @DeleteMapping("/news/delete/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
