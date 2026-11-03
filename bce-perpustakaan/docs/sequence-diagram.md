# Sequence Diagram Latihan (pesan diberi nomor)

Nomor `[n]` yang sama ditulis sebagai komentar di kode.
(Tempel blok mermaid ke https://mermaid.live untuk menggambar/ekspor gambar.)

## UC-02 Kelola Stok Buku (aktor: Pustakawan)
Kode: `HalamanStokController`, `StokService`, entity `Buku`

```mermaid
sequenceDiagram
    actor P as Pustakawan
    participant B as HalamanStokController<br/>«boundary»
    participant C as StokService<br/>«control»
    participant E as Buku<br/>«entity»
    participant R as BukuRepository

    P->>B: [1] bukaHalamanStok()
    B->>C: [2] daftarBuku()
    C->>R: [3] findAll()
    R-->>C: [4] List<Buku>
    C-->>B: List<Buku>
    B-->>P: [5] tampilkan daftar buku & stok

    P->>B: [6] ubahStok (tambah)
    B->>C: [7] tambahStok(idBuku, jumlah)
    C->>R: [8] findById(idBuku)
    C->>E: [9] tambahStok(jumlah)
    C->>R: [10] save(buku)
    alt berhasil
        B-->>P: [11] pesan "Stok berhasil ditambah"
    else jumlah tidak valid / buku tidak ada
        B-->>P: [12] pesan gagal
    end

    P->>B: [6] ubahStok (koreksi)
    B->>C: [13] koreksiStok(idBuku, stokBaru)
    C->>R: [14] findById(idBuku)
    C->>E: [15] koreksiStok(stokBaru)
    C->>R: [16] save(buku)
    alt berhasil
        B-->>P: [17] pesan "Stok berhasil dikoreksi"
    else stok negatif / buku tidak ada
        B-->>P: [18] pesan gagal
    end
```

## UC-03 Ajukan Reservasi («extend» Pinjam Buku, aktor: Anggota)
Kode: `HalamanReservasiController`, `ReservasiService`, entity `Reservasi`

```mermaid
sequenceDiagram
    actor A as Anggota
    participant B as HalamanReservasiController<br/>«boundary»
    participant C as ReservasiService<br/>«control»
    participant Bk as Buku<br/>«entity»
    participant R as Reservasi<br/>«entity»
    participant Rp as Repository (JPA)

    A->>B: [1] klik "Ajukan Reservasi" (pinjam.html)
    B->>C: [2] cariBuku(idBuku)
    B->>C: [3] daftarReservasi(idAnggota)
    B-->>A: [4] tampilkan halaman reservasi

    A->>B: [5] ajukan(idBuku)
    B->>C: [6] ajukanReservasi(idAnggota, idBuku)
    C->>Rp: [7] findById(idAnggota), findById(idBuku)
    C->>Bk: [8] cekStok()
    Bk-->>C: [9] true / false
    alt stok habis
        C->>R: [10] Reservasi.buat(anggota, buku)
        C->>Rp: [11] save(reservasi)
        Rp-->>C: [12] reservasi tersimpan
        B-->>A: [14] pesan "Reservasi berhasil diajukan"
    else stok masih ada / sudah mereservasi
        C-->>B: [13] throw ReservasiGagalException
        B-->>A: [15] tampilkan pesan gagal
    end
```
