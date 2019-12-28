package com.jqk.mydemo.dagger2.login;

import com.jqk.commonlibrary.util.L;

import javax.inject.Inject;

public class UserRemoteDataSource {
    private final LoginRetrofitService loginRetrofitService;

    @Inject
    public UserRemoteDataSource(LoginRetrofitService loginRetrofitService) {
        L.d("UserRemoteDataSource");
        this.loginRetrofitService = loginRetrofitService;
    }
}
