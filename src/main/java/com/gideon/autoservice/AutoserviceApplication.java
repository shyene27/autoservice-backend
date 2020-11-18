package com.gideon.autoservice;

import com.gideon.autoservice.controllers.UserRestController;
import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoserviceApplication.class, args);
	}


}
