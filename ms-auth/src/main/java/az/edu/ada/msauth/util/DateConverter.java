package az.edu.ada.msauth.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
