import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LaTeXtoPDF {

	public static void main(String[] args) {
		Path currentRelativePath = Paths.get("");
		String dataFolder = currentRelativePath.toAbsolutePath().toString() + "\\testit\\";
		
		String texex = "laskutaitotesti.*\\.tex";
		var start = LocalTime.now();
		try {
			
			File dir = new File(dataFolder);
			  File[] directoryListing = dir.listFiles();
			  if (directoryListing != null) {
			    for (File child : directoryListing) {
					Matcher matcher = Pattern.compile(texex).matcher(child.getAbsolutePath());
					int i = 0;
					if (matcher.find()) {
						String command = "powershell.exe -Command \"& {"+ "pdflatex " + matcher.group()
								+ " --include-directory=" + dataFolder  
								+ " --output-directory=" + dataFolder + "pdf\\"
								+ " --aux-directory="+ dataFolder + "auxfiles\\}\"" ;
						ProcessBuilder builder = new ProcessBuilder(
				                "cmd.exe","/c", command);
			        	builder.redirectErrorStream(true);
			        	Process cmd = builder.start();
						var fut = cmd.onExit();
						while(!fut.isDone()) {Thread.sleep(200); System.out.println("kek");}
						System.out.println(++i);
					}
			    }
			  } else {
			  }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		var end = LocalTime.now();
		
		System.out.println(start + "|" + end);
		

	}

}
