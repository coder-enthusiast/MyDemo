package com.jqk.mydemo.databinding;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jqk.mydemo.R;

public class DBTestActivity extends AppCompatActivity {

    private com.jqk.mydemo.databinding.ActivityDbtestBinding binding;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dbtest);
        binding.setView(this);
        user = new User();
        user.userName.set("123");
        user.url.set("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530785850098&di=973db687d2afcb1989c3d2d5d7973015&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201509%2F23%2F140610ebbookekbr1qbte1.jpg");
        user.id.set(R.mipmap.ic_launcher);
        binding.setUser(user);

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(DBTestActivity.this, user.userName.get(), Toast.LENGTH_SHORT).show();
                user.userName.set("456");
            }
        });


//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530785850098&di=973db687d2afcb1989c3d2d5d7973015&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201509%2F23%2F140610ebbookekbr1qbte1.jpg").into(binding.img);
    }

//    @BindingAdapter({"imageUrl", "error"})
//    public static void loadImage(ImageView view, String url, int id) {
//        Glide.with(view.getContext()).load(url).error(id).into(view);
//    }
//
//    <ImageView app:imageUrl="@{venue.imageUrl}" app:error="@{@drawable/venueError}" />


    // 如果imageUrl和placeholder都用于ImageView对象并且imageUrl是字符串而placeholder是Drawable，则调用适配器。
    // 如果希望在设置任何属性时调用适配器，
    // 则可以将适配器的可选requireAll标志设置为false
    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, Drawable placeholder) {
        if (url == null) {
            imageView.setImageDrawable(placeholder);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
        }
    }

    public void click(View view) {
        Toast.makeText(this, "我", Toast.LENGTH_SHORT).show();
    }
}
