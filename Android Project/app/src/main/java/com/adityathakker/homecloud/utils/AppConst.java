package com.adityathakker.homecloud.utils;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 19/10/16.
 */
public class AppConst {
    public static abstract class SharedPrefs{
        public static final String IS_ALREADY_LOGGED_IN = "is_already_logged_in";
        public static final String LOGGED_IN_USERNAME = "logged_in_username";
        public static final String LOGGED_IN_NAME = "logged_in_name";
    }

    public static abstract class URLs{
        public static final String BASE_URL = "http://192.168.43.181/home_cloud/";
        public static final String BASE_CONTENT_DIR = "users_dir/";
    }
}
