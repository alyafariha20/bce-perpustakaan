package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.BukuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** «control» daftar, cari, dan tambah buku. */
@Service
public class BukuService {

    private final BukuRepository bukuRepository;

    public BukuService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> cariBuku(String kata) {
        if (kata == null || kata.isBlank()) {
            return bukuRepository.findAll();
        }
        return bukuRepository.findByJudulContainingIgnoreCase(kata.trim());
    }

    public Buku tambahBuku(String idBuku, String judul, int stok) {
        if (bukuRepository.existsById(idBuku)) {
            throw new IllegalArgumentException("ID buku sudah dipakai");
        }
        // stok negatif ditolak oleh constructor entity Buku
        return bukuRepository.save(new Buku(idBuku, judul, stok));
    }
}
