import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PartTwoMapper
     extends Mapper<Object, Text, Text, Text>{

  private Text file = new Text();
  private Text word = new Text();
  public Text t = new Text("Key");
  public void map(Object key, Text value, Context context
                  ) throws IOException, InterruptedException {
	 HashMap<Text, Text> m = new HashMap<Text,Text>();
	 StringTokenizer itr = new StringTokenizer(value.toString());
	 FileSplit fileSplit = (FileSplit)context.getInputSplit();
	
	 
	 String filename = fileSplit.getPath().getName();
	 file.set(filename);
	 while (itr.hasMoreTokens()) {
		 word.set(itr.nextToken());
		 System.out.println(word + " Ouput Mapper");
		 if(m.containsKey(word) != true) {
			 m.put(word, word);
			 System.out.println(word + "Has gone through Mapper");
			 
			 context.write(t, file);
		 }
    }
  }
}