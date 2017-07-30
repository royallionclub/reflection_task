package beings;

import annotations.JsonValue;

import java.time.LocalDate;

public class Pet {
    @JsonValue(name = "catName")
    private String name;
    private LocalDate birthDate;

    public Pet(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }


}
