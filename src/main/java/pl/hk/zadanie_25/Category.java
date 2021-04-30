package pl.hk.zadanie_25;

public enum Category {
    HOUSEHOLD_DUTIES("obowiązki domowe"), JOB("praca"), COURSE("kurs");

    private final String description;


    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
