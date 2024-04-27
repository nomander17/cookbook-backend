package com.major.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.major.cookbook.services.UserService;

@SpringBootApplication
public class CookbookApplication implements CommandLineRunner{

	@Autowired
    private UserService userServices;
	
	public static void main(String[] args) {
		SpringApplication.run(CookbookApplication.class, args);
	}
	@Override
	public void run(String... args){
        userServices.addAdmin();
    }
}
