package e2;

public class StorageExample {
    public static void main(String[] args) {
        // The choice of the creator lives here, separated from the business logic
        FileManager localManager = new FileManager(new LocalStorageCreator());
        FileManager cloudManager = new FileManager(new CloudStorageCreator());

        // TEST
        localManager.backup("photo.jpg");
        cloudManager.backup("video.mp4");
    }
}


// Business logic: only knows the abstractions

class FileManager {
    private Storage storage;

    FileManager(StorageCreator creator) {
        this.storage = creator.createStorage();
    }

    void backup(String file) {
        storage.save(file);
        System.out.println(storage.retrieve(file));
    }
}

// Interface

interface Storage {
    void save(String file);
    String retrieve(String file);
}

class LocalStorage implements Storage {
    @Override
    public void save(String file) {
        System.out.println("Saved on local disk: " + file);
    }

    @Override
    public String retrieve(String file) {
        return "Retrieved from local disk: " + file;
    }
}

class CloudStorage implements Storage {
    @Override
    public void save(String file) {
        System.out.println("Uploaded to the cloud: " + file);
    }

    @Override
    public String retrieve(String file) {
        return "Downloaded from the cloud: " + file;
    }
}

// Creators

abstract class StorageCreator {
    public abstract Storage createStorage();
}

class LocalStorageCreator extends StorageCreator {
    @Override
    public Storage createStorage() {
        return new LocalStorage();
    }
}

class CloudStorageCreator extends StorageCreator {
    @Override
    public Storage createStorage() {
        return new CloudStorage();
    }
}
