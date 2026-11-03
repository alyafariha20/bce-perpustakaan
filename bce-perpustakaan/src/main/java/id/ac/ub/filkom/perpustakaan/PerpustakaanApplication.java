package id.ac.ub.filkom.perpustakaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Titik masuk aplikasi. Component scan mencakup boundary, control, dan entity. */
@SpringBootApplication
public class PerpustakaanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerpustakaanApplication.class, args);
    }
}
