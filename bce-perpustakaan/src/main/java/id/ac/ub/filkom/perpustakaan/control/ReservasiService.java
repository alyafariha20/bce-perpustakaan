package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;
import id.ac.ub.filkom.perpustakaan.entity.AnggotaRepository;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.BukuRepository;
import id.ac.ub.filkom.perpustakaan.entity.Reservasi;
import id.ac.ub.filkom.perpustakaan.entity.ReservasiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * «control» use case Ajukan Reservasi («extend» dari Pinjam Buku).
 * Nomor [n] mengacu ke docs/sequence-diagram.md (UC-03).
 */
@Service
public class ReservasiService {

    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;
    private final ReservasiRepository reservasiRepository;

    public ReservasiService(AnggotaRepository anggotaRepository,
                            BukuRepository bukuRepository,
                            ReservasiRepository reservasiRepository) {
        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
        this.reservasiRepository = reservasiRepository;
    }

    /** [2] cari data buku untuk ditampilkan di halaman reservasi. */
    @Transactional(readOnly = true)
    public Buku cariBuku(String idBuku) {
        return bukuRepository.findById(idBuku)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Buku"));
    }

    /** [3] daftarReservasi(idAnggota) */
    @Transactional(readOnly = true)
    public List<Reservasi> daftarReservasi(String idAnggota) {
        return reservasiRepository.findByAnggotaIdAnggotaOrderByTanggalReservasiDesc(idAnggota);
    }

    /** [6] ajukanReservasi(idAnggota, idBuku) */
    @Transactional
    public Reservasi ajukanReservasi(String idAnggota, String idBuku) {
        // [7] findById(..) anggota & buku
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Anggota"));
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Buku"));

        // [8] cekStok()  -> [9] true/false
        if (buku.cekStok()) {
            throw ReservasiGagalException.stokMasihAda();     // [13] pesanGagal
        }
        if (reservasiRepository.existsByAnggotaIdAnggotaAndBukuIdBukuAndStatus(
                idAnggota, idBuku, Reservasi.MENUNGGU)) {
            throw ReservasiGagalException.sudahMereservasi(); // [13] pesanGagal
        }

        // alt [stok habis]
        // [10] Reservasi.buat(anggota, buku)  -> [11] save(reservasi)
        Reservasi reservasi = Reservasi.buat(anggota, buku);
        return reservasiRepository.save(reservasi);           // [12] reservasi tersimpan
    }
}
