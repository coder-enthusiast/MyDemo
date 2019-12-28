package com.jqk.mydemo.dagger2.login;

import com.jqk.commonlibrary.util.L;

import javax.inject.Inject;

public class UserLocalDataSource {
    @Inject
    public UserLocalDataSource() {
        L.d("UserLocalDataSource");
    }
}
