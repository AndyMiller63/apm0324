import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class CalculateChargeDays {

    // Below are used for calculating the number of days that incur a charge
    int getChargeDaysCount(ToolType toolType, LocalDate startDate, int numberOfDays) {
        int result = 0;
        for (int i = 0; i < numberOfDays; i++) {
            // get cost for startDate + i
            LocalDate date = startDate.plusDays(i);
            CalculateChargeDays.DayType dayType = getDayType(date);
            switch (dayType) {
                case HOLIDAY: {
                    if (toolType.isHolidayCharge()) {
                        result++;
                    }
                    break;
                }
                case WEEKEND: {
                    if (toolType.isWeekendCharge()) {
                        result++;
                    }
                    break;
                }
                default: { // weekday
                    if (toolType.isWeekdayCharge()) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public enum DayType {WEEKDAY, WEEKEND, HOLIDAY}
    public DayType getDayType(LocalDate date) {
        if (isHoliday(date))
            return DayType.HOLIDAY;
        if (isWeekend(date))
            return DayType.WEEKEND;
        return DayType.WEEKDAY;
    }

    public static boolean isFirstMondayOfSeptember(int dayOfMonth, Month month, DayOfWeek dayOfWeek) {
        return month == Month.SEPTEMBER && dayOfWeek == DayOfWeek.MONDAY && dayOfMonth <= 7;
    }

    private boolean isHoliday(LocalDate date){
        Month month = date.getMonth();
        int dayOfMonth = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // 4th of July: the 4th if it falls on a weekday, otherwise the closest weekday
        if (month == Month.JULY) {
            if (dayOfMonth >= 3 && dayOfMonth <= 5) { // Limit the check to 3rd, 4th, and 5th
                if (dayOfMonth == 4) {
                    // If it's the 4th, check that it's not a weekend
                    return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
                } else if (dayOfMonth == 3) {
                    // If it's the 3rd, it must be a Friday and the 4th a Saturday
                    return dayOfWeek == DayOfWeek.FRIDAY;
                } else { // dayOfMonth == 5
                    // If it's the 5th, it must be a Monday and the 4th a Sunday
                    return dayOfWeek == DayOfWeek.MONDAY;
                }
            }
            return false;
        }
        // Labor Day: First Monday in September
        if (isFirstMondayOfSeptember(dayOfMonth, month, dayOfWeek)) {
            return true;
        }
        return false;
    }

    private boolean isWeekend(LocalDate date){
        DayOfWeek day = date.getDayOfWeek();
        return (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
    }
}
