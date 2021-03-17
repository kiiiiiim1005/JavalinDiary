package com.gmail.kiiiiiim1005.diary.util;

import com.gmail.kiiiiiim1005.diary.dao.UserDAO;
import com.gmail.kiiiiiim1005.diary.entity.User;
import io.javalin.http.Context;

public class Util {

    /**
     * userID 세션값과 id가 일치하는 유저를 리턴. 일치하는 유저가 없을 시 null 리턴
     */
    public static User getUser(Context ctx, UserDAO userDAO) {
        final Object attrUserID = ctx.sessionAttribute("userID");
        if(attrUserID == null) return null;
        long id = ((long) attrUserID);
        final User user = userDAO.get(id);
        return user;
    }

    public static void sendToSigninWithRedirect(Context ctx) {
        ctx.redirect("/signin?redirect=" + ctx.path());
    }

    public static void printCauses(Throwable t) {
        if (t.getCause() != null) {
            System.out.println(t.getCause().getClass());
            printCauses(t.getCause());
        }
    }


}
