package org.example.service;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface TemplateService {
    void writeTemplateToHtmlFile(String str) throws IOException, TemplateException;
}