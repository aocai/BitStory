package com.example.mochi.bitstory;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class BitStoryMessagingService extends FirebaseMessagingService {

    final static String TAG = "BitStoryMesService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0)
        {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            handleMessageNow(remoteMessage);
        }

        if (remoteMessage.getNotification() != null)
        {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleMessageNow(RemoteMessage remoteMessage) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("message", remoteMessage.getData().get("message"));
        Log.d(TAG, remoteMessage.getData().get("message"));
        editor.commit();
        Log.d(TAG, "handlemessagenow() ended");
    }
}
