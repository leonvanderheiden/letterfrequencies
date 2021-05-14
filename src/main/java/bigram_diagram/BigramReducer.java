package bigram_diagram;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class BigramReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text Key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
    }
}
