import java.time.LocalDate;

public class BrgyRental implements Payable {

    private static int nextId = 1;
    private static final double COURT_FEE = 1500.0;
    private static final double TENT_FEE = 300.0;
    private static final String TYPE_COURT = "Covered Court";
    private static final String TYPE_TENT = "Tent";

    // fields
    private final String rentalId;
    private String renterName;
    private String rentalType;
    private LocalDate rentalDate;

    //Constructors

    public BrgyRental(String renterName, String rentalType, LocalDate rentalDate) {
        setRenterName(renterName);
        setRentalType(rentalType);
        setRentalDate(rentalDate);
        // ID is generated last so a failed validation never uses up a number
        this.rentalId = String.format("RNT-%04d", nextId++);
    }


    public BrgyRental(String rentalId, String renterName, String rentalType, LocalDate rentalDate) {
        if (rentalId == null || !rentalId.matches("RNT-\\d{4,}")) {
            throw new IllegalArgumentException("Invalid rental ID format. Expected RNT-0001.");
        }
        setRenterName(renterName);
        setRentalType(rentalType);
        if (rentalDate == null) {
            throw new IllegalArgumentException("Rental date is required.");
        }
        this.rentalId = rentalId;
        this.rentalDate = rentalDate;
    }

    //Payable

    @Override
    public double getFee() {
        return rentalType.equals(TYPE_COURT) ? COURT_FEE : TENT_FEE;
    }

    //
    @Override
    public String getPaymentDisplay() {
        return String.format("%s rental fee: PHP %.2f", rentalType, getFee());
    }

    //  Display

    // Summary for the staff record: ID + type + renter + date + fee
    public String getSummary() {
        return rentalId + " | Type: " + rentalType + " | Renter: " + renterName
                + " | Date: " + rentalDate + " | Fee: PHP " + String.format("%.2f", getFee());
    }

    //  Getters

    public String getRentalId() { return rentalId; }
    public String getRenterName() { return renterName; }
    public String getRentalType() { return rentalType; }
    public LocalDate getRentalDate() { return rentalDate; }

    //Validating setters

    public void setRenterName(String renterName) {
        if (renterName == null || renterName.trim().isEmpty()) {
            throw new IllegalArgumentException("Renter name cannot be blank.");
        }
        String name = renterName.trim();
        if (name.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Renter name cannot contain digits.");
        }
        if (name.contains("|")) {
            throw new IllegalArgumentException("Renter name cannot contain '|'.");
        }
        this.renterName = name;
    }

    public void setRentalType(String rentalType) {
        if (rentalType == null) {
            throw new IllegalArgumentException("Rental type is required.");
        }
        String type = rentalType.trim();
        if (type.equalsIgnoreCase(TYPE_COURT)) {
            this.rentalType = TYPE_COURT;
        } else if (type.equalsIgnoreCase(TYPE_TENT)) {
            this.rentalType = TYPE_TENT;
        } else {
            throw new IllegalArgumentException("Rental type must be 'Covered Court' or 'Tent'.");
        }
    }

    // For NEW or edited rentals: date must be today or later.
    public void setRentalDate(LocalDate rentalDate) {
        if (rentalDate == null) {
            throw new IllegalArgumentException("Rental date is required.");
        }
        if (rentalDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Rental date must be today or a future date.");
        }
        this.rentalDate = rentalDate;
    }

    //  Static ID counter

    public static int getNextId() { return nextId; }

    public static void setNextId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Next ID must be at least 1.");
        }
        nextId = id;
    }
}