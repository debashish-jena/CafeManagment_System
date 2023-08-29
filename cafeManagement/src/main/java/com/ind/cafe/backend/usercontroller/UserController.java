package com.ind.cafe.backend.usercontroller;

import com.ind.cafe.backend.constents.CafeConstant;
import com.ind.cafe.backend.userservices.UserService;
import com.ind.cafe.backend.utils.CafeUtils;
import com.ind.cafe.backend.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController   {

    @Autowired
    private UserService userService;


    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String , String> requestMap)
   {

    try {
       return userService.signUp(requestMap);
    }catch (Exception ex)
    {
       ex.printStackTrace();
    }
   return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
}

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String , String> requestMap)
    {

        try {
            return userService.login(requestMap);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "/get")
    public  ResponseEntity<List<UserWrapper>> getAllUser()
    {

       try {
           return userService.getAllUser();

       }catch (Exception ex){
           ex.printStackTrace();
       }
       return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String,String> requestMap){
        try {
            return userService.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
