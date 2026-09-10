// ALBANES, Luciano Joaquín
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // get the singleton instance two times
        ConfigurationParameters config1 = ConfigurationParameters.getInstance();
        ConfigurationParameters config2 = ConfigurationParameters.getInstance();

        // Compare if both references point to the same instance
        if (config1 == config2) {
            System.out.println("Both references point to the same instance.");
        } else {
            System.out.println("References point to different instances.");
        }

        // Obtain the parameters from the singleton instance
        System.out.println(config1.getParameter("hostname"));

    }
}

class ConfigurationParameters {
    // Here is the singleton instance
    private static ConfigurationParameters instance;

    // Here is the dictionary that will hold the parameters
    private final Map<String, String> parameters;


    // Constructor. Parameters are loaded.
    private ConfigurationParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    // Method to get the singleton instance
    // This is the unique (public) way to get an instance of this class.
    public static ConfigurationParameters getInstance() {
        if(instance == null) {

            instance = new ConfigurationParameters(loadParameters());
        }
        return instance;
    }

    // Method to load parameters from a file (simulated here)
    private static HashMap<String, String> loadParameters() {
        HashMap<String, String> params = new HashMap<>();

        // Simulate loading parameters from a file
        params.put("version", "1.00.01");
        params.put("hostname", "artemis2");
        params.put("port", "8080");


        return params;
    }
    

    // Public method to get parameters
    public String getParameter(String key) {
        return parameters.get(key);
    }
}
