package com.jqk.mydemo.show.showview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jqk.mydemo.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Integer> datas;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(int x, int y, int width, int height);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyRecyclerViewAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.img.setImageResource(datas.get(position));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
//                holder.mmq.getLocationInWindow(location); //获取在当前窗口内的绝对坐标，含toolBar
                holder.img.getLocationOnScreen(location); //获取在整个屏幕内的绝对坐标，含statusBar
                onClickListener.onClick(location[0], location[1], holder.img.getMeasuredWidth(), holder.img.getMeasuredHeight());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
        }
    }
}
