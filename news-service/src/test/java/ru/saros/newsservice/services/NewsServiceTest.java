package ru.saros.newsservice.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.saros.newsservice.models.News;
import ru.saros.newsservice.views.NewsView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class NewsServiceTest {

    @Autowired
    public NewsService newsService;

    News news;

    NewsView newsView;

    Long id;

    @BeforeAll
    public void init() {
        news = new News();
        news.setTitle("Test title");
        news.setDescription("Test description");
    }

    @Test
    @Order(1)
    public void createNewsTest() {
        news = newsService.createNews(news);
        id = news.getId();
        assertNotNull(id);
        assertNotNull(news.getNewsDate());
    }

    @Test
    @Order(2)
    public void getNewsByIdTest() {
        newsView = newsService.getNewsById(id);
        assertEquals(id, newsView.getId());
        assertNotNull(news.getTitle(), newsView.getTitle());
        assertNotNull(news.getDescription(), newsView.getDescription());
    }

    @Test
    @Order(3)
    public void getNewsByPagesTest() {
        List<NewsView> newsViews = newsService.getNewsByPages(null);
        assertEquals(1, newsViews.size());
        newsViews = newsService.getNewsByPages(1);
        assertEquals(0, newsViews.size());
    }

    @Test
    @Order(4)
    public void updateNewsTest() {
        news.setTitle("Another title");
        newsService.updateNews(news);
        assertEquals(1, newsService.getNewsByPages(0).size());
    }

    @Test
    @Order(5)
    public void deleteNewsTest() {
        newsService.deleteNews(id);
        assertEquals(0, newsService.getNewsByPages(0).size());
    }
}
