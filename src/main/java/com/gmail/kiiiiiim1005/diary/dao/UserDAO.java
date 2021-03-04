package com.gmail.kiiiiiim1005.diary.dao;

import com.gmail.kiiiiiim1005.diary.entity.User;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAO {

    private final Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public User create(String email, String password, String nickname) {
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);
        u.setNickname(nickname);
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        return u;
    }

    public User get(String email) {
        session.beginTransaction();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<User> cr = cb.createQuery(User.class);
        final Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("email"), email));

        Query query = session.createQuery(cr);
        query.setMaxResults(1);
        List<User> result = query.getResultList();
        if(result.size() > 0) return result.get(0);
        return null;
    }

    public User get(long id) {
        session.beginTransaction();
        return session.get(User.class, id);
    }

}
