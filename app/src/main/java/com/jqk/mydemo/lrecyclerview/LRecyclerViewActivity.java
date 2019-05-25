package com.jqk.mydemo.lrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Button;

import com.jqk.loadmorelibrary.LRecyclerView;
import com.jqk.mydemo.R;

import java.util.ArrayList;

public class LRecyclerViewActivity extends AppCompatActivity {
    private static final int SUCCESS = 1000;
    private static final int ERROR = 1001;
    private static final int OVER = 1002;
    private static final int REFRESH = 1003;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LRecyclerView lRecyclerView;
    private MyRecyclerViewAdapter myAdapter;
    private int page = 1;
    private ArrayList<String> datas;

    private boolean isError = true;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    if (page == 4) {
                        lRecyclerView.setNomoreData();
                    } else {
                        page++;
                        for (int i = 0; i < 10; i++) {
                            datas.add(i + "add");
                        }
                        lRecyclerView.loadmoreSuccess();
                        lRecyclerView.notifyDataSetChanged();
                    }
                    break;
                case ERROR:
                    lRecyclerView.loadmoreFail();
                    break;
                case REFRESH:
                    swipeRefreshLayout.setRefreshing(false);
                    page = 1;
                    datas.clear();
                    for (int i = 0; i < 10; i++) {
                        datas.add(i + "");
                    }
                    lRecyclerView.reset();
                    lRecyclerView.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrecyclerview);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lRecyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(REFRESH, 3000);
            }
        });

        datas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            datas.add(i + "");
        }

        myAdapter = new MyRecyclerViewAdapter(this, datas);
        lRecyclerView.setAdapter(myAdapter);
        lRecyclerView.addHeadView(new Button(this));
        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerView.setOnLoadmoreListener(new LRecyclerView.OnLoadmoreListener() {
            @Override
            public void onLoadmore() {

                if (page == 2) {
                    if (isError) {
                        handler.sendEmptyMessageDelayed(ERROR, 3000);
                        isError = false;
                    } else {
                        handler.sendEmptyMessageDelayed(SUCCESS, 3000);
                    }
                } else {
                    handler.sendEmptyMessageDelayed(SUCCESS, 3000);
                }
            }
        });
    }
}
