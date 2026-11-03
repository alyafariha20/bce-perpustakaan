package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.AnggotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** «boundary» lihat dan tambah anggota. */
@Controller
@RequestMapping("/anggota")
public class HalamanAnggotaController {

    private final AnggotaService anggotaService;         // «control»

    public HalamanAnggotaController(AnggotaService anggotaService) {
        this.anggotaService = anggotaService;
    }

    // GET http://localhost:8080/anggota
    @GetMapping
    public String lihatAnggota(Model model) {
        model.addAttribute("daftarAnggota", anggotaService.lihatSemuaAnggota());
        return "anggota";                                 // templates/anggota.html
    }

    // POST dari form tambah anggota
    @PostMapping
    public String tambahAnggota(@RequestParam String idAnggota,
                                @RequestParam String nama,
                                @RequestParam int batasPinjam,
                                Model model) {
        try {
            anggotaService.tambahAnggota(idAnggota, nama, batasPinjam);
        } catch (IllegalArgumentException e) {
            model.addAttribute("pesan", e.getMessage());
            model.addAttribute("daftarAnggota", anggotaService.lihatSemuaAnggota());
            return "anggota";                             // tampilkan lagi + pesan
        }
        return "redirect:/anggota";                       // pola Post-Redirect-Get
    }
}
