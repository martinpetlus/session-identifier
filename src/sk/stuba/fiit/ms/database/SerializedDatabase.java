package sk.stuba.fiit.ms.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.stuba.fiit.ms.downloading.ResultContent;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.utils.Logger;

public final class SerializedDatabase implements Database {

    private static SerializedDatabase instance;

    private static final String FILE = "docs.ser";

    private final Map<String, ResultContent> resultsContent;

    @SuppressWarnings("unchecked")
    private SerializedDatabase() {
        Map<String, ResultContent> resultsContent = null;

        try {
            ObjectInput input = new ObjectInputStream(new FileInputStream(FILE));

            resultsContent = (Map<String, ResultContent>) input.readObject();

            Logger.log("Number of loaded results contents: " + resultsContent.size());

            input.close();
        } catch (Exception ex) {
            Logger.err("Cannot load: " + FILE);
        }

        if (resultsContent != null && !resultsContent.isEmpty()) {
            this.resultsContent = resultsContent;
        } else {
            this.resultsContent = new HashMap<String, ResultContent>();
        }
    }

    @Override
    public boolean containsContent(final String url) {
        return resultsContent.containsKey(url);
    }

    @Override
    public ResultContent getContent(final String url) {
        return resultsContent.get(url);
    }

    @Override
    public void save(final ResultContent doc) {
        resultsContent.put(doc.getUrl(), doc);
    }

    @Override
    public int size() {
        return resultsContent.size();
    }

    @Override
    public void close() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));

            oos.writeObject(resultsContent);

            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ResultContent> getAllContents() {
        return new ArrayList<ResultContent>(resultsContent.values());
    }

    @Override
    public void setResultsContent(final List<Session> sessions) {
        for (Session session : sessions) {
            for (Result result : session.getAllResults()) {
                String url = result.getUrl();

                if (this.containsContent(url)) {
                    result.setContent(this.getContent(url).getContent());
                }
            }
        }
    }

    public synchronized static SerializedDatabase getInstance() {
        if (instance == null) {
            instance = new SerializedDatabase();
        }

        return instance;
    }

}
