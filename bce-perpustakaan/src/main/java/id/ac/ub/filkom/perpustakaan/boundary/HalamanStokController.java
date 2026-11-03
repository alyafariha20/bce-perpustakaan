package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.StokService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * «boundary» HalamanStok — use case Kelola Stok Buku (aktor: Pustakawan).
 * Nomor [n] mengacu ke docs/sequence-diagram.md (UC-02).
 */
@Controller
@RequestMapping("/stok")
public class HalamanStokController {

    private final StokService stokService;               // «control»

    public HalamanStokController(StokService stokService) {
        this.stokService = stokService;
    }

    /** [1] bukaHalamanStok()  -> [2] daftarBuku()  ... [5] tampilkan daftar. */
    @GetMapping
    public String tampilHalaman(Model model) {
        model.addAttribute("daftarBuku", stokService.daftarBuku());
        return "stok";                                    // templates/stok.html
    }

    /** [6] ubahStok: tambah stok  -> [7] tambahStok(idBuku, jumlah). */
    @PostMapping("/tambah")
    public String tambahStok(@RequestParam String idBuku,
                             @RequestParam int jumlah,
                             RedirectAttributes redirect) {
        try {
            stokService.tambahStok(idBuku, jumlah);
            redirect.addFlashAttribute("sukses", "Stok berhasil ditambah");     // [11]
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirect.addFlashAttribute("pesan", e.getMessage());                // [12] pesan gagal
        }
        return "redirect:/stok";                          // Post-Redirect-Get
    }

    /** [6] ubahStok: koreksi stok  -> [13] koreksiStok(idBuku, stokBaru). */
    @PostMapping("/koreksi")
    public String koreksiStok(@RequestParam String idBuku,
                              @RequestParam int stokBaru,
                              RedirectAttributes redirect) {
        try {
            stokService.koreksiStok(idBuku, stokBaru);
            redirect.addFlashAttribute("sukses", "Stok berhasil dikoreksi");    // [17]
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirect.addFlashAttribute("pesan", e.getMessage());                // [18] pesan gagal
        }
        return "redirect:/stok";
    }
}
