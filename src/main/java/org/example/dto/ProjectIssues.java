package org.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectIssues {
    private int total;
    private List<Issue> issues;
}