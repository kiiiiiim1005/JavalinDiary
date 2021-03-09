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
                    UserDAO userDAO = new UserDAO(getLocalSession());
                    User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        sendToSigninWithRedirect(ctx);
                        closeLocalSession();
                        return;
                    }

                    int page = 1;
                    String pageParam = ctx.req.getParameter("page");
                    if(pageParam != null) {
                        try {
                            page = Integer.parseInt(pageParam);
                        } catch (Exception ignored) { }
                    }
                    DiaryDAO diaryDAO = new DiaryDAO(getLocalSession());
                    List<Diary> diaries = diaryDAO.getDiaries(user, 20, (page - 1) * 20);

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("diaries", diaries);
                    ctx.render("templates/diary/list.ftl", map);
                    closeLocalSession();
                });

                get("public", ctx-> {
                    int page = 1;
                    String pageParam = ctx.req.getParameter("page");
                    if(pageParam != null) {
                        try {
                            page = Integer.parseInt(pageParam);
                        } catch (Exception ignored) { }
                    }
                    DiaryDAO diaryDAO = new DiaryDAO(getLocalSession());
                    List<Diary> diaries = diaryDAO.getPublicDiaries(20, (page - 1) * 20);

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("diaries", diaries);
                    ctx.render("templates/diary/public.ftl", map);
                    closeLocalSession();
                });

                get("write", ctx-> {
                    UserDAO userDAO = new UserDAO(getLocalSession());
                    User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        sendToSigninWithRedirect(ctx);
                    } else {
                        ctx.render("templates/diary/write.ftl");
                    }
                    closeLocalSession();
                });

                post("write", ctx-> {
                    Session localSession = getLocalSession();
                    UserDAO userDAO = new UserDAO(localSession);
                    User user = Util.getUser(ctx, userDAO);
                    if(user == null) {
                        sendToSigninWithRedirect(ctx);
                    } else {
                        DiaryDAO diaryDAO = new DiaryDAO(localSession);
                        String contents = ctx.req.getParameter("contents");
                        String title = ctx.req.getParameter("title");
                        String isPublicStr = ctx.req.getParameter("public");
                        if(Strings.emptyCheck(contents, title, isPublicStr)) {
                            ctx.redirect("/diary/write");
                        } else {
                            if(contents.length() > 5000) {
                                ctx.redirect("/diary/write");
                            } else {
                                boolean isPublic = isPublicStr.equalsIgnoreCase("true");
                                ctx.redirect("/diary/view/" + diaryDAO.write(user, title, Strings.removeScriptTags(contents), isPublic).getId());
                            }
                        }
                    }
                    closeLocalSession();
                });

                get("view/:diaryID", ctx -> {
                    Session localSession = getLocalSession();
                    DiaryDAO diaryDAO = new DiaryDAO(localSession);
                    long id;
                    try {
                        id = Long.parseLong(ctx.pathParam("diaryID"));
                    } catch (Exception e) {
                        ctx.render("templates/diary/unknown.ftl");
                        closeLocalSession();
                        return;
                    }
                    Diary diary = diaryDAO.getById(id);
                    if (diary == null) {
                        ctx.render("templates/diary/unknown.ftl");
                    } else {
                        UserDAO userDAO = new UserDAO(localSession);
                        User user = Util.getUser(ctx, userDAO);
                        if(!diary.isPublic() && (user == null || diary.getWriter().getId() != user.getId())) {
                            ctx.render("templates/diary/private.ftl");
                        } else {
                            HashMap<String, Object> map = new HashMap<>();
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
