
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

public class Main {
	
    private static final int problemAmount = 10;
	private static String[] problems = new String[10];
	private static String[] answers = new String[10];
	
	private static final String[] p6 = {
			"2x(4x^2 - x)(1 + 2x)",
			"-3x(2x^3 + 3x^2)(1 - x)",
			"x(5x^2 - 3x)(1 + 4x)",
			"-4x(3x^3 - 2x^2)(1 - 3x)",
			"2x(2x^2 + 5x)(1 + x)",
			"-x(6x^2 - 2x)(1 - 2x)",
			"3x(3x^3 + 4x^2)(1 + 3x)",
			"-4x(2x^2 - x)(1 - x)",
			"x(7x^2 + x)(1 + 5x)",
			"-5x(4x^3 - 3x^2)(1 - 4x)",

			"2x(4x^2 - x) + (1 + 2x)",
			"-3x(2x^3 + 3x^2) - (1 - x)",
			"x(5x^2 - 3x) + (1 + 4x)",
			"-4x(3x^3 - 2x^2) - (1 - 3x)",
			"2x(2x^2 + 5x) + (1 + x)",
			"-x(6x^2 - 2x) - (1 - 2x)",
			"3x(3x^3 + 4x^2) + (1 + 3x)",
			"-4x(2x^2 - x) - (1 - x)",
			"x(7x^2 + x) + (1 + 5x)",
			"-5x(4x^3 - 3x^2) - (1 - 4x)"
	};
	
	private static final String[] a6 = {
			"16x^4 +4x^3 -2x^2",
			"6x^5 + 3x^4 -9x^3",
			"20x^4 -7x^3 -3x^2",
			"36x^5 -36x^4 +8x^3",
			"4x^4 +14x^3 +10x^2",
			"12x^4 -10x^3 +2x^2",
			"27x^5 +45x^4 +12x^3",
			"8x^4 -12x^3 +4x^2",
			"35x^4 +12x^3 +x^2",
			"80x^5 -80x^4 +15x^3",

			"8x^3 -2x^2 +20x +1",
			"-6x^4 -9x^3 + x -1",
			"5x^3 -3x^2 +4x +1",
			"-12x^4 +8x^3 +3x -1",
			"4x^3 +10x^2 +x +1",
			"-6x^3 +2x^2 +2x -1",
			"9x^4 +12x^3 +3x +1",
			"-8x^3 +4x^2 +x -1",
			"7x^3 +x^2 +5x +1",
			"-20x^4 +15x^3 +4x -1"
	};

	private static final String[] p8 = {
			"4x - (2x + 2) = 2 - 3x",
			"2x - (x+3) = 1 - 4x",
			"5x - (2x-1) = 4 - 6x",
			"6x - (3x+4) = 5 - 7x",
			"7x - (4x-2) = 6 - 8x",
			"3x - (x+5) = 2 - 2x",
			"8x - (3x+1) = 7 - 9x",
			"9x - (5x-3) = 8 - 10x",
			"4x - (2x-4) = 3 - 5x",
			"10x - (4x+2) = 9 - 11x",
			"2x + 3(5 - 2x) = 9",
			"2x + 3(6 + x) = 10",
			"2x + 3(4 - 3x) = 8",
			"2x + 3(8 - 2x) = 12",
			"2x + 3(3 + 2x) = 7",
			"2x + 3(7 - x) = 11",
			"2x + 3(9 - 2x) = 15",
			"2x + 3(5 + 3x) = 13",
			"2x + 3(6 - x) = 12",
			"2x + 3(4 + 4x) = 14"
	};
	
	private static final String[] a8 = {
			"x = 4/5",
			"x = 4/5",
			"x = 1/3",
			"x = 9/10",
			"x = 4/11",
			"x = 7/4",
			"x = 4/7",
			"x = 5/14",
			"x = -1/7",
			"x = 11/17",
			"x = 3/2",
			"x = -8/5",
			"x = 4/7",
			"x = 3",
			"x = -1/4",
			"x = 10",
			"x = 3",
			"x = -2/11",
			"x = 6",
			"x = 1/7"
	};
	
	
	private static final String[] p10 = {
			"pQr = \\frac{mS+o}{lK}",
			"eFg = \\frac{hI+j}{kS}",
			"xYz = \\frac{rS+a}{vB}",
			"dEf = \\frac{gS+i}{jK}",
			"uVw = \\frac{oP+t}{qS}",
			"aBc = \\frac{dE+S}{gH}",
			"lMn = \\frac{pS+r}{sT}",
			"zXy = \\frac{vW+S}{uV}",
			"kLm = \\frac{nO+q}{rS}",
			"gHi = \\frac{jS+l}{mN}",
			
	};

