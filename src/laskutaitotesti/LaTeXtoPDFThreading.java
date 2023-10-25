import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class LaTeXtoPDFThreading {

	public static void main(String[] args) {
		Path currentRelativePath = Paths.get("");
		String dataFolder = currentRelativePath.toAbsolutePath().toString() + "\\testit\\";
		
		String texex = "laskutaitotesti.*\\.tex";
		List<Thread> threads = new ArrayList<Thread>();
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
				}
		    }
		  } else {

		  }
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
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
