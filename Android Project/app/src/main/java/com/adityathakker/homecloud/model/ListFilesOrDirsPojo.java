package com.adityathakker.homecloud.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 19/10/16.
 */
public class ListFilesOrDirsPojo {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public ArrayList<FileOrDirPojo> filesOrDirs;

    public ListFilesOrDirsPojo(String status, ArrayList<FileOrDirPojo> filesOrDirs) {
        this.status = status;
        this.filesOrDirs = filesOrDirs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<FileOrDirPojo> getFilesOrDirs() {
        return filesOrDirs;
    }

    public void setFilesOrDirs(ArrayList<FileOrDirPojo> filesOrDirs) {
        this.filesOrDirs = filesOrDirs;
    }
}
