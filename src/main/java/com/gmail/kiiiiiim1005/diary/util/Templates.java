package com.gmail.kiiiiiim1005.diary.util;

import freemarker.template.Configuration;
import freemarker.template.Version;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class Templates {

    public static TemplateEngine getTemplateEngine() {
        TemplateEngine te = new TemplateEngine();
        ClassLoaderTemplateResolver ct = new ClassLoaderTemplateResolver();
        ct.setTemplateMode(TemplateMode.HTML);
        //System.out.println("CT: " + ct.getCharacterEncoding()); // null
        ct.setCharacterEncoding("UTF-8");
        te.setTemplateResolver(ct);
        return te;
    }

    public static Configuration getFreemarkerConfig() {
        Configuration c = new Configuration(new Version("2.3.30"));
        c.setClassForTemplateLoading(JavalinFreemarker.class, "/");
        c.setDefaultEncoding("UTF-8");
        return c;
    }




}
