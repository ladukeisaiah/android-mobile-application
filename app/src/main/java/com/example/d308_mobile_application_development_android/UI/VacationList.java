package com.example.d308_mobile_application_development_android.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.d308_mobile_application_development_android.Database.Repository;
import com.example.d308_mobile_application_development_android.R;
import com.example.d308_mobile_application_development_android.entities.Excursion;
import com.example.d308_mobile_application_development_android.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        repository= new Repository(getApplication());
        List<Vacation> allVacations=repository.getAllVacations();
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
//        System.out.println(getIntent().getStringExtra("test"));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter= new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        repository=new Repository(getApplication());
        if(item.getItemId()==R.id.mysampleme) {
            //Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(0, "bicycle vacation", 100.0, "Maryland Stay","02/20/24", "02/28/24");
            repository.insert(vacation);
            vacation = new Vacation(0,"Hawaii", 600.0,"Larabar Hotel","02/20/24", "02/28/24");
            repository.insert(vacation);
            Excursion excursion = new Excursion(0, "Snorkeling", 200.0, 1, "02/21/24");
            repository.insert(excursion);
            excursion = new Excursion(0, "Sail Boatin'", 400.0, 1, "02/21/24");
            repository.insert(excursion);
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}