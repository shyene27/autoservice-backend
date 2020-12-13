package com.gideon.autoservice.entities;

import com.gideon.autoservice.enums.Role;
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

    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String firstName;

    private String lastName;

    private boolean isEnabled;

    @Column(name = "is_not_deleted")
    private boolean isNotExpired = true;

    public String getFullName(){
        return (this.firstName+" "+this.lastName);
    }

}
