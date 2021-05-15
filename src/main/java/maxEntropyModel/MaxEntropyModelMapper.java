package maxEntropyModel;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaxEntropyModelMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    public void map(LongWritable Key, Text value, Context context) throws IOException, InterruptedException {
        Map<String, Double> scoreMap = new HashMap<>();
        Map<String, Double> engelsMap = new HashMap<>();
        Map<String, Double> nederlandsMap = new HashMap<>();

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


    }
}
