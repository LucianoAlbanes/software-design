package e4;

public class FinancialReportExample {
    public static void main(String[] args) {
        // CREATORS
        ReportCreator pdfCreator = new PDFReportCreator();
        ReportCreator csvCreator = new CSVReportCreator();

        // Data: first row is the header
        String[][] data = {
            {"Concept", "Amount"},
            {"Sales", "15000"},
            {"Expenses", "6200"}
        };

        ReportCreator[] creators = { pdfCreator, csvCreator };

        // TEST (With for each, to test inheritance)
        for (ReportCreator creator : creators) {
            creator.generateReport(data);
        }
    }
}


// Interfaz
interface ExportReport {
    boolean validate(String[][] data);
    void getFile(String[][] data);
}

class PDFReport implements ExportReport {
    @Override
    public boolean validate(String[][] data) {
        // Needs the header and at least one row
        return data.length > 1;
    }

    @Override
    public void getFile(String[][] data) {
        System.out.println("Downloading as pdf...");
        for (String[] row : data) {
            System.out.println(String.join(" | ", row));
        }
    }
}

class CSVReport implements ExportReport {
    @Override
    public boolean validate(String[][] data) {
        // Every row needs the same columns as the header
        for (String[] row : data) {
            if (row.length != data[0].length) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void getFile(String[][] data) {
        System.out.println("Downloading as csv...");
        for (String[] row : data) {
            System.out.println(String.join(",", row));
        }
    }
}

// Creators

abstract class ReportCreator {
    abstract ExportReport createReport();

    // Template method: creates the report, validates and exports
    public void generateReport(String[][] data) {
        ExportReport report = createReport();
        if (report.validate(data)) {
            report.getFile(data);
        } else {
            System.out.println("Invalid data, can't export");
        }
    }
}

class PDFReportCreator extends ReportCreator {
    @Override
    ExportReport createReport() {
        return new PDFReport();
    }
}

class CSVReportCreator extends ReportCreator {
    @Override
    ExportReport createReport() {
        return new CSVReport();
    }
}
