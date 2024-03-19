import java.time.LocalDate;
import java.util.Scanner;


/* RentalServiceConsole is a console interface for calling RentalService, which in turn creates a RentalAgreement
It has the following functionality
    1) create a new rental agreement
    2) view the inventory (the tools available and their details)
    3) view some analytics
    4) eXit
 */
public class RentalServiceConsole {

    public static void print(String color, String text) {
        System.out.print(color+text+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE);
    }

    public static void println(String color, String text) {
        System.out.println(color+text+ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE);
    }


    // create new rental agreement, show the inventory or show some analytics (or exit the service)
    private static boolean handleOp(Scanner scanner, RentalService rentalService, Analytics analytics,
                                     String inventoryString) {
        while (true) {
            print(ConsoleColors.BLUE,"Type 1 for rental, 2 for inventory, 3 for stats or X to eXit: ");
            String result = scanner.nextLine();
            switch (result) {
                case "x":
                case "X":
                    return false;
                case "1": // rental
                    return true;
                case "2": // inventory
                    showInventory(inventoryString);
                    break;
                case "3": // stats
                    analytics.run(rentalService.getRentals());
                    break;
                default:
                    println(ConsoleColors.RED, "Input not valid, please select from given options.");
            }
        }
    }

    private static String handleToolCode(Scanner scanner, RentalService rentalService) {
        while (true) {
            print(ConsoleColors.BLUE,"Enter the tool code: ");
            String result = scanner.nextLine().toUpperCase();
            if (rentalService.getTool(result) != null) {
                return result;
            }
            println(ConsoleColors.RED,"Tool code " +result+ " not recognized.\nTry again, please.");
        }
    }

    private static int handleRentalDays(Scanner scanner) {
        while (true) {
            try {
                print(ConsoleColors.BLUE, "Enter the number of rental days greater than 0: (default = 1) ");
                String inputString = scanner.nextLine();
                int rentalDays = (inputString.isEmpty()) ? 1 : Integer.parseInt(inputString);
                if (rentalDays < 1) {
                    println(ConsoleColors.RED, "You need to enter a rental period greater than 0: ");
                }
                return rentalDays;
            } catch (NumberFormatException ex) {
                println(ConsoleColors.RED, "Invalid input: Please enter a valid number for rental days greater than 0.");
            }
        }
    }

    private static int handleDiscountPercent(Scanner scanner) {
        while (true) {
            try {
                print(ConsoleColors.BLUE, "Enter the discount percentage 0-100: (default = 0) ");
                String inputString = scanner.nextLine();
                int discountPercent = (inputString.isEmpty()) ? 0 : Integer.parseInt(inputString);
                if (discountPercent < 0 || discountPercent > 100) {
                    println(ConsoleColors.RED, "You need to enter a discount percentage between 0 and 100.");
                }
                return discountPercent;
            } catch (NumberFormatException ex) {
                println(ConsoleColors.RED, "Invalid input: valid number for discount percentage.");
            }
        }
    }

    public static String getTodaysDate() {
        LocalDate localDate = LocalDate.now();
        return Utility.dateFormat(localDate);
    }

    private static String handleCheckoutDate(Scanner scanner) {
        while (true) {
            print(ConsoleColors.BLUE, "Enter the checkout date (mm/dd/yy): (default = today) ");
            String checkoutDateString = scanner.nextLine();
            if (checkoutDateString.isEmpty()) {
                checkoutDateString = getTodaysDate();
            }
            if (!Utility.isDateValid(checkoutDateString)) {
                println(ConsoleColors.RED, "Invalid date: " + checkoutDateString);
                continue;
            }
            return checkoutDateString;
        }
    }

    private static void showInventory(String inventoryString) {
        println(ConsoleColors.PURPLE, "Here is what we have in the inventory:");
        println(ConsoleColors.PURPLE, inventoryString);
    }

    private static void handleShowAgreement(RentalAgreement agreement) {
        // Print the rental agreement to the console
        println(ConsoleColors.GREEN, "\nRental Agreement:");
        println(ConsoleColors.GREEN, agreement.toString()+"\n");
    }

    public static void handleLoop() {
        Logger.info("Service started at: "+Utility.getCurrentDateTime());
        Scanner scanner = new Scanner(System.in);
        RentalService rentalService = new RentalService(); // the workhorse class to handle business logic
        String inventoryString = rentalService.getInventoryForDisplayAsString(); // the inventory never changes
        Analytics analytics = new Analytics(); // get the min/max/sum/ave of various rental properties
        println(ConsoleColors.BLUE, "Welcome to the Tool Rental Service!");
        while (true) {
            if (!handleOp(scanner, rentalService, analytics, inventoryString)) {
                break;
            }
            String toolCode = handleToolCode(scanner, rentalService);
            int rentalDays = handleRentalDays(scanner);
            int discountPercent = handleDiscountPercent(scanner);
            String checkoutDateString = handleCheckoutDate(scanner);
            try {
                // create the rental agreement via a checkout
                RentalAgreement agreement = rentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDateString);
                handleShowAgreement(agreement);
            } catch (Exception ex) {
                Logger.error("Exception creating rentalAgreement: "+ex.getMessage());
            }
        }
        scanner.close();
        println(ConsoleColors.BLUE, "Thank you for using the Tool Rental Service. Goodbye!");
        Logger.info("Service stopped at: "+Utility.getCurrentDateTime());
    }
}
