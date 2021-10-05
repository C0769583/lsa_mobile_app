package com.lsa.lsamobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forget_password extends AppCompatActivity {
    private EditText reset_emailId;
    private Button reset_button;
    private ProgressBar progress;
    FirebaseAuth fireAuth;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        progress=findViewById(R.id.progressBar_reset);
        reset_emailId=findViewById(R.id.reset_email);
        reset_button=findViewById(R.id.reset);
        fireAuth=FirebaseAuth.getInstance();

        toolbar=(Toolbar) findViewById(R.id.toolbar1);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent back=new Intent(forget_password.this,Login.class);
                startActivity(back);
                finish();

            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailId=reset_emailId.getText().toString().trim();
                boolean check_email=isValidEmail(emailId);
                if(emailId.isEmpty()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                    builder.setTitle("Error!");
                    builder.setMessage("Please fill email.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    reset_emailId.requestFocus();
                    return;
                }

                if(check_email==false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                    builder.setTitle("Error!");
                    builder.setMessage("please enter valid organization email address.eg: user@letsstopaids.org");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

                else {


                    fireAuth.sendPasswordResetEmail(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                                builder.setTitle("Note");
                                builder.setMessage("We have sent you instructions to reset your password!");

                                // add a button
                                builder.setPositiveButton("OK", null);

                                // create and show the alert dialog
                                AlertDialog dialog = builder.create();
                                dialog.show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                                builder.setTitle("Note");
                                builder.setMessage("Failed to send reset email link! Please make sure entered email is correct.");

                                // add a button
                                builder.setPositiveButton("OK", null);

                                // create and show the alert dialog
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    });
                }

            }
        });
    }

    private boolean isValidEmail(String email_address) {
        String patern="^[a-zA-Z0-9_.]+@(letsstopaids)+\\.org$";
        Pattern p=Pattern.compile(patern);
        Matcher m=p.matcher(email_address);
        if(m.matches()){
            return true;
        }
        return false;
    }
}