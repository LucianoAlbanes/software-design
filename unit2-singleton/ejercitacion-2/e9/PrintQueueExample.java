package e9;

// ALBANES, Luciano Joaquín

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class PrintQueue {
    // Safe creation: the JVM initializes Holder only once, even with many threads.
    private static class Holder {
        private static final PrintQueue INSTANCE = new PrintQueue();
    }

    // Safe creation doesn't protect the state: these need synchronized methods.
    private final Queue<Document> queue = new LinkedList<>();
    private final Set<String> ids = new LinkedHashSet<>();
    private final List<Document> processed = new ArrayList<>();

    private PrintQueue() {}

    public static record Document(String id, String name, String sector, int pages) {
        @Override
        public String toString() {
            return id + " " + name + " [" + sector + "] " + pages + "p";
        }
    }

    // Public Methods
    public static PrintQueue getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void enqueue(Document doc) {
        if (!ids.add(doc.id())) {
            throw new IllegalArgumentException("Duplicated document id: " + doc.id());
        }
        queue.add(doc);
    }

    // FIFO: poll() takes the oldest document.
    public synchronized void processAll() {
        Document doc;
        while ((doc = queue.poll()) != null) {
            System.out.println("Printing: " + doc);
            processed.add(doc);
        }
    }

    public synchronized String report() {
        int pages = 0;
        for (Document doc : processed) {
            pages += doc.pages();
        }
        return "Processed: " + processed.size() + " documents, " + pages + " pages. Pending: " + queue.size();
    }

    public synchronized List<Document> getProcessed() {
        return List.copyOf(processed);
    }
}

public class PrintQueueExample {
    public static void main(String[] args) throws InterruptedException {
        String[] sectors = {"Recepcion", "Facturacion", "Archivo"};
        Thread[] workers = new Thread[10];
        PrintQueue[] seen = new PrintQueue[workers.length];

        // 10 threads x 3 documents = 30
        for (int t = 0; t < workers.length; t++) {
            int index = t;
            workers[t] = new Thread(() -> {
                seen[index] = PrintQueue.getInstance();
                for (int d = 0; d < 3; d++) {
                    String id = "DOC-" + (index * 3 + d + 1);
                    seen[index].enqueue(new PrintQueue.Document(id, "Form " + id,
                            sectors[index % sectors.length], 1 + d));
                }
            });
            workers[t].start();
        }
        for (Thread worker : workers) {
            worker.join();
        }

        PrintQueue printer = PrintQueue.getInstance();
        boolean sameInstance = true;
        for (PrintQueue instance : seen) {
            if (instance != printer) sameInstance = false;
        }
        System.out.println("Same instance in all threads? " + sameInstance);

        printer.processAll();
        System.out.println(printer.report());

        // Each document processed exactly once
        Set<String> uniqueIds = new LinkedHashSet<>();
        for (PrintQueue.Document doc : printer.getProcessed()) {
            uniqueIds.add(doc.id());
        }
        System.out.println("Unique processed ids: " + uniqueIds.size());

        // Processing again prints nothing
        printer.processAll();
        System.out.println(printer.report());
    }
}
