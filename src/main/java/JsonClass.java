import annotations.CustomDateFormat;
import annotations.JsonValue;


import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class JsonClass {


    public static String toJson(Object object) throws IllegalAccessException, IllegalArgumentException {
        String s = checkAnnotationChangeFieldCreateString(object);
        return s;
    }

    public static String checkAnnotationChangeFieldCreateString(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> mapFields = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(object) != null) {
                if (field.isAnnotationPresent(JsonValue.class)) {
                    String newFieldName = field.getAnnotation(JsonValue.class).name();
                    mapFields.put(newFieldName, field.get(object));
                } else if (field.isAnnotationPresent(CustomDateFormat.class)) {
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern(field.getAnnotation(CustomDateFormat.class).format());
                    LocalDate o = (LocalDate) field.get(object);
                    mapFields.put(field.getName(), o.format(formatter));
                } else {
                    mapFields.put(field.getName(), field.get(object));
                }
                field.setAccessible(false);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        int count = mapFields.size();
        stringBuilder.append("{");
        for (Map.Entry<String, Object> stringObjectEntry : mapFields.entrySet()) {
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
