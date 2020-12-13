package com.gideon.autoservice.dao;

import com.gideon.autoservice.entities.Order;
import com.gideon.autoservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

}
