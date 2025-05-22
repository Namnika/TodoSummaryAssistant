package com.todoSummaryApplication.todoSummary.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class CohereService {

	@Value("${cohere.api.key}")
	private String CohereApiKey;

	private final RestTemplate restTemplate = new RestTemplate();

	public String generateSummary(String text) {
		String CohereApiUrl = "https://api.cohere.com/v1/summarize";

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(CohereApiKey);

		Map<String, Object> body = new HashMap<>();
		body.put("text", text);
		body.put("length", "medium");
		body.put("format", "paragraph");
		body.put("model", "command");

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		try {
			ResponseEntity<Map<String, Object>> response = restTemplate.exchange(CohereApiUrl, HttpMethod.POST, request,
					new ParameterizedTypeReference<Map<String, Object>>() {
					});

			Map<String, Object> responseBody = response.getBody();

			if (responseBody != null && responseBody.containsKey("summary")) {
				return responseBody.get("summary").toString();
			} else {
				return "Failed to generate summary using Cohere API!";
			}
		} catch (Exception e) {
			return "Error in generating summary using Cohere API!" + e.getMessage();
		}

	}
}
