package com.example.mochi.bitstory;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Mochi on 5/17/2017.
 */

public class FirebaseRegistrationToken extends FirebaseInstanceIdService {

    final static String TAG = "FirebaseRegToken";
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        storeRegistrationToken(refreshedToken);

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void storeRegistrationToken(String refreshedToken) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regToken", refreshedToken);
        editor.commit();
    }

    private void sendRegistrationToServer(String refreshedToken) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String SENDER_ID = "908033597396";
        fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com").setMessageId(Integer.toString(5)).addData("regToken", refreshedToken).build());
        Log.d(TAG, "upstream msg sent");
    }
}
