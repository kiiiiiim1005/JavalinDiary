package com.gmail.kiiiiiim1005.diary.controller;

import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.Diary;
import com.gmail.kiiiiiim1005.diary.entity.User;
import com.gmail.kiiiiiim1005.diary.util.BaseController;
import com.gmail.kiiiiiim1005.diary.util.Util;
import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.*;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import org.hibernate.Session;

import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DiaryController extends BaseController {

    public DiaryController(Javalin app) {
        super(app);
    }

    @Override
    public void applyRoutes() {
        app.routes(()->{
            path("diary", () -> {
                get("list", ctx-> {
                    final UserDAO userDAO = new UserDAO(getLocalSession());
                    final User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        ctx.redirect("/signin");
                        return;
                    }

                    ctx.render("templates/diary/list.ftl");

                    final Set<Diary> diaries = user.getDiaries();
                    final Diary diary = new Diary();
                    diary.setContents("TEST123");
                    diary.setWriter(user);
                    diaries.add(diary);
                    user.setDiaries(diaries);
                    getLocalSession().save(diary);
                    getLocalSession().getTransaction().commit();
                    closeLocalSession();
                    System.out.println(user.getDiaries());

                    final UserDAO dao2 = new UserDAO(getLocalSession());
                    System.out.println(Util.getUser(ctx, dao2).getDiaries());
                    closeLocalSession();
                });
                get("write", ctx-> {
                    /*
                    final UserDAO userDAO = new UserDAO(getLocalSession());
                    final User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        ctx.redirect("/signin");
                        closeLocalSession();
                        return;
                    }

                     */
                    ctx.render("templates/diary/write.ftl");
                });
            });

        });

    }
}
