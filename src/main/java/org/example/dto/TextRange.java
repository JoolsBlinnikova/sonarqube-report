package org.example.dto;

import lombok.Data;

@Data
public class TextRange {
    private long startLine;
    private long endLine;

    @Override
    public String toString() {
        return "Lines: " + startLine +" - " + endLine;
    }
}