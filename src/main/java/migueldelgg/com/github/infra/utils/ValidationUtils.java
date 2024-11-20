package migueldelgg.com.github.infra.utils;

import migueldelgg.com.github.infra.entity.WeekDay;

import java.time.LocalTime;

public class ValidationUtils {

    public static Boolean isTheSameWeekDay(WeekDay dayOfWeekStart, WeekDay dayOfWeekEnd) {
        return dayOfWeekStart.equals(dayOfWeekEnd);
    }

    public static Boolean endTimeIsBeforeStartTime(LocalTime startTime, LocalTime endTime) {
        return !startTime.isBefore(endTime);
    }

    public static Boolean isStartTimeAndEndTimeNull(LocalTime startTime, LocalTime endTime) {
        return startTime == null || endTime == null;
    }

    public static Boolean isStartWeekDayAndEndWeekDayNull(WeekDay start, WeekDay end) {
        return start == null || end == null;
    }
}
