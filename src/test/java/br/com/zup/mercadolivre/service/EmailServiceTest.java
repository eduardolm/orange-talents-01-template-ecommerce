package br.com.zup.mercadolivre.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSend() {

        // Arrange and Act
        this.emailService.send("fakemail@mail.com", "jane.doe@example.org", "Hello from the Dreaming Spires",
                "Text");
    }
}

