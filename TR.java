import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class TR {
    public int counter;
    public int T = 0;
    public int Token_count=20;
    public String logfile = "increment.txt";
    public List<PT> runs;
    class PT extends Thread {

    private int pid;
    private String logfile;
    private final TR tr;

    public PT(int pid, String logfile, TR tr){
        this.pid = pid;
        this.logfile = logfile;
        this.tr = tr;
    }
    
    
    public void run(){
        try{
            while (tr.T!= Token_count) {
                if (tr.counter == pid) {
                    BufferedReader input = null;
                    PrintWriter output = null;
                    input = new BufferedReader(new FileReader(logfile));
                    String line = input.readLine();
                    int value = Integer.parseInt(line.trim());
                    output = new PrintWriter(logfile);
			        output.print(String.valueOf(value+1));
			        input.close();
      		        output.close();
                    tr.nextC();
                    //System.out.println("run P" + pid + " accessed file. item : " + (value + 1) + " time: " + tr.T);
                    System.out.println("Token is currently passed to: P" + pid  );
                    System.out.println("Incrementing the file counter:" + (value + 1));
                }else{Thread.yield();}
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


  	
  	private void createNewFile(){
      	PrintWriter output = null;
        try{
          	output = new PrintWriter(logfile);
			output.print("1");
      		output.close();
        }catch (Exception e){
			e.getStackTrace();
        }
    }
 
    public void nextC(){
      	counter = counter < 5 ? counter+1 : 1;
        T++;
    }
  
    public void start(){
        createNewFile();
        runs = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            runs.add(new PT(i + 1, logfile, this));
        }
      	counter = 1;
      	runs.stream().forEach(p -> p.start());
    }
  	
  	public static void main(String[] args) {
        TR token = new TR();
        token.start();
    }
}
