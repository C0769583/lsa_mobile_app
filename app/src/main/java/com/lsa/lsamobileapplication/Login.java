package com.lsa.lsamobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private ProgressBar progress_login;
    private EditText login_edit,login_password;
    private TextView pwd_link,signUp_link;
    private Button login;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress_login = findViewById(R.id.progressBar_login);
        login_edit = findViewById(R.id.login_email_edittext);  //Email edittext
        login_password = findViewById(R.id.login_password_edittext);  //Password edittext
        pwd_link = findViewById(R.id.forget_pwd);
        signUp_link = findViewById(R.id.to_registration);
        login = findViewById(R.id.sign_in);
        fauth = FirebaseAuth.getInstance();


        login_password.setOnTouchListener(new ShowHidePassword());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login_email = login_edit.getText().toString().trim();
                final String login_passwrd = login_password.getText().toString().trim();

                boolean check_email = isValidEmail(login_email);
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Error!");

                if (login_email.isEmpty()) {
                    builder.setMessage("Please fill email.");

                    // add a button
                    builder.setPositiveButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    login_edit.requestFocus();
                    return;
                }
                if (login_passwrd.isEmpty()) {
                    builder.setMessage("Please fill password.");

                    // add a button
                    builder.setPositiveButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    login_password.requestFocus();
                    return;
                }

                if (check_email == false) {

                    builder.setMessage("please enter valid organization email address.eg: user@letsstopaids.org");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                else{
                    progress_login.setVisibility(View.VISIBLE);
                    fauth.signInWithEmailAndPassword(login_email, login_passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            progress_login.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                if (fauth.getCurrentUser().isEmailVerified()) {
                                    login_edit.setText("");
                                    login_password.setText("");
                                    Toast.makeText(Login.this, "Logged in Succesfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), Record.class));
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                    builder.setTitle("Note");
                                    builder.setMessage("Email Verification is pending.Would you like us to resend verifiaction email link again @" + login_email + "?");

                                    // add the buttons
                                    builder.setPositiveButton("Resend", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            sendVerificationEmail();

                                        }
                                    });
                                    builder.setNegativeButton("Cancel", null);

                                    // create and show the alert dialog
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }


                            } else {
                                progress_login.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setTitle("Oops!!");
                                builder.setMessage(task.getException().getMessage());

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



        // Forget Password Link

        pwd_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,forget_password.class);
                finish();
                startActivity(intent);

            }
        });

        // Link to sign Up page
        signUp_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registeration.class);
                finish();
                startActivity(intent);


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

    public class ShowHidePassword implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_RIGHT = 2;
             EditText editView = (EditText) v;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editView.getRight() - editView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    editView.requestFocus();
                    if(editView.getTransformationMethod() instanceof PasswordTransformationMethod){
                        editView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editView.setSelection(editView.getText().length());
                        editView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_view_password, 0);
                    }else{
                        editView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editView.setSelection(editView.getText().length());
                        editView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_hide_password, 0);
                    }
                    return true;
                }
            }

            return false;
        }


    }
    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user
                            FirebaseAuth.getInstance().signOut();


                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }
}