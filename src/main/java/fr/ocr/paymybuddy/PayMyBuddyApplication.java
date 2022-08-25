package fr.ocr.paymybuddy;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class PayMyBuddyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }
}
