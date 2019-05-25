package com.jqk.mydemo.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jqk.mydemo.R;

/**
 * Created by Administrator on 2018/7/1.
 */

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private EditText userName, passWord;
    private Button login;

    private LoginPresenter loginPresenter;

    private FragmentTransaction ft;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(this);

        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(progressDialog, "ProgressDialog");
            if (!progressDialog.isAdded() && !progressDialog.isVisible() && !progressDialog.isRemoving()) {
                ft.commitAllowingStateLoss();
            }
        } else {
            if (!progressDialog.isAdded() && !progressDialog.isVisible() && !progressDialog.isRemoving()) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.add(progressDialog, "ProgressDialog");
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    public String getUserName() {
        return userName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return passWord.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                loginPresenter.login();
                break;
        }
    }
}
