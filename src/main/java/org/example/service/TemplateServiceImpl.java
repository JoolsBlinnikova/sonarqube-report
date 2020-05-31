package org.example.service;

import freemarker.template.TemplateException;
import org.example.config.ConfigurationApp;
import org.example.dto.Issue;
import org.example.dto.Project;
import org.example.dto.ProjectComponents;
import org.example.dto.ProjectIssues;
import org.example.util.DataUtil;
import org.example.util.FreeMarkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@EnableConfigurationProperties({ConfigurationApp.class})
public class TemplateServiceImpl implements TemplateService {
    private final static Logger logger = Logger.getLogger(TemplateServiceImpl.class.getName());

    private ConfigurationApp configuration;

    @Autowired
    public void setConfiguration(ConfigurationApp configuration) {
        this.configuration = configuration;
    }

    @Override
    public void writeTemplateToHtmlFile(String projectKey) throws IOException, TemplateException {
        Map<String, Object> data = setDataToTemplate(projectKey);
        if (!data.isEmpty()) {
            String report = FreeMarkerUtil.createTemplate(data);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(configuration.getReportName()))) {
                writer.write(report);
            }
        }
    }

    private Map<String, Object> setDataToTemplate(String projectKey) {
        Map<String, Object> input = new HashMap<>();
        String token = configuration.getToken();
        String sonarQubeURL = configuration.getSonarQubeURL();

        Project project = DataUtil.getProjectFromAPI(projectKey, token, sonarQubeURL);
        if (!project.getComponents().isEmpty()) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            ProjectComponents projectComponents = project.getComponents().get(0);
            ProjectIssues projectIssues = DataUtil.getProjectIssuesFromAPI(projectKey, token, sonarQubeURL);
            List<Issue> issueList = projectIssues.getIssues();

            long countBugs = issueList.stream().filter(a -> a.getType().equals("BUG")).count();
            long countVulnerabilities = issueList.stream().filter(a -> a.getType().equals("VULNERABILITY")).count();
            long countCodeSmells = issueList.stream().filter(a -> a.getType().equals("CODE_SMELL")).count();

            long countBlocker = issueList.stream().filter(a -> a.getSeverity().equals("BLOCKER")).count();
            long countCritical = issueList.stream().filter(a -> a.getSeverity().equals("CRITICAL")).count();
            long countMajor = issueList.stream().filter(a -> a.getSeverity().equals("MAJOR")).count();
            long countMinor = issueList.stream().filter(a -> a.getSeverity().equals("MINOR")).count();
            long countInfo = issueList.stream().filter(a -> a.getSeverity().equals("INFO")).count();

            for (Issue issue : issueList) {
                issue.setComponent(getFileSourceName(issue.getComponent(), projectKey));
                issue.setAuthor(setAuthorName(issue.getAuthor()));
                issue.setCreationDate(issue.getCreationDate().substring(0, 10));
                issue.setUpdateDate(issue.getUpdateDate().substring(0, 10));
                issue.setRuleName(DataUtil.getRuleFromAPI(issue.getRule(), token, sonarQubeURL).getRules().get(0));
            }

            projectComponents.setLastAnalysisDate(projectComponents.getLastAnalysisDate().substring(0, 10));

            input.put("date", date);
            input.put("project", projectComponents);
            input.put("projectIssues", projectIssues);
            input.put("issueList", issueList);

            input.put("countBugs", countBugs);
            input.put("countVulnerabilities", countVulnerabilities);
            input.put("countCodeSmells", countCodeSmells);

            input.put("countBlocker", countBlocker);
            input.put("countCritical", countCritical);
            input.put("countMajor", countMajor);
            input.put("countMinor", countMinor);
            input.put("countInfo", countInfo);
            return input;
        } else {
            logger.log(Level.SEVERE, "Project key '" + projectKey + "' not exist");
            return new HashMap<>();
        }
    }

    private String getFileSourceName(String component, String projectKey) {
        return component.split(projectKey + ":")[1];
    }

    private String setAuthorName(String author) {
        if (author.isEmpty()) {
            return "Not assigned";
        } else return author;
    }
}