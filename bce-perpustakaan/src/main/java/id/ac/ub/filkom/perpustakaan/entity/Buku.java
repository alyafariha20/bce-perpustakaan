package id.ac.ub.filkom.perpustakaan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** «entity» Buku — tabel buku (skema 8a). */
@Entity
@Table(name = "buku")
public class Buku {

    @Id
    @Column(name = "id_buku", length = 10)
    private String idBuku;

    @Column(name = "judul", length = 200)
    private String judul;

    @Column(name = "stok")
    private int stok;

    protected Buku() { }   // wajib untuk JPA

    public Buku(String idBuku, String judul, int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        this.idBuku = idBuku;
        this.judul = judul;
        this.stok = stok;
    }

    /** +cekStok() : boolean */
    public boolean cekStok() {
        return stok > 0;
    }

    /** +kurangiStok() : void */
    public void kurangiStok() {
        if (stok <= 0) {
            throw new IllegalStateException("Stok sudah habis");
        }
        stok--;
    }

    /** Latihan (Kelola Stok Buku): +tambahStok(jumlah) : void */
    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah tambah stok harus lebih dari 0");
        }
        stok += jumlah;
    }

    /** Latihan (Kelola Stok Buku): +koreksiStok(stokBaru) : void */
    public void koreksiStok(int stokBaru) {
        if (stokBaru < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        stok = stokBaru;
    }

    public String getIdBuku() { return idBuku; }
    public String getJudul()  { return judul; }
    public int getStok()      { return stok; }
}
