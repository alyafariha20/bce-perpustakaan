# BCE Perpustakaan

Aplikasi perpustakaan lengkap dengan pola BCE (boundary / control / entity) di Spring Boot.
Gabungan: Pinjam Buku, Kelola Stok, Ajukan Reservasi (slide), plus daftar/cari/tambah buku
dan lihat/tambah anggota.

Jalankan: `mvn spring-boot:run` atau tombol Run di `PerpustakaanApplication.java`
Buka:     http://localhost:8080
Uji:      `mvn test`
H2:       http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:perpustakaan`, user `SA`)

| URL | Fungsi | Boundary -> Control |
|---|---|---|
| /pinjam | Pinjam buku, bukti | HalamanPinjamController -> PinjamService |
| /buku | Daftar, cari, tambah buku | HalamanBukuController -> BukuService |
| /anggota | Lihat, tambah anggota | HalamanAnggotaController -> AnggotaService |
| /stok | Kelola stok (Pustakawan) | HalamanStokController -> StokService |
| /reservasi | Ajukan reservasi | HalamanReservasiController -> ReservasiService |

Anggota yang meminjam adalah A001 (disimpan di sesi). Login tidak termasuk cakupan slide.

## Refactor: contoh 01 -> BCE

Contoh 01 memakai package per lapisan teknis (`controller`, `service`, `repository`, `entity`).
Di proyek ini disusun ulang menurut stereotype BCE:

| Contoh 01 | BCE Perpustakaan |
|---|---|
| `controller/BukuController` + `buku.html` | `boundary/HalamanBukuController` + `buku.html` |
| `service/BukuService` | `control/BukuService` |
| `entity/Buku` + `repository/BukuRepository` | `entity/Buku` + `entity/BukuRepository` |
| (anggota belum ada) | `boundary/HalamanAnggotaController`, `control/AnggotaService`, `entity/Anggota` |
| (pinjam belum ada) | `boundary/HalamanPinjamController`, `control/PinjamService`, `entity/TransaksiPinjam` |

Aturan stok dipindah ke entity `Buku` (`cekStok`, `kurangiStok`, `tambahStok`, `koreksiStok`)
sehingga controller dan service tidak mengubah stok langsung.

Sequence diagram Latihan: `docs/sequence-diagram.md`.
