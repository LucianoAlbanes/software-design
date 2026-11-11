package e8;

public class ImportExamples {
    public static void main(String[] args) {
        // Creators (el main es el unico que conoce las clases concretas)
        DataImporterCreator csvCreator = new CSVDataImporterCreator();
        DataImporterCreator xmlCreator = new XMLDataImporterCreator();
        DataImporterCreator jsonCreator = new JSONDataImporterCreator();

        // test
        DataImporterCreator[] creators = {csvCreator, xmlCreator, jsonCreator};

        for (DataImporterCreator creator : creators) {
            ImportService service = new ImportService(creator);
            service.process("Importandome");
        }
    }
}


// Consumidor: recibe el creator por constructor y solo trabaja con las abstracciones,
// no sabe si abajo hay CSV, XML o JSON

class ImportService {
    private final DataImporterCreator creator;

    public ImportService(DataImporterCreator creator) {
        this.creator = creator;
    }

    public void process(String source) {
        DataImporter importer = creator.createDataImporter();
        importer.importData(source);
    }
}




// Producto: interfaz e importadores concretos CSV, XML, JSON

interface DataImporter {
    void importData(String source);
}

class CSVDataImporter implements DataImporter {
    @Override
    public void importData(String source) {
        System.out.println("Imported CSV " + source);
    }
}

class XMLDataImporter implements DataImporter {
    @Override
    public void importData(String source) {
        System.out.println("Imported XML " + source);
    }
}

class JSONDataImporter implements DataImporter {
    @Override
    public void importData(String source) {
        System.out.println("Imported JSON " + source);
    }
}


// Creators

abstract class DataImporterCreator {
    public abstract DataImporter createDataImporter();
}

class CSVDataImporterCreator extends DataImporterCreator {
    @Override
    public DataImporter createDataImporter() {
        return new CSVDataImporter();
    }
}

class XMLDataImporterCreator extends DataImporterCreator {
    @Override
    public DataImporter createDataImporter() {
        return new XMLDataImporter();
    }
}

class JSONDataImporterCreator extends DataImporterCreator {
    @Override
    public DataImporter createDataImporter() {
        return new JSONDataImporter();
    }
}
