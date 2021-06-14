package com.gustavobussolotti.security.jwt.config;

import com.gustavobussolotti.security.jwt.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

   // you can use @Autowired here


   @Bean
   public UserDetailServiceImpl userDetailService() {
      return new UserDetailServiceImpl();
   }

}