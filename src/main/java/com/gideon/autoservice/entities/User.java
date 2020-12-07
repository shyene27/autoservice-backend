package com.gideon.autoservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long userId;

    @Column(nullable = false)
    private String userEmail;

    //  @Column(nullable = false)
    private String userPassword;

    // Can be ADMIN, MECHANIC, CUSTOMER
    @Column(nullable = false)
    private String role;

    private String firstName;

    private String lastName;

    private boolean isEnabled;

    @Column(name = "is_not_deleted")
    private boolean isNotExpired = true;


}
