package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.BukuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** «boundary» daftar, cari, dan tambah buku. */
@Controller
@RequestMapping("/buku")
public class HalamanBukuController {

    private final BukuService bukuService;               // «control»

    public HalamanBukuController(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    // GET http://localhost:8080/buku?kata=java
    @GetMapping
    public String daftarBuku(@RequestParam(required = false) String kata, Model model) {
        model.addAttribute("daftarBuku", bukuService.cariBuku(kata));
        model.addAttribute("kata", kata);
        return "buku";                                    // templates/buku.html
    }

    // POST dari form tambah buku
    @PostMapping
    public String tambahBuku(@RequestParam String idBuku,
                             @RequestParam String judul,
                             @RequestParam int stok,
                             Model model) {
        try {
            bukuService.tambahBuku(idBuku, judul, stok);
        } catch (IllegalArgumentException e) {
            model.addAttribute("pesan", e.getMessage());
            model.addAttribute("daftarBuku", bukuService.cariBuku(null));
            return "buku";                                // tampilkan lagi + pesan
        }
        return "redirect:/buku";                          // pola Post-Redirect-Get
    }
}
