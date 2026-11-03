package id.ac.ub.filkom.perpustakaan.control;

/** Alur alternatif use case Pinjam Buku (dilempar control, ditangkap boundary). */
public class PinjamGagalException extends RuntimeException {

    private final boolean bisaReservasi;

    private PinjamGagalException(String pesan, boolean bisaReservasi) {
        super(pesan);
        this.bisaReservasi = bisaReservasi;
    }

    /** [13] pesanHabis */
    public static PinjamGagalException stokHabis() {
        return new PinjamGagalException("Stok buku habis", true);
    }

    /** alur 4b */
    public static PinjamGagalException melewatiBatas() {
        return new PinjamGagalException("Melewati batas pinjam", false);
    }

    public static PinjamGagalException tidakDitemukan(String objek) {
        return new PinjamGagalException(objek + " tidak ditemukan", false);
    }

    public boolean isBisaReservasi() {
        return bisaReservasi;
    }
}
