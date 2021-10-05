package com.lsa.lsamobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class thanks extends AppCompatActivity {
    RelativeLayout thanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        thanks=(RelativeLayout) findViewById(R.id.layout_thanks);
        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(thanks.this, Record.class);
                startActivity(back);
                finish();
            }
        });
    }
}