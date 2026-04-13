package com.message.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {

        SpringApplication.run(ChatApplication.class, args);
        System.out.println("Chat Web App is running!");
	}

}
