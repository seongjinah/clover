package com.example.clover.wiseword;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clover.R;

import java.util.ArrayList;

public class WisewordRVAdapter extends RecyclerView.Adapter<WisewordRVAdapter.ViewHolder> {
    ArrayList<Saying> data;
    public WisewordRVAdapter(ArrayList<Saying> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.wrapper_wiseword, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //내용설정
        holder.wiseword.setText(data.get(position).getSaying());
        holder.author.setText(data.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView wiseword = itemView.findViewById(R.id.wrapper_wiseword);
        TextView author = itemView.findViewById(R.id.wrapper_author);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
