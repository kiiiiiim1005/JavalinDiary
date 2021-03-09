package com.gmail.kiiiiiim1005.diary;

import com.gmail.kiiiiiim1005.diary.controller.AccountController;
import com.gmail.kiiiiiim1005.diary.controller.DiaryController;
import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.User;
import com.gmail.kiiiiiim1005.diary.util.HibernateUtil;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.util.HashMap;
import java.util.Map;

import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.closeLocalSession;
import static com.gmail.kiiiiiim1005.diary.util.HibernateUtil.getLocalSession;
import static com.gmail.kiiiiiim1005.diary.util.Templates.getFreemarkerConfig;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Application {

    Javalin server;

    Application(int port) {
        server = Javalin.create(c-> {
            c.addStaticFiles("static");
        });


        server.exception(Exception.class, (exception, ctx) -> {
            System.out.println("EXCEPTION HANDLING");
            closeLocalSession();
            exception.printStackTrace();
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
           get("test", ctx-> {
               final UserDAO userDAO = new UserDAO(getLocalSession());
               for(int i = 0; i<100000; i++) {
                   userDAO.create("test" + i + "@test.com", "1234", "test" + i);
               }
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
