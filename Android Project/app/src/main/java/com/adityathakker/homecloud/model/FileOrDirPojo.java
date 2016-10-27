package com.adityathakker.homecloud.model;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 21/10/16.
 */

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 19/10/16.
 */
public class FileOrDirPojo {
    @SerializedName("type")
    public String type;
    @SerializedName("name")
    public String name;

    public FileOrDirPojo(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

