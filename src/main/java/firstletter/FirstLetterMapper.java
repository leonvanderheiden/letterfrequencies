package firstletter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FirstLetterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] lines = value.toString().split("\n");

        for (String line : lines)
        {
            int totalTimes = Integer.parseInt(line.split("\\s+")[1]);
            context.write(new Text(String.valueOf(line.charAt(0))), new IntWritable(totalTimes));
        }
    }
}
