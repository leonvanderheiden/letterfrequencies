package score;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ScoreMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    static Map<String, Double> bigramMap = new HashMap<>();
    static Map<String, Double> letterFrequencyMap = new HashMap<>();

    public void map(LongWritable Key, Text value, Context context) throws IOException, InterruptedException {
        String[] lines = value.toString().split("\n");

        for (String line : lines)
        {
            String[] split = line.split("\\s+");
            if (split[0].length() == 1) {
                letterFrequencyMap.put(split[0], Double.valueOf(split[1]));
            }
            else {
                bigramMap.put(split[0], Double.valueOf(split[1]));
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");

        for (Map.Entry<String, Double> bigram : bigramMap.entrySet()) {
            for (Map.Entry<String, Double> totalLetters : letterFrequencyMap.entrySet()) {
                if (bigram.getKey().charAt(0) == totalLetters.getKey().charAt(0)) {
                    double perc = bigram.getValue() / totalLetters.getValue();
                    String round = df.format(perc).replace(',', '.');
                    context.write(new Text(bigram.getKey()), new DoubleWritable(Double.valueOf(round)));
                }
            }
        }
    }
}
