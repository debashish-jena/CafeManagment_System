package com.ind.cafe.backend.Helper;

import com.ind.cafe.backend.pojo.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CafeHelper {

    public boolean valiidateSignUp (Map<String , String> requestMap)
    {
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
        && requestMap.containsKey("password"))
        {
            return true;
        }
        return false;
    }

    public User getUserFormRequest(Map<String , String> requestMap)
    {
        User user = new User();
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setPassword(requestMap.get("password"));
        user.setRole("user");
        user.setStatus("false");
        return user;
    }
}
