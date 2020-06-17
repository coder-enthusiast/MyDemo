package com.jqk.jetpacklibrary.viewmodel;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.jqk.commonlibrary.util.L;
import com.jqk.jetpacklibrary.R;
import com.jqk.jetpacklibrary.databinding.ActivityViewmodelBinding;

/**
 * checkbox,switch尽量不要使用双向绑定
 */
public class ViewModelActivity extends AppCompatActivity {
    private ActivityViewmodelBinding binding;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewmodel);
        binding.setView(this);

        myViewModel = new ViewModelFactory().create(MyViewModel.class);

        binding.setViewModel(myViewModel);

        getLifecycle().addObserver(myViewModel);

        myViewModel.getCheck().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                L.d("aBoolean = " + aBoolean);
//                binding.checkbox02.setChecked(!aBoolean);
            }
        });

        myViewModel.getCheck2().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                L.d("aBoolean2 = " + aBoolean);
//                binding.checkbox.setChecked(!aBoolean);
            }
        });

        binding.checkbox.setChecked(true);
        binding.checkbox02.setChecked(false);

//        myViewModel.initView();
//
//        myViewModel.getCheck().setValue(false);
//        myViewModel.getCheck2().setValue(true);
    }
}
