package org.example.util;

import com.google.gson.Gson;
import org.example.dto.Project;
import org.example.dto.ProjectIssues;
import org.example.dto.RuleClass;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataUtil {

    private final static Logger logger = Logger.getLogger(DataUtil.class.getName());

    public static ProjectIssues getProjectIssuesFromAPI(String projectKey, String token, String sonarQubeURL) {
        return new Gson().fromJson(String.valueOf(getDataFromInputStream(sonarQubeURL + "/api/issues/search?componentKeys=" + projectKey, token)), ProjectIssues.class);
    }

    public static RuleClass getRuleFromAPI(String ruleKey, String token, String sonarQubeURL) {
        return new Gson().fromJson(String.valueOf(getDataFromInputStream(sonarQubeURL + "/api/rules/search?rule_key=" + ruleKey, token)), RuleClass.class);
    }

    public static Project getProjectFromAPI(String projectKey, String token, String sonarQubeURL) {
        return new Gson().fromJson(String.valueOf(getDataFromInputStream(sonarQubeURL + "/api/projects/search?projects=" + projectKey, token)), Project.class);
    }

    private static StringBuilder getDataFromInputStream(String url, String token) {
        try (InputStream is = connectToSonarQube(url, token).getInputStream()) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String inputLine;
            StringBuilder data = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                data.append(inputLine);
            }
            return data;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Not connect to SonarQube");
            return new StringBuilder();
        }
    }

    private static HttpsURLConnection connectToSonarQube(String urlString, String token) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        String tokenBase = token + ":";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));
        conn.setRequestProperty("Authorization", basicAuth);
        return conn;
    }
}