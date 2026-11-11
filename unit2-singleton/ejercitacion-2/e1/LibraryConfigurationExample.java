package e1;

import java.security.InvalidParameterException;

// ALBANES, Luciano Joaquín

public class LibraryConfigurationExample {
    public static void main(String[] args) {
        LibraryConfiguration c1 = LibraryConfiguration.getInstance();
        LibraryConfiguration c2 = LibraryConfiguration.getInstance();


        if (c1 == c2) {
            System.out.println("Same instances");
        }

        c1.setInstitutionName("ITU's Great Library");
        System.out.println(c2.getInstitutionName());
    }
}

class LibraryConfiguration {
    // Here is the singleton instance
    private static final LibraryConfiguration instance = new LibraryConfiguration();

    // Store parameters
    private String institutionName = "LibraryConfiguration";
    private int maxBooksPerMember = 3;
    private int returnPeriodDays = 14;


    // Constructor. Parameters are loaded.
    private LibraryConfiguration() {}

    // Method to get the singleton instance
    // This is the unique (public) way to get an instance of this class.
    public static LibraryConfiguration getInstance() {
        return instance;
    }

    // Getters and Setters
    public String getInstitutionName() {
        return institutionName;
    }

    public int getMaxBooksPerMember() {
        return maxBooksPerMember;
    }

    public int getReturnPeriodDays() {
        return returnPeriodDays;
    }

    public void setInstitutionName(String institutionName) throws InvalidParameterException {
        if (institutionName == null || institutionName.isBlank()) {
            throw new InvalidParameterException("Institution name can't be blank.");
        }
        this.institutionName = institutionName;
    }

    public void setMaxBooksPerMember(int maxBooksPerMember) throws InvalidParameterException {
        if (maxBooksPerMember <= 0) {
            throw new InvalidParameterException("maxBooksPerMember must be greater than 0.");
        }
        this.maxBooksPerMember = maxBooksPerMember;
    }

    public void setReturnPeriodDays(int returnPeriodDays) throws InvalidParameterException {
        if (returnPeriodDays <= 0) {
            throw new InvalidParameterException("returnPeriodDays must be greater than 0.");
        }
        this.returnPeriodDays = returnPeriodDays;
    }

    
}
