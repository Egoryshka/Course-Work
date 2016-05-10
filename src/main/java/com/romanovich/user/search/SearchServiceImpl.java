package com.romanovich.user.search;

import com.romanovich.user.model.Movie;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Alex on 17.02.2016.
 */
@Repository
@Transactional
public class SearchServiceImpl implements SearchService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SearchServiceImpl() {
    }

    @Override
    public List<Movie> search(String text) {
        org.hibernate.search.jpa.FullTextQuery jpaQuery = getFullTextQuery(text);
        @SuppressWarnings("unchecked")
        List<Movie> results = jpaQuery.getResultList();
        return results;
    }

    private FullTextQuery getFullTextQuery(String text) {
        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
        QueryBuilder queryBuilder = getQueryBuilder(fullTextEntityManager);
        org.apache.lucene.search.Query query = getQuery(text, queryBuilder);
        return fullTextEntityManager.createFullTextQuery(query, Movie.class);
    }

    private FullTextEntityManager getFullTextEntityManager() {
        return org.hibernate.search.jpa.Search.
                getFullTextEntityManager(entityManager);
    }

    private QueryBuilder getQueryBuilder(FullTextEntityManager fullTextEntityManager) {
        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Movie.class).get();
    }

    private Query getQuery(String text, QueryBuilder queryBuilder) {
        return queryBuilder
                .keyword()
                .onFields("title", "text", "category", "user.firstName", "user.lastName",
                        "comments.text", "tags.text")
                .matching(text.toLowerCase())
                .createQuery();
    }


}


