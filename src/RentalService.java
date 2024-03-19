import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;


/*
Business rules:
1) Tool code - Specified at checkout
2) Tool type - From tool info
3) Tool brand - From tool info
4) Rental days - Specified at checkout
5) Check out date - Specified at checkout
6) Due date - Calculated from checkout date and rental days.
7) Daily rental charge - Amount per day, specified by the tool type.
8) Charge days - Count of chargeable days, from day after checkout through and including due
date, excluding “no charge” days as specified by the tool type.
9) Pre-discount charge - Calculated as charge days X daily charge. Resulting total rounded half up
to cents.
10) Discount percent - Specified at checkout.
11) Discount amount - calculated from discount % and pre-discount charge. Resulting amount
rounded half up to cents.
12) Final charge - Calculated as pre-discount charge - discount amount.

 */
class RentalService {

    // create the inventory to select the tools from
    final private Inventory inventory;
    // create a hashmap for the agreements created. You can then use the Analytics to see various statistics.
    final private HashMap<String, RentalAgreement> rentals = new HashMap<>();
    public RentalService() {
        inventory = new Inventory();
    }

    public Tool getTool(String toolCode) {
        return inventory.getTool(toolCode);
    }

    public String getInventoryForDisplayAsString() {
        return inventory.getInventoryForDisplayAsString();
    }

    public HashMap<String, RentalAgreement> getRentals() {
        return rentals;
    }

    public Tool validateInputsAndReturnTool(String toolCode, int rentalDays, int discountPercent, String checkoutDateString) throws Exception {
        // Validate inputs
        // min of 1 rentalDays
        if (rentalDays < 1) {
            throw new Exception("Rental day count must be 1 or greater.");
        }

        // don't allow negative discount or greater than 100%
        if (discountPercent < 0 || discountPercent > 100) {
            throw new Exception("Discount percent must be in the range 0-100.");
        }

        // we need to this check and make sure the date is ok. Just calling LocalDate.parse("02/29/01", ...),
        // (there are no leap day that year) returns 02/28/01, as opposed to an exception
        if (!Utility.isDateValid(checkoutDateString)) {
            throw new Exception("Date is not valid.");
        }

        // make sure tool exists
        Tool tool = inventory.getTool(toolCode);
        if (tool == null) {
            throw new Exception("Tool code is not recognized.");
        }

        return tool;
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, String checkoutDateString) throws Exception {

        // validate the inputs and return the tool
        Tool tool = validateInputsAndReturnTool(toolCode, rentalDays, discountPercent, checkoutDateString);

        // Parse checkout date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        //convert String to LocalDate
        LocalDate checkoutDate = LocalDate.parse(checkoutDateString, formatter);
        // calculate due date
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        // Calculate charge days
        int chargeDays = new CalculateChargeDays().getChargeDaysCount(tool.getToolType(), checkoutDate, rentalDays);

        // Calculate charges
        BigDecimal dailyCharge = tool.getToolType().getDailyCharge();
        BigDecimal preDiscountCharge = dailyCharge.multiply(new BigDecimal(chargeDays));
        BigDecimal discountAmount = preDiscountCharge
                .multiply(new BigDecimal(discountPercent))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        // Create and return the rental agreement
        RentalAgreement rentalAgreement =  new RentalAgreement(tool, rentalDays, checkoutDate, dueDate, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
        rentals.put(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()), rentalAgreement);
        return rentalAgreement;
    }

}