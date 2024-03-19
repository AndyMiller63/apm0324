import java.math.BigDecimal;
import java.time.LocalDate;

// Rental agreement that gets produced when a tool is rented
class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private int discountPercent;
    private LocalDate checkoutDate;
    private LocalDate dueDate;

    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    private void setTool(Tool tool) {
        this.tool = tool;
    }

    private void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    private void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    private void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    private void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    private void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    private void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    private void setFinalCharge(BigDecimal finalCharge) {
        this.finalCharge = finalCharge;
    }

    public String getToolCode() { return tool.getToolCode(); }

    public int getRentalDays() {
        return rentalDays;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public int getChargeDays() { return chargeDays; }


    private void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }
    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    // Constructor and methods...
    public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, LocalDate dueDate,
                           int chargeDays, BigDecimal preDiscountCharge,
                           int discountPercent, BigDecimal discountAmount, BigDecimal finalCharge) {
        setTool(tool);
        setRentalDays(rentalDays);
        setCheckoutDate(checkoutDate);
        setDueDate(dueDate);
        setChargeDays(chargeDays);
        setPreDiscountCharge(preDiscountCharge);
        setDiscountPercent(discountPercent);
        setDiscountAmount(discountAmount);
        setFinalCharge(finalCharge);
    }

    @Override
    public String toString() {
        ToolType toolType = tool.getToolType();
        // Format the rental agreement details here

        return String.format(

                "For one %s, a %s brand %s\n"+
                "Checkout date: %s. with a rental period of %d days makes your due date %s.\n" +
                "During this rental period you will be charged for weekdays that are not holidays.\n"+
                "Incurs cost over weekends: %s, incurs cost over holidays: %s.\n"+
                "Over the %d days following the date of %s you will be charged for %d of those days at %s per day, before any discounts.\n"+
                "Pre-discount charge over %d days @ %s per day, is %s.\n"+
                "Discount percent: %d%% gives you a discount of %s.\n"+
                "Your final charge will be %s.",

                tool.getToolCode(), tool.getBrand(), toolType.getToolTypeName(),
                Utility.dateFormat(checkoutDate), rentalDays, Utility.dateFormat(dueDate),
                Utility.yesNo(toolType.isWeekendCharge()), Utility.yesNo(toolType.isHolidayCharge()),
                rentalDays, Utility.dateFormat(checkoutDate), chargeDays, toolType.getDailyChargeAsString(),
                chargeDays, toolType.getDailyChargeAsString(), Utility.currencyFormat(preDiscountCharge),
                discountPercent, Utility.currencyFormat(discountAmount),
                Utility.currencyFormat(finalCharge));
    }

}