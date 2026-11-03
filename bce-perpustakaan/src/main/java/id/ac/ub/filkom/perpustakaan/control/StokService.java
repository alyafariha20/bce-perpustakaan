package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.BukuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * «control» use case Kelola Stok Buku (aktor: Pustakawan).
 * Nomor [n] mengacu ke docs/sequence-diagram.md (UC-02).
 */
@Service
public class StokService {

    private final BukuRepository bukuRepository;

    public StokService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    /** [2] daftarBuku()  -> [3] findAll()  -> [4] List<Buku> */
    @Transactional(readOnly = true)
    public List<Buku> daftarBuku() {
        return bukuRepository.findAll();
    }

    /** [7] tambahStok(idBuku, jumlah) */
    @Transactional
    public Buku tambahStok(String idBuku, int jumlah) {
        Buku buku = cariBuku(idBuku);             // [8] findById(idBuku)
        buku.tambahStok(jumlah);                  // [9] tambahStok(jumlah)
        return bukuRepository.save(buku);         // [10] save(buku)
    }

    /** [13] koreksiStok(idBuku, stokBaru) */
    @Transactional
    public Buku koreksiStok(String idBuku, int stokBaru) {
        Buku buku = cariBuku(idBuku);             // [14] findById(idBuku)
        buku.koreksiStok(stokBaru);               // [15] koreksiStok(stokBaru)
        return bukuRepository.save(buku);         // [16] save(buku)
    }

    private Buku cariBuku(String idBuku) {
        return bukuRepository.findById(idBuku)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan"));
    }
}
