package com.lsa.lsamobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class select_days extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_next, select_days;
    View days;
    AppCompatButton submit_info;
    String emotion="",startDate,endDate;
    int hrs;
    Date objDate;
    private DatabaseReference mDatabase;
    String userId,dateFromDatabase;
    ArrayList<String> all_names= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_days);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent back = new Intent(select_days.this, main_fragment_activity.class);
                startActivity(back);
                finish();
            }
        });


        NumberPicker pick = findViewById(R.id.picker);

        String[] values= {"0hr",  "2 hr", "4 hr", "6 hr", "8 hr", "10 hr", "12 hr", "14 hr",
                "16 hr",  "18 hr", "20 hr", "22 hr", "24 hr", "26 hr", "28 hr", "30 hr",
                "32 hr", "34 hr", "36 hr", "38 hr", "40 hr"};
        pick.setMinValue(0);
        pick.setMaxValue(values.length-1);
        pick.setValue(2);
        pick.setDisplayedValues(values);


        pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal==0){
                    hrs=0;
                }
                if(newVal==1){
                    hrs=2;
                }
                if(newVal==2){
                    hrs=4;
                }
                if(newVal==3){
                    hrs=6;
                }
                if(newVal==4){
                    hrs=8;
                }
                if(newVal==5){
                    hrs=10;
                }
                if(newVal==6){
                    hrs=12;
                }
                if(newVal==7){
                    hrs=14;
                }
                if(newVal==8){
                    hrs=16;
                }
                if(newVal==9){
                    hrs=18;
                }
                if(newVal==10){
                    hrs=20;
                }
                if(newVal==11){
                    hrs=22;
                }
                if(newVal==12){
                    hrs=24;
                }
                if(newVal==13){
                    hrs=26;
                }
                if(newVal==14){
                    hrs=28;
                }
                if(newVal==15){
                    hrs=30;
                }
                if(newVal==16){
                    hrs=32;
                }
                if(newVal==17){
                    hrs=34;
                }
                if(newVal==18){
                    hrs=36;
                }
                if(newVal==19){
                    hrs=38;
                }
                if(newVal==20){
                    hrs=40;
                }


            }
        });

        select_days = (TextView) findViewById(R.id.text2);
        select_days.setVisibility(View.VISIBLE);
        select_days.setTextColor(Color.RED);
        days = (View) findViewById(R.id.calendar_icon);
        days.setBackgroundResource(R.drawable.ic_color_calendar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userId=user.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Data");
        Query query = mDatabase.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childish: dataSnapshot.getChildren()) {
                    dateFromDatabase=childish.child("date").getValue().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                    try {
                        objDate = dateFormat.parse(dateFromDatabase);
                        Log.d("User val", String.valueOf(objDate));
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: Handle errors.
            }
        });

        submit_info=(AppCompatButton) findViewById(R.id.Submit_hours);
        submit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all_names = (ArrayList<String>) getIntent().getSerializableExtra("names");
                emotion = getIntent().getExtras().getString("emoticon");
                Date date1 = new Date();
                DateFormat checkformate = new SimpleDateFormat("yyyy/MM/dd");
                String currentdate = checkformate.format(date1).toString();
                User values = new User();
                values.setEmoticon(emotion);
                values.setArray(all_names);
                values.setHours(hrs);
                values.setDate(currentdate);
                if (isDateInCurrentWeek(objDate)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(select_days.this);
                    builder.setTitle("Note");
                    builder.setMessage("You already logged on your hours for this week. Do you want to override the information");

                    // add the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childish: dataSnapshot.getChildren()) {
                                        childish.getRef().setValue(values);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // TODO: Handle errors.
                                }
                            });
                            Toast.makeText(select_days.this, "Response Submitted", Toast.LENGTH_SHORT).show();
                            Intent back = new Intent(select_days.this, thanks.class);
                            startActivity(back);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent back = new Intent(select_days.this, thanks.class);
                            startActivity(back);
                            finish();

                        }
                    });

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    mDatabase.push().setValue(values).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(select_days.this, "Response Submitted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(select_days.this, "Ooops", Toast.LENGTH_SHORT).show();
                            }
                            Intent back = new Intent(select_days.this, thanks.class);
                            startActivity(back);
                            finish();
                        }

                    });
                }

            }});

    }

   /* public String currentWeek(){
        Calendar calendar = Calendar.getInstance();

        Date date1 = calendar.getTime();
        SimpleDateFormat checkformate = new SimpleDateFormat("MMM dd",Locale.getDefault());
        String currentCheckdate = checkformate.format(date1);

        int weekn = calendar.get(Calendar.WEEK_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.clear();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.WEEK_OF_MONTH, weekn);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd",Locale.getDefault());

        Date datef = calendar.getTime();
        Long time = calendar.getTimeInMillis() + 518400000L;
        Date dateL = new Date(time);
        String firtdate = simpleDateFormat.format(datef);
        String lastdate = simpleDateFormat.format(dateL);
        String firtdateCheck = checkformate.format(datef);
        String lastdateCheck = checkformate.format(dateL);


        if (!firtdateCheck.toString().equalsIgnoreCase(currentCheckdate)) {
            firtdate = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + "1";
        }

        if (!lastdateCheck.toString().equalsIgnoreCase(currentCheckdate)) {

            int ma = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            lastdate = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + String.valueOf(ma);
        }
        endDate = lastdate.toString();
        startDate = firtdate.toString();

        return startDate+"-"+endDate;
    }*/


  public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

}