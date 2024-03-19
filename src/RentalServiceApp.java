
/*
 The application is a point-of-sale tool for a store, like Home Depot, that rents big tools.
 Customers rent a tool for a specified number of days.
 When a customer checks out a tool, a Rental Agreement is produced.
 The store charges a daily rental fee, whose amount is different for each tool type.
 Some tools are free of charge on weekends or holidays.
 Clerks may give customers a discount that is applied to the total daily charges to reduce the final
  charge.

  The ToolType class defines the toolType (Chainsaw, Ladder or Jackhammer) along with the daily charges and if the
  renter will be billed for weekends or holidays.

  The Tool class defines a tool having a toolCode, an unrestricted string, but by example may be limited to 4 characters.

  The only calls here other than adjust logger setting is call the main loop in RentalServiceConsole
 */

public class RentalServiceApp {

    public static void main(String[] args) {
        try {
            Logger.adjustLoggerSettings();
            RentalServiceConsole.handleLoop();
        } catch (Exception ex) {
            Logger.error("Service aborted. An error occurred: "+ex);
        }
    }

}


