package Server.Services;

import java.sql.Date;
import java.util.Calendar;

public class TimeDateService {
    
    private int day;
    private int month;
    private int year;

    public TimeDateService(int day, String monthString, int year) {
        this.day = day;
        this.month = month2int(monthString); 
        this.year = year;
    }

    public TimeDateService(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH) + 1; 
        this.year = calendar.get(Calendar.YEAR);
    }

    public TimeDateService(TimeDateService date) {
        this.day = date.day;
        this.month = date.month; 
        this.year = date.year;
    }
    public TimeDateService(String dateString) {
        String[] parts = dateString.split(":");
        this.day = Integer.parseInt(parts[0]);
        this.month = Integer.parseInt(parts[1]);
        this.year = Integer.parseInt(parts[2]);

    }
    private int month2int(String month){

        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                           "August", "September", "October", "November", "December"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(month)) {
                return i + 1;
            }
        }
        return 0;

    }
    @Override
    public String toString(){

        return day + ":" + month + ":" + year;

    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
