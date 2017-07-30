package com.remswork.classmanager.utils;

import java.util.Locale;

/**
 * Created by Rafael on 7/28/2017.
 */

public class StringFormatter {

    private static StringFormatter stringFormatterInstance;

    public StringFormatter(){
        if(stringFormatterInstance == null)
            stringFormatterInstance = new StringFormatter();
    }

    public StringFormatter getInstance(){
        return stringFormatterInstance;
    }

    public static String cutLongString(final String word, final int numberOfCharacter){
        if(word.toCharArray().length <= numberOfCharacter)
            return word;
        else
            return word.substring(0, numberOfCharacter-3) + "...";
    }

    public static String getToDate(final String time, final int numberOfHour) {
        try {
            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1].split(" ")[0].trim());
            String meridiem = (time.split(":")[1].split(" ").length > 1 ?
                    time.split(":")[1].split(" ")[1] : null);

            minute += numberOfHour;
            hour += (int) (minute >= 60 ? minute / 60 : 0);
            minute %= 60;

            if (meridiem != null) {
                if (meridiem.equalsIgnoreCase("PM"))
                    hour += 12;
            }
            return String.format(Locale.ENGLISH, "%s:%02d %s", (hour <= 12 ? hour : hour % 12 == 0
                            ? 12 : hour % 12),
                    minute, (hour > 11 && hour < 24 ? "PM" : "AM"));
        } catch (NumberFormatException e) {
            return "Time not set";
        }
    }
}
