package stis.ks2.group2.eventagenda.model;

public class Events {
    private String id;
    private String nama;
    private String tanggalMulai;
    private String jamMulai;
    private String tanggalSelesai;
    private String lokasi;
    private String deskripsi;
    private String cp;
    private String gambar;

    public Events() {

    }

    public Events(String id, String nama, String tanggalMulai, String jamMulai, String tanggalSelesai, String lokasi, String deskripsi, String cp, String gambar) {
        this.id = id;
        this.nama = nama;
        this.tanggalMulai = tanggalMulai;
        this.jamMulai = jamMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.cp = cp;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
