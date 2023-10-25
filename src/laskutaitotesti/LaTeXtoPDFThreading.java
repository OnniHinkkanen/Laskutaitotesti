import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.regex.Pattern;

import me.tongfei.progressbar.ProgressBar;

import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;


 
/**
 * A class for converting the LaTeX files to PDF. The progress bar is implemented with an external Java class
 * ctongfei.progressbar, which is published uncer the MIT license stated below.
 * 
 *   The MIT License (MIT)
 *Copyright (c) 2015--2020 Tongfei Chen and contributors
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 */
public class LaTeXtoPDFThreading {

	public static void main(String[] args) {
		Path currentRelativePath = Paths.get("");
		String dataFolder = currentRelativePath.toAbsolutePath().toString() + "\\testit\\";
		
		convertToPdf(dataFolder);	

	}
	private static ProgressBar pb;
	
	public static void convertToPdf(String filePath) {
		
		System.out.println("Starting PDF conversion. This might take a while.");		
		String dataFolder = filePath;
		
		String texex = "laskutaitotesti.*\\.tex";
		List<Thread> threads = new ArrayList<Thread>();
		try {
			Files.createDirectories(Paths.get(dataFolder));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File dir = new File(dataFolder);
		File[] directoryListing = dir.listFiles();
		int texFiles = 0;
		for (int i = 0; i < directoryListing.length; i++) {
			if (directoryListing[i].isFile() && directoryListing[i].getAbsolutePath().contains(".tex")) texFiles++;
		}
		
		pb = new ProgressBar("Progress", texFiles);
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
		  }
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		var end = LocalTime.now();
		pb.close();
		System.out.println("PDF conversion finished!");
		
	}

	static class PDFThread implements Runnable {
		   private Thread t;
		   private String threadName;
		   private String command;
		   
		   PDFThread(String command, String threadName) {
			  this.command = command;
		      this.threadName = threadName;
		      //System.out.println("Creating " +  threadName );
		   }
		   
		   public void run() {
		      //System.out.println("Running " +  threadName );
		      ProcessBuilder builder = new ProcessBuilder(
		                "cmd.exe","/c", command);
	        	builder.redirectErrorStream(true);
	        	try {
					Process cmd = builder.start();
					var fut = cmd.onExit();
					while(!fut.isDone()) {
						Thread.sleep(200);
					}
					pb.step();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
	        	
		      //System.out.println("Finished converting " +  threadName);
		   }
		   
		   public void start () {
		     // System.out.println("Starting " +  threadName );
		      if (t == null) {
		         t = new Thread (this, threadName);
		         t.start ();
		      }
		   }
		}
}
