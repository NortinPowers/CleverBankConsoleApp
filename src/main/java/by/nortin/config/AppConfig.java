package by.nortin.config;

import java.io.InputStream;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

@Log4j2
public class AppConfig {

    private static final String CONFIG_FILE = "application.yaml";

    private Map<String, Map<String, Object>> config;

    public AppConfig() {
        loadConfig();
    }

    /**
     * The method reads the yaml file in config Map.
     */
    private void loadConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            Yaml yaml = new Yaml();
            config = yaml.load(inputStream);
        } catch (Exception e) {
            log.error("Exception (loadConfig())", e);
        }
    }

    /**
     * The method returns a value by its key.
     *
     * @param key - the key of the read value
     * @return Map of String - Object
     */
    public Map<String, Object> getProperty(String key) {
        return config.get(key);
    }
}
