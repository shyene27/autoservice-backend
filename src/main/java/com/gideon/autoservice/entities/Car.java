package com.gideon.autoservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long carId;

    @Column(nullable = false)
    private String vin;

    private String make;

    private String model;

    private Short year;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",nullable = false)
    private User user;

    private boolean isDeleted;


    public String getFullName(){

        return (this.make+" "+this.model+" "+this.year);
    }
}
