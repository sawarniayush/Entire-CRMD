package com.example.android.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> pendingAdapter;
        setContentView(R.layout.activity_home);
        textView = (TextView) findViewById(R.id.welcome_txt);
        String message = getIntent().getStringExtra("message");
        textView.setText(message);

//        String supervisor[] = getIntent().getStringArrayExtra("supervisor");

//        JSONObject jo = jo.getJSONObject()
//            Log.v("checking: ",BackgroundTask.sArray[0]);
        Log.v("checking: ", BackgroundTask.sString[0]);
//            Log.v("checking type: ",BackgroundTask.supervisor.getClass().getName());
//        Log.v("checking type: ",BackgroundTask.sString.getClass().getName());
//        Log.v("checking: ",BackgroundTask.sString);

        List<String> complaintList = new ArrayList<String>(Arrays.asList(BackgroundTask.sString));
        pendingAdapter = new ArrayAdapter<String>(HomeActivity.this, R.layout.list_view_item, R.id.textView, complaintList);
//
//
//        ListView listView = (ListView) findViewById(R.id.pendingList);
//        listView.setAdapter(pendingAdapter);


    }

    public void clicked(View v)
    {
        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
        HomeActivity.this.finish();

    }


}
