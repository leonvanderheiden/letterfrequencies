package maxEntropyModel;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.thirdparty.protobuf.MapEntry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaxEntropyModelMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    static Map<String, Double> scoreMap = new HashMap<>();
    static Map<String, Double> engelsMap = new HashMap<>();
    static Map<String, Double> nederlandsMap = new HashMap<>();

    public void map(LongWritable Key, Text value, Context context) throws IOException, InterruptedException {
        float engelsScore = 0;
        float nederlandsScore = 0;

        String[] lines = value.toString().split("\n");
        for (String line : lines) {
            double score = Double.valueOf(line.split("\\s+")[1]);
            String bigram = line.split("\\s+")[0];
            if (context.getInputSplit().toString().contains("score_output")) {
                scoreMap.put(bigram, score);
            }
            else if (context.getInputSplit().toString().contains("engels")) {
                engelsMap.put(bigram, score);
            }
            else if (context.getInputSplit().toString().contains("nederlands")) {
                nederlandsMap.put(bigram, score);
            }
        }
        for (Map.Entry<String, Double> score : scoreMap.entrySet()) {
            for (Map.Entry<String, Double> engels : engelsMap.entrySet()) {
                if (score.getKey().equals(engels.getKey())) {
                    engelsScore += score.getValue() * engels.getValue();
                }
            }
            for (Map.Entry<String, Double> nederlands : nederlandsMap.entrySet()) {
                if (score.getKey().equals(nederlands.getKey())) {
                    nederlandsScore += score.getValue() * nederlands.getValue();
                }
            }
        }


    }
}
