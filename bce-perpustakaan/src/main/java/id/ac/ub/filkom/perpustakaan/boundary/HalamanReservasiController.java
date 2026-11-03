package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.ReservasiGagalException;
import id.ac.ub.filkom.perpustakaan.control.ReservasiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * «boundary» HalamanReservasi — use case «extend» Ajukan Reservasi.
 * Nomor [n] mengacu ke docs/sequence-diagram.md (UC-03).
 */
@Controller
@RequestMapping("/reservasi")
public class HalamanReservasiController {

    private final ReservasiService reservasiService;     // «control»

    public HalamanReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    /** [1] klik "Ajukan Reservasi" di pinjam.html  -> [2] cariBuku, [3] daftarReservasi. */
    @GetMapping
    public String tampilHalaman(@RequestParam(required = false) String idBuku,
                                HttpSession session, Model model) {
        String idAnggota = SesiAnggota.idAnggotaLogin(session);
        model.addAttribute("idAnggota", idAnggota);
        try {
            if (idBuku != null) {
                model.addAttribute("buku", reservasiService.cariBuku(idBuku));
            }
        } catch (ReservasiGagalException e) {
            model.addAttribute("pesan", e.getMessage());
        }
        model.addAttribute("daftarReservasi", reservasiService.daftarReservasi(idAnggota));
        return "reservasi";                               // templates/reservasi.html
    }

    /** [5] ajukan(idBuku)  -> [6] ajukanReservasi(idAnggota, idBuku). */
    @PostMapping
    public String ajukan(@RequestParam String idBuku,
                         HttpSession session, Model model) {
        String idAnggota = SesiAnggota.idAnggotaLogin(session);
        try {
            reservasiService.ajukanReservasi(idAnggota, idBuku);
            model.addAttribute("sukses", "Reservasi berhasil diajukan");   // [14]
        } catch (ReservasiGagalException e) {
            model.addAttribute("pesan", e.getMessage());                    // [13] pesanGagal
        }
        return tampilHalaman(idBuku, session, model);                       // [15] tampilkan hasil
    }
}
