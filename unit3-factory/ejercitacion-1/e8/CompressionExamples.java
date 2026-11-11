package e8;

public class CompressionExamples {
    public static void main(String[] args) {
        // Creators (el main es el unico que conoce las clases concretas)
        FileCompressorCreator zipCreator = new ZIPFileCompressorCreator();
        FileCompressorCreator tarCreator = new TARFileCompressorCreator();
        FileCompressorCreator gzCreator = new GZFileCompressorCreator();

        // test
        FileCompressorCreator[] creators = {zipCreator, tarCreator, gzCreator};
        
        for (FileCompressorCreator creator : creators) {
            CompressionService service = new CompressionService(creator);
            service.process("Comprimiendome", "Expandiendome");
        }
    }
}


// Consumidor: recibe el creator por constructor y solo trabaja con las abstracciones,
// no sabe si abajo hay ZIP, TAR o GZ

class CompressionService {
    private final FileCompressorCreator creator;

    public CompressionService(FileCompressorCreator creator) {
        this.creator = creator;
    }

    public void process(String toCompress, String toExpand) {
        FileCompressor compressor = creator.createFileCompressor();
        compressor.compress(toCompress);
        compressor.expand(toExpand);
    }
}




// Producto: interfaz y compresores concretos ZIP, TAR, GZ

interface FileCompressor {
    void compress(String file);
    void expand(String file);
}

class ZIPFileCompressor implements FileCompressor {
    @Override
    public void compress(String file) {
        System.out.println("Compressed ZIP " + file);
    }

    @Override
    public void expand(String file) {
        System.out.println("Expanded ZIP " + file);
    }
}

class TARFileCompressor implements FileCompressor {
    @Override
    public void compress(String file) {
        System.out.println("Compressed TAR " + file);
    }

    @Override
    public void expand(String file) {
        System.out.println("Expanded TAR " + file);
    }
}

class GZFileCompressor implements FileCompressor {
    @Override
    public void compress(String file) {
        System.out.println("Compressed GZ " + file);
    }

    @Override
    public void expand(String file) {
        System.out.println("Expanded GZ " + file);
    }
}


// Creators

abstract class FileCompressorCreator {
    public abstract FileCompressor createFileCompressor();
}

class ZIPFileCompressorCreator extends FileCompressorCreator {
    @Override
    public FileCompressor createFileCompressor() {
        return new ZIPFileCompressor();
    }
}

class TARFileCompressorCreator extends FileCompressorCreator {
    @Override
    public FileCompressor createFileCompressor() {
        return new TARFileCompressor();
    }
}

class GZFileCompressorCreator extends FileCompressorCreator {
    @Override
    public FileCompressor createFileCompressor() {
        return new GZFileCompressor();
    }
}