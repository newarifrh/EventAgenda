package stis.ks2.group2.eventagenda.model;

public class Pertanyaan {
    private String pertanyaan;
    private String jawaban;
    private String idPertanyaan;
    private String idJawaban;

    public Pertanyaan(){

    }

    public String getIdPertanyaan() {
        return idPertanyaan;
    }

    public void setIdPertanyaan(String idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public String getIdJawaban() {
        return idJawaban;
    }

    public void setIdJawaban(String idJawaban) {
        this.idJawaban = idJawaban;
    }

    public Pertanyaan(String pertanyaan, String jawaban, String idPertanyaan, String idJawaban) {
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
        this.idPertanyaan = idPertanyaan;
        this.idJawaban = idJawaban;
    }


    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
