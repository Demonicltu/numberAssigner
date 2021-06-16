package com.assigner.helper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

public class ArgumentHelper {

    public static String getOptionValue(CommandLine line, String option) {
        if (line.hasOption(option)) {
            return line.getOptionValue(option);
        }

        System.err.printf("Missing %s value%n", option);
        printAppHelp();

        System.exit(1);
        return null;
    }

    public static CommandLine parseArguments(String[] args) {
        Options options = getOptions();
        CommandLine line = null;

        CommandLineParser parser = new DefaultParser();

        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Failed to parse command line arguments");
            printAppHelp();

            System.exit(1);
        }

        return line;
    }

    private static Options getOptions() {
        Options options = new Options();

        options.addOption("if", "inputFilename", true, "File name to read from");
        options.addOption("of", "outputFilename", true, "File name to save response to");
        options.addOption("api", "apiUrl", true, "Api url address");
        options.addOption("user", "apiUsername", true, "Api access username");
        options.addOption("pass", "apiPassword", true, "Api access password");

        return options;
    }

    private static void printAppHelp() {
        Options options = getOptions();

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("com.assigner.NumberAssignEx", options, true);
    }

}
