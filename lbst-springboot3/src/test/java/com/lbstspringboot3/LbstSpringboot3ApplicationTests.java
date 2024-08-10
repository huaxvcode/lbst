package com.lbstspringboot3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class LbstSpringboot3ApplicationTests {

	@Test
	void contextLoads() {
		log.info(System.getProperty("user.path"));
	}

}
