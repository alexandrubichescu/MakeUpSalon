package ubb.proiect.MakeupSalon.model;

public enum Status {
    APPROVED("Approved"),
    PENDING("Pending"),
    REJECTED("Rejected"),
    EXPIRED("Expired");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
