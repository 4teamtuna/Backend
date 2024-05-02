package com.example.gsmoa.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.gsmoa.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository repository;


    @Test
    @Transactional
    @Rollback(false)
    public void testArticle() {
        Article article = new Article(
                null, "aaa","hi", "realA", "Hello", null
        );

        repository.save(article);

        Article findArticle = repository.findByTitle(article.getTitle());

        assertEquals(findArticle.getId(), article.getId());
        assertEquals(findArticle.getUserid(), article.getUserid());
        assertEquals(findArticle, article);
    }


}