	private static final String[] a10 = {
			"S = \\frac{pQrlK -o}{m}",
			"S = \\frac{hI+j}{eFk}",
			"S = \\frac{xYzvB-a}{s}",
			"S = \\frac{dEfjk-i}{g}",
			"S = \\frac{oP+t}{uVwq}",
			"S = aBcgH -dE",
			"S = \\frac{lMnsT -r}{p}",
			"S = zXyuV -vW",
			"S = \\frac{nO+q}{rkLm}",
			"S = \\frac{gHimN}{j}",
			
	};
	
	/**
	 * @param args does nothing
	 */
	public static void main(String[] args) {
	    


	    for (int nro = 1; nro < 41; nro++){
	    makeProblems(nro);
		try {
			var rivit = readFromFile("template.tex");
			
			int i = -1;
			for (var rivi: rivit) {
				i++;
				
				for (int j = 0; j < problemAmount; j++) {
				    if (problems[j] == null || answers[j] == null) continue;
				    Matcher matcher1 = Pattern.compile("\\;\\;T" + (j+1) + "\\;\\;").matcher(rivi);
				    Matcher matcher2 = Pattern.compile("\\;\\;T" + (j+1) + "ans\\;\\;").matcher(rivi);
				    Matcher matcher3 = Pattern.compile("\\;\\;tunniste\\;\\;").matcher(rivi);
				    if (matcher1.find()) {
				        String uusi = rivi.replace(";;T"+(j+1)+ ";;", problems[j]);
				        rivit.set(i, uusi);
				    }
				    if (matcher2.find()) {
				        String uusi = rivi.replace(";;T"+(j+1)+ "ans;;", answers[j]);
				        rivit.set(i, uusi);
				    }	
				     if (matcher3.find()) {
				         String uusi = rivi.replace(";;tunniste;;", "L1T" + nro +"A");
				         rivit.set(i, uusi);
				     }
				     
				}
			}
			
			//File file = new File("testi.tex");
			//file.delete();
			
			writeFile(rivit, "laskutaitotesti1_L1T" + nro + "A.tex");
			
			
			/*
			// This code example demonstrates how to create a PDF from TeX source file.
			// Working directory
			String dataDir = System.getProperty("user.dir");

			// Create typesetting options.
			TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());

			// Specify a file system working directory for input.
			options.setInputWorkingDirectory(new InputFileSystemDirectory(dataDir));

			// Specify a file system working directory for output.
			options.setOutputWorkingDirectory(new OutputFileSystemDirectory(dataDir));

			// Specify memory stream as output terminal.
			options.setTerminalOut(new OutputMemoryTerminal());

			// Set options for rendering into PDF format.
			options.setSaveOptions(new PdfSaveOptions());

			// Run typesetting.
			new TeXJob(dataDir + "laskutaitotesti1_L1T1A.tex", new PdfDevice(), options).run();
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
	
	
	private static void makeProblems(int nro) {
        makeProblem(1);
        makeProblem(2);
        makeProblem(3);
        makeProblem(4);
        makeProblem(5);
        makeProblem(6, nro);
        makeProblem(7);
        makeProblem(8, nro);
        makeProblem(9);
        makeProblem(10, nro);
        
    }
	
	private static void makeProblem(int i, int j) {
		switch (i) {
    	case 6:{
    		problems[i-1] = p6[(j-1)%20];
    		answers[i-1] = a6[(j-1)%20];
    		break;
        	}
        case 8: {
    		problems[i-1] = p8[(j-1)%20];
    		answers[i-1] = a8[(j-1)%20];
    		break;
        	}
		
		case 10:{
    		problems[i-1] = p10[(j-1)%10];
    		answers[i-1] = a10[(j-1)%10];
    		break;
        	}
		}
	}


    private static void makeProblem(int i) {
		switch (i) {
			case 1:{
				int a = randInt(3,50);
				while (a%5 == 0) {
					a = randInt(3,37);
				}
				
				double d = a / 25.0;
				
				int nom = (int) (d*100);
				int denom = 100;
				
				int gcd = gcd(nom, denom);
				
				nom = nom/gcd; denom = denom/gcd;
				problems[i-1] = Double.toString(d);
				answers[i-1] = "\\frac{"+ nom +"}{"+ denom + "}";
				
			break;}
        case 2:{
				var decim = randDouble(1,4);
				var exp = randInt(-5, -9);
				problems[i-1] = decim + "\\cdot 10^{"+ exp + "}";
				
				var len = decim.toString().length() + Math.abs(exp) -2;
				
				answers[i-1] = String.format("%."+ len + "f",decim.doubleValue()*Math.pow(10, exp));
				
			break;}
        case 3:{
				int b = randInt(3, 10);
				int d = randInt(3,10);
				
				while (d == b || isMultiple(d,b)) {
					d = randInt(3,10);
				}
				
				int a = randInt(2,b);
				while (gcd(a,b) > 1) {
					a = randInt(2,b);
				}
				
				int c = randInt(2,d);
				while (gcd(c,d) > 1) {
					c = randInt(2,d);
				}
				int sgn = randSgn();
				
				problems[i-1] = (sgn > 0) ? "\\frac{" + a + "}{"+ b +"}" + "+" + "\\frac{" + c + "}{"+ d +"}"
				        : "\\frac{" + a + "}{"+ b +"}" + "-" + "\\frac{" + c + "}{"+ d +"}";
				
				
				int nom = a*d + sgn*c*b;
				int denom = d*b;
				
				int gcd = gcd(denom, nom);
				answers[i-1] = "\\frac{" + nom/gcd + "}{"+ denom/gcd +"}";
				
			break;}
        case 4:{
				int b = randInt(3, 13);
				int d = randInt(3,13);
				int a = randInt(2, b);
				int c = randInt(2, d);
				
				while (b*d > 108 || gcd(a,b)>1 || gcd(c,d)>1 || gcd(a*c, b*d) == 1 || b == c || a == d) {
					b = randInt(3, 13);
					d = randInt(3,13);
					a = randInt(2, b);
					c = randInt(2, d);
				}
				
				int nom = a*c;
				int denom = b*d;
				
				int gcd = gcd(nom, denom);
				nom = nom/gcd;
				denom = denom/gcd;
				
				problems[i-1] = "\\frac{"+a+"}{"+b+"}\\cdot\\frac{"+c+"}{"+d+"}";
				answers[i-1] = "\\frac{"+nom+"}{"+denom+"}";
			
            break;}
        case 5:{
            int m = randInt(2, 8);
            int n= randInt(2, 3);
            
            var p1 = new Torni(m,n);
            
            int o= randInt(2, 8);
            
            var p2 = new Pot(o);
            int p= randInt(2, 8);
            int q= randInt(2, 4);
            
            var p3 = new PotPot(p,q);
            
            int jarj= randInt(1, 4);
            
            int vast = 0;
            switch(jarj){
            case 1:
                vast = p1.arvo() + p2.arvo() -p3.arvo();
                problems[i-1] = "\\frac{" +p1.toString() +"\\cdot "+p2.toString()+"}{"+p3.toString()+"}";
                answers[i-1] = "a^{" + vast +"}";              
                                break;
            case 2: 
                vast = p2.arvo() + p3.arvo() -p1.arvo();
                problems[i-1] = "\\frac{"+p2.toString() +" \\cdot "+p3.toString()+"}{"+p1.toString() +"}";
                answers[i-1] = "a^{" + vast +"}";              
                break;
            case 3:
                vast = p3.arvo() + p1.arvo() -p2.arvo();
                problems[i-1] = "\\frac{"+p3.toString()+" \\cdot "+p1.toString() +"}{"+p2.toString()+"}";
                answers[i-1] = "a^{" + vast +"}";
                break;
            default:
                break;
                
            }
            while(vast <= 0) {
                m = randInt(2, 8);
                n= randInt(2, 3);
                
                p1 = new Torni(m,n);
                
                o= randInt(2, 8);
                
                p2 = new Pot(o);
                p= randInt(2, 8);
                q= randInt(2, 4);
                
                p3 = new PotPot(p,q);
                
                jarj= randInt(1, 4);
                
                vast = 0;
                switch(jarj){
                case 1:
                    vast = p1.arvo() + p2.arvo() -p3.arvo();
                    problems[i-1] = "\\frac{" +p1.toString() +"\\cdot "+p2.toString()+"}{"+p3.toString()+"}";
                    answers[i-1] = "a^{" + vast +"}";              
                    break;
                case 2: 
                    vast = p2.arvo() + p3.arvo() -p1.arvo();
                    problems[i-1] = "\\frac{"+p2.toString() +" \\cdot "+p3.toString()+"}{"+p1.toString() +"}";
                    answers[i-1] = "a^{" + vast +"}";              
                    break;
                case 3:
                    vast = p3.arvo() + p1.arvo() -p2.arvo();
                    problems[i-1] = "\\frac{"+p3.toString()+" \\cdot "+p1.toString() +"}{"+p2.toString()+"}";
                    answers[i-1] = "a^{" + vast +"}";
                    break;
                default:
                    break;
            }
            }
            System.out.println();
			break;}
        case 7:{
            int a = 0, b=0, c=0, d=0, divis = 1;
            
            while(a == 0 || b == 0 || c == 0 || d == 0 || b == d || divis == 0) {
                a = randInt(1,5, true);
                b = randInt(-3, 4, true);
                c = randInt(-3, 4, true);
                d = randInt(-3, 4, true);
                
                String poly = a +"/(x+"+b+") + ("+ c +")/(x +"+d+")";
                String num =callMaxima("num(ratsimp("+poly+"));");
                String denom = callMaxima("denom(ratsimp("+poly+"));");   
                
                //Tässä numeron ja x:n väliin kertomerkki regexillä
                String multRegex = "(?<=\\d)\\s(?=x)";
                num = num.replaceAll(multRegex, "*");
                denom = denom.replaceAll(multRegex, "*");
                
                
                //Tämä pitää miettiä uudestaan, ehkä
                String div = callMaxima("second(divide("+num+","+denom+"));").replace(" ", "");
                try {
                divis = Integer.parseInt(div);
                } catch (NumberFormatException e) {
                    divis = 0;
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("\\frac{" + a + "}{x");
                if (b > 0 ) sb.append("+" +b + "}");
                else sb.append(b + "}");
                
                if (c > 0) sb.append("+ \\frac{ " + c);
                else sb.append("- \\frac{"+ Math.abs(c));
                
                if (d > 0) sb.append("}{x + " + d + "}");
                else sb.append("}{x - " + Math.abs(d) + "}");
                
                //KORJAA
                problems[i-1] = sb.toString() ;
                answers[i-1] = "\\frac{" + num + "}{"+ denom + "}";
                System.out.println();
            }
            
            break;
        }
    	case 9:{
    		int a=0,b=0,c=0;
    		while(a==0 ||gcd(a,b) > 1 || gcd(a,b) < -1 || c == 0) {
    			a = randSgn()*randInt(1, 7);
    			b = randInt(2, 7);
    			
    			c = randInt(-3, 4);
    		}
    		
    		String eq = callMaxima("ratsimp("+ b +"*(x +" + a +"/"+b+")*(x+"+c +"));");
    		//var split = eq.split("(?<=\\d\\sx)\\s\\s", 2);
    		//problems[i-1] = split[0] + "^2" + split[1] + "= 0";
    		problems[i-1] = eq + "= 0";
    		answers[i-1] = "x = " +(-1 * a) + "/" + b +" tai x = " + (-1 * c);
        	break;
        }
        default:
		}
			
	}
	
	
    private static int randInt(int i, int j, boolean b) {
        int rand = 0;
        while (rand == 0) {
            rand = randInt(i, j);
        }
        
        return rand;
    }


    // method to calculate gcd of two numbers
    static int gcd(int a, int b)
    {
        return (a == 0) ?b : gcd(b%a, a);
    }
    
    private static boolean isMultiple(int a, int b) {
        return a%b == 0 || b%a == 0;
    }
	
    private static String callMaxima(String args) {
    	String ans = "";   
    	List<String> lines = new ArrayList<String>();
        try {
   	        ProcessBuilder builder = new ProcessBuilder(
   	                "cmd.exe", "/c", "maxima -q --batch-string=\""+args +"\"");
   	            builder.redirectErrorStream(true);
   	            Process p = builder.start();
   	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
   	            String line;
   	            String regex = "(?<=(\\(\\%o\\d\\))).*$";
   	            
   	            while (true) {
   	                line = r.readLine();
   	                if (line == null) { break; }
   	                
                    Matcher matcher = Pattern.compile(regex).matcher(line);
   	                if (matcher.find()) {ans = line; break;}
   	                lines.add(line);
   	                System.out.println(line);
   	            }
           } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
           }
    	   /*
    	    Picked up _JAVA_OPTIONS: -Djava.vendor="Sun Microsystems Inc."

			'\\FILESERVICES.AD.JYU.FI\homes\onukilla\Desktop\Laskutaitotesti'
			CMD.EXE was started with the above path as the current directory.
			UNC paths are not supported.  Defaulting to Windows directory.
			(%i1) ratsimp(5*(x+(-6)/5)*(x+2))
			                                   2
			(%o1)                           5 x  + 4 x - 12
			    	     
			    	     
    	    */
    	   var res = ans.split("\\s", 2);
    	   return lines.get(lines.size() -1).contains("2") ? res[1].trim().replaceFirst("x", "x^2") : res[1].trim();
    	//return res[1].trim();
    }
	
	private static boolean writeFile(List<String> rivit, String tiednimi) {
		
		try (PrintStream fo = new PrintStream(new FileOutputStream(tiednimi, true))){
			for (var rivi : rivit) {
				fo.println(rivi);
			}
		}catch (Exception e) {
			return false;
		}
		return true;	
	}
	
	
	private static List<String> readFromFile(String polku) throws Exception {
	
		var riviLista = new ArrayList<String>();
		
        try ( BufferedReader fi = new BufferedReader(new FileReader(polku)) ) {
           String rivi = fi.readLine();
           
            while ((rivi = fi.readLine()) != null) {
            	riviLista.add(rivi);
            }
            
        } catch (FileNotFoundException e) {
            throw new Exception("Tiedosto " + polku + " ei aukea");
        } catch (IOException e) {
            throw new Exception("Ongelmia tiedoston kanssa: " +e.getMessage());
        }
        
        return riviLista;
    }
	
	
	
	private static BigDecimal randDouble(int min, int max) {
		return truncateDecimal(ThreadLocalRandom.current().nextDouble(min, max), 6);
	}
	
	
	/**
	 * Samples a random integer from the interval [min, max)
	 * @param min minimum
	 * @param max maximum
	 * @return random integer
	 */
	private static int randInt(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}

	private static int randSgn() {
		int i = randInt(0,2);
		return (i==1) ? 1 : -1;
		
	}
	
	
	@SuppressWarnings("deprecation")
	private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	{
	   
        return ( x > 0) ? new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR)
	    : new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	}
	

	
	private static class Torni implements Potenssi{
		private int a;
		private int b;
		
		private Torni(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
        public int arvo() {
			return (int)Math.pow(a, b);
		}
		
		@Override
		public String toString() {
			return "a^{"+ a +"^"+b+"}";
		}
	}
	
	private static class Pot implements Potenssi{
		private int a;
		private Pot(int a) {
			this.a = a;
		}
		
		@Override
        public int arvo() {
			return a;
		}
		
		@Override
		public String toString() {
			return "a^"+ a;
		}
	}
	
	private static class PotPot implements Potenssi{
		private int a;
		private int b;
		private PotPot(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
        public int arvo() {
			return a*b;
		}
		
		@Override
		public String toString() {
			return "\\left(a^" + a +"\\right)^" + b;
		}
	}
	
}

interface Potenssi{
	int arvo();
	@Override
    String toString();
	}
