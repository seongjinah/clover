package com.example.clover.wiseword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clover.DiaryActivity;
import com.example.clover.MainActivity;
import com.example.clover.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class WisewordRVAdapter extends RecyclerView.Adapter<WisewordRVAdapter.ViewHolder> {
    ArrayList<Saying> data;
    Context mContext;
    String userEmail;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mDatabase.getReference();

    public WisewordRVAdapter(Context mContext, ArrayList<Saying> data) {
        this.data = data;
        this.mContext = mContext;
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
        Saying article = data.get(position);
        String sfName="save";
        SharedPreferences sf = mContext.getSharedPreferences(sfName, Context.MODE_PRIVATE);
        userEmail = sf.getString("email", "");

        if(article.getId().contains(userEmail)) {
            holder.star.setBackgroundResource(R.drawable.star_on);
        }
        else {
            holder.star.setBackgroundResource(R.drawable.star_off);
        }

        holder.wiseword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent;
                intent = new Intent(context, WiseWord_Click.class);
                intent.putExtra("it_saying",data.get(position).getSaying());
                intent.putExtra("it_author",data.get(position).getAuthor());
                mContext.startActivity(intent);
                ((WisewordActivity)mContext).finish();
                return;
            }
        });

        holder.author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent;
                intent = new Intent(context, WiseWord_Click.class);
                intent.putExtra("it_saying",data.get(position).getSaying());
                intent.putExtra("it_author",data.get(position).getAuthor());
                mContext.startActivity(intent);
                ((WisewordActivity)mContext).finish();
                return;
            }
        });

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseReference.child("Wise_Saying").child(data.get(position).getcategory()).child(Integer.toString(position+1)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Saying article1 = dataSnapshot.getValue(Saying.class);
                        if (article1.addLover(userEmail)) {
                            holder.star.setImageResource(R.drawable.star_on);
                        }
                        else {
                            holder.star.setBackgroundResource(R.drawable.star_off);
                        }
                        mDatabaseReference.child("Wise_Saying").child(data.get(position).getcategory()).child(Integer.toString(position+1)).child("id").setValue(article1.getId());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public  TextView wiseword;
        public  TextView author;
        public ImageView star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            this.wiseword = itemView.findViewById(R.id.wrapper_wiseword);
            this.author = itemView.findViewById(R.id.wrapper_author);
            this.star = itemView.findViewById(R.id.wrapper_star);
        }

    }
}
