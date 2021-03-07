package com.gmail.kiiiiiim1005.diary.controller;

import com.gmail.kiiiiiim1005.diary.dao.DiaryDAO;
import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.Diary;
import com.gmail.kiiiiiim1005.diary.entity.User;
import com.gmail.kiiiiiim1005.diary.util.BaseController;
import com.gmail.kiiiiiim1005.diary.util.Strings;
import com.gmail.kiiiiiim1005.diary.util.Util;
import io.javalin.Javalin;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.closeLocalSession;
import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.getLocalSession;
import static com.gmail.kiiiiiim1005.diary.util.Util.sendToSigninWithRedirect;
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
                        sendToSigninWithRedirect(ctx);
                        closeLocalSession();
                        return;
                    }

                    int page = 1;
                    final String pageParam = ctx.req.getParameter("page");
                    if(pageParam != null) {
                        try {
                            page = Integer.parseInt(pageParam);
                        } catch (Exception e) { }
                    }
                    System.out.println("PAGE: " + page);
                    final DiaryDAO diaryDAO = new DiaryDAO(getLocalSession());
                    final List<Diary> diaries = diaryDAO.getDiaries(user, 20, (page - 1) * 20);

                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("diaries", diaries);
                    ctx.render("templates/diary/list.ftl", map);
                    closeLocalSession();
                });
                get("write", ctx-> {
                    final UserDAO userDAO = new UserDAO(getLocalSession());
                    final User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        sendToSigninWithRedirect(ctx);
                    } else {
                        ctx.render("templates/diary/write.ftl");
                    }
                    closeLocalSession();
                });

                post("write", ctx-> {
                    final Session localSession = getLocalSession();
                    UserDAO userDAO = new UserDAO(localSession);
                    final User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        sendToSigninWithRedirect(ctx);
                    } else {
                        final DiaryDAO diaryDAO = new DiaryDAO(localSession);
                        String contents = ctx.req.getParameter("contents");
                        String title = ctx.req.getParameter("title");
                        String isPublicStr = ctx.req.getParameter("public");
                        if(Strings.emptyCheck(contents, title, isPublicStr)) {
                            ctx.redirect("/diary/write");
                        } else {
                            boolean isPublic = isPublicStr.equalsIgnoreCase("true");
                            ctx.redirect("/diary/view/" + diaryDAO.write(user, title, contents, isPublic).getId());
                        }
                    }
                    closeLocalSession();
                });

                get("view/:diaryID", ctx -> {
                    final Session localSession = getLocalSession();
                    final DiaryDAO diaryDAO = new DiaryDAO(localSession);
                    long id;
                    try {
                        id = Long.parseLong(ctx.pathParam("diaryID"));
                    } catch (Exception e) {
                        ctx.render("templates/diary/unknown.ftl");
                        closeLocalSession();
                        return;
                    }
                    final Diary diary = diaryDAO.getById(id);
                    if (diary == null) {
                        ctx.render("templates/diary/unknown.ftl");
                    } else {
                        UserDAO userDAO = new UserDAO(localSession);
                        final User user = Util.getUser(ctx, userDAO);
                        if(!diary.isPublic() && (user == null || diary.getWriter().getId() != user.getId())) {
                            ctx.render("templates/diary/private.ftl");
                        } else {
                            final HashMap<String, Object> map = new HashMap<>();
                            map.put("diary", diary);
                            ctx.render("templates/diary/view.ftl", map);
                        }
                    }
                    closeLocalSession();
                });
            });

        });

    }
}
