package com.example.d308_mobile_application_development_android.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308_mobile_application_development_android.DAO.ExcursionDAO;
import com.example.d308_mobile_application_development_android.DAO.VacationDAO;
import com.example.d308_mobile_application_development_android.entities.Excursion;
import com.example.d308_mobile_application_development_android.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version= 6,exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
