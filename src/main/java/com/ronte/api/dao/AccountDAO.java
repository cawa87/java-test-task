package com.ronte.api.dao;

import com.ronte.api.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Account> findAll() {
        String hql = "FROM Account a ORDER BY a.id";
        return (List<Account>) entityManager.createQuery(hql).getResultList();
    }

    public Account findById(long accountId) {
        return entityManager.find(Account.class, accountId);
    }

    public void save(Account account) {
        entityManager.persist(account);
    }

    public void delete(long accountId) {
        Account account = findById(accountId);
        entityManager.remove(account);
    }

    public void flush() {
        entityManager.flush();
    }

    public Account findByLogin(String login) {
        String hql = "FROM Account a WHERE a.login = :login";
        return (Account) entityManager.createQuery(hql)
                .setParameter("login", login)
                .getSingleResult();
    }
}
