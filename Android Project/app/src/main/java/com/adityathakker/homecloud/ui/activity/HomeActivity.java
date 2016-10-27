package com.adityathakker.homecloud.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.adityathakker.homecloud.R;
import com.adityathakker.homecloud.adapters.FilesAndDirAdapter;
import com.adityathakker.homecloud.interfaces.APIs;
import com.adityathakker.homecloud.model.FileOrDirPojo;
import com.adityathakker.homecloud.model.GeneralPojo;
import com.adityathakker.homecloud.model.ListFilesOrDirsPojo;
import com.adityathakker.homecloud.model.TaskStatus;
import com.adityathakker.homecloud.ui.custom.RecyclerEmptyView;
import com.adityathakker.homecloud.utils.AppConst;
import com.adityathakker.homecloud.utils.CommonTasks;
import com.adityathakker.homecloud.utils.Uploads;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int SELECT_FILE = 1;
    @BindView(R.id.content_home_recyclerview)
    RecyclerEmptyView recyclerEmptyView;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    private FilesAndDirAdapter filesAndDirAdapter;

    private SharedPreferences sharedPreferences;
    private String currentPath = null;
    private String selectedPath = null;
    private String selectedSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        currentPath = intent.getStringExtra("path");
        Log.d(TAG, "onCreate: Current Path : " + currentPath);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        setSupportActionBar(toolbar);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerEmptyView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(HomeActivity.this);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        HomeActivity.this,
                        android.R.layout.select_dialog_item);
                arrayAdapter.add("File");
                arrayAdapter.add("Folder");

                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String type = which==0?"file":"folder";
                                Log.d(TAG, "onClick: Which: " + which);
                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setTitle("Title");

                                final EditText input = new EditText(HomeActivity.this);
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

                                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(AppConst.URLs.BASE_URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        final APIs apIs = retrofit.create(APIs.class);

                                        Call<GeneralPojo> generalPojoCall =  apIs.create_file_or_dir_task(sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null), type, currentPath, input.getText().toString());
                                        generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                                            @Override
                                            public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                                                GeneralPojo generalPojo = response.body();
                                                if(generalPojo.getStatus().equals("success")){
                                                    Toast.makeText(HomeActivity.this, "Added To Queue", Toast.LENGTH_SHORT).show();

                                                    final ProgressDialog progressDialog  = new ProgressDialog(HomeActivity.this);
                                                    progressDialog.setIndeterminate(true);
                                                    progressDialog.setMessage("Executing...");
                                                    progressDialog.setCancelable(false);
                                                    progressDialog.show();


                                                    Timer t = new Timer();
                                                    t.scheduleAtFixedRate(new TimerTask() {
                                                        @Override
                                                        public void run() {

                                                            Call<TaskStatus> taskStatusCall = apIs.check_create_task_status(sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null), type, currentPath, input.getText().toString());
                                                            taskStatusCall.enqueue(new Callback<TaskStatus>() {
                                                                @Override
                                                                public void onResponse(Call<TaskStatus> call, Response<TaskStatus> response) {
                                                                    TaskStatus taskStatus = response.body();
                                                                    if(taskStatus.getStatus().equals("success")){
                                                                        if(taskStatus.getExecuted().equals("yes") && taskStatus.getResult().equals("success")){
                                                                            cancel();
                                                                            progressDialog.dismiss();
                                                                            onStart();
                                                                            //do something
                                                                        }else if(taskStatus.getExecuted().equals("no")){
                                                                            return;
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(HomeActivity.this, taskStatus.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<TaskStatus> call, Throwable t) {
                                                                    Log.d(TAG, "onFailure: "+ t.getLocalizedMessage());
                                                                    Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();;
                                                                }
                                                            });
                                                        }
                                                    }, 0, 3000);

                                                }else{
                                                    Toast.makeText(HomeActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GeneralPojo> call, Throwable t) {
                                                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                                                Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                builder.show();
                            }
                        });
                builderSingle.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(CommonTasks.isInternetAvailable(this)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final APIs apIs = retrofit.create(APIs.class);


            Log.d(TAG, "onStart: Path: " + currentPath);
            Call<ListFilesOrDirsPojo> listFilesOrDirsPojoCall = apIs.list_files_and_dirs(currentPath);
            listFilesOrDirsPojoCall.enqueue(new Callback<ListFilesOrDirsPojo>() {
                @Override
                public void onResponse(Call<ListFilesOrDirsPojo> call, Response<ListFilesOrDirsPojo> response) {
                    ListFilesOrDirsPojo listFilesOrDirsPojo = response.body();
                    ArrayList<FileOrDirPojo> arr = listFilesOrDirsPojo.getFilesOrDirs();
                    if(currentPath.equals(AppConst.URLs.BASE_CONTENT_DIR + sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null))){
                        arr.remove(0);
                        arr.remove(0);
                    }
                    filesAndDirAdapter = new FilesAndDirAdapter(HomeActivity.this, arr);
                    recyclerEmptyView.setAdapter(filesAndDirAdapter);

                    filesAndDirAdapter.setOnItemClickListener(new FilesAndDirAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, FileOrDirPojo clickedFileOrDirPojo) {
                            if(clickedFileOrDirPojo.getType().equals("file")){
                                Toast.makeText(HomeActivity.this, "Files Cannot Be Opened", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                                intent.putExtra("path", currentPath + "/" + clickedFileOrDirPojo.getName());
                                intent.putExtra("title", clickedFileOrDirPojo.getName());
                                startActivity(intent);

                            }
                        }
                    });

                    filesAndDirAdapter.setOnItemLongClickListener(new FilesAndDirAdapter.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(View view, int position, final FileOrDirPojo clickedFileOrDirPojo) {
                            final AlertDialog.Builder builderSingle = new AlertDialog.Builder(HomeActivity.this);
                            builderSingle.setTitle("Delete This " + clickedFileOrDirPojo.getType());
                            builderSingle.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Call<GeneralPojo> generalPojoCall = apIs.delete_file_or_dir_task(sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null), clickedFileOrDirPojo.getType(), currentPath, clickedFileOrDirPojo.name);
                                    generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                                        @Override
                                        public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                                            GeneralPojo generalPojo = response.body();
                                            if(generalPojo.getStatus().equals("success")){


                                                Toast.makeText(HomeActivity.this, "Added To Queue", Toast.LENGTH_SHORT).show();

                                                final ProgressDialog progressDialog  = new ProgressDialog(HomeActivity.this);
                                                progressDialog.setIndeterminate(true);
                                                progressDialog.setMessage("Executing...");
                                                progressDialog.setCancelable(false);
                                                progressDialog.show();


                                                Timer t = new Timer();
                                                t.scheduleAtFixedRate(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        Log.d(TAG, "run: Type: " + clickedFileOrDirPojo.getType());
                                                        Call<TaskStatus> taskStatusCall = apIs.check_delete_task_status(sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null), clickedFileOrDirPojo.getType(), currentPath, clickedFileOrDirPojo.getName());
                                                        taskStatusCall.enqueue(new Callback<TaskStatus>() {
                                                            @Override
                                                            public void onResponse(Call<TaskStatus> call, Response<TaskStatus> response) {
                                                                TaskStatus taskStatus = response.body();
                                                                if(taskStatus.getStatus().equals("success")){
                                                                    if(taskStatus.getExecuted().equals("yes") && taskStatus.getResult().equals("success")){
                                                                        cancel();
                                                                        progressDialog.dismiss();
                                                                        onStart();
                                                                        //do something
                                                                    }else if(taskStatus.getExecuted().equals("no")){
                                                                        return;
                                                                    }
                                                                }/*else{
                                                                    Toast.makeText(HomeActivity.this, taskStatus.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }*/
                                                            }

                                                            @Override
                                                            public void onFailure(Call<TaskStatus> call, Throwable t) {
                                                                Log.d(TAG, "onFailure: "+ t.getLocalizedMessage());
                                                                Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();;
                                                            }
                                                        });
                                                    }
                                                }, 0, 3000);


                                            }else{
                                                Toast.makeText(HomeActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GeneralPojo> call, Throwable t) {
                                            Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            builderSingle.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builderSingle.show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<ListFilesOrDirsPojo> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                sharedPreferences.edit().putBoolean(AppConst.SharedPrefs.IS_ALREADY_LOGGED_IN, false).commit();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            case R.id.action_upload:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_FILE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String stringArr[] = getPath(selectedImageUri);
                selectedPath = stringArr[0];
                selectedSize = stringArr[1];
//                textView.setText(selectedPath);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConst.URLs.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final APIs apIs = retrofit.create(APIs.class);

                Call<GeneralPojo> generalPojoCall = apIs.upload_file(sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null), currentPath, selectedSize);
                generalPojoCall.enqueue(new Callback<GeneralPojo>() {
                    @Override
                    public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                        GeneralPojo generalPojo = response.body();
                        if(generalPojo.getStatus().equals("success")){

                            Toast.makeText(HomeActivity.this, "Added To Queue", Toast.LENGTH_SHORT).show();

                            final ProgressDialog progressDialog  = new ProgressDialog(HomeActivity.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Executing...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();


                            Timer t = new Timer();
                            t.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "run: Running");
                                    Call<TaskStatus> taskStatusCall = apIs.check_upload_task_status(sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null), currentPath, selectedSize);
                                    taskStatusCall.enqueue(new Callback<TaskStatus>() {
                                        @Override
                                        public void onResponse(Call<TaskStatus> call, Response<TaskStatus> response) {
                                            TaskStatus taskStatus = response.body();
                                            if(taskStatus.getStatus().equals("success")){
                                                if(taskStatus.getExecuted().equals("exe")){
                                                    progressDialog.setMessage("Uploading...");
                                                    uploadFile();

                                                } else if(taskStatus.getExecuted().equals("yes") && taskStatus.getResult().equals("success")){
                                                    cancel();
                                                    progressDialog.dismiss();
                                                    onStart();
                                                    //do something
                                                }else if(taskStatus.getExecuted().equals("no")){
                                                    return;
                                                }
                                            }else{
                                                Toast.makeText(HomeActivity.this, taskStatus.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<TaskStatus> call, Throwable t) {
                                            Log.d(TAG, "onFailure: Check Upload Status"+ t.getLocalizedMessage());
                                            Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();;
                                        }
                                    });
                                }
                            }, 0, 3000);

                        }else{
                            Toast.makeText(HomeActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralPojo> call, Throwable t) {
                        Log.d(TAG, "onFailure: Upload File" + t.getLocalizedMessage());
                        Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public String[] getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        String size = cursor.getString(sizeIndex);
        Log.d(TAG, "getPath: Size: " + size);
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return new String[]{path, size};
    }

    private void uploadFile() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "onPostExecute: "+ s);
            }

            @Override
            protected String doInBackground(Void... params) {
                Uploads u = new Uploads();
                String msg = u.uploadFile(selectedPath);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }
}
