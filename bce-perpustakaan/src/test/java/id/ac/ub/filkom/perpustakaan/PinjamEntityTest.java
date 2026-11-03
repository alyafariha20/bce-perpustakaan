package id.ac.ub.filkom.perpustakaan;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.TransaksiPinjam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Unit test method entity yang dipanggil control pada sequence. */
class PinjamEntityTest {

    @Test
    void cekStok_dan_kurangiStok() {
        Buku buku = new Buku("B001", "Pemrograman Java", 1);
        assertTrue(buku.cekStok());
        buku.kurangiStok();
        assertEquals(0, buku.getStok());
        assertFalse(buku.cekStok());
        assertThrows(IllegalStateException.class, buku::kurangiStok);
    }

    @Test
    void cekBatasPinjam_menolak_jika_sudah_mencapai_batas() {
        Anggota anggota = new Anggota("A002", "Sari Dewi", 1);
        Buku buku = new Buku("B003", "Basis Data", 2);
        assertTrue(anggota.cekBatasPinjam());

        TransaksiPinjam t = TransaksiPinjam.buat(anggota, buku);
        assertEquals(TransaksiPinjam.DIPINJAM, t.getStatus());
        assertFalse(anggota.cekBatasPinjam());
    }

    @Test
    void buat_menghasilkan_id_9_karakter() {
        TransaksiPinjam t = TransaksiPinjam.buat(
                new Anggota("A001", "Budi", 2), new Buku("B001", "Java", 1));
        assertEquals(9, t.getIdTransaksi().length());
        assertTrue(t.getIdTransaksi().startsWith("T"));
        assertTrue(t.isDipinjam());
    }

    @Test
    void stok_awal_negatif_ditolak() {
        assertThrows(IllegalArgumentException.class, () -> new Buku("B009", "X", -1));
    }
}
