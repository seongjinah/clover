package com.example.clover;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {

    MediaPlayer mp;
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    DatabaseReference mDbRef = mDb.getReference();
    FirebaseStorage mStorage = FirebaseStorage.getInstance();
    StorageReference mStRef = mStorage.getReference();

    final ArrayList<String> music = new ArrayList<>();
    int num = 0;
    int size;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service","start!!");
        if(mp==null){
            //////////////////////////////////////////알림창//////////////////////////////////////////////////////////////////////////
            Intent mMainIntent = new Intent(this,MainActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(this,1,mMainIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder=null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String channelID="channel_01";
                String channelName="MyChannel01";
                NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(channel);
                mBuilder=new NotificationCompat.Builder(this, channelID);
            }else{
                mBuilder= new NotificationCompat.Builder(this, null);
            }
            mBuilder.setSmallIcon(R.drawable.clover4)
                    .setContentTitle("Clover")
                    .setContentText("음악 재생 중")
                    .setContentIntent(mPendingIntent);
                    //.setAutoCancel(true);
            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(001,mBuilder.build());

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            mDbRef.child("Music").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String music_url = postSnapshot.child("url").getValue(String.class);
                        music.add(music_url);
                        Log.d("url",music_url);
                    }
                    size =music.size();

                    mp = new MediaPlayer();
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mp.setDataSource(music.get(num));
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //mp.setLooping(false);
                    mp.start();

                    mp.setOnCompletionListener(completionListener);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return START_STICKY;
    }

    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            num=(num+1)%size;
            mp.reset();
            mp.release();
            mp=null;
            mp = new MediaPlayer();
            try {
                mp.setDataSource(music.get(num));
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
            mp.setOnCompletionListener(completionListener);
        }
    };

    @Override
    public void onDestroy() {
        if(mp!=null){
            mp.stop();
            mp.reset();
            mp.release();
            mp=null;
            Log.d("Service","end!!");
        }
        super.onDestroy();
    }
}
