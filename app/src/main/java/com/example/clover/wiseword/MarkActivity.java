package com.example.clover.wiseword;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clover.DiaryActivity;
import com.example.clover.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarkActivity extends Fragment {

    static MarkActivity instance;
    RecyclerView recyclerView;
    String userEmail;
    LinearLayoutManager linearLayoutManager;
    WisewordRVAdapter wisewordRVAdapter;
    ArrayList<Saying> sayinglist = new ArrayList();
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Wise_Saying");
    public MarkActivity() {
    }

    public static MarkActivity getInstance(){
        if(instance == null) instance = new MarkActivity();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override //화면 보여주기
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_love, container, false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.love_recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        wisewordRVAdapter = new WisewordRVAdapter(getContext(),sayinglist);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(wisewordRVAdapter);
        String sfName="save";
        SharedPreferences sf = getActivity().getSharedPreferences(sfName, Context.MODE_PRIVATE);
        userEmail = sf.getString("email", "");

        dataRef.child("사랑").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sayinglist.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Saying tmp = snap.getValue(Saying.class);
                    tmp.setcategory("사랑");
                    if(tmp.getId().contains(userEmail)){
                        sayinglist.add(tmp);
                    }
                }
                wisewordRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataRef.child("희망").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()){
                    Saying tmp = snap.getValue(Saying.class);
                    tmp.setcategory("희망");
                    if(tmp.getId().contains(userEmail)){
                        sayinglist.add(tmp);
                    }
                }
                wisewordRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataRef.child("행복").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()){
                    Saying tmp = snap.getValue(Saying.class);
                    tmp.setcategory("행복");
                    if(tmp.getId().contains(userEmail)){
                        sayinglist.add(tmp);
                    }
                }
                wisewordRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataRef.child("자신감").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()){
                    Saying tmp = snap.getValue(Saying.class);
                    tmp.setcategory("자신감");
                    if(tmp.getId().contains(userEmail)){
                        sayinglist.add(tmp);
                    }
                }
                wisewordRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // 데이터 불러오고
        //그 다음
        wisewordRVAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private DiaryActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final DiaryActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

    }
}