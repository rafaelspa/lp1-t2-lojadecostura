package com.example.lojacosturafx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class LojaCosturaFxApplication {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Application.launch(JavaFxApplication.class, args);
	}
}

//https://www.youtube.com/watch?v=_pm7tzGsheY crud com lista