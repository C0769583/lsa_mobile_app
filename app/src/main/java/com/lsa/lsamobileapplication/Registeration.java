package com.lsa.lsamobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registeration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   private Toolbar toolbar;
   private EditText first_name,last_name,email,password;
   private Button register_button;
   private ProgressBar progress_Bar;
   private FirebaseAuth mAuth;
   private DatabaseReference databaseReference;
   private CheckBox check;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);


        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent back=new Intent(Registeration.this,Login.class);
                startActivity(back);
                finish();
            }
        });



        mAuth = FirebaseAuth.getInstance();  // Firebase Work

        //Fetching Ids
        first_name=(EditText) findViewById(R.id.firstname_text);
        last_name=(EditText) findViewById(R.id.lastname_text);
        email=(EditText)findViewById(R.id.email_edittext);
        password=(EditText) findViewById(R.id.register_password_text);
        register_button=(Button) findViewById(R.id.sign_up);
        progress_Bar=(ProgressBar) findViewById(R.id.progressBar);
        password.setOnTouchListener(new ShowHidePassword());
        check=(CheckBox) findViewById(R.id.terms_checkbox);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName=first_name.getText().toString().trim();
                final String lastName=last_name.getText().toString().trim();
                final String email_address=email.getText().toString().trim();
                final String passwrd=password.getText().toString().trim();

                boolean check_email=isValidEmail(email_address);
                boolean check_password=isValidPassword(passwrd);
                AlertDialog.Builder builder = new AlertDialog.Builder(Registeration.this);
                builder.setTitle("Error!");

                if(firstName.isEmpty()){

                    builder.setMessage("Please fill first name.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    first_name.requestFocus();
                    return;
                }
               if(lastName.isEmpty()){
                    builder.setMessage("Please fill last name");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    last_name.requestFocus();
                    return;
                }
                if(email_address.isEmpty()){
                    builder.setMessage("Please fill email.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    email.requestFocus();
                    return;
                }

                if(passwrd.isEmpty()){
                    builder.setMessage("Please fill password.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    password.requestFocus();
                    return;
                }

                if(check_email==false){
                    builder.setMessage("please enter valid organization email address.eg: user@letsstopaids.org");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    email.requestFocus();
                    return;
                }

                if(check_password==false){

                    builder.setMessage("Password must be 8 characters long,must have a capital letter(A-Z),a small letter(a-z),a number(0-9) and a special character.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    password.requestFocus();
                    return;
                }

                if(!check.isChecked()){
                    builder.setMessage("Please accept terms of use.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }


                register(firstName,lastName,email_address,passwrd);


            }
        });



    }

    private boolean isValidPassword(String passwrd) {
        String patern="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern p=Pattern.compile(patern);
        Matcher m=p.matcher(passwrd);
        if(m.matches()){
            return true;
        }
        return false;
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

    private boolean isValidEmail(String email_address) {
        String patern="^[a-zA-Z0-9_.]+@(letsstopaids)+\\.org$";
        Pattern p=Pattern.compile(patern);
        Matcher m=p.matcher(email_address);
        if(m.matches()){
            return true;
        }
        return false;
    }

    private void register(String firstName, String lastName, String email_address, String passwrd) {
        progress_Bar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email_address, passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String userId = currentUser.getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("userId", userId);
                        hashMap.put("Firstname", firstName);
                        hashMap.put("Lastname", lastName);
                        hashMap.put("Email", email_address);
                        hashMap.put("Password", passwrd);

                        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                progress_Bar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    first_name.setText("");
                                    last_name.setText("");
                                    email.setText("");
                                    password.setText("");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeration.this);
                                    builder.setTitle("Note");
                                    builder.setMessage("Email sent successfully, Please verify to login to your account.");
                                    // add a button
                                    builder.setPositiveButton("OK", null);
                                    // create and show the alert dialog
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                    sendVerificationEmail();


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeration.this);
                                    builder.setTitle("Oops!!");
                                    builder.setMessage(Objects.requireNonNull(task.getException()).getMessage());

                                    // add a button
                                    builder.setPositiveButton("OK", null);

                                    // create and show the alert dialog
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }
                            }
                        });

                    } else {
                        progress_Bar.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registeration.this);
                        builder.setTitle("Oops!!");
                        builder.setMessage( task.getException().getMessage());

                        // add a button
                        builder.setPositiveButton("OK", null);

                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();


                    }

                }
            });

    }



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice=parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),choice,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


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
}