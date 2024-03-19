import java.math.BigDecimal;


// ToolType represents the types of tools available, the daily costs and which days to include when creating the rental
//  agreement. See CalculateChargeDays to see how this is done
public class ToolType {
    private String toolTypeName;
    private BigDecimal dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    // Constructor
    public ToolType(String toolTypeName, BigDecimal dailyCharge,
                    boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        setToolTypeName(toolTypeName);
        setDailyCharge(dailyCharge);
        setWeekdayCharge(weekdayCharge);
        setWeekendCharge(weekendCharge);
        setHolidayCharge(holidayCharge);
    }

    // Getters and Setters
    public String getToolTypeName() {
        return toolTypeName;
    }

    public void setToolTypeName(String toolTypeName) {
        if (toolTypeName == null || toolTypeName.trim().isEmpty()) {
            throw new IllegalArgumentException(ConsoleColors.RED+"Tool code cannot be null or empty."+ConsoleColors.RESET);
        }
        this.toolTypeName = toolTypeName;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }
    public String getDailyChargeAsString() {
        return Utility.currencyFormat(dailyCharge);
    }

    private void setDailyCharge(BigDecimal dailyCharge) {
        if (dailyCharge == null || dailyCharge.signum() < 0) {
            throw new IllegalArgumentException(ConsoleColors.RED+"Daily charge cannot be null or negative."+ConsoleColors.RESET);
        }
        this.dailyCharge = dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    private void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    private void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    private void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    // toString here meant for creating rental agreement
    @Override
    public String toString() {
        return  toolTypeName +
                " " + Utility.currencyFormat(dailyCharge) +
                " per day, incurred on weekdays: " + Utility.yesNo(weekdayCharge) +
                ", weekends: " + Utility.yesNo(weekendCharge) +
                ", holidays: " + Utility.yesNo(holidayCharge);
    }
}