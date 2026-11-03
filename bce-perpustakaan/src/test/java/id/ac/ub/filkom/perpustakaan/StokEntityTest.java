package id.ac.ub.filkom.perpustakaan;

import id.ac.ub.filkom.perpustakaan.entity.Buku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Latihan: unit test method entity baru pada use case Kelola Stok Buku. */
class StokEntityTest {

    @Test
    void tambahStok_menambah_jumlah() {
        Buku buku = new Buku("B002", "Rekayasa Perangkat Lunak", 0);
        buku.tambahStok(5);
        assertEquals(5, buku.getStok());
        assertTrue(buku.cekStok());
    }

    @Test
    void tambahStok_menolak_jumlah_nol_atau_negatif() {
        Buku buku = new Buku("B001", "Pemrograman Java", 3);
        assertThrows(IllegalArgumentException.class, () -> buku.tambahStok(0));
        assertThrows(IllegalArgumentException.class, () -> buku.tambahStok(-2));
        assertEquals(3, buku.getStok());
    }

    @Test
    void koreksiStok_mengganti_stok() {
        Buku buku = new Buku("B001", "Pemrograman Java", 3);
        buku.koreksiStok(0);
        assertEquals(0, buku.getStok());
        buku.koreksiStok(10);
        assertEquals(10, buku.getStok());
    }

    @Test
    void koreksiStok_menolak_negatif() {
        Buku buku = new Buku("B001", "Pemrograman Java", 3);
        assertThrows(IllegalArgumentException.class, () -> buku.koreksiStok(-1));
        assertEquals(3, buku.getStok());
    }
}
