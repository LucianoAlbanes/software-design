package e3;


public class NotificationExample {
    public static void main(String[] args) {
        // Instantiate Creators
        NotificationCreator smsCreator =     new SMSNotificationCreator();
        NotificationCreator emailCreator =   new EMailNotificationCreator();

        // Create notifications
        Notification smsNotif =      smsCreator.createNotification();
        Notification emailNotif =  emailCreator.createNotification();

        // TEST
        smsNotif.send("Hello from an SMS!");
        emailNotif.send("Hello Internet!");
    }
}



// Interface

interface Notification {
    void send(String txt);
}

class SMSNotification implements Notification {
    @Override
    public void send(String txt) {
        System.out.println("SMS Sent: " + txt);
    }
}

class EMailNotification implements Notification {
    @Override
    public void send(String txt) {
        System.out.println("Email Sent: " + txt);
    }
}

// Creators

abstract class NotificationCreator {
    public abstract Notification createNotification();
}

class SMSNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new SMSNotification();
    }
}

class EMailNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new EMailNotification();
    }
}