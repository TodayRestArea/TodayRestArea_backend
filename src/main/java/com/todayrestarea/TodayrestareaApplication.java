package com.todayrestarea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class TodayrestareaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodayrestareaApplication.class, args);
	}

}
