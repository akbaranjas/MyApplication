package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.SerializedName;

public class CreateResponse {

    @SerializedName("message")
    private String message;

    public CreateResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
