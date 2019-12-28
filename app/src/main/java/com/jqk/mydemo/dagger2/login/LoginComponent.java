package com.jqk.mydemo.dagger2.login;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Subcomponent;
@ActivityScope
@Subcomponent
public interface LoginComponent {
    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Builder
    interface Factory {
        LoginComponent create();
    }

    void inject(LoginActivity loginActivity);
}
