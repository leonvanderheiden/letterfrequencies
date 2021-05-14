import bigram_diagram.BigramMapper;
import bigram_diagram.BigramReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LetterFrequenties {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Bigram");

        job.setJarByClass(LetterFrequenties.class);
        job.setMapperClass(BigramMapper.class);
        job.setReducerClass(BigramReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path("Bigram_output"));

        job.setInputFormatClass(TextInputFormat.class);
        job.waitForCompletion(true);
    }
}

