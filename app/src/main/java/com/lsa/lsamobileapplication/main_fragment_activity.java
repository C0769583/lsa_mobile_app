package com.lsa.lsamobileapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static com.lsa.lsamobileapplication.R.*;

public class main_fragment_activity extends AppCompatActivity {
    Toolbar toolbar;
    View v,project;
    TextView select_project,b;
    Button submit;
    ToggleButton  rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9,rb10,rb11,rb12;
    private DatabaseReference mDatabase;
    String userId;
    ArrayList <String> project_name= new ArrayList<String>();
    String emotion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_fragment);
        toolbar = (Toolbar) findViewById(id.toolbar);
        toolbar.setNavigationIcon(drawable.ic_back2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent back = new Intent(main_fragment_activity.this, Record.class);
                startActivity(back);
                finish();
            }
        });
        project = (View) findViewById(id.project_icon);
        project.setBackgroundResource(drawable.ic_color_project);

        select_project = (TextView) findViewById(id.text1);
        select_project.setVisibility(View.VISIBLE);
        select_project.setTextColor(Color.RED);


        rb1 = (ToggleButton) findViewById(id.rb1);
        rb2 = (ToggleButton) findViewById(id.rb2);
        rb3 = (ToggleButton) findViewById(id.rb3);
        rb4 = (ToggleButton) findViewById(id.rb4);
        rb5 = (ToggleButton) findViewById(id.rb5);
        rb6 = (ToggleButton) findViewById(id.rb6);
        rb7 = (ToggleButton) findViewById(id.rb7);
        rb8 = (ToggleButton) findViewById(id.rb8);
        rb9 = (ToggleButton) findViewById(id.rb9);
        rb10 = (ToggleButton) findViewById(id.rb10);
        rb11 = (ToggleButton) findViewById(id.rb11);
        rb12 = (ToggleButton) findViewById(id.rb12);

        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("BTru")) {
                        project_name.add("BTru");
                    }
                }
                if(!isChecked){
                    if(project_name.contains("BTru")) {
                        project_name.remove("BTru");
                    }

                }

            }
        });

        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("SpreadTrees,Not AIDS")) {
                        project_name.add("SpreadTrees,Not AIDS");
                    }

                }
                if(!isChecked) {
                    if (project_name.contains("SpreadTrees,Not AIDS")) {
                        project_name.remove("SpreadTrees,Not AIDS");
                    }
                }


                }
        });
        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("No Time To Wait")) {
                        project_name.add("No Time To Wait");
                    }

                }
                if(!isChecked) {
                    if (project_name.contains("No Time To Wait")) {
                        project_name.remove("No Time To Wait");
                    }
                }

            }
        });
        rb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("Website Design")) {
                        project_name.add("Website Design");
                    }

                }
                if(!isChecked) {
                    if (project_name.contains("Website Design")) {
                        project_name.remove("Website Design");
                    }
                }

            }
        });
        rb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("Youth-HIV")) {
                        project_name.add("Youth-HIV");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("Youth-HIV")) {
                        project_name.remove("Youth-HIV");
                    }
                }

            }
        });


        rb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb6.setTextColor(Color.BLACK);
                    if(!project_name.contains("Live Life Loving")) {
                        project_name.add("Live Life Loving");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("Live Life Loving")) {
                        project_name.remove("Live Life Loving");
                    }
                }

            }
        });

        rb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb7.setTextColor(Color.BLACK);
                    if(!project_name.contains("24 Hour Wake")) {
                        project_name.add("24 Hour Wake");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("24 Hour Wake")) {
                        project_name.remove("24 Hour Wake");
                    }
                }

            }
        });
        rb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb8.setTextColor(Color.BLACK);
                    if(!project_name.contains("Hey COVID 19")) {
                        project_name.add("Hey COVID 19");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("Hey COVID 19")) {
                        project_name.remove("Hey COVID 19");
                    }
                }
            }
        });
        rb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("International Aids Conference")) {
                        project_name.add("International Aids Conference");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("International Aids Conference")) {
                        project_name.remove("International Aids Conference");
                    }
                }

            }
        });
        rb10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("Other")) {
                        project_name.add("Other");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("Other")) {
                        project_name.remove("Other");
                    }
                }
            }
        });
        rb11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("Peer Education")) {
                        project_name.add("Peer Education");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("Peer Education")) {
                        project_name.remove("Peer Education");
                    }
                }
            }
        });
        rb12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!project_name.contains("Social Media Content Development")) {
                        project_name.add("Social Media Content Development");
                    }
                }
                if(!isChecked) {
                    if (project_name.contains("Social Media Content Development")) {
                        project_name.remove("Social Media Content Development");
                    }
                }
            }
        });

        submit = (Button) findViewById(id.Submit_project);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion=getIntent().getExtras().getString("emoticon");
                if(project_name.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(main_fragment_activity.this);
                    builder.setTitle("Attention!");
                    builder.setMessage("Please select atleast one project!");

                    // add a button
                    builder.setPositiveButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                Intent back = new Intent(main_fragment_activity.this, select_days.class);
                back.putExtra("names",project_name);
                back.putExtra("emoticon",emotion);
                startActivity(back);
                finish();
            }
        });


     /*   b = (TextView) findViewById(id.next1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion=getIntent().getExtras().getString("emoticon");
                Intent back = new Intent(main_fragment_activity.this, select_days.class);
                back.putExtra("names",project_name);
                back.putExtra("emoticon",emotion);
                startActivity(back);
                finish();
            }
        });*/

    }
}