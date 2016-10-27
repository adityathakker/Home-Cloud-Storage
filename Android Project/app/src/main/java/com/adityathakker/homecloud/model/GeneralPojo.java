package com.adityathakker.homecloud.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 19/10/16.
 */
public class GeneralPojo {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;

    public GeneralPojo(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
