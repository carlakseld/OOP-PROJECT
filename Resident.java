public class Resident {
    private String fullName;
    private String address;
    private String contactNumber;

    // Constructor
    public Resident(String fullName, String address, String contactNumber) {
        setFullName(fullName);
        setAddress(address);
        setContactNumber(contactNumber);
    }

    // Getter
    public String getFullName() {
        return fullName;
    }
    public String getAddress() {
        return address;
    }
    public String getContactNumber() {
        return contactNumber;
    }

    // Setter for name: not blank, no digits, no '|'
    public void setFullName(String fullName) {
        // Reject a missing or blank name
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be blank.");
        }
        String name = fullName.trim();
        // Reject names that contain digits
        if (name.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Full name cannot contain digits.");
        }
        // Reject the '|' symbol
        if (name.contains("|")) {
            throw new IllegalArgumentException("Full name cannot contain '|'.");
        }
        this.fullName = name;
    }

    // Setter for address: not blank, no '|'
    public void setAddress(String address) {
        // Reject a missing or blank address
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be blank.");
        }
        // Reject the '|' symbol
        if (address.contains("|")) {
            throw new IllegalArgumentException("Address cannot contain '|'.");
        }
        this.address = address.trim();
    }

    // Setter for contact number: exactly 11 digits, starts with 09
    public void setContactNumber(String contactNumber) {
        // Reject a missing number or one that is not 09 followed by 9 digits
        if (contactNumber == null || !contactNumber.trim().matches("09\\d{9}")) {
            throw new IllegalArgumentException("Contact number must be 11 digits and start with 09.");
        }
        this.contactNumber = contactNumber.trim();
    }
}