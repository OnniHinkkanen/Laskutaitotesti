
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.tongfei.progressbar.ProgressBar;

import java.lang.Math;
/**
 * This program only works on Windows. Generating the tests requires installation of Maxima
 * and generating the output PDFs in addition to the .tex files will require installation
 * of MiKTeX.
 * 
 * This program generates a given amount of 'Laskutaitotesti's for the course Calculus 1 at University of Jyväskylä.
 * Most of the problems are computer-generated, while some remain manual due to the programmer not feeling the time
 * spent automating these would be better spent elsewhere. The restrictions used in generating the automated problems 
 * will be made clear later on in the documentation.
 * 
 * One needs to have a template.tex -file in the folder from which the program reads the required LaTeX. It is formatted
 * in such a way that the place where one wants problem 1 is denoted by ;;T1;; and the place for the answer 
 * is denoted by ;;T1ans;;. The numbering schema location is denoted by ;;tunniste;;. These are hard-coded for reasons
 * of convenience but can be edited easily in the main function.
 * 
 * This program relies on Maxima to perform mathematical calculations. Hence, one needs to either have 
 * the installation path set in the enviromental variable PATH (Maxima installer does not do this automatically for some
 * reason even though it can be called from the command line.) or provide the path as a parameter with the
 * -maxima-path argument. In addition, the conversion to PDF is performed with pdftolatex in PowerShell, which
 * should not require additional actions.
 * 
 * The programmer notes that no JUnit test have been written due to laziness. This breaks proper coding conventions. 
 * However, in the original specifications, every test is meant to be checked by hand anyway. This means the occasional
 * imperfections will be caught.
 * 
 * Known limitations:
 * -If the folder \tests\ in the path has other .tex files, the program will convert them all to PDF if the PDF option is set.
 * 
 * @author Onni Hinkkanen
 * @version 1.0.1
 * @since 25.10.2023
 */
public class Main {
	


	/*
	 * Equations for problem 6. The variable one solves for is always x. 
	 * Size isn't limited to 20 problems i.e. one can have as many as they want if they make sure there
	 * are as many entries in the next list containing the solutions. One must have that for the problem p6[i]
	 * the answer is contained in a6[i]. They are looped through normally when generating the tests, 
	 * so no randomization occurs in the ordering of these.
	 */
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
	/*
	 * Answers for problem 6.
	 */
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

	/*
	 * Equations for problem 8. The variable one solves for is always x. 
	 * Size isn't limited to 20 problems i.e. one can have as many as they want if they make sure there
	 * are as many entries in the next list containing the solutions. One must have that for the problem p8[i]
	 * the answer is contained in a8[i]. They are looped through normally when generating the tests, 
	 * so no randomization occurs in the ordering of these.
	 */
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
	
	/*
	 * Solutions for problem 8.
	 */
	private static final String[] a8 = {
			"\\frac{4}{5}",
			"\\frac{4}{5}",
			"\\frac{1}{3}",
			"\\frac{9}{10}",
			"\\frac{4}{11}",
			"\\frac{7}{4}",
			"\\frac{4}{7}",
			"\\frac{5}{14}",
			"\\frac{-1}{7}",
			"\\frac{11}{17}",
			"\\frac{3}{2}",
			"\\frac{-8}{5}",
			"\\frac{4}{7}",
			"3",
			"\\frac{-1}{4}",
			"10",
			"3",
			"\\frac{-2}{11}",
			"6",
			"\\frac{1}{7}"
	};
	
	/*
	 * Equations for problem 10. The variable one solves for is always S due to the programmer being lazy. 
	 * Size isn't limited to 10 problems i.e. one can have as many as they want if they make sure there
	 * are as many entries in the next list containing the solutions. One must have that for the problem p10[i]
	 * the answer is contained in a10[i]. They are looped through normally when generating the tests, 
	 * so no randomization occurs in the ordering of these.
	 */
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
	
	/*
	 * Solutions for problem 10.
	 */
	private static final String[] a10 = {
			" \\frac{pQrlK -o}{m}",
			" \\frac{hI+j}{eFkg}",
			" \\frac{xYzvB-a}{r}",
			" \\frac{dEfjk-i}{g}",
			" \\frac{oP+t}{uVwq}",
			" aBcgH -dE",
			" \\frac{lMnsT -r}{p}",
			" zXyuV -vW",
			" \\frac{nO+q}{rkLm}",
			" \\frac{gHimN - l}{j}",
			
	};
	
