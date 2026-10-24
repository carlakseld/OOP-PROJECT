import java.time.LocalDate;

public class Residency extends DocumentRequest implements Payable {

    private static final int MIN_MONTHS = 6;
    private static final double FEE = 30.0;
    private int monthsOfResidency;

    // Constructor for a NEW residency request
    public Residency(Resident resident, int monthsOfResidency) {
        super(resident);
        setMonthsOfResidency(monthsOfResidency);
    }

    // Overloading Constructor for a saved residency request
    public Residency(Resident resident, String requestId, LocalDate dateRequested, int monthsOfResidency) {
        super(resident, requestId, dateRequested);
        setMonthsOfResidency(monthsOfResidency);
    }

    // Returns the type name
    @Override
    public String getDocumentType() {
        return "Residency";
    }
    // Eligible when months of residency reach the minimum
    @Override
    public boolean isEligible() {
        return monthsOfResidency >= MIN_MONTHS;
    }

    // Setter for months of residency: zero or more
    public void setMonthsOfResidency(int monthsOfResidency) {
        // Reject negative months
        if (monthsOfResidency < 0) {
            throw new IllegalArgumentException("Months of residency cannot be negative.");
        }
        this.monthsOfResidency = monthsOfResidency;
    }

    // Getter for months of residency
    public int getMonthsOfResidency() {
        return monthsOfResidency;
    }

    // Summary for the staff record: base + type + months + eligibility + fee
    @Override
    public String getSummary() {
        return super.getSummary()
                + " | Type: " + getDocumentType()
                + " | Months: " + monthsOfResidency
                + " | Eligible: " + (isEligible() ? "Yes" : "No")
                + " | Fee: PHP " + String.format("%.2f", FEE);
    }

    // Payable: returns the fixed fee
    @Override
    public double getFee() {
        return FEE;
    }

    // Payable: fee text for the record only
    @Override
    public String getPaymentDisplay() {
        return String.format("Certificate of Residency fee: PHP %.2f", FEE);
    }
}