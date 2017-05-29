package com.example.mochi.bitstory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.UnknownHostException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String regToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regToken", regToken);
        editor.commit();
    }

    public void newStory(View view)
    {
        Intent intent = new Intent(this, newStory.class);
        startActivity(intent);
    }

    public void createRoom(View view)
    {
        Intent intent = new Intent(this, createRoom.class);
        startActivity(intent);
    }

    public void joinRoom(View view)
    {
        Intent intent = new Intent(this, joinRoom.class);
        startActivity(intent);
    }
}
