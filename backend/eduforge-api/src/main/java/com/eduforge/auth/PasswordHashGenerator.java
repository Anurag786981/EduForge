package com.eduforge.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    String password = "EduForge@123";

    String hash = encoder.encode(password);

    System.out.println(hash);
  }
  /*
          * After getting Hashcode We have to execute the below query bto update the password
          *
          * UPDATE users
  SET
      password = '$2a$10$Kr1QJ.TTUElduvz5doY9H..djC2Bw2bz91DLLSDpUOFg3P3FSzvtO',
      account_locked = false,
      failed_login_attempts = 0,
      password_change_required = false
  WHERE email = 'AnuragEduForge@gmail.com';
          * */
}
