package com.example.d308_mobile_application_development_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private double price;
    private String startVacationDate;

    private String endVacationDate;
    private String hotel;

    public Vacation(int vacationID, String vacationName, double price, String hotel, String startVacationDate, String endVacationDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.price = price;
        this.hotel = hotel;
        this.startVacationDate= startVacationDate;
        this.endVacationDate = endVacationDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public void setStartVacationDate(String startVacationDate) {
        this.startVacationDate = startVacationDate;
    }
    public String getStartVacationDate() {
        return startVacationDate;
    }

    public void setEndVacationDate(String endVacationDate) {
        this.endVacationDate = endVacationDate;
    }

    public String getEndVacationDate() {
        return endVacationDate;
    }
}
