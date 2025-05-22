package com.todoSummaryApplication.todoSummary.service;

import org.springframework.stereotype.Service;

@Service
public class CohereService {
    public String generateSummary(String text) {
        return "Summary: " + text;
    }
}
