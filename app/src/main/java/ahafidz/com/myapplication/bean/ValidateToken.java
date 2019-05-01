package ahafidz.com.myapplication.bean;

import com.google.gson.annotations.SerializedName;

public class ValidateToken {

    @SerializedName("jwt")
    private String jwt;

    public ValidateToken(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
