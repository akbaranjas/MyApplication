package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("jwt")
    private String jwt;

    public LoginResponse(String message, String jwt) {
        this.message = message;
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
