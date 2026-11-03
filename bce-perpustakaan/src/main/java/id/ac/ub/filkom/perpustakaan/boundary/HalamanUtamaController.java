package id.ac.ub.filkom.perpustakaan.boundary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Alamat "/" diarahkan ke halaman Pinjam. */
@Controller
public class HalamanUtamaController {

    @GetMapping("/")
    public String awal() {
        return "redirect:/pinjam";
    }
}
