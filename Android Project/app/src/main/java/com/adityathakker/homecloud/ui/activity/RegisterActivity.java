package com.adityathakker.homecloud.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    @BindView(R.id.textInputEditText_name)
    TextInputEditText name;
    @BindView(R.id.textInputEditText_username)
    TextInputEditText username;
    @BindView(R.id.textInputEditText_password)
    TextInputEditText password;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @OnClick(R.id.content_register_button_register)
    public void register(View view){
        String nameString = name.getText().toString();
        if(nameString == null || nameString.equals("")){
            Toast.makeText(this, "Name Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String usernameString = username.getText().toString();
        if(usernameString == null || usernameString.equals("")){
            Toast.makeText(this, "Username Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString();
        if(passwordString == null || passwordString.equals("")){
            Toast.makeText(this, "Password Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(CommonTasks.isInternetAvailable(this)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);

            Call<GeneralPojo> registerPojoCall = apIs.insert_into_users(usernameString, passwordString, nameString);
            registerPojoCall.enqueue(new Callback<GeneralPojo>() {
                @Override
                public void onResponse(Call<GeneralPojo> call, Response<GeneralPojo> response) {
                    GeneralPojo generalPojo = response.body();
                    if(generalPojo.getStatus().equals("success")){
                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, generalPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralPojo> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: ");
                }
            });
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
