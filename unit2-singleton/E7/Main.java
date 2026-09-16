import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Printer printer = Printer.getInstance();

        PrintingDocument doc = new PrintingDocument("Hello World!", "Luciano");
        printer.addDocumentToQueue(doc);

        PrintingDocument doc2 = new PrintingDocument("Hola Mundo!", "Lucas");
        printer.addDocumentToQueue(doc2);

        PrintingDocument doc3 = new PrintingDocument("Kon'nichiwa sekai!", "Tomohiko Itō.");
        printer.addDocumentToQueue(doc3);

        printer.printAll();
    }

}

class Printer {
    // Intance
    private static final Printer INSTANCE = new Printer();

    // Resource managed. The queue
    private final Queue<PrintingDocument> printingQueue = new LinkedList<>(); 

    // Constructor
    private Printer() {}


    // Public Static Methods
    public static Printer getInstance() {
        return INSTANCE;
    }

    // Public Methods
    public synchronized void addDocumentToQueue(PrintingDocument doc) {
        printingQueue.add(doc);
    }

    // Print 
    public synchronized void printAll() {
        PrintingDocument doc;
        while ((doc = printingQueue.poll()) != null) {
            // Here should print the doc
            System.out.println("Printed: \n \t" + doc);
        }
    }

}

class PrintingDocument {
    public final String doc;
    public final String author;

    public PrintingDocument(String doc, String author) {
        this.doc = doc;
        this.author = author;
    }

    @Override
    public String toString() {
        return ("[" + author + "]:" + doc);
    }
}