import annotations.CustomDateFormat;
import annotations.JsonValue;


import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class JsonClass {

    public static Map<String, Object> map = new HashMap<>();


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
            field.setAccessible(true);
            if (field.get(object) != null) {
                if (field.isAnnotationPresent(JsonValue.class)) {
                    String newFieldName = field.getAnnotation(JsonValue.class).name();
                    map.put(newFieldName, field.get(object));
                } else if (field.isAnnotationPresent(CustomDateFormat.class)) {
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern(field.getAnnotation(CustomDateFormat.class).format());
                    LocalDate o = (LocalDate) field.get(object);
                    map.put(field.getName(), o.format(formatter));
                } else {
                    map.put(field.getName(), field.get(object));
                }
                field.setAccessible(true);
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
