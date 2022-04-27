import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;

public class PartTwoReducer
     extends Reducer<Text,Text,Text,Text> {


  public void reduce(Text key, Iterable<Text> values,
                     Context context
                     ) throws IOException, InterruptedException {
    
	  HashMap<Text, Integer> h = new HashMap<Text, Integer>();
    	for (Text val : values) {
    		System.out.println("Reducer 1: " + val);
    		
    		if(h.containsKey(val) != true) {
    			h.put(val, 1);
    			System.out.println("Reducer 2: " + val);
    		} else {
    			System.out.println("Reducer 3: " + val + " " + h.get(val)+1);
    			h.put(val, h.get(val)+1); 
    		}
    		
    		//context.write(key,  val);
    	}
    	
    	int i = 0;
    	Text a = new Text();
    	for(Text t: h.keySet()) {
    		System.out.println(h.get(t).toString() + t);
    		
    		if(h.get(t) > i) {
    			i = h.get(t);
    			a = t;
    			
    			
    		}
    		
    		
    	}
    	
    	Text b = new Text(Integer.toString(i));
    	context.write(a, b);
   
    
    
    
  }
}