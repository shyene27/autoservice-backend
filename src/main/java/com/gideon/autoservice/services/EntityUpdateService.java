package com.gideon.autoservice.services;

import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.entities.Order;
import com.gideon.autoservice.entities.User;

public class EntityUpdateService {

    public static User updateUserUtil(User modifiedUser, User currentUser){

        if (modifiedUser.getUserEmail()!=null) currentUser.setUserEmail(modifiedUser.getUserEmail());
        if (modifiedUser.getRole()!=null) currentUser.setRole(modifiedUser.getRole());
        if (modifiedUser.getFirstName()!=null) currentUser.setFirstName(modifiedUser.getFirstName());
        if (modifiedUser.getLastName()!=null) currentUser.setLastName(modifiedUser.getLastName());

        return currentUser;
    }


    public static Car updateCarUtil(Car modifiedCar, Car currentCar) {

        if (modifiedCar.getVin() != null) currentCar.setVin(modifiedCar.getVin());
        if (modifiedCar.getMake() != null) currentCar.setMake(modifiedCar.getMake());
        if (modifiedCar.getModel() != null) currentCar.setModel(modifiedCar.getModel());
        if (modifiedCar.getYear() != null) currentCar.setYear(modifiedCar.getYear());

        return currentCar;
    }

    public static Order updateOrderUtil(Order modifiedOrder, Order currentOrder){

        return currentOrder;
    }
}
