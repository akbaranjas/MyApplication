
package ahafidz.com.myapplication.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryReadResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("records")
    @Expose
    private List<Record> records = null;
    @SerializedName("token")
    @Expose
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CategoryReadResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public CategoryReadResponse withRecords(List<Record> records) {
        this.records = records;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CategoryReadResponse withToken(String token) {
        this.token = token;
        return this;
    }

}
