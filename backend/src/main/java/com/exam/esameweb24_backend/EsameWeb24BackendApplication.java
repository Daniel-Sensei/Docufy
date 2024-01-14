package com.exam.esameweb24_backend;

import com.exam.esameweb24_backend.controller.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsameWeb24BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsameWeb24BackendApplication.class, args);
        Utility.updateAllDocumentsState();
    }

}
