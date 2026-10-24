import java.time.LocalDate;

public class Indigency extends DocumentRequest {

    private static final double INCOME_LIMIT = 10000.0;
    private double monthlyIncome;
    private String reason;

    //Constructor for a new Indigency
    public Indigency(Resident resident, double monthlyIncome, String reason) {
        super(resident);
        setMonthlyIncome(monthlyIncome);
        setReason(reason);
    }

    // Overloading Constructor for a saved indigency request
    public Indigency(Resident resident, String requestId, LocalDate dateRequested, double monthlyIncome, String reason) {
        super(resident, requestId, dateRequested);
        setMonthlyIncome(monthlyIncome);
        setReason(reason);
    }

    //Own Implemetntation of Type
    @Override
    public String getDocumentType() {
        return "Indigency";
    }
    //Own implementation of Eligibility Req
    @Override
    public boolean isEligible() {
        return monthlyIncome <= INCOME_LIMIT;
    }


    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getReason() {
        return reason;
    }

    public static double getIncomeLimit() {
        return INCOME_LIMIT;
    }

    //Setter for a monthly Income
    public void setMonthlyIncome(double monthlyIncome) {
        // Reject NaN and infinity (a typed "NaN" or "Infinity" would otherwise get through)
        if (Double.isNaN(monthlyIncome) || Double.isInfinite(monthlyIncome)) {
            throw new IllegalArgumentException("Monthly income must be a valid number.");
        }
        if (monthlyIncome < 0) {
            throw new IllegalArgumentException("Monthly income cannot be negative.");
        }
        this.monthlyIncome = monthlyIncome;
    }

    // Setter for reason
    public void setReason(String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be blank.");
        }
        if (reason.contains("|")) {
            throw new IllegalArgumentException("Reason cannot contain '|'.");
        }
        this.reason = reason.trim();
    }

    @Override
    public String getSummary() {
        return super.getSummary()
                + " | Type: " + getDocumentType()
                + " | Monthly Income: " + String.format("%.2f", monthlyIncome)
                + " | Reason: " + reason
                + " | Eligible: " + (isEligible() ? "Yes" : "No")
                + " | Fee: FREE";
    }
}