package com.eduforge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class EduforgeApiApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void generatePassword() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println("Encoded password " + encoder.encode("Admin@123"));
  }
}
