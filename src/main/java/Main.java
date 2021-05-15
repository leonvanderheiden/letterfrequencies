import bigram.BigramMapper;
import bigram.BigramReducer;
import firstletter.FirstLetterMapper;
import firstletter.FirstLetterReducer;
import maxEntropyModel.MaxEntropyModelMapper;
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
import score.ScoreMapper;
import score.ScoreReducer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
//        bigramJob(args);
//        firstLetterJob();
//        scoreJob();
        maxEntropyModelJob();
    }

    public static void bigramJob(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Bigram");

        job.setJarByClass(Main.class);
        job.setMapperClass(BigramMapper.class);
        job.setReducerClass(BigramReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path("output/bigram_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }

    public static void firstLetterJob() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Firstletters");

        job.setJarByClass(Main.class);
        job.setMapperClass(FirstLetterMapper.class);
        job.setReducerClass(FirstLetterReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("output/bigram_output"));
        FileOutputFormat.setOutputPath(job, new Path("output/firstletter_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }

    public static void scoreJob() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Score");

        job.setJarByClass(Main.class);
        job.setMapperClass(ScoreMapper.class);
        job.setReducerClass(ScoreReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        SequenceFileInputFormat.setInputPaths(job, new Path("output/bigram_output"), new Path("output/firstletter_output"));
        FileOutputFormat.setOutputPath(job, new Path("output/score_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }

    public static void maxEntropyModelJob() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "MaxEntropyModel");

        job.setJarByClass(Main.class);
        job.setMapperClass(MaxEntropyModelMapper.class);
        //job.setReducerClass(ScoreReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        SequenceFileInputFormat.setInputPaths(job, new Path("models/engels"), new Path("models/nederlands"), new Path("output/score_output"));
        FileOutputFormat.setOutputPath(job, new Path("output/maxentropymodel_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }
}



