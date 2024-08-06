package com.lbstspringboot3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lbstspringboot3.mapper")
public class LbstSpringboot3Application {

	public static void main(String[] args) {
		SpringApplication.run(LbstSpringboot3Application.class, args);
	}

}
