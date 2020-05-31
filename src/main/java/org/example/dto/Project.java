package org.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class Project {
    private List<ProjectComponents> components;
}