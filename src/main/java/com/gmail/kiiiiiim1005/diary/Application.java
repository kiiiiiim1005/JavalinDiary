package com.gmail.kiiiiiim1005.diary;

import com.gmail.kiiiiiim1005.diary.controller.AccountController;
import com.gmail.kiiiiiim1005.diary.controller.DiaryController;
import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.User;
import com.gmail.kiiiiiim1005.diary.util.HibernateUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ExceptionHandler;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.gmail.kiiiiiim1005.diary.util.Templates.getFreemarkerConfig;
import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Application {

    Javalin server;

    Application(int port) {
        server = Javalin.create(c-> {
            c.addStaticFiles("static");
        });


        server.exception(Exception.class, new ExceptionHandler<Exception>() {
            @Override
            public void handle(@NotNull Exception exception, @NotNull Context ctx) {
                System.out.println("EXCEPTION HANDLING");
                closeLocalSession();
                exception.printStackTrace();
            }
        });

        JavalinFreemarker.configure(getFreemarkerConfig());
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");

        server.routes(()->{
           get("/", ctx -> {
               final Object attrUserID = ctx.sessionAttribute("userID");
               if(attrUserID != null) {
                   long id = ((long) attrUserID);
                   final UserDAO userDAO = new UserDAO(getLocalSession());
                   final User user = userDAO.get(id);
                   closeLocalSession();
                   if(user != null) {
                       Map<String, Object> map = new HashMap<>();
                       map.put("user", user);
                       ctx.render("templates/main.ftl", map);
                       return;
                   }

               }
               ctx.render("templates/main.ftl");
           });
        });

        new AccountController(server).applyRoutes();
        new DiaryController(server).applyRoutes();

        server.start(port);
    }



    public static void main(String[] args) throws ClassNotFoundException {
        HibernateUtil.buildSessionFactory();
        new Application(8092);
    }
}
