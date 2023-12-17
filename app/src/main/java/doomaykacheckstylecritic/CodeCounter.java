package doomaykacheckstylecritic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeCounter {
    private CheckStyleModel model;
    private List<String> errorMessages;
    private List<String> warningMessages;
    private List<String> refactorMessages;
    private List<String> conventionMessages;
    private int errorMultiplier;
    private int warningMultiplier;
    private int refactorMultiplier;
    private int conventionMultiplier;
    private int errorsCounter;
    private int warningsCounter;
    private int refactorsCounter;
    private int conventionsCounter;
    private long linesPrepared;

    public CodeCounter(CheckStyleModel model, int errorMultiplier, int warningMultiplier, int refactorMultiplier,
            int conventionMultiplier) {
        this.model = model;

        setErrorMessages(new ArrayList<>());
        setWarningMessages(new ArrayList<>());
        setRefactorMessages(new ArrayList<>());
        setConventionMessages(new ArrayList<>());

        this.errorMultiplier = errorMultiplier;
        this.warningMultiplier = warningMultiplier;
        this.refactorMultiplier = refactorMultiplier;
        this.conventionMultiplier = conventionMultiplier;
        
        errorsCounter = 0;
        warningsCounter = 0;
        refactorsCounter = 0;
        conventionsCounter = 0;
        
        linesPrepared = 0;
    }

    public float calculate() {
        long linesCount = calculateLinesCount();
        linesPrepared = linesCount;

        calculateErrorsCount();

        float totalCritics = (errorMultiplier * errorsCounter + warningMultiplier * warningsCounter
                + refactorMultiplier * refactorsCounter + conventionMultiplier * conventionsCounter);

        float rating = 10 - (totalCritics / linesCount) * 10;

        return rating;
    }

    private long calculateLinesCount() {
        long linesCount = 0;

        if (this.model != null) {
            for (CheckStyleFileModel file : this.model.getFiles()) {
                Scanner scanner;
                try {
                    String trash = "";
                    scanner = new Scanner(new File(file.getFileName()));

                    while(scanner.hasNextLine()) {
                        trash = scanner.nextLine();
                        linesCount++;
                    }

                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return linesCount;
    }

    private void calculateErrorsCount() {
        if (this.model == null) {
            return;
        }

        for (CheckStyleFileModel file : model.getFiles()) {
            for (CheckStyleErrorModel error : file.getErrors()) {
                String message = error.getMessage();
                boolean containsErrors = errorMessages.contains(message);
                boolean containsWarnings = warningMessages.contains(message);
                boolean containsRefactors = refactorMessages.contains(message);
                boolean containsConvention = conventionMessages.contains(message);

                if (containsErrors) {
                    errorsCounter++;
                }

                if (containsWarnings) {
                    warningsCounter++;
                }

                if (containsRefactors) {
                    refactorsCounter++;
                }

                if (containsConvention) {
                    conventionsCounter++;
                }

                if (
                   !containsErrors 
                   && !containsWarnings 
                   && !containsRefactors 
                   && !containsConvention
                   ) {
                    conventionsCounter++;
                }
            }
        }
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public void setRefactorMessages(List<String> refactorMessages) {
        this.refactorMessages = refactorMessages;
    }

    public void setConventionMessages(List<String> conventionMessages) {
        this.conventionMessages = conventionMessages;
    }
    
    public int getErrorsCounter() {
        return errorsCounter;
    }

    public void setErrorsCounter(int errorsCounter) {
        this.errorsCounter = errorsCounter;
    }
    
    public int getWarningsCounter() {
        return warningsCounter;
    }

    public void setWarningsCounter(int warningsCounter) {
        this.warningsCounter = warningsCounter;
    }
    
    public int getRefactorsCounter() {
        return refactorsCounter;
    }

    public void setRefactorsCounter(int refactorsCounter) {
        this.refactorsCounter = refactorsCounter;
    }
    
    public int getConventionsCounter() {
        return conventionsCounter;
    }

    public void setConventionsCounter(int conventionsCounter) {
        this.conventionsCounter = conventionsCounter;
    }

    public long getLinesPrepared() {
        return linesPrepared;
    }
}
