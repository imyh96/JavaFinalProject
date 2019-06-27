package edu.handong.csee.java;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


public class Runner {
	String input;
	String output;
	
	boolean help;

	public void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			System.out.println("You provided \"" + input + "\" as the value of the option i");
			System.out.println("You provided \"" + output + "\" as the value of the option o");
				
			ZipReader zipReader = new ZipReader();
			String[] arg = {input, output};
				
			zipReader.run(arg);
		}
	}
	
	// Parsing Stage
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
		
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.required()
				.hasArg()
				.argName("Input path")
				.desc("Set an input file path")
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.required()
				.hasArg()
				.argName("Output path")
				.desc("Set an output file path")
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.argName("Help")
		        .desc("Show a Help page")
		        .build());

		return options;
	}
	
	// Unexpected error from Parsing stage Execute it and finish program 
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "Merge two Excel files";
		String footer =""; // Leave this empty
		formatter.printHelp("Merge two Excel files", header, options, footer, true);
	}
}
