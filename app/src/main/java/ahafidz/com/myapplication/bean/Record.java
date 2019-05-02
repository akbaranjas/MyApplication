
package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("dibuatOleh")
    @Expose
    private String dibuatOleh;
    @SerializedName("dirubahOleh")
    @Expose
    private String dirubahOleh;
    @SerializedName("tglDibuat")
    @Expose
    private String tglDibuat;
    @SerializedName("tglDirubah")
    @Expose
    private String tglDirubah;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Record withId(String id) {
        this.id = id;
        return this;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Record withKode(String kode) {
        this.kode = kode;
        return this;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Record withNama(String nama) {
        this.nama = nama;
        return this;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Record withKeterangan(String keterangan) {
        this.keterangan = keterangan;
        return this;
    }

    public String getDibuatOleh() {
        return dibuatOleh;
    }

    public void setDibuatOleh(String dibuatOleh) {
        this.dibuatOleh = dibuatOleh;
    }

    public Record withDibuatOleh(String dibuatOleh) {
        this.dibuatOleh = dibuatOleh;
        return this;
    }

    public String getDirubahOleh() {
        return dirubahOleh;
    }

    public void setDirubahOleh(String dirubahOleh) {
        this.dirubahOleh = dirubahOleh;
    }

    public Record withDirubahOleh(String dirubahOleh) {
        this.dirubahOleh = dirubahOleh;
        return this;
    }

    public String getTglDibuat() {
        return tglDibuat;
    }

    public void setTglDibuat(String tglDibuat) {
        this.tglDibuat = tglDibuat;
    }

    public Record withTglDibuat(String tglDibuat) {
        this.tglDibuat = tglDibuat;
        return this;
    }

    public String getTglDirubah() {
        return tglDirubah;
    }

    public void setTglDirubah(String tglDirubah) {
        this.tglDirubah = tglDirubah;
    }

    public Record withTglDirubah(String tglDirubah) {
        this.tglDirubah = tglDirubah;
        return this;
    }

}
