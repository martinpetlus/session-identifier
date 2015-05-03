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

    public boolean useSessionTrack2011() {
        return ((Boolean) jsonConfig.get("session_track_2011")).booleanValue();
    }

    public boolean useSessionTrack2012() {
        return ((Boolean) jsonConfig.get("session_track_2012")).booleanValue();
    }

    public boolean useSessionTrack2013() {
        return ((Boolean) jsonConfig.get("session_track_2013")).booleanValue();
    }

    public boolean useSessionTrack2014() {
        return ((Boolean) jsonConfig.get("session_track_2014")).booleanValue();
    }

    public boolean useSokeSessions() {
        return ((Boolean) jsonConfig.get("soke_sessions")).booleanValue();
    }

    public boolean downloadContentsOfClickedResults() {
        return ((Boolean) jsonConfig.get("download_contents_of_clicked_results")).booleanValue();
    }

    public double getRatioBetweenTrainingAndTestingSessions() {
        return ((Double) jsonConfig.get("ratio_between_training_and_testing_sessions")).doubleValue();
    }

    public static AppConfig load(final String configFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object json = parser.parse(new FileReader(configFile));

        JSONObject jsonConfig = (JSONObject) json;

        return new AppConfig(jsonConfig);
    }

}
