package com.example.clover.wiseword;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clover.DiaryActivity;
import com.example.clover.MainActivity;
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
        public  TextView wiseword;
        public  TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            this.wiseword = itemView.findViewById(R.id.wrapper_wiseword);
            this.author = itemView.findViewById(R.id.wrapper_author);
        }

    }
}
