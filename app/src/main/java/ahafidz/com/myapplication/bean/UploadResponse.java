package ahafidz.com.myapplication.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("uploaded")
    @Expose
    private List<Uploaded> uploaded = null;

    public List<Uploaded> getUploaded() {
        return uploaded;
    }

    public void setUploaded(List<Uploaded> uploaded) {
        this.uploaded = uploaded;
    }

    public UploadResponse withUploaded(List<Uploaded> uploaded) {
        this.uploaded = uploaded;
        return this;
    }

}