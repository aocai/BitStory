package com.example.mochi.bitstory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.jxmpp.stringprep.XmppStringprepException;

import java.net.UnknownHostException;

public class createRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        Spinner lengthDropDown2 = (Spinner)findViewById(R.id.lengthSpinner2);
        String[] lengthLimit = new String[]{"128 chars", "256 chars", "512 chars", "1024 chars"};
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lengthLimit);
        lengthDropDown2.setAdapter(lengthAdapter);

        Spinner timeDropDown2 = (Spinner)findViewById(R.id.timeSpinner2);
        String[] timeLimit = new String[]{"unlimited", "1 minute", "5 minutes", "10 minutes", "30 minutes"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, timeLimit);
        timeDropDown2.setAdapter(timeAdapter);
    }

    public void createChatRoom(View view)
    {
        Intent intent = new Intent(this, chatRoom.class);
        startActivity(intent);
    }
}
