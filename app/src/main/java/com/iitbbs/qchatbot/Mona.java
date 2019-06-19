package com.iitbbs.qchatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Mona extends AppCompatActivity {

    private TextView qAndA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mona);

        qAndA = (TextView)findViewById(R.id.QandA);

        qAndA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mona.this, Sample.class);
                startActivity(intent);
            }
        });
    }
}
