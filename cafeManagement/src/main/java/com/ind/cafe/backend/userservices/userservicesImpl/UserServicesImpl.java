package com.ind.cafe.backend.userservices.userservicesImpl;

import com.ind.cafe.backend.Helper.CafeHelper;
import com.ind.cafe.backend.constents.CafeConstant;
import com.ind.cafe.backend.jwt.CustomerUsersDetailsService;
import com.ind.cafe.backend.jwt.JwtFilter;
import com.ind.cafe.backend.jwt.JwtUtil;
import com.ind.cafe.backend.pojo.User;
import com.ind.cafe.backend.repository.UserRepository;
import com.ind.cafe.backend.userservices.UserService;
import com.ind.cafe.backend.utils.CafeUtils;
import com.ind.cafe.backend.utils.EmailUtils;
import com.ind.cafe.backend.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServicesImpl implements UserService {

    @Autowired
    private CafeHelper cafeHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("inside SignUp {}",requestMap);
        try {

            if (cafeHelper.valiidateSignUp(requestMap)) {
                User user = userRepository.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userRepository.save(cafeHelper.getUserFormRequest(requestMap));
                    return CafeUtils.getResponseEntity(CafeConstant.SUCCESS, HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity(CafeConstant.USER_PRESENT, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
//        log.info("Inside login");
        System.out.println("Inside login");
        try {
            System.out.println("12345678");
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if (auth.isAuthenticated()){
                System.out.println("12345");
                System.out.println(customerUsersDetailsService.getUserDetail());

                if (customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    System.out.println("insdie status");
                    return new ResponseEntity<String>("{\"token\":\""+jwtUtil
                            .generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                    HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception ex){
//            log.error("{}",ex);
            System.out.println("{}"+ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()){
                return  new ResponseEntity<>(userRepository.getAllUser(),HttpStatus.OK);

            }else {
                return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){

               Optional<User> optional= userRepository.findById(Integer.parseInt(requestMap.get("userId")));

               if (!optional.isEmpty()){
                   userRepository.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("userId")));
                   sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userRepository.getAllAdmin());
                   return CafeUtils.getResponseEntity("User Status Updated Successful",HttpStatus.OK);
               }else {
                   return CafeUtils.getResponseEntity("User id does not exist",HttpStatus.OK);
               }

            }else {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {

        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved","USER:- " + user + "\n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled","USER:- " + user + "\n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);

        }
    }


}
