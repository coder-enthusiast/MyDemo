package com.jqk.mydemo.jetpack.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by jiqingke
 * on 2019/2/19
 */
@Entity
data class Book(
        @PrimaryKey
        @NonNull
        var name: String
)