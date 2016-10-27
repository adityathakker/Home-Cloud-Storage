package com.adityathakker.homecloud.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adityathakker.homecloud.R;
import com.adityathakker.homecloud.interfaces.APIs;
import com.adityathakker.homecloud.model.GeneralPojo;
import com.adityathakker.homecloud.utils.AppConst;
import com.adityathakker.homecloud.utils.CommonTasks;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.activity_login_edittext_username)
    EditText usernameEditText;
    @BindView(R.id.activity_login_edittext_password)
    EditText passwordEditText;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        if(sharedPreferences.getBoolean(AppConst.SharedPrefs.IS_ALREADY_LOGGED_IN, false)){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            String path = AppConst.URLs.BASE_CONTENT_DIR + sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null);
            intent.putExtra("path", path);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.activity_login_button_login)
    public void login(View view){
        final String usernameString = usernameEditText.getText().toString();
        String passwordString = passwordEditText.getText().toString();

        if(usernameString.trim().equals("")){
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwordString.trim().equals("")){
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(CommonTasks.isInternetAvailable(this)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);

            Call<GeneralPojo> loginPojoCall = apIs.check_credentials(usernameString, passwordString);
            loginPojoCall.enqueue(new Callback<GeneralPojo>() {
                @Override
                public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                    GeneralPojo generalPojo = response.body();
                    if(generalPojo.getStatus().equals("success")){

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(AppConst.SharedPrefs.IS_ALREADY_LOGGED_IN, true);
                        editor.putString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, usernameString);
                        editor.putString(AppConst.SharedPrefs.LOGGED_IN_NAME, generalPojo.getMessage());
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        String path = AppConst.URLs.BASE_CONTENT_DIR + sharedPreferences.getString(AppConst.SharedPrefs.LOGGED_IN_USERNAME, null);
                        intent.putExtra("path", path);
                        intent.putExtra("title", "Home");
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralPojo> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: ");
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.activity_login_button_register)
    public void register(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
