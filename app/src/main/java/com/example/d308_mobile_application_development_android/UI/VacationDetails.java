package com.example.d308_mobile_application_development_android.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d308_mobile_application_development_android.Database.Repository;
import com.example.d308_mobile_application_development_android.R;
import com.example.d308_mobile_application_development_android.entities.Excursion;
import com.example.d308_mobile_application_development_android.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    static int notificationID;
    String hotel;
    double price;
    int vacationID;
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editPrice;
    EditText editHotel;
    TextView editStartVacaDate;
    TextView editEndVacaDate;
    Vacation currentVacation;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startVacaDate;
    DatePickerDialog.OnDateSetListener endVacaDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);

        editName = findViewById(R.id.titletext);
        editPrice = findViewById(R.id.pricetext);
        editHotel = findViewById(R.id.hoteltext);
        editStartVacaDate = findViewById(R.id.startvacationdate);
        editEndVacaDate = findViewById(R.id.endvacationdate);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        price = getIntent().getDoubleExtra("price", 0.0);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editName.setText(name);
        editHotel.setText(hotel);
        editPrice.setText(Double.toString(price));
        vacationID = getIntent().getIntExtra("id", -1);
        editStartVacaDate.setText(startVacationDate);
        editEndVacaDate.setText(endVacationDate);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Log.d("DebugTag", "name: " + name + " vacationID: " + vacationID + " Hotel: " + hotel + ", startVacationDate: " + startVacationDate + ", endVacationDate: " + endVacationDate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("startVacationDate", startVacationDate);
                intent.putExtra( "endVacationDate", endVacationDate);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, startVacationDate, endVacationDate);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        startVacaDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };

        endVacaDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelEnd();
            }

        };

        editStartVacaDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editStartVacaDate.getText().toString();
                if(info.equals(""))info="02/10/24";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startVacaDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                }
        });

        editEndVacaDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editEndVacaDate.getText().toString();
                if(info.equals(""))info="02/10/24";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetails.this, endVacaDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());

                datePickerDialog.show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartVacaDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndVacaDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationId) {
        Intent intent = new Intent(VacationDetails.this, MyVacationReceiver.class);
        intent.putExtra("key", message);
        intent.putExtra("notification_id", notificationId);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, notificationId, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
        if(item.getItemId()== R.id.vacationsave) {
            Vacation vacation;
            if (vacationID==-1) {
                if (repository.getAllVacations().size() == 0) vacationID = 1;
                else
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editStartVacaDate.getText().toString(), editEndVacaDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            }
            else{
                vacation = new Vacation(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editStartVacaDate.getText().toString(), editEndVacaDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        if(item.getItemId()== R.id.vacationdelete) {
            for (Vacation vaca : repository.getAllVacations()) {
                if (vaca.getVacationID() == vacationID) currentVacation = vaca;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a Vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(item.getItemId()== R.id.addSampleExcursions){
            if (vacationID == -1)
                Toast.makeText(VacationDetails.this, "Please save Vacation before adding excursions", Toast.LENGTH_LONG).show();

            else {
                int excursionID;

                if (repository.getAllExcursions().size() == 0) excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                Excursion excursion = new Excursion(excursionID, "Snorkeling", 250, vacationID, "02/26/24");
                repository.insert(excursion);
                excursion = new Excursion(++excursionID, "Hiking", 15, vacationID, "02/26/24");
                repository.insert(excursion);
                excursion = new Excursion(++excursionID, "Bus Tour", 55, vacationID, "02/26/24");
                repository.insert(excursion);
                excursion = new Excursion(++excursionID, "Cooking Lesson", 500, vacationID, "02/26/24");
                repository.insert(excursion);
                RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
                final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, startVacationDate, endVacationDate);
                recyclerView.setAdapter(excursionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Excursion> filteredExcursions = new ArrayList<>();
                for (Excursion p : repository.getAllExcursions()) {
                    if (p.getVacationID() == vacationID) filteredExcursions.add(p);
                }
                excursionAdapter.setExcursions(filteredExcursions);
                return true;
            }
        }
        if (item.getItemId()== R.id.vacationshare) {
            Intent sendIntent = new Intent();
            List<Excursion> filteredExcursions = new ArrayList<>();
            for (Excursion p : repository.getAllExcursions()) {
                if (p.getVacationID() == vacationID) filteredExcursions.add(p);
            }
            StringBuilder excursionsDetails = new StringBuilder();
            for (Excursion p : filteredExcursions) {
                excursionsDetails.append("Excursion Name: ")
                        .append(p.getExcursionName())
                        .append(", Price: $")
                        .append(p.getPrice())
                        .append("\n");  // Add more details as needed
            }
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "These are the Vacation details: vacation ID: " + vacationID + ", The name of our vacation: " +
                    editName.getText().toString() + ", price: $" +
                    Double.parseDouble(editPrice.getText().toString()) + ", the hotel name: " +
                    editHotel.getText().toString() + ", this is our start date: " +
                    editStartVacaDate.getText().toString() + ", this is our end date: " +
                    editEndVacaDate.getText().toString() + ", associated excursions: " +  excursionsDetails
                    + " Let us know what you think!");
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString() + "EXTRA_TITLE");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if(item.getItemId()== R.id.vacationnotify) {
            String startdate = editStartVacaDate.getText().toString();
            String enddate = editEndVacaDate.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myStartDate = null;
            Date myEndDate = null;
            try {
                myStartDate = sdf.parse(startdate);
                myEndDate = sdf.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Schedule start date notification
                scheduleAlarm(alarmManager, myStartDate.getTime(), "Vacation Start: " + name,  notificationID++);

                // Schedule end date notification
                scheduleAlarm(alarmManager, myEndDate.getTime(), "Vacation End: " + name, notificationID++);



//                Long triggerStart = myStartDate.getTime();
//                Intent intentStart = new Intent(VacationDetails.this, MyVacationReceiver.class);
//                intentStart.putExtra("key", "Vacation Start: " + getIntent().getStringExtra("name"));
//                PendingIntent senderStart = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intentStart, PendingIntent.FLAG_IMMUTABLE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, senderStart);
//
//                // End Date Alarm
//                Long triggerEnd = myEndDate.getTime();
//                Intent intentEnd = new Intent(VacationDetails.this, MyVacationReceiver.class);
//                intentEnd.putExtra("key2", "Vacation End: " + getIntent().getStringExtra("name"));
//                PendingIntent senderEnd = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert2, intentEnd, PendingIntent.FLAG_IMMUTABLE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, senderEnd);
            } catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
