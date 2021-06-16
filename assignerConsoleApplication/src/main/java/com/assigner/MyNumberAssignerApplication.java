package com.assigner;

import com.assigner.client.NumberAssignApiClient;
import com.assigner.client.NumberAssignApiClientImpl;
import com.assigner.exception.ApiFailException;
import com.assigner.helper.ArgumentHelper;
import com.assigner.object.Response;
import org.apache.commons.cli.CommandLine;
import com.assigner.singleton.ArgumentsHolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyNumberAssignerApplication {

    private final NumberAssignApiClient numberAssignApiClient;

    public MyNumberAssignerApplication() {
        numberAssignApiClient = new NumberAssignApiClientImpl();
    }

    public void run(String[] args) {
        loadArguments(args);

        try {
            executeFile();
        } catch (IOException e) {
            System.err.printf("System error. IOException occurred: %s%n", e.getMessage());

            System.exit(1);
        }
    }

    private void loadArguments(String[] args) {
        CommandLine line = ArgumentHelper.parseArguments(args);

        String inputFileName = ArgumentHelper.getOptionValue(line, "inputFilename");
        String outputFileName = ArgumentHelper.getOptionValue(line, "outputFilename");
        String apiUrl = ArgumentHelper.getOptionValue(line, "apiUrl");
        String apiUrlUsername = ArgumentHelper.getOptionValue(line, "apiUsername");
        String apiUrlPassword = ArgumentHelper.getOptionValue(line, "apiPassword");

        ArgumentsHolder.createInstance(
                inputFileName,
                outputFileName,
                apiUrl,
                apiUrlUsername,
                apiUrlPassword
        );
    }

    private void executeFile() throws IOException {
        File inputFile = new File(ArgumentsHolder.getInstance().getInputFileName());

        try (
                Scanner inputReader = new Scanner(inputFile);
                PrintWriter outputWriter = new PrintWriter(new FileWriter(ArgumentsHolder.getInstance().getOutputFileName()))
        ) {
            while (inputReader.hasNextLine()) {
                String data = inputReader.nextLine().trim();

                if (data.isEmpty()) {
                    outputWriter.write("\n");

                    continue;
                }

                Response response;
                try {
                    response = numberAssignApiClient.sendRequest(data);
                } catch (ApiFailException e) {
                    System.out.println("ApiFailException occurred.");
                    outputWriter.write("\n");

                    continue;
                }

                outputWriter.write(String.format("%4s %4d\n", response.getS(), response.getValue()));
            }
        }
    }

}
