package com.gmail.kiiiiiim1005.diary.util;

import io.javalin.Javalin;

public abstract class BaseController {

    protected Javalin app;

    public BaseController(Javalin app) {
        this.app = app;
    }

    public abstract void applyRoutes();

}
