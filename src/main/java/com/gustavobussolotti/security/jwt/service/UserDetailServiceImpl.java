package com.gustavobussolotti.security.jwt.service;

import com.gustavobussolotti.security.jwt.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserData user = findUser(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    private UserData findUser(String username){
        return new UserData("admin", bCryptPasswordEncoder.encode("ninja"));

    }

    public List<UserData> listUsers(){
        ArrayList<UserData> list = new ArrayList<>();
        list.add(findUser("admin"));
        return list;
    }

}
