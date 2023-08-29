package com.ind.cafe.backend.jwt;
import com.ind.cafe.backend.pojo.User;
import com.ind.cafe.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

 private com.ind.cafe.backend.pojo.User userDetail;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        //log.info("inside loadUserByUsername {}",username);

        System.out.println("inside loadUserByUsername {}"+username);
        userDetail = userRepository.findByEmailId(username);
       // System.out.println(userDetail);
        if (!Objects.isNull(userDetail)) {
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());

        }
        else
            throw new UsernameNotFoundException("User not found");
    }

    public com.ind.cafe.backend.pojo.User getUserDetail(){

        return userDetail;
    }


}
