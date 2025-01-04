
package blotterrecordmanagement;

import java.util.Calendar;

/**
 *
 * @author Naps
 */
public class CurrentCalendar {
    Calendar rightNow = Calendar.getInstance();
    Calendar dateadjust = Calendar.getInstance();
    
    public void setDate(){
        //rightNow.set
    }
    
    
    
    public String getFullDate(){
        return getStringMonth() + " " + getDate() + ", " + getYear();
    }
    
    public int getDate(){
        return rightNow.get(Calendar.DATE);
    }
    
    public String getStringMonth(){
        
        String strmonth = "";
        Calendar dateMonth = rightNow;
        
        if(dateMonth.get(Calendar.MONTH)==0){
            strmonth = "January";
        }else if(dateMonth.get(Calendar.MONTH)==1){
            strmonth = "February";
        }else if(dateMonth.get(Calendar.MONTH)==2){
            strmonth = "March";
        }else if(dateMonth.get(Calendar.MONTH)==3){
            strmonth = "April";
        }else if(dateMonth.get(Calendar.MONTH)==4){
            strmonth = "May";
        }else if(dateMonth.get(Calendar.MONTH)==5){
            strmonth = "June";
        }else if(dateMonth.get(Calendar.MONTH)==6){
            strmonth = "July";
        }else if(dateMonth.get(Calendar.MONTH)==7){
            strmonth = "August";
        }else if(dateMonth.get(Calendar.MONTH)==8){
            strmonth = "September";
        }else if(dateMonth.get(Calendar.MONTH)==9){
            strmonth = "October";
        }else if(dateMonth.get(Calendar.MONTH)==10){
            strmonth = "November";
        }else if(dateMonth.get(Calendar.MONTH)==11){
            strmonth = "December";
        }
        
        return strmonth;
    }
    
    public String getStringMonth(int m){
        
        String strmonth = "";
        int month = m;
        
        if(month==0){
            strmonth = "January";
        }else if(month==1){
            strmonth = "February";
        }else if(month==2){
            strmonth = "March";
        }else if(month==3){
            strmonth = "April";
        }else if(month==4){
            strmonth = "May";
        }else if(month==5){
            strmonth = "June";
        }else if(month==6){
            strmonth = "July";
        }else if(month==7){
            strmonth = "August";
        }else if(month==8){
            strmonth = "September";
        }else if(month==9){
            strmonth = "October";
        }else if(month==10){
            strmonth = "November";
        }else if(month==11){
            strmonth = "December";
        }
        
        return strmonth;
    }
    
    public int getIntMonth(String strmonth){
        int intmonth = 0;
        
        if(strmonth.equalsIgnoreCase("January") || strmonth.equalsIgnoreCase("Jan")){
            intmonth = 0;
        }else if(strmonth.equalsIgnoreCase("February") || strmonth.equalsIgnoreCase("Feb")){
            intmonth = 1;
        }else if(strmonth.equalsIgnoreCase("March") || strmonth.equalsIgnoreCase("Mar")){
            intmonth = 2;
        }else if(strmonth.equalsIgnoreCase("April") || strmonth.equalsIgnoreCase("Apr")){
            intmonth = 3;
        }else if(strmonth.equalsIgnoreCase("May")){
            intmonth = 4;
        }else if(strmonth.equalsIgnoreCase("June") || strmonth.equalsIgnoreCase("Jun")){
            intmonth = 5;
        }else if(strmonth.equalsIgnoreCase("July") || strmonth.equalsIgnoreCase("Jul")){
            intmonth = 6;
        }else if(strmonth.equalsIgnoreCase("August") || strmonth.equalsIgnoreCase("Aug")){
            intmonth = 7;
        }else if(strmonth.equalsIgnoreCase("September") || strmonth.equalsIgnoreCase("Sep")){
            intmonth = 8;
        }else if(strmonth.equalsIgnoreCase("October") || strmonth.equalsIgnoreCase("Oct")){
            intmonth = 9;
        }else if(strmonth.equalsIgnoreCase("November") || strmonth.equalsIgnoreCase("Nov")){
            intmonth = 10;
        }else if(strmonth.equalsIgnoreCase("December") || strmonth.equalsIgnoreCase("Dec")){
            intmonth = 11;
        }else{
            intmonth = 12;
        }
        
        return intmonth;
    }
    
    public String getDayOfWeek(){
        String weekofday = "";
        
        if(rightNow.get(Calendar.DAY_OF_WEEK)==1){
            weekofday = "Sunday";
        }else if(rightNow.get(Calendar.DAY_OF_WEEK)==2){
            weekofday = "Monday";
        }else if(rightNow.get(Calendar.DAY_OF_WEEK)==3){
            weekofday = "Tuesday";
        }else if(rightNow.get(Calendar.DAY_OF_WEEK)==4){
            weekofday = "Wednesday";
        }else if(rightNow.get(Calendar.DAY_OF_WEEK)==5){
            weekofday = "Thursday";
        }else if(rightNow.get(Calendar.DAY_OF_WEEK)==6){
            weekofday = "Friday";
        }else if(rightNow.get(Calendar.DAY_OF_WEEK)==7){
            weekofday = "Saturday";
        }
        
        return weekofday;
    }
    
    public String getDayOfWeek(int d){
        int daycount = d;
        String weekofday = "";
        
        if(daycount==1){
            weekofday = "Sunday";
        }else if(daycount==2){
            weekofday = "Monday";
        }else if(daycount==3){
            weekofday = "Tuesday";
        }else if(daycount==4){
            weekofday = "Wednesday";
        }else if(daycount==5){
            weekofday = "Thursday";
        }else if(daycount==6){
            weekofday = "Friday";
        }else if(daycount==7){
            weekofday = "Saturday";
        }
        
        return weekofday;
    }
    
    public int getYear(){
        return rightNow.get(Calendar.YEAR);
    }
    
    public int getDaysOfMonth(){
        return rightNow.getActualMaximum(Calendar.DATE);
    }
    
    public Calendar rawCalendar(){
        return rightNow;
    }
    
    public String getCurrentTime(){
        String meridian;
        int hour = rightNow.get(Calendar.HOUR);
        String minute = String.format("%02d", rightNow.get(Calendar.MINUTE));
        String seconds = String.format("%02d", rightNow.get(Calendar.SECOND));
        
        if(hour==0){
            hour = 12;
        }
        
        if(rightNow.get(Calendar.AM_PM)==0){
            meridian = "am";
        }else{
            meridian = "pm";
        }
        
        //String.format("%02.0f", myNumber)
        
        return hour + ":" + minute + ":" + seconds/* + " " */+ meridian;
    }
    
    public String getSimpleTime(){
        String meridian;
        int hour = rightNow.get(Calendar.HOUR);
        String minute = String.format("%02d", rightNow.get(Calendar.MINUTE));
        String seconds = String.format("%02d", rightNow.get(Calendar.SECOND));
        
        if(hour==0){
            hour = 12;
        }
        
        if(rightNow.get(Calendar.AM_PM)==0){
            meridian = "am";
        }else{
            meridian = "pm";
        }
        
        //String.format("%02.0f", myNumber)
        
        return hour + ":" + minute + meridian;
    }
}
