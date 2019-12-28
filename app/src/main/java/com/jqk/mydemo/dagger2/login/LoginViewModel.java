package com.jqk.mydemo.dagger2.login;

import com.jqk.commonlibrary.util.L;

import javax.inject.Inject;
@ActivityScope
public class LoginViewModel {
    private final UserRepository userRepository;

    // @Inject tells Dagger how to create instances of LoginViewModel
    @Inject
    public LoginViewModel(UserRepository userRepository) {
        L.d("LoginViewModel");
        this.userRepository = userRepository;
    }
}
