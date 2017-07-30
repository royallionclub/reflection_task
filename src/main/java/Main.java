import annotations.CustomDateFormat;
import annotations.JsonValue;
import beings.Human;
import beings.Pet;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static Map<String, Object> map = new HashMap<>();

    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException {
        Human max = new Human("Ivan", "Petrov",
                "reading", LocalDate.of(1980, Month.JANUARY, 28));
        Human vit = new Human("Sergei", "Kovalchuk",
                "videogames", LocalDate.of(1985, Month.MAY, 5));
        Pet pet = new Pet("Murchik", LocalDate.of(2011, Month.NOVEMBER, 10));

        String being1 = toJson(max);
        System.out.println(being1);

        String being2 = toJson(vit);
        System.out.println(being2);

        String being3 = toJson(pet);
        System.out.println(being3);


    }

    public static String toJson(Object object) throws IllegalAccessException, IllegalArgumentException {
        checkAnnotationChangeField(object);
        String s = createString();
        map.clear();
        return s;
    }

    public static void checkAnnotationChangeField(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonValue.class)) {
                field.setAccessible(true);
                String newFieldName = field.getAnnotation(JsonValue.class).name();
                map.put(newFieldName, field.get(object));
                field.setAccessible(false);
            } else if (field.isAnnotationPresent(CustomDateFormat.class)) {
                field.setAccessible(true);
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(field.getAnnotation(CustomDateFormat.class).format());
                LocalDate o = (LocalDate) field.get(object);
                map.put(field.getName(), o.format(formatter));
                field.setAccessible(false);
            } else {
                field.setAccessible(true);
                map.put(field.getName(), field.get(object));
                field.setAccessible(false);
            }
        }
    }


    public static String createString() throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        int count = map.size();
        stringBuilder.append("{");
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            stringBuilder.append("\"")
                    .append(stringObjectEntry.getKey())
                    .append("\":\"")
                    .append(stringObjectEntry.getValue());
            if (count != 1) stringBuilder.append("\",");
            else stringBuilder.append("\"}");
            count--;
        }
        return String.valueOf(stringBuilder);
    }

}
