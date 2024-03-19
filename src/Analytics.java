import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


// below are basic metrics on the data. The min, max, sum and ave for the most obvious questions (to me at least)
public class Analytics {

    private void println(String text) {
        println(ConsoleColors.PURPLE, text);
    }

    private void println(String color, String text) {
        RentalServiceConsole.println(color, text);
    }

    public void run(HashMap<String, RentalAgreement> rentals) {

        // Calculate min, max, sum and average FinalCharge value
        Optional<BigDecimal> minFinalCharge = rentals.values().stream()
                .map(RentalAgreement::getFinalCharge)
                .min(BigDecimal::compareTo);

        Optional<BigDecimal> maxFinalCharge = rentals.values().stream()
                .map(RentalAgreement::getFinalCharge)
                .max(BigDecimal::compareTo);

        BigDecimal sumFinalCharge = rentals.values().stream()
                .map(RentalAgreement::getFinalCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OptionalDouble averageFinalCharge = rentals.values().stream()
                .mapToDouble(data -> data.getFinalCharge().doubleValue())
                .average();

        // Calculate min, max, and average PreDiscount value
        Optional<BigDecimal> minPreDiscountCharge = rentals.values().stream()
                .map(RentalAgreement::getPreDiscountCharge)
                .min(BigDecimal::compareTo);

        Optional<BigDecimal> maxPreDiscountCharge = rentals.values().stream()
                .map(RentalAgreement::getPreDiscountCharge)
                .max(BigDecimal::compareTo);

        BigDecimal sumPreDiscountCharge = rentals.values().stream()
                .map(RentalAgreement::getPreDiscountCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        OptionalDouble averagePreDiscountCharge = rentals.values().stream()
                .mapToDouble(data -> data.getPreDiscountCharge().doubleValue())
                .average();

        // Calculate min, max, and average PreDiscount value
        Optional<BigDecimal> minDiscountAmount = rentals.values().stream()
                .map(RentalAgreement::getDiscountAmount)
                .min(BigDecimal::compareTo);

        Optional<BigDecimal> maxDiscountAmount = rentals.values().stream()
                .map(RentalAgreement::getPreDiscountCharge)
                .max(BigDecimal::compareTo);

        BigDecimal sumDiscountAmount = rentals.values().stream()
                .map(RentalAgreement::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        OptionalDouble averageDiscountAmount = rentals.values().stream()
                .mapToDouble(data -> data.getDiscountAmount().doubleValue())
                .average();


        // Calculate min, max, and average RentalDays value
        OptionalInt minRentalDays = rentals.values().stream()
                .mapToInt(RentalAgreement::getRentalDays)
                .min();

        OptionalInt maxRentalDays = rentals.values().stream()
                .mapToInt(RentalAgreement::getRentalDays)
                .max();

        OptionalDouble averageRentalDays = rentals.values().stream()
                .mapToInt(RentalAgreement::getRentalDays)
                .average();

        // Calculate min, max, and average ChargeDays value
        OptionalInt minChargeDays = rentals.values().stream()
                .mapToInt(RentalAgreement::getChargeDays)
                .min();

        OptionalInt maxChargeDays = rentals.values().stream()
                .mapToInt(RentalAgreement::getChargeDays)
                .max();

        OptionalDouble averageChargeDays = rentals.values().stream()
                .mapToInt(RentalAgreement::getChargeDays)
                .average();

        int sumChargeDays = 0;
        for (RentalAgreement value : rentals.values()) {
            sumChargeDays += value.getChargeDays();
        }

        // Calculate min, max, and average Discount Percentage value
        OptionalInt minDiscountPercent = rentals.values().stream()
                .mapToInt(RentalAgreement::getDiscountPercent)
                .min();

        OptionalInt maxDiscountPercent = rentals.values().stream()
                .mapToInt(RentalAgreement::getDiscountPercent)
                .max();

        OptionalDouble averageDiscountPercent = rentals.values().stream()
                .mapToInt(RentalAgreement::getDiscountPercent)
                .average();

        // Print the results
        println(String.format("There are currently %d Rental Agreements", rentals.size()));

        if (!rentals.isEmpty()) {
            Map<String, Long> frequencyMap = rentals.values().stream()
                    .map(RentalAgreement::getToolCode) // Map each agreement to its toolCode
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // Collect frequencies

            // Print the frequency map
            frequencyMap.forEach((key, value) -> println("ToolCode: "+key + " times rented: " + value));
        }

        println("Final Charge Min: " + Utility.currencyFormat(minFinalCharge.orElse(null)) +
                ", Max: " + Utility.currencyFormat(maxFinalCharge.orElse(null)) +
                ", Average: " + (averageFinalCharge.isPresent() ? String.format( "%.2f", averageFinalCharge.getAsDouble()) : "N/A") +
                ", Sum: " + Utility.currencyFormat(sumFinalCharge));

        println("PreDiscount Charge Min: " + Utility.currencyFormat(minPreDiscountCharge.orElse(null)) +
                ", Max: " + Utility.currencyFormat(maxPreDiscountCharge.orElse(null)) +
                ", Average: " + (averagePreDiscountCharge.isPresent() ? String.format( "%.2f", averagePreDiscountCharge.getAsDouble()) : "N/A") +  ", Sum: " + Utility.currencyFormat(sumPreDiscountCharge) +
                ", Sum: " + Utility.currencyFormat(sumPreDiscountCharge));

        println("Discount Percentage Min: " + (minDiscountPercent.isPresent() ? minDiscountPercent.getAsInt() : "N/A") +
                ", Max: " + (maxDiscountPercent.isPresent() ? maxDiscountPercent.getAsInt() : "N/A") +
                ", Average: " + (averageDiscountPercent.isPresent() ? averageDiscountPercent.getAsDouble() : "N/A"));

        println("Discount Amount Min: " + Utility.currencyFormat(minDiscountAmount.orElse(null)) +
                ", Max: " + Utility.currencyFormat(maxDiscountAmount.orElse(null)) +
                ", Average: " + (averageDiscountAmount.isPresent() ? String.format( "%.2f", averageDiscountAmount.getAsDouble()) : "N/A") +
                ", Sum: " + Utility.currencyFormat(sumDiscountAmount));

        println("Rental Days Min: " + (minRentalDays.isPresent() ? minRentalDays.getAsInt() : "N/A") +
                ", Max: " + (maxRentalDays.isPresent() ? maxRentalDays.getAsInt() : "N/A") +
                ", Average: " + (averageRentalDays.isPresent() ? averageRentalDays.getAsDouble() : "N/A"));

        println("Charge Days Min: " + (minChargeDays.isPresent() ? minChargeDays.getAsInt() : "N/A") +
                ", Max: " + (maxChargeDays.isPresent() ? maxChargeDays.getAsInt() : "N/A") +
                ", Average: " + (averageChargeDays.isPresent() ? averageChargeDays.getAsDouble() : "N/A") +
                ", Sum: " + sumChargeDays);
        println("");
    }
}

