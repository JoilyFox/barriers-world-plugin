package usf.barriersland.barriersworld.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeUtil {

    public static Date parseWeeklyTime(String dayTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE HH:mm");
        return sdf.parse(dayTime);
    }

    public static Date parseSpecificTime(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.parse(dateTime);
    }

    public static String getNextOpeningTime(List<String> weeklyOpenings, List<String> dateSpecificOpenings) {
        // Assuming the lists are sorted, find the next opening time from the current time.
        String nextOpeningTime = null;
        long currentTime = System.currentTimeMillis();
        for (String opening : weeklyOpenings) {
            // Convert opening time to milliseconds for comparison.
            try {
                long openingTime = parseWeeklyTime(opening).getTime();
                if (openingTime > currentTime) {
                    nextOpeningTime = opening;
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return nextOpeningTime;
    }

    public static String getNextClosingTime(List<String> weeklyOpenings, List<String> dateSpecificOpenings, int openDuration) {
        String nextClosingTime = null;
        long currentTime = System.currentTimeMillis();

        // Merging both lists for simplicity and assuming all times are in the future.
        List<String> allOpenings = new ArrayList<>(weeklyOpenings);
        allOpenings.addAll(dateSpecificOpenings);

        // Sorting all openings assuming they are in a consistent, parsable format.
        allOpenings.sort(String::compareTo);

        for (String opening : allOpenings) {
            try {
                Date openingDate = parseWeeklyTime(opening);  // Assuming all dates follow the weekly format.
                long openingTime = openingDate.getTime();

                if (openingTime > currentTime) {
                    // Adding openDuration (assumed to be in minutes) to find the closing time.
                    long closingTime = openingTime + ((long) openDuration * 60 * 1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE HH:mm");
                    nextClosingTime = sdf.format(new Date(closingTime));
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();  // Log the error or handle it as necessary.
            }
        }

        return nextClosingTime;
    }


    public static boolean isTimeToOpen(String nextOpeningTime) {
        try {
            long openingTime = parseWeeklyTime(nextOpeningTime).getTime();
            long currentTime = System.currentTimeMillis();
            return currentTime >= openingTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isTimeToClose(String nextClosingTime) {
        try {
            // Parse the nextClosingTime string into a Date object.
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE HH:mm");
            Date closingDate = sdf.parse(nextClosingTime);

            // Get the current time.
            long currentTime = System.currentTimeMillis();

            // Check if the current time is on or after the closing time.
            return currentTime >= closingDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();  // Log the error or handle it as necessary.
            return false;  // Return false if the nextClosingTime string cannot be parsed.
        }
    }


    public static long getTimeDifference(long currentTime, String nextOpeningTime) {
        try {
            long openingTime = parseWeeklyTime(nextOpeningTime).getTime();
            return openingTime - currentTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String formatTimeDifference(long timeToNextOpening) {
        long hours = timeToNextOpening / (1000 * 60 * 60);
        long minutes = (timeToNextOpening % (1000 * 60 * 60)) / (1000 * 60);
        return String.format("%02d hours %02d minutes", hours, minutes);
    }

    // Other utility methods to handle time calculations, like getting the time difference in minutes/hours/seconds
}
