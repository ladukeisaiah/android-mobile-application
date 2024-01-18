package com.example.d308_mobile_application_development_android.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.d308_mobile_application_development_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacationList extends AppCompatActivity {

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
        System.out.println(getIntent().getStringExtra("test"));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mysample) {
            Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}