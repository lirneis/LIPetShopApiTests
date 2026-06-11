package core.clients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiClient {
    private final String baseUrl;

    public ApiClient() {
        this.baseUrl = determineBaseUrl();
    }

    private String determineBaseUrl() {
        String environment = System.getProperty("any", "test");
        String configProfileFile = "application+" + environment + ".properties";

        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configProfileFile)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found" + configProfileFile);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration file" + configProfileFile, e);
        }
        return properties.getProperty("baseUrl");
    }
}
