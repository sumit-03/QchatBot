package com.iitbbs.qchatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText userId;
    private Button logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userId = (EditText)findViewById(R.id.userId);
        logIn = (Button)findViewById(R.id.logIn);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId.equals("sumit") || userId.equals("sourabha") || !userId.equals("")) {
                    Intent intent = new Intent(MainActivity.this, Mona.class);
                    startActivity(intent);
                }
            }
        });
    }
}
