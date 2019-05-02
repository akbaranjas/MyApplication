package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.SerializedName;

public class CreateDataRequest {

    @SerializedName("jwt")
    private String jwt;
    @SerializedName("id")
    private Integer id;
    @SerializedName("kode")
    private String kode;
    @SerializedName("nama")
    private String nama;
    @SerializedName("keterangan")
    private String keterangan;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public CreateDataRequest withJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public CreateDataRequest withKode(String kode) {
        this.kode = kode;
        return this;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public CreateDataRequest withNama(String nama) {
        this.nama = nama;
        return this;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public CreateDataRequest withKeterangan(String keterangan) {
        this.keterangan = keterangan;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
