package e8;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

/*
 * BEFORE (legacy, coupled):
 *
 * class Task {
 *     private EmailService email = new EmailService();
 *     private StatsService stats = new StatsService();
 *
 *     public void setStatus(TaskStatus status) {
 *         this.status = status;
 *         email.sendStatusMail(title, status);   // direct call
 *         stats.registerChange(status);          // direct call
 *     }
 * }
 *
 * AFTER: Task only changes its status and notifies the listeners.
 */
public class TaskRefactorExample {
    public static void main(String[] args) {
        // Create publisher
        Task task = new Task("Write observer TP");

        // Task doesn't know these classes, we subscribe them here
        EmailNotifier sub1 = new EmailNotifier();
        StatsModule sub2   = new StatsModule();

        task.subscribe(sub1);
        task.subscribe(sub2);

        System.out.println("Moving task to IN_PROGRESS...");
        task.changeStatus(TaskStatus.IN_PROGRESS);

        System.out.println("\nMoving task to DONE...");
        task.changeStatus(TaskStatus.DONE);

        System.out.println("\nStats: " + sub2.getChanges() + " changes, " + sub2.getDone() + " done");
    }
}

enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE
}

// Subject (Publisher)
class Task {
    private final String title;
    private TaskStatus status = TaskStatus.TODO;

    // No EmailService or StatsService here anymore
    private final Set<TaskListener> listeners = new LinkedHashSet<>();

    public Task(String title) {
        this.title = title;
    }

    public void changeStatus(TaskStatus newStatus) {
        TaskStatus oldStatus = status;
        status = newStatus;
        notifyListeners(oldStatus);
    }

    // Publisher logic/methods
    private void notifyListeners(TaskStatus oldStatus) {
        for (TaskListener listener : new ArrayList<>(listeners)) { // Safe for unsubscribe()
            try {
                listener.onStatusChanged(title, oldStatus, status);
            } catch (Exception e) {
                System.err.println("Failed to update: " + listener.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(TaskListener o)   {listeners.add(o);}
    public void unsubscribe(TaskListener o) {listeners.remove(o);}
}


// Contract
interface TaskListener {
    void onStatusChanged(String title, TaskStatus oldStatus, TaskStatus newStatus);
}

// Observer (Subscriber)

class EmailNotifier implements TaskListener {
    @Override
    public void onStatusChanged(String title, TaskStatus oldStatus, TaskStatus newStatus) {
        System.out.println("[EmailNotifier] Mail sent: '" + title + "' " + oldStatus + " -> " + newStatus);
    }
}

class StatsModule implements TaskListener {
    private int changes = 0;
    private int done = 0;

    public int getChanges() {
        return changes;
    }

    public int getDone() {
        return done;
    }

    @Override
    public void onStatusChanged(String title, TaskStatus oldStatus, TaskStatus newStatus) {
        changes++;
        if (newStatus == TaskStatus.DONE) {
            done++;
        }
        System.out.println("[StatsModule] Change registered (" + changes + " total)");
    }
}
