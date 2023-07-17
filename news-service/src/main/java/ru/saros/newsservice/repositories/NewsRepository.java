package ru.saros.newsservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.saros.newsservice.models.News;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
}
