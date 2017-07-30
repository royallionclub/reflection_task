import beings.Human;
import beings.Pet;

import java.time.LocalDate;
import java.time.Month;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException {
        Human max = new Human("Ivan", "Petrov",
                "reading", LocalDate.of(1980, Month.JANUARY, 28));
        Human vit = new Human("Sergei", "Kovalchuk",
                "videogames", LocalDate.of(1985, Month.MAY, 5));
        Pet pet = new Pet(null, LocalDate.of(2011, Month.NOVEMBER, 10));

        String being1 = JsonClass.toJson(max);
        System.out.println(being1);

        String being2 = JsonClass.toJson(vit);
        System.out.println(being2);

        String being3 = JsonClass.toJson(pet);
        System.out.println(being3);

    }
}
