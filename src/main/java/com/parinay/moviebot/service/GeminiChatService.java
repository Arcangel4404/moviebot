package com.parinay.moviebot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class GeminiChatService {

    // FIX: Updated to the current stable model for high-speed use cases.
    // The API version 'v1beta' remains the standard for generateContent.
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=AIzaSyAa4iXnP8z6Ap7ufUsu0F5qvaf-INrjiXQ";

    public String chatWithGemini(String prompt, List<String> history) {
        RestTemplate restTemplate = new RestTemplate();

        List<Map<String, Object>> contents = new ArrayList<>();
        
        // Build conversation history with alternating 'user' and 'model' roles.
        for (int i = 0; i < history.size(); i++) {
            String role = (i % 2 == 0) ? "user" : "model"; 
            
            contents.add(Map.of(
                "role", role, 
                "parts", List.of(Map.of("text", history.get(i)))
            ));
        }

        // Add the current 'prompt' as the final 'user' message.
        contents.add(Map.of(
            "role", "user",
            "parts", List.of(Map.of("text", prompt))
        ));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_API_URL, entity, Map.class);
            
            // Safe extraction of the response text
            if (response.getBody() != null && response.getBody().containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    return (String) parts.get(0).get("text");
                }
            }
            return "Sorry, the Gemini API returned an empty response.";

        } catch (Exception e) {
            System.err.println("Gemini API Error: " + e.getMessage());
            return "Sorry, the Gemini API is unavailable or an error occurred. (Check your URL and API Key!)";
        }
    }
}