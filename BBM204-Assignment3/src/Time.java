import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Time {

    //Compare hours
    public static int compareHours(String firstTime, String secondTime){

        //Convert hours to minutes
        int firstTimeAsMinutes = Integer.parseInt(firstTime.split(":")[0] ) * 60 + Integer.parseInt(firstTime.split(":")[1] );
        int secondTimeAsMinutes = Integer.parseInt(secondTime.split(":")[0] ) * 60 + Integer.parseInt(secondTime.split(":")[1] );

        //if second is greater than first, then result will be positive
        return secondTimeAsMinutes - firstTimeAsMinutes;

    }


    public static long compareDate(String startDate, String endDate){
        long minutes_difference = 0;
        try {

            String format = "dd/MM/yyyy";

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date dateObj1 = sdf.parse(startDate);
            Date dateObj2 = sdf.parse(endDate);

            long time_difference = dateObj2.getTime() - dateObj1.getTime();//if date2 is greater than date 1 time_difference will be greater than 0.
            minutes_difference = (TimeUnit.MILLISECONDS.toDays(time_difference) % 365) * 1440;//difference as a minutes

        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes_difference;

    }

    //The sum of two hours
    public static String theSumOfTwoHours(String firstHours, String secondHours){

        String[] firstArr = firstHours.split(":");
        String[] secondArr = secondHours.split(":");


        int seconds = Integer.parseInt(secondArr[1]) + Integer.parseInt(firstArr[1]);//add seconds
        int hours = Integer.parseInt(secondArr[0]) + Integer.parseInt(firstArr[0]);//add hours

        //if second is greater than 59 add 1 to hours, and second will remain
        if(seconds>59)

        {
            hours++;
            seconds%=60;

        }

        String stringHours = Integer.toString(hours);
        String stringSeconds = Integer.toString(seconds);

        // if hours or seconds are single digit, add 0 to the beginning
        if(hours < 10){
            stringHours = "0" + hours;
        }

        if(seconds < 10){
            stringSeconds = "0" + seconds;
        }

        return stringHours + ":" + stringSeconds;

    }



}

