package org.example;

import org.example.service.TemplateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class App implements CommandLineRunner {
    private final static Logger logger = Logger.getLogger(App.class.getName());

    final TemplateService templateService;

    public App(TemplateService templateService) {
        this.templateService = templateService;
    }

    public static void main(String[] args) {
		if (args.length == 1) {
            SpringApplication.run(App.class, args);
		} else {
            logger.log(Level.SEVERE, "Project key not passed as argument");
        }
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        templateService.writeTemplateToHtmlFile(args[0]);
    }
}