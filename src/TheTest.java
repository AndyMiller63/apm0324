import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheTest {


    @Test
    public void testInventory() {
        Inventory inventory = new Inventory();
        Tool tool = inventory.getTool("CHNS");
        assertEquals("Chainsaw", tool.getToolType().getToolTypeName());
    }

    @Test
    public void testToolType() {
        assertThrows(IllegalArgumentException.class, () ->
            new ToolType(null, BigDecimal.valueOf(1.49), true, false, true));
        assertThrows(IllegalArgumentException.class, () ->
            new ToolType("", BigDecimal.valueOf(1.49), true, false, true));
        assertThrows(IllegalArgumentException.class, () ->
            new ToolType("   ", BigDecimal.valueOf(1.49), true, false, true));
    }

    @Test
    public void testRentalServiceCheckout() {
        RentalService rentalService = new RentalService();

        Exception exception = assertThrows(Exception.class, () ->
            rentalService.checkout("asdasd", 1, 0, "03/15/24"));
        assertEquals("Tool code is not recognized.", exception.getMessage(), "ToolCode: Exception message is not as expected");

        exception = assertThrows(Exception.class, () ->
            rentalService.checkout("LADW", 0, 0, "03/15/24"));
        assertEquals("Rental day count must be 1 or greater.", exception.getMessage(), "RentalDay: Exception message is not as expected");

        exception = assertThrows(Exception.class, () ->
            rentalService.checkout("LADW", 1, -1, "03/15/24"));
        assertEquals("Discount percent must be in the range 0-100.", exception.getMessage(), "DiscountPercent: Exception message is not as expected");

        exception = assertThrows(Exception.class, () ->
            rentalService.checkout("LADW", 1, 200, "03/15/24"));
        assertEquals("Discount percent must be in the range 0-100.", exception.getMessage(), "DiscountPercent: Exception message is not as expected");

        // test successful case
        try {
            RentalAgreement agreement = rentalService.checkout("LADW", 123, 100, "03/15/23");
            assertEquals(123, agreement.getRentalDays());
            assertEquals(100, agreement.getDiscountPercent());
            assertEquals("($0.00)", Utility.currencyFormat(agreement.getFinalCharge()));
        } catch (Exception e) {
            System.out.println("create agreement test failed: "+e.getMessage());
        }
    }
}
