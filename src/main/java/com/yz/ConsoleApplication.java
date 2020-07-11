package com.yz;

import com.yz.sample.MainApp;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    public static void main(String[] args) {
//        SpringApplication.run(ConsoleApplication.class, args);
        Application.launch(MainApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
