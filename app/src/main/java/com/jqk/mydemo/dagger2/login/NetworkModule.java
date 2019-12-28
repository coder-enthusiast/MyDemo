package com.jqk.mydemo.dagger2.login;

import com.jqk.commonlibrary.util.L;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetworkModule {
    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. LoginRetrofitService).
    // Function parameters are the dependencies of this type.
    @Singleton
    @Provides
    public LoginRetrofitService provideLoginRetrofitService() {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.

        L.d("LoginRetrofitService");

        return new Retrofit.Builder()
                .baseUrl("https://example.com")
                .build()
                .create(LoginRetrofitService.class);
    }
}
