package com.gmail.kiiiiiim1005.diary.controller;

import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.User;
import com.gmail.kiiiiiim1005.diary.util.BaseController;
import com.gmail.kiiiiiim1005.diary.util.CauseIterator;
import io.javalin.Javalin;
import org.eclipse.jetty.util.ajax.JSON;

import java.sql.BatchUpdateException;
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

            // Ajax
            post("signup", ctx->{
                String email = ctx.req.getParameter("email");
                if (email == null || !isEmailPattern(email)) {
                    ctx.status(403);
                    closeLocalSession();
                    return;
                }
                UserDAO userDAO = new UserDAO(getLocalSession());
                try {
                    userDAO.create(email, ctx.req.getParameter("password"), ctx.req.getParameter("nickname"));
                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("registered", true);
                    ctx.status(200);
                } catch (Throwable pe) {
                    ctx.status(500);
                }
                System.out.println("sleep");
                Thread.sleep(5000);
                System.out.println("sleep end");
                closeLocalSession();
            });

            // Ajax
            post("login", ctx->{
                final String email = ctx.req.getParameter("email");
                final String password = ctx.req.getParameter("password");
                final UserDAO userDAO = new UserDAO(getLocalSession());
                try {
                    final User user = userDAO.get(email);
                    if (user != null) {
                        if(user.getPassword().equals(password)) {
                            ctx.sessionAttribute("userID", user.getId());
                            ctx.status(200);
                        } else {
                            ctx.status(401);
                        }
                    } else {
                        ctx.status(401);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                    ctx.status(500);
                } finally {
                    closeLocalSession();
                }
            });

            post("check-duplicate", ctx-> {
                
            });
        });
    }
}
