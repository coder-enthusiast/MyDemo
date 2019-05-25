package com.jqk.mydemo.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class User extends BaseObservable {

    public final ObservableField<String> userName = new ObservableField<>();

    public final ObservableField<String> url = new ObservableField<>();

    public final ObservableInt id = new ObservableInt();

}
