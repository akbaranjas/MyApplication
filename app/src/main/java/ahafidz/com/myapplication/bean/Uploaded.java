package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Uploaded implements Serializable {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("file")
    @Expose
    private String file;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Uploaded withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Uploaded withFile(String file) {
        this.file = file;
        return this;
    }

}
