package ahafidz.com.myapplication.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("uploaded")
    @Expose
    private List<String> uploaded = null;

    public List<String> getUploaded() {
        return uploaded;
    }

    public void setUploaded(List<String> uploaded) {
        this.uploaded = uploaded;
    }

    public UploadResponse withUploaded(List<String> uploaded) {
        this.uploaded = uploaded;
        return this;
    }

}