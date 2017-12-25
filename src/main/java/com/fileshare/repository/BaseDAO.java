package com.fileshare.repository;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseDAO<ENTITY> {
    ENTITY findById(Long id);

    @SuppressWarnings("unchecked")
    ENTITY findById(Integer id);

    List<ENTITY> findAll();

    List<ENTITY> findByExample(ENTITY exampleEntity);

    ENTITY findByQueryUniqueResult(String hsql, Object... parameters);

    List findByQuery(String hsql, Object... parameters);

    List findBySQLQuery(String hsql, Object... parameters);

    void setSessionFactory(SessionFactory sessionFactory);

    Long getCountOfResultsFromQuery(String hsql, Map<String, Object> parameters);

    Query createQuery(String query);

    ENTITY saveOrUpdate(ENTITY entity);

    ENTITY update(ENTITY entity);

    void saveOrUpdate(List<ENTITY> entityList);

    void saveOrUpdate(Set<ENTITY> entityList);

    void flush();

    void clear();

    @SuppressWarnings("rawtypes")
    List findAll(String hsql);

    void logStatistics();

    ENTITY findByExampleUniqueResult(ENTITY exampleEntity);

    void delete(ENTITY entity);

    void deleteByQuery(String hsql, Object... parameters);


    @SuppressWarnings("rawtypes")
    Criteria createCriteria();

    List<ENTITY> findByCriteria(Criteria criteria);
}
