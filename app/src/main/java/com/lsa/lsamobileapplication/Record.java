 package com.lsa.lsamobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

 public class Record extends AppCompatActivity implements View.OnClickListener {
    TextView rec;
    private DatabaseReference mDatabase;
    String userId;
    ImageButton floatingButton, first,second,third,forth,fifth,sixth;

    String emotion="";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        rec= (TextView) findViewById(R.id.name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        mDatabase=FirebaseDatabase.getInstance().getReference("Users").child(userId);

        rec=(TextView)findViewById(R.id.name);

       mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               String firstname=snapshot.child("Firstname").getValue(String.class);
               String lastname=snapshot.child("Lastname").getValue(String.class);
               rec.setText(firstname+" "+lastname);
           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });

       //Image code
        first= (ImageButton) findViewById(R.id.first);
        first.setOnClickListener(this);
        second= (ImageButton) findViewById(R.id.second);
        second.setOnClickListener(this);
        third= (ImageButton) findViewById(R.id.third);
        third.setOnClickListener(this);
        forth= (ImageButton) findViewById(R.id.forth);
        forth.setOnClickListener(this);
        fifth= (ImageButton) findViewById(R.id.fifth);
        fifth.setOnClickListener(this);
        sixth= (ImageButton) findViewById(R.id.sixth);
        sixth.setOnClickListener(this);


        floatingButton= (ImageButton) findViewById(R.id.floating_action_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emotion.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Record.this);
                    builder.setTitle("Attention!");
                    builder.setMessage("Please enter How are you feeling today?");

                    // add a button
                    builder.setPositiveButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                Intent back = new Intent(Record.this, main_fragment_activity.class);
                back.putExtra("emoticon",emotion);
                startActivity(back);
                finish();
            }
        });

        }

    @Override
    public void onClick(View v) {
        switch ((v.getId())){
            case R.id.first:{
                emotion="Happy";
                second.setBackground(null);
                third.setBackground(null);
                forth.setBackground(null);
                fifth.setBackground(null);
                sixth.setBackground(null);
                first.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                break;

            }
            case R.id.second:{
                emotion="Sad";
                first.setBackground(null);
                third.setBackground(null);
                forth.setBackground(null);
                fifth.setBackground(null);
                sixth.setBackground(null);
                second.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                break;

            }
            case R.id.third:{
                first.setBackground(null);
                second.setBackground(null);
                forth.setBackground(null);
                fifth.setBackground(null);
                sixth.setBackground(null);
                emotion="Surprised";
                third.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                break;

            }
            case R.id.forth:{
                first.setBackground(null);
                third.setBackground(null);
                second.setBackground(null);
                fifth.setBackground(null);
                sixth.setBackground(null);
                forth.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                emotion="Angry";
                break;
            }
            case R.id.fifth:{
                first.setBackground(null);
                third.setBackground(null);
                forth.setBackground(null);
                second.setBackground(null);
                sixth.setBackground(null);
                fifth.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                emotion="Neutral";
                break;
            }
            case R.id.sixth:{
                first.setBackground(null);
                third.setBackground(null);
                forth.setBackground(null);
                fifth.setBackground(null);
                second.setBackground(null);
                sixth.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                emotion="Very Happy";
                break;
            }
        }

    }
}
