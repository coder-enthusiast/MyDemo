package com.jqk.mydemo.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jqk.mydemo.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ScanResult> datas;

    public MyAdapter(List<ScanResult> datas) {
        this.datas = datas;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void itemClickListener(BluetoothDevice device);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bluetooth, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(datas.get(i).getDevice().getName());
        viewHolder.id.setText(datas.get(i).getDevice().getAddress());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClickListener(datas.get(i).getDevice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, id;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
