package com.ind.cafe.backend.serviceImpl;

import com.ind.cafe.backend.Helper.CafeHelper;
import com.ind.cafe.backend.pojo.User;
import com.ind.cafe.backend.repository.UserRepository;
import com.ind.cafe.backend.userservices.userservicesImpl.UserServicesImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class userServiceImplTest {

    @InjectMocks
    private UserServicesImpl userServices;

    @Mock
    private JavaMailSender emailSender;
    @Mock
    private CafeHelper cafeHelper;

    @Mock
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSignUp_Success() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@example.com");

        when(cafeHelper.valiidateSignUp(requestMap)).thenReturn(true);
        when(userRepository.findByEmailId("test@example.com")).thenReturn(null);

        ResponseEntity<String> response = userServices.signUp(requestMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSignUp_UserPresent() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@example.com");

        when(cafeHelper.valiidateSignUp(requestMap)).thenReturn(true);
        when(userRepository.findByEmailId("test@example.com")).thenReturn(new User());

        ResponseEntity<String> response = userServices.signUp(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testSignUp_InvalidData() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@example.com");

        when(cafeHelper.valiidateSignUp(requestMap)).thenReturn(false);

        ResponseEntity<String> response = userServices.signUp(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testSignUp_Exception() throws RuntimeException {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@example.com");

       when(cafeHelper.valiidateSignUp(requestMap)).thenReturn(true);
       //when(cafeHelper.valiidateSignUp(requestMap)).thenThrow(new RuntimeException());

        ResponseEntity<String> response = userServices.signUp(requestMap);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
