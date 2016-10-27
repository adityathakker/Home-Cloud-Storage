package com.adityathakker.homecloud.interfaces;

import com.adityathakker.homecloud.model.GeneralPojo;
import com.adityathakker.homecloud.model.ListFilesOrDirsPojo;
import com.adityathakker.homecloud.model.TaskStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 19/10/16.
 */

public interface APIs {
    @GET("check_credentials.php")
    Call<GeneralPojo> check_credentials(@Query("username") String username, @Query("password") String password);

    @GET("list_dir_content.php")
    Call<ListFilesOrDirsPojo> list_files_and_dirs(@Query("path") String path);

    @GET("insert_into_users.php")
    Call<GeneralPojo> insert_into_users(@Query("username") String username, @Query("password") String password, @Query("name") String name);

    @GET("create_file_or_dir.php")
    Call<GeneralPojo> create_file_or_dir_task(@Query("username") String username, @Query("type") String type,
                                              @Query("path") String path, @Query("name") String name);

    @GET("upload_file.php")
    Call<GeneralPojo> upload_file(@Query("username") String username, @Query("path") String path, @Query("size") String size);

    @GET("check_upload_task_status.php")
    Call<TaskStatus> check_upload_task_status(@Query("username") String username, @Query("path") String path, @Query("size") String size);

    @GET("delete_file_or_dir.php")
    Call<GeneralPojo> delete_file_or_dir_task(@Query("username") String username, @Query("type") String type,
                                              @Query("path") String path, @Query("name") String name);

    @GET("check_create_task_status.php")
    Call<TaskStatus> check_create_task_status(@Query("username") String username, @Query("type") String type,
                                              @Query("path") String path, @Query("name") String name);

    @GET("check_delete_task_status.php")
    Call<TaskStatus> check_delete_task_status(@Query("username") String username, @Query("type") String type,
                                              @Query("path") String path, @Query("name") String name);
}
