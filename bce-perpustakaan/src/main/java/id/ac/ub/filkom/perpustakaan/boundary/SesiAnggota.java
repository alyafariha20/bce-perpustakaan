package id.ac.ub.filkom.perpustakaan.boundary;

import jakarta.servlet.http.HttpSession;

/**
 * Pembantu boundary: id anggota yang sedang login disimpan di HttpSession
 * (bukan di field controller, karena @Controller adalah singleton).
 * Login tidak termasuk cakupan latihan, jadi dipakai anggota contoh A001.
 */
final class SesiAnggota {

    static final String KUNCI = "idAnggota";
    static final String ANGGOTA_CONTOH = "A001";

    private SesiAnggota() { }

    static String idAnggotaLogin(HttpSession session) {
        Object id = session.getAttribute(KUNCI);
        if (id == null) {
            session.setAttribute(KUNCI, ANGGOTA_CONTOH);
            return ANGGOTA_CONTOH;
        }
        return (String) id;
    }
}
