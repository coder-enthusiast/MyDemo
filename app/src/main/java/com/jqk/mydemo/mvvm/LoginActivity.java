package com.jqk.mydemo.mvvm;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jqk.mydemo.R;
import com.jqk.mydemo.databinding.ActivityLoginbdBinding;
import com.jqk.mydemo.mvp.ProgressDialog;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginbdBinding binding;
    private LoginViewModel viewModel;

    private ProgressDialog progressDialog;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loginbd);

        viewModel = new LoginViewModel(this, binding);

        viewModel.login(new View(this));
    }

    public String getUserName() {
        return binding.userName.getText().toString().trim();
    }

    public String getPassWord() {
        return binding.password.getText().toString().trim();
    }

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

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
