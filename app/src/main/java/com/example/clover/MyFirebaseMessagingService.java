package com.example.clover;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(remoteMessage != null && remoteMessage.getData().size()>0)
            sendNotification(remoteMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        final String CHANNEL_ID = "ChannerID";
        NotificationManager mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            final String CHANNEL_NAME = "ChannerName";
            final String CHANNEL_DESCRIPTION = "ChannerDescription";
            final int importance = NotificationManager.IMPORTANCE_HIGH;

            //add in API level 26
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            mManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentTitle(title);
        builder.setContentText(message);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            builder.setContentTitle(title);
            builder.setVibrate(new long[]{500, 500});
        }
        mManager.notify(0, builder.build());
    }

    //새 토큰이 생성될 때마다 onNewToken 함수가 호출됨. 토큰은 다음의 이유로 변경될 수 있음
    //1. 앱에서 인스턴스 ID 삭제
    //2. 새 기기에서 앱 복원
    //3. 사용자가 앱 삭제/ 재설치
    //4. 사용자가 앱 데이터 소거
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}






