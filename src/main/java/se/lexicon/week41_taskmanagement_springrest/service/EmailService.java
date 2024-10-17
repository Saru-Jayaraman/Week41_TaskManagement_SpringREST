package se.lexicon.week41_taskmanagement_springrest.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.EmailDTO;

@Service
public class EmailService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String EMAIL_SERVICE_SEND_URL = "http://localhost:9090/api/v1/email";

    public HttpStatusCode sendRegistrationEmail(String email) {
        EmailDTO emailDTO = EmailDTO.builder()
                                .to(email)
                                .subject("Welcome, You are registered to the TODO application")
                                .html("<p style='color: blue; font-size: 36px; text-align: center; padding: 20px;'>" +
                                        "Hello and welcome to our application. Please confirm your email." +
                                        "</p>")
                                .build();
        ResponseEntity<EmailDTO> responseEntity = restTemplate.exchange(EMAIL_SERVICE_SEND_URL, HttpMethod.POST, new HttpEntity<>(emailDTO), EmailDTO.class);
        return responseEntity.getStatusCode();
    }
}
