package ubb.proiect.MakeupSalon.model;

public enum Role {
    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    CUSTOMER("Customer");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
