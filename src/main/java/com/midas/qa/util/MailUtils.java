package com.midas.qa.util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailUtils {
	 static String password = "Passwor@d123";
    private static final String BASE_URL = "https://api.mail.tm";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Method to create a temporary email account
    public static String createTemporaryEmail() throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Get available domains
            HttpGet getDomains = new HttpGet(BASE_URL + "/domains");
            CloseableHttpResponse response = client.execute(getDomains);
            String domainsJson = EntityUtils.toString(response.getEntity());
            JsonNode domainsNode = objectMapper.readTree(domainsJson);
            String domain = domainsNode.get("hydra:member").get(0).get("domain").asText();

            // Generate random email and password
            String email = "user" + System.currentTimeMillis() + "@" + domain;
           

            // Create account
            HttpPost createAccount = new HttpPost(BASE_URL + "/accounts");
            String jsonBody = String.format("{\"address\":\"%s\",\"password\":\"%s\"}", email, password);
            
            createAccount.setEntity(new StringEntity(jsonBody));
            createAccount.setHeader("Content-Type", "application/json");
            client.execute(createAccount).close();

            return email;
        }
    }

    // Method to read inbox and extract URL from email with specific subject
    public static String extractUrlFromEmail(String email, String subjectKeyword) throws IOException, InterruptedException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Authenticate and get token
            HttpPost authRequest = new HttpPost(BASE_URL + "/token");
            
            String authJson = String.format("{\"address\":\"%s\",\"password\":\"%s\"}", email, password);
            authRequest.setEntity(new StringEntity(authJson));
            authRequest.setHeader("Content-Type", "application/json");
            CloseableHttpResponse authResponse = client.execute(authRequest);
            String authResponseJson = EntityUtils.toString(authResponse.getEntity());
         //   System.out.println("Auth Response JSON: " + authResponseJson);
            
            JsonNode authNode = objectMapper.readTree(authResponseJson);
            JsonNode tokenNode = authNode.get("token");
            if (tokenNode == null || tokenNode.isNull()) {
                System.out.println("❌ Token not found in response: " + authNode.toPrettyString());
                throw new RuntimeException("Authentication failed: Token not found.");
            }
            String token = tokenNode.asText();


            // Poll for messages
            HttpGet getMessages = new HttpGet(BASE_URL + "/messages");
            getMessages.setHeader("Authorization", "Bearer " + token);
            int retries = 10;
            while (retries-- > 0) {
                CloseableHttpResponse messagesResponse = client.execute(getMessages);
                String messagesJson = EntityUtils.toString(messagesResponse.getEntity());
                JsonNode messagesNode = objectMapper.readTree(messagesJson);
                Iterator<JsonNode> messages = messagesNode.get("hydra:member").elements();
                while (messages.hasNext()) {
                    JsonNode message = messages.next();
                    String subject = message.get("subject").asText();
                    if (subject.contains(subjectKeyword)) {
                        String messageId = message.get("id").asText();
                        // Get message content
                        HttpGet getMessage = new HttpGet(BASE_URL + "/messages/" + messageId);
                        getMessage.setHeader("Authorization", "Bearer " + token);
                        CloseableHttpResponse messageResponse = client.execute(getMessage);
                        String messageContentJson = EntityUtils.toString(messageResponse.getEntity());
                        JsonNode messageContentNode = objectMapper.readTree(messageContentJson);
                        String html = null;
                        JsonNode htmlNode = messageContentNode.get("html");
                        if (htmlNode != null && htmlNode.isArray() && htmlNode.size() > 0) {
                             html = htmlNode.get(0).asText();
                            
                            // Extract URL from HTML
                             Pattern urlPattern = Pattern.compile("https?://[^\\s\"'<>]+");

                            Matcher matcher = urlPattern.matcher(html);
                            if (matcher.find()) {
                                return matcher.group();
                            }
                        } else {
                            System.out.println("No HTML content found in email: " + messageContentNode.toPrettyString());
                        }

                        /* Extract URL from HTML
                        Pattern urlPattern = Pattern.compile("https?://[^\\s\"'<>]+");
                     
						Matcher matcher = urlPattern.matcher(html);
                        if (matcher.find()) {
                            return matcher.group();
                        }   */
                    } 
                }
                Thread.sleep(5000); // Wait before retrying
            }
        }
        return null;
    }
}
