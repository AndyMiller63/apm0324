import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static String currencyFormat(BigDecimal n) {
        if (n == null) {
            return "null";
        }
        return NumberFormat.getCurrencyInstance().format(n);
    }

    public static String dateFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return date.format(formatter);
    }

    public static String yesNo(boolean flag) {
        return flag ? "Yes" : "No";
    }

    public static boolean isDateValid(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(false); // Require strict matching
        try {
            sdf.parse(dateString); // Attempt to parse the string
            return true; // Successfully parsed, date is valid
        } catch (ParseException e) {
            return false; // Failed to parse, date is invalid
        }
    }

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
        return formatter.format(now);
    }

}
