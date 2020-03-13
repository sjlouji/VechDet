package com.example.vechdet;

public class Users {
    public String number_plate;
    public String entry_date;
    public String exit_date;

    public Users(){

    }

    public Users(String number_plate, String entry_date, String exit_date) {
        this.number_plate = number_plate;
        this.entry_date = entry_date;
        this.exit_date = exit_date;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getExit_date() {
        return exit_date;
    }

    public void setExit_date(String exit_date) {
        this.exit_date = exit_date;
    }
}