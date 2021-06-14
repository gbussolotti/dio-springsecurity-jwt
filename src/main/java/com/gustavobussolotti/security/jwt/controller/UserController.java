package com.gustavobussolotti.security.jwt.controller;

import com.gustavobussolotti.security.jwt.data.UserData;
import com.gustavobussolotti.security.jwt.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    //Teste
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @RequestMapping("/all-users")
    public List<UserData> listAllUsers(){
        return userDetailService.listUsers();
    }
}
