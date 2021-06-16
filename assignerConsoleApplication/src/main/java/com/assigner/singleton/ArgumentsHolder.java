package com.assigner.singleton;

public final class ArgumentsHolder {

    private static ArgumentsHolder single_instance = null;

    private final String inputFileName;
    private final String outputFileName;
    private final String apiUrl;
    private final String apiUsername;
    private final String apiPassword;

    private ArgumentsHolder(
            String inputFileName,
            String outputFileName,
            String apiUrl,
            String apiUsername,
            String apiPassword
    ) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.apiUrl = apiUrl;
        this.apiUsername = apiUsername;
        this.apiPassword = apiPassword;
    }

    public static void createInstance(
            String inputFileName,
            String outputFileName,
            String apiUrl,
            String apiUsername,
            String apiPassword
    ) {
        single_instance = new ArgumentsHolder(
                inputFileName,
                outputFileName,
                apiUrl,
                apiUsername,
                apiPassword
        );
    }

    public static ArgumentsHolder getInstance() {
        return single_instance;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiUsername() {
        return apiUsername;
    }

    public String getApiPassword() {
        return apiPassword;
    }

}
