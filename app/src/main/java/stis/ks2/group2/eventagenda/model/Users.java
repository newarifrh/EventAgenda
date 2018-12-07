package stis.ks2.group2.eventagenda.model;

public class Users {
    private String nim;
    private String nama;
    private String kelas;
    private String id;

    public Users() {

    }

    public Users(String nim, String nama, String kelas, String id) {
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
