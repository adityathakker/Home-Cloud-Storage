package com.adityathakker.homecloud.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 25/10/16.
 */

public class TaskStatus {
    @SerializedName("status")
    public String status;
    @SerializedName("executed")
    public String executed;
    @SerializedName("result")
    public String result;
    @SerializedName("comment")
    public String comment;
    @SerializedName("message")
    public String message;

    public TaskStatus(String status, String executed, String result, String comment, String message) {
        this.status = status;
        this.executed = executed;
        this.result = result;
        this.comment = comment;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
