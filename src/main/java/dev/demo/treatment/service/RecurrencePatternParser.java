package dev.demo.treatment.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class RecurrencePatternParser {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RecurrencePatternParser.class);

    public Optional<LocalDateTime> shouldGenerate(String pattern, LocalDateTime currentTime, LocalDateTime endTime) {
        String[] timeParts = validatePattern(pattern);

        List<Integer> hours = getHours(timeParts, currentTime);

        List<Integer> minutes = getMinutes(timeParts, currentTime);

        List<Integer> daysOfMonth = getDaysOfMonth(timeParts, currentTime);

        List<Integer> months = getMonths(timeParts, currentTime);

        List<Integer> daysOfWeek = getDaysOfWeek(timeParts, currentTime);


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
                        return Optional.of(LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), hour, minute));
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static List<Integer> getDaysOfWeek(String[] timeParts, LocalDateTime currentTime) {
        return timeParts[5].equals("*") ?
                List.of(currentTime.getDayOfWeek().getValue()) :
                Arrays.stream(timeParts[5].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getMonths(String[] timeParts, LocalDateTime currentTime) {
        return timeParts[4].equals("*") ?
                List.of(currentTime.getMonth().getValue()) :
                Arrays.stream(timeParts[4].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getDaysOfMonth(String[] timeParts, LocalDateTime currentTime) {
        return timeParts[3].equals("*") ?
                List.of(currentTime.getDayOfMonth()) :
                Arrays.stream(timeParts[3].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getMinutes(String[] timeParts, LocalDateTime currentTime) {
        return timeParts[1].equals("*") ?
                List.of(currentTime.getMinute()) :
                Arrays.stream(timeParts[1].split(",")).map(Integer::parseInt).toList();
    }

    private static List<Integer> getHours(String[] timeParts, LocalDateTime currentTime) {
        return timeParts[2].equals("*") ?
                List.of(currentTime.getHour()):
                Arrays.stream(timeParts[2].split(",")).map(Integer::parseInt).toList();
    }

    private static String[] validatePattern(String pattern) {
        final String INVALID_PATTERN_MESSAGE = "Invalid pattern: ";
        if (pattern == null || pattern.isEmpty()) {
            logger.error("Pattern cannot be null");
            throw new IllegalArgumentException("Pattern cannot be null");
        }

        String[] timeParts = pattern.split(" ");
        if (timeParts.length != 6) {
            String errorMessage = INVALID_PATTERN_MESSAGE + pattern;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (timeParts[0].equals("*") && timeParts[1].equals("*") && timeParts[2].equals("*") && timeParts[3].equals("*") && timeParts[4].equals("*") && timeParts[5].equals("*")) {
            String errorMessage = INVALID_PATTERN_MESSAGE + pattern;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (timeParts[0].equals("0") && timeParts[1].equals("0") && timeParts[2].equals("0") && timeParts[3].equals("0") && timeParts[4].equals("0") && timeParts[5].equals("0")) {
            String errorMessage = INVALID_PATTERN_MESSAGE + pattern;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return timeParts;
    }

}