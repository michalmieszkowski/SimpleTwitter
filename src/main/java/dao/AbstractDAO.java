package dao;

import hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;

public abstract class AbstractDAO {

    protected final HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    protected final EntityManager entityManager = hibernateUtil.getEntityManager();
}
