package com.apple.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
		var a = new Homework();
		a.setName("홍길동");
		a.setAge(60);
		a.나이설정(20);
		a.한살더하기();
		System.out.println(a.getAge());
	}



}
