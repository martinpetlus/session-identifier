package sk.stuba.fiit.ms.apps;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public final class AppConfig {

    private final JSONObject jsonConfig;

    private AppConfig(final JSONObject jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    public static AppConfig load(final String configFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object json = parser.parse(new FileReader(configFile));

        JSONObject jsonConfig = (JSONObject) json;

        return new AppConfig(jsonConfig);
    }

}
