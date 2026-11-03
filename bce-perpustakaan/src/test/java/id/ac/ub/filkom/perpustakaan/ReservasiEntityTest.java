package id.ac.ub.filkom.perpustakaan;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.Reservasi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Latihan: unit test method entity Reservasi. */
class ReservasiEntityTest {

    private Reservasi reservasiBaru() {
        return Reservasi.buat(new Anggota("A001", "Budi", 2),
                new Buku("B002", "Rekayasa Perangkat Lunak", 0));
    }

    @Test
    void buat_membuat_reservasi_berstatus_menunggu() {
        Reservasi r = reservasiBaru();
        assertEquals(Reservasi.MENUNGGU, r.getStatus());
        assertTrue(r.isMenunggu());
        assertEquals(9, r.getIdReservasi().length());
        assertTrue(r.getIdReservasi().startsWith("R"));
        assertNotNull(r.getTanggalReservasi());
    }

    @Test
    void batalkan_mengubah_status_dan_tidak_bisa_dua_kali() {
        Reservasi r = reservasiBaru();
        r.batalkan();
        assertEquals(Reservasi.DIBATALKAN, r.getStatus());
        assertFalse(r.isMenunggu());
        assertThrows(IllegalStateException.class, r::batalkan);
    }
}