	//Number of problems in the test.
	private static final int problemAmount = 10;
	//List of problems for a given test
	private static String[] problems = new String[problemAmount];
	//List of answers for a given test
	private static String[] answers = new String[problemAmount];

	private static boolean isWindows;
	
	private static Process maxima;
	private static BufferedReader in;
	private static Writer out;
	
	/**
	 * @param args arguments for the program
	 */
	public static void main(String[] args) {
		//Initialization
		Path currentRelativePath = Paths.get("");
		
		//TODO: Fix for Linux
		String path = currentRelativePath.toAbsolutePath().toString();
		isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		
		int numberOfTests = 20;
		String maximaPath = "";
		String templatePath = "";
		boolean convert = false;
		
		//Check the arguments
		if (args.length > 0) {
			if (args[0].contains("?")) {System.out.println("Help"); System.exit(0);}
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if (arg.equals("-pdf")) {convert = true; continue;}
				if (arg.equals("-tests")) {numberOfTests = Integer.parseInt(args[i+1]); continue;}
				if (arg.equals("-path")) {path = args[i+1]; continue;}
				if (arg.contains("-maxima-path")) {maximaPath = args[i+1]; continue;}
				if (arg.contains("-template-path")) {templatePath = args[i+1]; continue;}
			}
		}
		
		//More initialization
		if (!maximaPath.equals("") && !maximaPath.substring(maximaPath.length()-1).equals("\\"))	
			maximaPath = maximaPath + "\\";
		if (!templatePath.equals("") && templatePath.substring(templatePath.length()-1).equals("\\")) templatePath = templatePath + "\\";
		if (path.substring(path.length()-1).equals("\\")) path = path + "\\";
		String testsPath = (isWindows) ? path + "\\tests\\" : path + "/tests/";
	
		if (!maximaPath.equals("") && !new File(maximaPath + "\\maxima.bat").exists()) {
			System.err.println("The path specified for Maxima does not contain Maxima installation. Exiting.");
			System.exit(1);
		}
		
        //Open the Maxima instance
		
			try {
				ProcessBuilder builder = new ProcessBuilder();
				if (isWindows){
					builder.command("cmd.exe", "/k", maximaPath + "maxima -q");
				} else {
					builder.command("sh", "-c", maximaPath + "maxima -q");
				}
				builder.redirectErrorStream(true);
				maxima = builder.start();
				out = new BufferedWriter(new OutputStreamWriter(maxima.getOutputStream()));
				in = new BufferedReader(new InputStreamReader(maxima.getInputStream()));
				out.write("display2d:false$");
				out.flush();
				Files.createDirectories(Paths.get(testsPath));
			} catch (IOException e) {
				e.printStackTrace();
			}


		
		System.out.println("Started generating "+ numberOfTests + " tests");
		ProgressBar pb = new ProgressBar("Progress", numberOfTests);
		
