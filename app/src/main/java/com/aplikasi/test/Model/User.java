package com.aplikasi.test.Model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

public class User {
    @SerializedName("success")
    @Nullable
    private Boolean succes;
    @SerializedName("message")
    @Nullable
    private String message;
    @SerializedName("token")
    @Nullable
    private String token;
    @SerializedName("email")
    @Nullable
    private String email;
    @SerializedName("error")
    @Nullable
    private String error;

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    @Nullable
    public Boolean getSucces() {
        return succes;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }

    @Nullable
    public String getError() {
        return error;
    }
}
