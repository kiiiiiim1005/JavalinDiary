package com.gmail.kiiiiiim1005.diary.dao;

import com.gmail.kiiiiiim1005.diary.entity.Diary;
import com.gmail.kiiiiiim1005.diary.entity.User;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DiaryDAO {

    private final Session session;

    public DiaryDAO(Session session) {
        this.session = session;
    }

    public Diary write(User user, String title, String contents, boolean isPublic) {
        Diary diary = new Diary(user, title, contents, isPublic);
        session.save(diary);
        return diary;
    }

    public Diary getById(long id) {
        return session.get(Diary.class, id);
    }

    public List<Diary> getDiaries(User user, int limit, int start) {
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Diary> cr = cb.createQuery(Diary.class);
        final Root<Diary> root = cr.from(Diary.class);
        cr.select(root).where(cb.equal(root.get("writer"), user.getId()));
        Query query = session.createQuery(cr);
        query.setMaxResults(limit);
        query.setFirstResult(start);
        return query.getResultList();
    }

    public List<Diary> getPublicDiaries(int limit, int start) {
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Diary> cr = cb.createQuery(Diary.class);
        final Root<Diary> root = cr.from(Diary.class);
        cr.select(root).where(cb.equal(root.get("isPublic"), true));
        Query query = session.createQuery(cr);
        query.setMaxResults(limit);
        query.setFirstResult(start);
        return query.getResultList();
    }

}
