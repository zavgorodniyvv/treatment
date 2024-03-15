package dev.demo.treatment.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class RecurrencePatternParser {
    public boolean shouldGenerate(String pattern, LocalDateTime currentTime, LocalDateTime endTime) {
        String[] timeParts = validatePattern(pattern);

        List<Integer> hours = getHours(timeParts);

        List<Integer> minutes = getMinutes(timeParts);

        List<Integer> daysOfMonth = getDaysOfMonth(timeParts);

        List<Integer> months = getMonths(timeParts);

        List<Integer> daysOfWeek = getDaysOfWeek(timeParts);


        for (int hour : hours) {
            for (int minute : minutes) {
                if (hour >= currentTime.getHour() && hour <= endTime.getHour()) {
                    if (hour == currentTime.getHour() && minute < currentTime.getMinute()) {
                        continue;
                    }
                    if (hour == endTime.getHour() && minute > endTime.getMinute()) {
                        continue;
                    }
                    if (daysOfWeek.contains(currentTime.getDayOfWeek().getValue()) &&
                            daysOfMonth.contains(currentTime.getDayOfMonth()) &&
                            months.contains(currentTime.getMonthValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static List<Integer> getDaysOfWeek(String[] timeParts) {
        return timeParts[5].equals("*") ?
                IntStream.rangeClosed(1, 7).boxed().toList() :
                Arrays.stream(timeParts[5].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getMonths(String[] timeParts) {
        return timeParts[4].equals("*") ?
                IntStream.rangeClosed(1, 12).boxed().toList() :
                Arrays.stream(timeParts[4].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getDaysOfMonth(String[] timeParts) {
        return timeParts[3].equals("*") ?
                IntStream.rangeClosed(1, 31).boxed().toList() :
                Arrays.stream(timeParts[3].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getMinutes(String[] timeParts) {
        return timeParts[1].equals("*") ?
                IntStream.rangeClosed(0, 59).boxed().toList() :
                Arrays.stream(timeParts[1].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getHours(String[] timeParts) {
        return timeParts[2].equals("*") ?
                IntStream.rangeClosed(0, 23).boxed().toList() :
                Arrays.stream(timeParts[2].split(",")).map(Integer::parseInt).toList();
    }

    private static String[] validatePattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }

        String[] timeParts = pattern.split(" ");
        if (timeParts.length != 6) {
            throw new IllegalArgumentException("Invalid pattern: " + pattern);
        }

        if (timeParts[0].equals("*") && timeParts[1].equals("*") && timeParts[2].equals("*") && timeParts[3].equals("*") && timeParts[4].equals("*") && timeParts[5].equals("*")) {
            throw new IllegalArgumentException("Invalid pattern: " + pattern);
        }

        if (timeParts[0].equals("0") && timeParts[1].equals("0") && timeParts[2].equals("0") && timeParts[3].equals("0") && timeParts[4].equals("0") && timeParts[5].equals("0")) {
            throw new IllegalArgumentException("Invalid pattern: " + pattern);
        }
        return timeParts;
    }

}