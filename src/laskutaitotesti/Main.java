package laskutaitotesti;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    
	    try {
	        ProcessBuilder builder = new ProcessBuilder(
	                "cmd.exe", "/c", "maxima -q --batch-string=(2+1)*4;");
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while (true) {
	                line = r.readLine();
	                if (line == null) { break; }
	                System.out.println(line);
	            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

	    for (int nro = 1; nro < 11; nro++){
	    makeProblems();
		//System.out.println(prob2);
		System.out.println(problems[3]);
		System.out.println(answers[3]);
		System.out.println(isMultiple(6,3));
		try {
			var rivit = readFromFile("template.tex");
			
			Pattern[] patternsTeht = new Pattern[10];
			
			for (int i = 0; i < problemAmount; i++) {
			    patternsTeht[i] = Pattern.compile("\\;\\;T" + i+1 + "\\;\\;");
			}
			
			Pattern[] patternsVast = new Pattern[10];
			
			for (int i = 0; i < problemAmount; i++) {
			    patternsVast[i] = Pattern.compile("\\;\\;T" + i+1 + "ans\\;\\;");
			}
			
			Pattern tunnisteregex = Pattern.compile("\\;\\;tunniste\\;\\;");
			
			int i = -1;
			for (var rivi: rivit) {
				i++;
				
				for (int j = 0; j < patternsTeht.length; j++) {
				    if (problems[j] == null || answers[j] == null) continue;
				    Matcher matcher1 = patternsTeht[j].matcher(rivi);
				    Matcher matcher2 = patternsVast[j].matcher(rivi);
				    Matcher matcher3 = tunnisteregex.matcher(rivi);
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
	
	
	private static void makeProblems() {
        makeProblem(1);
        makeProblem(2);
        makeProblem(3);
        makeProblem(4);
        makeProblem(5);
        
    }


    private static void makeProblem(int num) {
		switch (num) {
			case 1:{
				int a = randInt(3,50);
				
				while (a%5 == 0) a = randInt(3,37);
				double d = a / 25.0;
				
				int nom = (int) (d*100);
				int denom = 100;
				
				int gcd = gcd(nom, denom);
				
				nom = nom/gcd; denom = denom/gcd;
				problems[num-1] = Double.toString(d);
				answers[num-1] = "\\frac{"+ nom +"}{"+ denom + "}";
				
			break;}
        case 2:{
				var decim = randDouble(1,4);
				var exp = randInt(-5, -9);
				
				problems[num-1]  = decim + "\\cdot 10^{"+ exp + "}";
				var len = decim.toString().length() + Math.abs(exp) -2;
				
				answers[num-1] = String.format("%."+ len + "f", decim.doubleValue()*Math.pow(10, exp));
				
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
				
				
				problems[num-1] = (sgn>10) ? "\\frac{" + a + "}{"+ b +"}" + "+" + "\\frac{" + c + "}{"+ d +"}" : "\\frac{" + a + "}{"+ b +"}" + "-" + "\\frac{" + c + "}{"+ d +"}";
				
				int nom = a*d + sgn*c*b;
				int denom = d*b;
				
				int gcd = gcd(denom, nom);
				answers[num-1] = "\\frac{" + nom/gcd + "}{"+ denom/gcd +"}";
				
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
				
				problems[num-1] = "\\frac{"+a+"}{"+b+"}\\cdot\\frac{"+c+"}{"+d+"}";
				answers[num-1] = "\\frac{"+nom+"}{"+denom+"}";
			
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
                break;
            case 2: 
                vast = p2.arvo() + p3.arvo() -p1.arvo();
                break;
            case 3:
                vast = p3.arvo() + p1.arvo() -p2.arvo();
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
                    break;
                case 2: 
                    vast = p2.arvo() + p3.arvo() -p1.arvo();
                    break;
                case 3:
                    vast = p3.arvo() + p1.arvo() -p2.arvo();
                    break;
                default:
                    break;
            }
            }
            System.out.println();
			break;}
        default:
		}
			
	}
	
	
    // method to calculate gcd of two numbers
    static int gcd(int a, int b)
    {
        if (a == 0)
            return b;
 
        return gcd(b % a, a);
    }
    
    private static boolean isMultiple(int a, int b) {
        return a%b == 0 || b%a == 0;
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
		double random = ThreadLocalRandom.current().nextDouble(min, max);
		return truncateDecimal(random, 6);
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
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
	    }
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
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
			return a^b;
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
