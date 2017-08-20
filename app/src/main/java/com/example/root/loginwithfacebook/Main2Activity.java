package com.example.root.loginwithfacebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView view = (TextView)findViewById(R.id.text);
        Bundle b = getIntent().getExtras();
        String tempName = b.getString("fileds");
        if (tempName.length() > 0) {
            view.setText("Welcome! "+tempName);
        }

    }
}
