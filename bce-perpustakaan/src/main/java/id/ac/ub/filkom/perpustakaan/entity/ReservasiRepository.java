package id.ac.ub.filkom.perpustakaan.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Akses tabel reservasi. */
public interface ReservasiRepository extends JpaRepository<Reservasi, String> {

    List<Reservasi> findByAnggotaIdAnggotaOrderByTanggalReservasiDesc(String idAnggota);

    boolean existsByAnggotaIdAnggotaAndBukuIdBukuAndStatus(String idAnggota, String idBuku, String status);
}
