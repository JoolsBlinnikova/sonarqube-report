package org.example.dto;

import lombok.Data;

@Data
public class Issue {
    private String key;
    private String rule;
    private String severity;
    private String component;
    private String project;
    private int line;
    private TextRange textRange;
    private String status;
    private String message;
    private String effort;
    private String author;
    private String creationDate;
    private String updateDate;
    private String type;

    private Rule ruleName;
}