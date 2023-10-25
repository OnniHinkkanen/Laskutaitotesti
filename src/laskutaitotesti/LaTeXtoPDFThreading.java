import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class LaTeXtoPDFThreading {

	public static void main(String[] args) {
		// This code example demonstrates how to create a PDF from TeX source file.
		// Working directory
		Path currentRelativePath = Paths.get("");
		String dataFolder = currentRelativePath.toAbsolutePath().toString() + "\\testit\\";
		
		String texex = "laskutaitotesti.*\\.tex";
		List<Thread> threads = new ArrayList<Thread>();
		//List<Process> procs = new ArrayList<Process>();
		var start = LocalTime.now();
		File dir = new File(dataFolder);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
				Matcher matcher = Pattern.compile(texex).matcher(child.getAbsolutePath());
				if (matcher.find()) {
					String command = "powershell.exe -Command \"& {"+ "pdflatex " + matcher.group()
							+ " --include-directory=" + dataFolder  
							+ " --output-directory=" + dataFolder + "pdf\\"
							+ " --aux-directory="+ dataFolder + "auxfiles\\}\"" ;
					
					var runnable = new PDFThread(command, matcher.group());
					Thread thread = new Thread(runnable);
					threads.add(thread);
					thread.start();
					//BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cmd.getOutputStream()));
					//BufferedReader in = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
					//out.write(command);
					//out.flush();
					//var fut = cmd.onExit();
					//while(!fut.isDone()) {Thread.sleep(200); System.out.println("kek");}
				}
		      // Do something with child
		    }
		  } else {
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		var end = LocalTime.now();
		System.out.println(start + "|" + end);
		

	}

	static class PDFThread implements Runnable {
		   private Thread t;
		   private String threadName;
		   private String command;
		   
		   PDFThread(String command, String threadName) {
			  this.command = command;
		      this.threadName = threadName;
		      System.out.println("Creating " +  threadName );
		   }
		   
		   public void run() {
		      System.out.println("Running " +  threadName );
		      ProcessBuilder builder = new ProcessBuilder(
		                "cmd.exe","/c", command);
	        	builder.redirectErrorStream(true);
	        	try {
					Process cmd = builder.start();
					var fut = cmd.onExit();
					while(!fut.isDone()) {
						Thread.sleep(200);
					}
				
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
		      System.out.println("Thread " +  threadName + " exiting.");
		   }
		   
		   public void start () {
		      System.out.println("Starting " +  threadName );
		      if (t == null) {
		         t = new Thread (this, threadName);
		         t.start ();
		      }
		   }
		}
}
