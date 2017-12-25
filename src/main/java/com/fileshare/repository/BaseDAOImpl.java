package com.fileshare.repository;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseDAOImpl<ENTITY> implements BaseDAO<ENTITY> {

    protected static final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

    private SessionFactory sessionFactory;
    private final Class<ENTITY> entityClass;

    public BaseDAOImpl() {
        this.entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY findById(Long id) {
        ENTITY entity = (ENTITY) getSession().load(this.entityClass, id);
        logStatistics();
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY findById(Integer id) {
        ENTITY entity = (ENTITY) getSession().load(this.entityClass, id);
        logStatistics();
        return entity;
    }

    @Override
    public List<ENTITY> findAll() {
        return findByCriteria();
    }

    @Override
    public Query createQuery(String query) {
        return getSession().createQuery(query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ENTITY> findByExample(ENTITY exampleEntity) {
        List<ENTITY> list = getSession().createCriteria(this.entityClass).add(Example.create(exampleEntity)).list();
        logStatistics();
        return list;
    }

    @SuppressWarnings("unchecked")
    protected List<ENTITY> findByCriteria(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        List<ENTITY> list = criteria.list();
        logStatistics();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY findByExampleUniqueResult(ENTITY exampleEntity) {
        ENTITY entity = (ENTITY) getSession().createCriteria(this.entityClass).add(Example.create(exampleEntity)).uniqueResult();
        logStatistics();
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY findByQueryUniqueResult(String hsql, Object... parameters) {
        Query query = getSession().createQuery(hsql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        ENTITY entity = (ENTITY) query.uniqueResult();
        logStatistics();
        return entity;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findByQuery(String hsql, Object... parameters) {
        Session session = getSession();
        Query query = session.createQuery(hsql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        List list = query.list();
        logStatistics();
        return list;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findBySQLQuery(String sql, Object... parameters) {
        Session session = getSession();
        Query query = session.createSQLQuery(sql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        List list = query.list();
        logStatistics();
        return list;
    }

    @Override
    public Long getCountOfResultsFromQuery(String hsql, Map<String, Object> parameters) {
        Query query = createQuery(hsql);
        setNamedParameters(parameters, query);
        return (Long) query.uniqueResult();
    }

    @Override
    public ENTITY saveOrUpdate(ENTITY entity) {
        logger.debug("Entity to be persisted: " + entity.toString());
        getSession().saveOrUpdate(entity);
        logStatistics();
        return entity;
    }

    @Override
    public ENTITY update(ENTITY entity) {
        logger.debug("Entity to be persisted: " + entity.toString());
        getSession().merge(entity);
        logStatistics();
        return entity;
    }

    @Override
    public void saveOrUpdate(List<ENTITY> entityList) {

        for (ENTITY entity : entityList) {
            logger.debug("Entity to be persisted: " + entity.toString());
            getSession().merge(entity);
            logStatistics();
        }
    }

    @Override
    public void saveOrUpdate(Set<ENTITY> entityList) {

        for (ENTITY entity : entityList) {
            logger.debug("Entity to be persisted: " + entity.toString());
            getSession().merge(entity);
            logStatistics();
        }
    }


    private void setNamedParameters(Map<String, Object> parameters, Query query) {
        for (String keyVal : parameters.keySet()) {
            query.setParameter(keyVal, parameters.get(keyVal));
        }
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();

    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findAll(String hsql) {
        Session session = getSession();
        Query query = session.createQuery(hsql);
        List list = query.list();
        logStatistics();
        return list;
    }


    @Override
    public void logStatistics() {
        if (logger.isDebugEnabled()) {
            Statistics statistics = getSession().getSessionFactory().getStatistics();
            logger.debug(String.format("2nd Level Cache: hits [%s]; misses [%s]; puts [%s]", statistics.getSecondLevelCacheHitCount(),
                    statistics.getSecondLevelCacheMissCount(), statistics.getSecondLevelCachePutCount()));
            statistics.logSummary();
        }
    }

    @Override
    public void delete(ENTITY entity) {
        logger.debug("Entity to be deleted: " + entity.toString());
        logStatistics();
        getSession().delete(entity);
    }

    @Override
    public void deleteByQuery(String hsql, Object... parameters) {
        Session session = getSession();
        Query query = session.createQuery(hsql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        logger.debug("List to be deleted: " + query.getQueryString());
        logStatistics();
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Criteria createCriteria() {
        return getSession().createCriteria(this.entityClass);
    }


    @Override
    public List<ENTITY> findByCriteria(Criteria criteria) {
        return criteria.list();
    }


}
