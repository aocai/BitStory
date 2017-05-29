package com.example.mochi.bitstory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

public class newStory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        Spinner lengthDropDown = (Spinner)findViewById(R.id.lengthSpinner);
        String[] lengthLimit = new String[]{"128 chars", "256 chars", "512 chars", "1024 chars"};
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lengthLimit);
        lengthDropDown.setAdapter(lengthAdapter);

        Spinner timeDropDown = (Spinner)findViewById(R.id.timeSpinner);
        String[] timeLimit = new String[]{"unlimited", "1 minute", "5 minutes", "10 minutes", "30 minutes"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, timeLimit);
        timeDropDown.setAdapter(timeAdapter);
    }
}
