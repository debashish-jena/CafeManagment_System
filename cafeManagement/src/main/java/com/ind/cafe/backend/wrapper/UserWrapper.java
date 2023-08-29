package com.ind.cafe.backend.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {
    private Integer userId;
    private String name ;
    private String contactNumber;
    private String email;
    private String password;
    private String role;
    private String status;
    private String firstName;
    private String lastName;


    public UserWrapper(Integer userId, String name, String contactNumber, String email, String password, String role, String status, String firstName, String lastName) {
        this.userId = userId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
