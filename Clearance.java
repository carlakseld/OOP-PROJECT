import java.time.LocalDate;

public class Clearance extends DocumentRequest implements Payable {
    private static final double FEE = 50.0;
    private String purpose;

    // Constructor for a NEW clearance
    public Clearance(Resident resident, String purpose) {
        super (resident);
        setPurpose(purpose);
    }

    // Constructor for LOADING a saved clearance
    public Clearance(Resident resident, String requestId, LocalDate dateRequested, String purpose) {
        super(resident, requestId, dateRequested);
        setPurpose(purpose);
    }

    // Returns the type name
    @Override
    public String getDocumentType() {
        return "Clearance";
    }

    // Eligible when the purpose is valid (not blank, no '|')
    @Override
    public boolean isEligible() {
        return purpose != null && !purpose.trim().isEmpty() && !purpose.contains("|");
    }



    // Payable: returns the fixed fee
    @Override
    public double getFee() {
        return FEE;
    }

    // Payable: fee text for the record only
    @Override
    public String getPaymentDisplay() {
        return String.format("Barangay Clearance fee: PHP %.2f", FEE);
    }

    // Getter for purpose
    public String getPurpose() {
        return purpose;
    }

    // Setter for purpose: not blank, no '|'
    public void setPurpose(String purpose) {
        // Reject a missing or blank purpose
        if (purpose == null || purpose.trim().isEmpty()) {
            throw new IllegalArgumentException("Purpose cannot be blank.");
        }
        // Reject the '|' symbol
        if (purpose.contains("|")) {
            throw new IllegalArgumentException("Purpose cannot contain '|'.");
        }
        this.purpose = purpose.trim();
    }

    // Summary for the staff record: base + type + purpose + eligibility + fee
    @Override
    public String getSummary() {
        return super.getSummary()
                + " | Type: " + getDocumentType()
                + " | Purpose: " + purpose
                + " | Eligible: " + (isEligible() ? "Yes" : "No")
                + " | Fee: PHP " + String.format("%.2f", FEE);
    }
}