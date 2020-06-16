package com.jqk.jetpacklibrary.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jqk.jetpacklibrary.room.User;

import java.util.List;

public class MyViewModel extends ViewModel implements LifecycleObserver, Observable {
    private MutableLiveData<List<User>> users;
    public MutableLiveData<Boolean> check;
    public MutableLiveData<Boolean> check2;

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }

    public MutableLiveData<Boolean> getCheck() {
        if (check == null) {
            check = new MutableLiveData<>();
        }
        return check;
    }

    public MutableLiveData<Boolean> getCheck2() {
        if (check2 == null) {
            check2 = new MutableLiveData<>();
        }
        return check2;
    }

    public void initView() {
        check.setValue(false);
        check2.setValue(true);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}

class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) (new MyViewModel());
    }
}
