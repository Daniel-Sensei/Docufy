package com.exam.esameweb24_backend;

import com.exam.esameweb24_backend.controller.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class EsameWeb24BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsameWeb24BackendApplication.class, args);
        // Utility.updateAllDocumentsState();
    }
}
