package sk.stuba.fiit.ms.semantic.lda;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import sk.stuba.fiit.ms.session.Search;

// http://mallet.cs.umass.edu/topics-devel.php
public final class LDAModel {

    private final ParallelTopicModel model;

    private final InstanceList instances;

    private final SearchFormatter formatter;

    private LDAModel(final SearchFormatter formatter, final InstanceList instances, final ParallelTopicModel model) {
        this.formatter = formatter;
        this.instances = instances;
        this.model = model;
    }

    public int getNumTopics() {
        return model.getNumTopics();
    }

    /**
     * Learn hidden topics from given file.
     * @param filename file that contains docs
     * @param formatter formatter of search into doc
     * @param topics number of hidden topics
     * @param iterations number of iterations of LDA
     * @return learned LDA model
     */
    public static LDAModel estimate(final String filename,
                                    final SearchFormatter formatter,
                                    final int topics,
                                    final int iterations) {
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

            model.setNumIterations(iterations);

            model.estimate();

            return new LDAModel(formatter, instances, model);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inferences topics for given search.
     * @param search search to inference topics from
     * @return array of topics probabilities
     */
    public double[] inference(final Search search) {
        InstanceList test = new InstanceList(instances.getPipe());

        test.addThruPipe(new Instance(formatter.searchToText(search), null, search.getId(), null));

        TopicInferencer inferencer = model.getInferencer();

        double[] probabilities = inferencer.getSampledDistribution(test.get(0), 10, 1, 5);

        return probabilities;
    }

}
