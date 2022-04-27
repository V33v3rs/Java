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

	//Goal of the reducer is to iterate over the aggregation of file names. 
	//Each file name that comes through the mapper is equal to one unique                       
	//Word (so file1 sent 5 times means file1 had 5 unique words)
  public void reduce(Text key, Iterable<Text> values,
                     Context context
                     ) throws IOException, InterruptedException {
    
	  HashMap<Text, Integer> h = new HashMap<Text, Integer>();
    	
	  for (Text val : values) { //For each value (file name)
    		
    		Text f = new Text(val);
    		//If the current text file is not in the ds
    		if(h.containsKey(f) != true) {
    			h.put(f, 1); //Add it and intital int
    			
    		} else { //Otherwise increment the int
    	
    			h.replace(f, h.get(f)+1); 
    		}

    	}
    	
    	//System.out.println(h.toString());
    	int i = 0; //Counter
    	Text a = new Text(); //Variable for output
    	for(Text t: h.keySet()) { //For each key in hashmap
    		//System.out.println(h.get(t).toString() + t);
    //Filter for largest amount of unique words
    		if(h.get(t) > i) {
    			i = h.get(t);
    			a = t;
    			
    			
    		}
    		
    		
    	}
    	
    	Text b = new Text(Integer.toString(i));
    	context.write(a, b);
   
    
    
    
  }
}