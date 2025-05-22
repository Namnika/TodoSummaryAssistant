package com.todoSummaryApplication.todoSummary;

import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import java.sql.*;

@RestController
@SpringBootApplication
public class TodoSummary {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUsername;
	@Value("${spring.datasource.password}")
	private String dbPassword;

	public static void main(String[] args) {
		SpringApplication.run(TodoSummary.class, args);
	}

	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World!";
	}

	@PostConstruct
	public void init() {
		try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {

			System.out.println("Connected to Supabase DB Successfully!");
		} catch (SQLException e) {
			System.err.println("Database Connection Failed: " + e.getMessage());
		}
	}

}
