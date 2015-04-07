package sk.stuba.fiit.ms.semantic.lda;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import sk.stuba.fiit.ms.session.Result;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public final class LDAModel {

    private final ParallelTopicModel model;

    private final InstanceList instances;

    private final ResultTextFormatter formatter;

    private final Map<Integer, double[]> topicPerDoc;

    private LDAModel(final ResultTextFormatter formatter,
            final InstanceList instances, final ParallelTopicModel model) {
        this.formatter = formatter;
        this.instances = instances;
        this.model = model;
        this.topicPerDoc = new HashMap<Integer, double[]>();

        this.saveTopicPerDoc(instances, model);
    }

    private final void saveTopicPerDoc(final InstanceList instances, final ParallelTopicModel model) {
        TopicInferencer inferencer = model.getInferencer();

        for (int i = 0; i < instances.size(); i++) {
            Instance inst = instances.get(i);

            double[] probabilities = inferencer.getSampledDistribution(inst, 10, 1, 5);

            Integer id = Integer.valueOf((String) inst.getName());

            topicPerDoc.put(id, probabilities);
        }
    }

    public boolean containsTopics(final int resultId) {
        return topicPerDoc.containsKey(resultId);
    }

    public double[] getTopics(final int resultId) {
        return topicPerDoc.get(resultId);
    }

    public int getNumTopics() {
        return model.getNumTopics();
    }

    public static LDAModel estimate(final String filename, final ResultTextFormatter formatter, final int topics) {
        try {
            List<Pipe> pipeList = new ArrayList<Pipe>();

            pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
            pipeList.add(new TokenSequence2FeatureSequence());

            InstanceList instances = new InstanceList(new SerialPipes(pipeList));

            Reader fileReader = new InputStreamReader(new FileInputStream(filename), "UTF-8");

            instances.addThruPipe(new CsvIterator(fileReader,
                    Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"), 3, 2, 1));

            ParallelTopicModel model = new ParallelTopicModel(topics, 1.0, 0.01);

            model.addInstances(instances);

            model.setNumThreads(2);

            model.setNumIterations(300);

            model.estimate();

            return new LDAModel(formatter, instances, model);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double[] inference(final Result res) {
        InstanceList test = new InstanceList(instances.getPipe());

        test.addThruPipe(new Instance(formatter.formatText(res), null, res.getId(), null));

        TopicInferencer inferencer = model.getInferencer();

        double[] probabilities = inferencer.getSampledDistribution(test.get(0), 10, 1, 5);

        return probabilities;
    }

}
