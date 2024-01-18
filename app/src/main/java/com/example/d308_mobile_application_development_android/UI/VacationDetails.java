package com.example.d308_mobile_application_development_android.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.d308_mobile_application_development_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                startActivity(intent);
            }
        });
        System.out.println(getIntent().getStringExtra("test"));
    }
    }
