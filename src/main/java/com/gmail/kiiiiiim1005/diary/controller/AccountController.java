package com.gmail.kiiiiiim1005.diary.controller;

import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.User;
import com.gmail.kiiiiiim1005.diary.util.BaseController;
import io.javalin.Javalin;

import java.util.HashMap;

import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.closeLocalSession;
import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.getLocalSession;
import static com.gmail.kiiiiiim1005.diary.util.Strings.isEmailPattern;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class AccountController extends BaseController {

    public AccountController(Javalin app) {
        super(app);
    }

    @Override
    public void applyRoutes() {
        app.routes(()->{
            get("signin", ctx-> {
                ctx.render("templates/account/signin.ftl");
            });

            get("signup", ctx-> {
                ctx.render("templates/account/signup.ftl");
            });
            get("signout", ctx-> {
                ctx.req.getSession().invalidate();
                ctx.redirect("/");
            });

            post("signup", ctx->{
                System.out.println("signup post");

                String email = ctx.req.getParameter("email");
                if (email == null || !isEmailPattern(email)) {
                    // TODO 중복
                    System.out.println("email err");
                    closeLocalSession();
                    return;
                }

                final UserDAO userDAO = new UserDAO(getLocalSession());
                try {
                    userDAO.create(email, ctx.req.getParameter("password"), ctx.req.getParameter("nickname"));
                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("registered", true);
                    ctx.render("templates/account/signin.ftl", map);
                } catch (Throwable t) {
                    t.printStackTrace();
                    // TODO
                }
                closeLocalSession();
            });

            // For AJAX
            post("logincheck", ctx->{
                final String email = ctx.req.getParameter("email");
                final String password = ctx.req.getParameter("password");
                final UserDAO userDAO = new UserDAO(getLocalSession());
                try {
                    final User user = userDAO.get(email);
                    if (user != null) {
                        if(user.getPassword().equals(password)) {
                            ctx.sessionAttribute("userID", user.getId());
                            ctx.res.getWriter().print("correct");
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    closeLocalSession();
                }
            });
        });
    }
}
