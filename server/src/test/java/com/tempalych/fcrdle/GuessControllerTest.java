package com.tempalych.fcrdle;

import com.tempalych.fcrdle.server.dto.rq.GuessRequest;
import com.tempalych.fcrdle.server.dto.rs.GuessResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuessControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void guessTest() {
        ResponseEntity<GuessResponse> response = restTemplate
                .postForEntity("/guess", new GuessRequest(), GuessResponse.class);
    }
}
