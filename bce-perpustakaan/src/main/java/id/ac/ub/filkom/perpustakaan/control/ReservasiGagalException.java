package id.ac.ub.filkom.perpustakaan.control;

/** Alur alternatif use case Ajukan Reservasi. */
public class ReservasiGagalException extends RuntimeException {

    public ReservasiGagalException(String pesan) {
        super(pesan);
    }

    public static ReservasiGagalException tidakDitemukan(String objek) {
        return new ReservasiGagalException(objek + " tidak ditemukan");
    }

    public static ReservasiGagalException stokMasihAda() {
        return new ReservasiGagalException("Stok buku masih tersedia, silakan pinjam langsung");
    }

    public static ReservasiGagalException sudahMereservasi() {
        return new ReservasiGagalException("Anda sudah mereservasi buku ini");
    }
}
