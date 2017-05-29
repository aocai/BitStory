package com.example.mochi.bitstory;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class chatRoom extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    final static String TAG = "chatroom";

    AtomicInteger msgId;
    private EditText msg_editText;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;
    Set<String> chatMessageSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        msg_editText = (EditText)findViewById(R.id.textMessage);
        msgListView = (ListView)findViewById(R.id.msgListView);
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);

        chatlist = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatlist);
        msgListView.setAdapter(chatAdapter);

        //check for persisted messages
        Set<String> oldChatMessages = pref.getStringSet("chatMessageSet", new HashSet<String>());
        Gson gson = new Gson();
        for (String s : oldChatMessages)
        {
            ChatMessage message = gson.fromJson(s, ChatMessage.class);
            chatAdapter.add(message);
        }
        chatAdapter.sortChatlist();
        chatAdapter.notifyDataSetChanged();

        chatMessageSet = new HashSet<>();
        chatMessageSet.addAll(oldChatMessages);

        msgId = new AtomicInteger(pref.getInt("msgId", 0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.unregisterOnSharedPreferenceChangeListener(this);

        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("chatMessageSet", chatMessageSet);
        editor.putInt("msgId", msgId.intValue());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences pref, String key)
    {
        Log.d(TAG, "onsharedpreferencechanged() called");
        if (key.equals("message"))
        {
            Log.d(TAG, "key is equal to message");
            String msg_data = pref.getString(key, "");
            printReceivedMessage(msg_data);

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("message", "");
            editor.apply();
        }
    }

    public void sendMessage(View v)
    {
        String msg = msg_editText.getEditableText().toString();
        if (!msg.equalsIgnoreCase(""))
        {
            //add message to chatAdapter
            ChatMessage chatMessage = new ChatMessage("user1", "user2", msg, Integer.toString(msgId.incrementAndGet()), true);
            msg_editText.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();

            Gson gson = new Gson();
            String json = gson.toJson(chatMessage);
            chatMessageSet.add(json);


            //send message to app server
            FirebaseMessaging fm = FirebaseMessaging.getInstance();
            String SENDER_ID = "908033597396";
            fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
                    .setMessageId(Integer.toString(msgId.get()))
                    .addData("message", msg)
                    .build());
        }
    }

    public void printReceivedMessage(String data)
    {
        Log.d(TAG, "printRecievedmessage called");

        if (!data.equalsIgnoreCase(""))
        {
            Log.d(TAG, "data is not null");
            ChatMessage chatMessage = new ChatMessage("user2", "user1", data, Integer.toString(msgId.incrementAndGet()), false);
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();

            Gson gson = new Gson();
            String json = gson.toJson(chatMessage);
            chatMessageSet.add(json);
        }
    }
}
