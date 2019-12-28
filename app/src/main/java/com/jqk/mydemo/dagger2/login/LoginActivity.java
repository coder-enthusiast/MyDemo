package com.jqk.mydemo.dagger2.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.mydemo.R;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    @Inject
    LoginViewModel loginViewModel;

    // Reference to the Login graph
    LoginComponent loginComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Make Dagger instantiate @Inject fields in LoginActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Creation of the login graph using the application graph
        loginComponent = ((MyApplication2) getApplicationContext())
                .appComponent.loginComponent().create();



        // Make Dagger instantiate @Inject fields in LoginActivity
        loginComponent.inject(this);
    }
}
