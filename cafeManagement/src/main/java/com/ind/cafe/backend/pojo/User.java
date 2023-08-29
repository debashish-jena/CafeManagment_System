package com.ind.cafe.backend.pojo;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")

@NamedQuery(name = "User.getAllUser",query = "select new com.ind.cafe.backend.wrapper.UserWrapper(u.userId,u.name,u.contactNumber,u.email,u.password,u.role,u.status,u.firstName,u.lastName) from User u where u.role='user'")


@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.userId=:userId")


@NamedQuery(name = "User.getAllAdmin",query = "select u.email from User u where u.role='admin'")



@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name ="users")
public class User implements Serializable {

    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="name")
    private String name ;

    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name ="password")
    private String password;

    @Column(name ="role")
    private String role;

    @Column (name = "status")
    private String status;

    @Column( name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
