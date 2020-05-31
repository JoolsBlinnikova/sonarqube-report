package org.example.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.App;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerUtil {
    private FreeMarkerUtil() {
    }

    public static String createTemplate(Map<String, Object> input) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassForTemplateLoading(App.class, "/templates");
        Template temp = cfg.getTemplate("report.ftl");
        Writer out = new StringWriter();
        temp.process(input, out);
        return out.toString();
    }
}