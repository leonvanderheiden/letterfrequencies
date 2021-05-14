package bigram_diagram;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class BigramMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    public void map(LongWritable Key, Text value, Context context) throws IOException, InterruptedException {

        String[] tokens = value.toString().split("[^a-zA-Z]");

        for (String s : tokens)
        {
            for(int i=0; i<(s.toCharArray().length - 1); i++)
            {
                String bigram = s.toCharArray()[i] + "" + s.toCharArray()[(i + 1)];
                context.write(new Text(bigram), new IntWritable(1));
            }
        }
    }
}