		for (int nro = 1; nro < numberOfTests + 1; nro++){
			
			//Generate the problems
			makeProblems(nro);
			//Write them into a file that corresponds to template.tex
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
					         String uusi = rivi.replace(";;tunniste;;", "L1T" + nro +"G");
						         rivit.set(i, uusi);
					     }
					}
				}
				writeFile(rivit, "laskutaitotesti1_L1T" + nro + "G.tex", testsPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("Finished generating test " + nro);
			pb.step();
		}
		//Close the progress bar and convert the files if option is set
		pb.close();
		if (convert) LaTeXtoPDFThreading.convertToPdf(testsPath);
		System.out.println("Exiting.");
		
	}
	
	/**
	 * Generates the problems for a given test. 
	 * @param nro Number of the test
	 */
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
	
	/**
	 * Function for generating problems 6, 8 and 10. These problems are 
	 * hand-made and contained in the corresponding instance variables 
	 * 
	 * Problem 6
	 * 
	 * Expansion of two polynomials
	 * 
	 * 
	 * Problem 8
	 * 
	 * Solve a first order equation
	 * 
	 * 
	 * Problem 10
	 * 
	 * Solve a variable from an equation with only variables.
	 * 
	 * @param i Number of the problem
	 * @param j Number of the test
	 */
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

	/**
	 * Function for generating the rest of the problems and answers. They are stored
	 * in the corresponding instance variables as a String, in LaTeX syntax.
	 * 
	 * Problem 1 
	 * 
	 * In this problem the student is given a decimal number which they need to expand into 
	 * a rational form and then simplify.
	 * 
	 * Samples a random integer, divides it by 25 and converts it to a decimal number 
	 * (between 0.12 and 1.5) for the problem.
	 * 
	 * Answer is the simplified form of the rational number.
	 * 
	 * 
	 * Problem 2
	 * 
	 * Student needs to convert scientific notation to a decimal number.
	 * 
	 * Samples a (truncated) double from the range (1,4) and an integer for the power from the ranges  [-6,-2)
	 * or [2,6), randomly. Makes sure the last integer of the double is not 0.
	 * 
	 * Answer is the decimal number after the conversion.
	 * 
	 * 
	 * Problem 3
	 * 
	 * Sum of two rational numbers.
	 * 
	 * Samples four integers and makes sure they are sensible (i.e. not of the form 2/2 or 2/4, for example, that the
	 * denominators are not the same integer and that the result is not an integer.)
	 * 
	 * Answer is the fraction given by the summation in simplified form.
	 * 
	 * 
	 * Problem 4
	 * 
	 * Product of two (non-negative) rational numbers.
	 * 
	 * Samples four integers and makes sure the fractions are sensible.
	 * 
	 * Answer is the fraction in simplified form.
	 * 
	 * 
	 * 
	 * Problem 5
	 * 
	 * Rules for calculations with exponents
	 * 
	 * 
	 * 
	 * 
	 * Problem 7
	 * 
	 * Similar to problem 3, but with variables. Calls Maxima to make sure the fractions are sensible.
	 * 
	 * 
	 * Problem 9
	 * 
	 * Solve a second order polynomial.
	 * 
	 * Samples random integers and creates a second order polynomial, of which one of the roots is rational i.e. not an integer.
	 * Calls Maxima for the answers.
	 * 
	 * @param i Number of the problem
	 */
    private static void makeProblem(int i) {
		switch (i) {
			case 1:{
				int a = randInt(3,50);
				while (a%5 == 0) {
					a = randInt(3,37);
				}
				
				double d = a / 25.0;
				
				int num = (int) (d*100);
				int denom = 100;
				
				int gcd = gcd(num, denom);
				
				num = num/gcd; denom = denom/gcd;
				problems[i-1] = Double.toString(d).replace(".", "{,}");
				answers[i-1] = "\\frac{"+ num +"}{"+ denom + "}";
				
			break;}
        case 2:{
				var decim = randDouble(1,4);
				var exp = (randSgn() == +1) ? randInt(-2, -6) : randInt(2,6);
				var len = decim.toString().length() + Math.abs(exp) -2;
				
				while (decim.toString().substring(decim.toString().length() -1 ).equals("0") || Math.abs(exp) > len) {
					decim = randDouble(1,4);
					exp = (randSgn() == +1) ? randInt(-2, -6) : randInt(2,6);
					len = decim.toString().length() + Math.abs(exp) -2;
				}
				problems[i-1] = decim.toString().replace(".", "{,}") + "\\cdot 10^{"+ exp + "}";
				answers[i-1] = (exp < 0) ?String.format("%."+ len + "f",decim.doubleValue()*Math.pow(10, exp))
						:String.format("%."+( decim.toString().length() - 2 - Math.abs(exp) )+ "f",decim.doubleValue()*Math.pow(10, exp));
				
			break;}
        case 3:{
        		int a = 0,b=0,c=0,d = 0, sgn=1, num=-1, denom=-1, gcd=0;
				

        		while (num < 0 || denom < 0) {
        			b = randInt(3, 10);
    				d = randInt(3,10);
    				
    				while (d == b || isMultiple(d,b)) {
    					d = randInt(3,10);
    				}
    				
    				a = randInt(2,b);
    				while (gcd(a,b) > 1) {
    					a = randInt(2,b);
    				}
    				
    				c = randInt(2,d);
    				while (gcd(c,d) > 1) {
    					c = randInt(2,d);
    				}
    				sgn = randSgn();
    				
    				num = a*d + sgn*c*b;
    				denom = d*b;
    				gcd = gcd(denom, num);
        		}
				
				
				problems[i-1] = (sgn > 0) ? "\\frac{" + a + "}{"+ b +"}" + "+" + "\\frac{" + c + "}{"+ d +"}"
				        : "\\frac{" + a + "}{"+ b +"}" + "-" + "\\frac{" + c + "}{"+ d +"}";
				answers[i-1] = "\\frac{" + num/gcd + "}{"+ denom/gcd +"}";
				
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
				
				int num = a*c;
				int denom = b*d;
				
				int gcd = gcd(num, denom);
				num = num/gcd;
				denom = denom/gcd;
				
				problems[i-1] = "\\frac{"+a+"}{"+b+"}\\cdot\\frac{"+c+"}{"+d+"}";
				answers[i-1] = "\\frac{"+num+"}{"+denom+"}";
			
            break;}
        case 5:{
            int m = randInt(2, 8);
            int n= randInt(2, 3);
            
            var p1 = new PowTow(m,n);
            
            int o= randInt(2, 8);
            
            var p2 = new Pow(o);
            int p= randInt(2, 8);
            int q= randInt(2, 4);
            
            var p3 = new PowPow(p,q);
            
            int jarj= randInt(1, 4);
            
            int vast = 0;
            switch(jarj){
            case 1:
                vast = p1.value() + p2.value() -p3.value();
                problems[i-1] = "\\frac{" +p1.toString() +"\\cdot "+p2.toString()+"}{"+p3.toString()+"}";
                answers[i-1] = "a^{" + vast +"}";              
                                break;
            case 2: 
                vast = p2.value() + p3.value() -p1.value();
                problems[i-1] = "\\frac{"+p2.toString() +" \\cdot "+p3.toString()+"}{"+p1.toString() +"}";
                answers[i-1] = "a^{" + vast +"}";              
                break;
            case 3:
                vast = p3.value() + p1.value() -p2.value();
                problems[i-1] = "\\frac{"+p3.toString()+" \\cdot "+p1.toString() +"}{"+p2.toString()+"}";
                answers[i-1] = "a^{" + vast +"}";
                break;
            default:
                break;
                
            }
            while(vast <= 0) {
                m = randInt(2, 8);
                n= randInt(2, 3);
                
                p1 = new PowTow(m,n);
                
                o= randInt(2, 8);
                
                p2 = new Pow(o);
                p= randInt(2, 8);
                q= randInt(2, 4);
                
                p3 = new PowPow(p,q);
                
                jarj= randInt(1, 4);
                
                vast = 0;
                switch(jarj){
                case 1:
                    vast = p1.value() + p2.value() -p3.value();
                    problems[i-1] = "\\frac{" +p1.toString() +"\\cdot "+p2.toString()+"}{"+p3.toString()+"}";
                    answers[i-1] = "a^{" + vast +"}";              
                    break;
                case 2: 
                    vast = p2.value() + p3.value() -p1.value();
                    problems[i-1] = "\\frac{"+p2.toString() +" \\cdot "+p3.toString()+"}{"+p1.toString() +"}";
                    answers[i-1] = "a^{" + vast +"}";              
                    break;
                case 3:
                    vast = p3.value() + p1.value() -p2.value();
                    problems[i-1] = "\\frac{"+p3.toString()+" \\cdot "+p1.toString() +"}{"+p2.toString()+"}";
                    answers[i-1] = "a^{" + vast +"}";
                    break;
                default:
                    break;
            }
            }
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
                
                
                String multRegex = "(?<=\\d)\\s(?=x)";
                num = num.replaceAll(multRegex, "*");
                denom = denom.replaceAll(multRegex, "*");
                
                
                
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
                
                
                problems[i-1] = sb.toString() ;
                answers[i-1] = ("\\frac{" + num + "}{"+ denom + "}").replace("*", "");
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
    		
    		String eq = callMaxima("ratsimp("+ b +"*(x +" + a +"/"+b+")*(x+"+c +"));").replace("*", "");
    		problems[i-1] = eq + "= 0";
    		answers[i-1] = "\\frac{" +(-1 * a) + "}{" + b +"} \\text{  tai } x = " + (-1 * c);
        	break;
        }
        default:
		}
			
	}
	
	/**
	 * Samples a random non-zero integer from the range [i, j).
	 * @param i lower bound
	 * @param j upper bound
	 * @param b this does nothing.
	 * @return
	 */
    private static int randInt(int i, int j, boolean b) {
        int rand = 0;
        while (rand == 0) {
            rand = randInt(i, j);
        }
        
        return rand;
    }


    /**
     * Returns the greatest common divisor of two numbers
     * @param a number one
     * @param b number two
     * @return greatest common divisor
     */
    static int gcd(int a, int b)
    {
        return (a == 0) ?b : gcd(b%a, a);
    }
    
    /**
     * Checks if a is a multiple of b
     * @param a integer
     * @param b integer
     * @return true, if a is a multiple of b or b is a multiple of a
     */
    private static boolean isMultiple(int a, int b) {
        return a%b == 0 || b%a == 0;
    }
	
	
    private static String callMaxima(String args) {
    	
		try {
			out.write(args);
			out.flush();
			for(int i = 0; i < Integer.MAX_VALUE; i++) {
				String ln = in.readLine();
				//System.out.println(ln);
				if (ln.contains("%o")) {
					var res = ln.split("\\s",2);
					return res[1].trim();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
    }
    
    /**
     * Writes the contents of a list to a file
     * @param rivit List containing the lines of the file
     * @param name name of the file
     * @param path path to the folder
     * @return true is the write was successful
     */
 	private static boolean writeFile(List<String> rivit, String name, String path) {
		
		//Path currentRelativePath = Paths.get("");
		String dataDir = path;
 		
		try (PrintStream fo = new PrintStream(new FileOutputStream( dataDir + name, true))){
			for (var rivi : rivit) {
				fo.println(rivi);
			}
		}catch (Exception e) {
			return false;
		}
		return true;	
	}
	
	/**
	 * Reads a given file and returns a list containing each line of the file.
	 * @param path path to the file
	 * @return List of every line in the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static List<String> readFromFile(String path) throws FileNotFoundException, IOException {
	
		var lines = new ArrayList<String>();
		
        try ( BufferedReader fi = new BufferedReader(new FileReader(path)) ) {
           String line = fi.readLine();
           
            while ((line = fi.readLine()) != null) {
            	lines.add(line);
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file " + path + " doesn't exist");
        } catch (IOException e) {
            throw new IOException("Problems with the file: " +e.getMessage());
        }
        
        return lines;
    }
	
	
	/**
	 * Samples a random double from the interval (min, max) and truncates it after 6 decimal places
	 * @param min lower bound 
	 * @param max upper bound
	 * @return Random real number of type BigDecimal
	 */
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

	/**
	 * Returns +1 or -1 randomly
	 * @return +1 or -1
	 */
	private static int randSgn() {
		int i = randInt(0,2);
		return (i==1) ? 1 : -1;
		
	}
	
	
	/**
	 * Truncates a double to given amount of decimals
	 * @param x double number to be truncated
	 * @param numberofDecimals number of decimals in the truncation
	 * @return truncated number of the type BigDecimal
	 */
	@SuppressWarnings("deprecation")
	private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	{
	   
        return ( x > 0) ? new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR)
	    : new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	}
	

	
	//======================================================================
	// These classes are 'stupid' in the sense that they are designed only for exponential algebra
	// for the same base number
	/**
	 * Class for power tower of two exponents
	 */
	private static class PowTow implements Power{
		private int a;
		private int b;
		
		private PowTow(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
        public int value() {
			return (int)Math.pow(a, b);
		}
		
		@Override
		public String toString() {
			return "a^{"+ a +"^"+b+"}";
		}
	}
	
	/**
	 * Class for simple power
	 */
	private static class Pow implements Power{
		private int a;
		private Pow(int a) {
			this.a = a;
		}
		
		@Override
        public int value() {
			return a;
		}
		
		@Override
		public String toString() {
			return "a^"+ a;
		}
	}
	
	/**
	 * Class for power of a power
	 */
	private static class PowPow implements Power{
		private int a;
		private int b;
		private PowPow(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
        public int value() {
			return a*b;
		}
		
		@Override
		public String toString() {
			return "\\left(a^" + a +"\\right)^" + b;
		}
	}
	
}

/**
 * Interface for the classes used in calculating powers in problem 5.
 */
interface Power{
	int value();
	@Override
    String toString();
	}
