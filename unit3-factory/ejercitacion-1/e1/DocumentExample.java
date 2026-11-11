package e1;

public class DocumentExample {
    public static void main(String[] args) {
        DocumentCreator plainTextDocCreator = new PlainTextDocumentCreator();
        DocumentCreator richTextDocCreator = new RichTextDocumentCreator();

        System.out.println(plainTextDocCreator.createDocument().readDocument());
        System.out.println(richTextDocCreator.createDocument().readDocument());
    }
}

// Document Classes and interface.

interface Document {
    String readDocument();
}

class PlainTextDocument implements Document {
    @Override
    public String readDocument() {
        return "I'm a Plain Text Document!";
    }
}

class RichTextDocument implements Document {
    @Override
    public String readDocument() {
        return "I'm a Rich Text Document!";
    }
}


// Abstract Document Creator and concrete Creator

abstract class DocumentCreator {
    public abstract Document createDocument();
}

class PlainTextDocumentCreator extends DocumentCreator {
    @Override
    public PlainTextDocument createDocument() {
        return new PlainTextDocument();
    }
}

class RichTextDocumentCreator extends DocumentCreator {
    @Override
    public RichTextDocument createDocument() {
        return new RichTextDocument();
    }
}