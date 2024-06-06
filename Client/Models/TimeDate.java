package Client.Models;

public class TimeDate {
    
    private int day;
    private int month;
    private int year;

    public TimeDate(int day, String monthString, int year) {
        this.day = day;
        this.month = month2int(monthString); 
        this.year = year;
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
