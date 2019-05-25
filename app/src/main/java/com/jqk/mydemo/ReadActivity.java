package com.jqk.mydemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jqk.mydemo.util.L;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReadActivity extends AppCompatActivity {
    Button read, write;
    TextView content;

    private String filePath = "storage/sdcard0/abc.txt";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        read = findViewById(R.id.read);
        write = findViewById(R.id.write);
        content = findViewById(R.id.content);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setText(read(filePath));
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write(filePath, "添加的内容");
            }
        });
    }

    public String read(String filePath) {

        File f = new File(filePath);
        if (!f.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            return "";
        } else {
            Toast.makeText(this, "文件存在", Toast.LENGTH_SHORT).show();
        }

        StringBuffer str = new StringBuffer();

        File file = new File(filePath);

        //读取文件(缓存字节流)
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //读取数据
        //一次性取多少字节
        byte[] bytes = new byte[2048];
        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
        int n = -1;
        //循环取出数据

        try {
            while ((n = in.read(bytes, 0, bytes.length)) != -1) {
                //转换成字符串
                String string = new String(bytes, 0, n, "UTF-8");
                str.append(string);
                //写入相关文件
            }
            //关闭流
            in.close();
        } catch (IOException e) {

        }

        return str.toString();
    }

    public void write(String filePath, String message) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            L.d("添加内容");
            FileWriter fw = new FileWriter(filePath, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);   //字符串末尾不需要换行符
            pw.flush();
            pw.close();
            fw.close();
            L.d("添加内容完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
