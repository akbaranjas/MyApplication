package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.SerializedName;

public class ValidateResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ValidateData data;

    public ValidateResponse(String message, ValidateData data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidateData getData() {
        return data;
    }

    public void setData(ValidateData data) {
        this.data = data;
    }
}
