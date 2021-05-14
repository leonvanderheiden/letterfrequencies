import bigram_diagram.BigramMapper;
import bigram_diagram.BigramReducer;
import firstletter.FirstLetterMapper;
import firstletter.FirstLetterReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import percentageBigram.PercentageBigramMapper;
import percentageBigram.PercentageBigramReducer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Bigram");

        job.setJarByClass(Main.class);
        job.setMapperClass(BigramMapper.class);
        job.setReducerClass(BigramReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path("Bigram_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
        firstLetterJob();
        percentageBigramJob();

    }

    public static void firstLetterJob() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Firstletters");

        job.setJarByClass(Main.class);
        job.setMapperClass(FirstLetterMapper.class);
        job.setReducerClass(FirstLetterReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("Bigram_output"));
        FileOutputFormat.setOutputPath(job, new Path("FirstLetter_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }

    public static void percentageBigramJob() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "PercentageBigram");

        job.setJarByClass(Main.class);
        job.setMapperClass(PercentageBigramMapper.class);
        job.setReducerClass(PercentageBigramReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        SequenceFileInputFormat.setInputPaths(job, new Path("Bigram_output"), new Path("Firstletter_output"));
        FileOutputFormat.setOutputPath(job, new Path("PercentageBigram_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }
}



