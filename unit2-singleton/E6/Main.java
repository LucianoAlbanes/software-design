// ALBANES, Luciano Joaquín

public class Main {
    public static void main(String[] args) {
        ThemeManager manager = ThemeManager.getInstance();

        manager.setTheme(ThemeManager.Theme.DARK);
        System.out.println("Actual theme?: " + manager.getActualTheme());

        manager.setTheme(ThemeManager.Theme.LIGHT);
        System.out.println("Actual theme?: " + manager.getActualTheme());
        

        ThemeManager manager2 = ThemeManager.getInstance();
        System.out.println("Same instance?: " + (manager == manager2)); // true
    }
    
}

class ThemeManager {
    // Instance
    private static final ThemeManager INSTANCE = new ThemeManager();


    // Enum 
    public enum Theme {
        LIGHT,
        DARK
    }

    private Theme actualTheme = Theme.LIGHT;

    private ThemeManager() {}

    // Public
    public static ThemeManager getInstance() {
        return INSTANCE;
    }

    public synchronized Theme getActualTheme() {
        return actualTheme;
    }

    public synchronized void setTheme(Theme theme) {
        actualTheme = theme;
    }
}
