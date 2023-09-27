package doomaykacheckstylecritic;

public class MessageGenerator {
    private String[] messages;
    private long linesCount; // %lc
    private int errorMultiplier; // %emp
    private int warningMultiplier; // %wmp
    private int refactorMultiplier; // %rmp
    private int conventionMultiplier; // %cmp
    private int errorsCounter; // %ect
    private int warningsCounter; // %wct
    private int refactorsCounter; // %rct
    private int conventionsCounter; // %cct
    private float rating; // %r

    MessageGenerator(
                    float rating, 
                    long linesCount, 
                    int errorMultiplier, 
                    int warningMultiplier, 
                    int refactorMultiplier,
                    int conventionMultiplier, 
                    int errorsCounter, 
                    int warningsCounter, 
                    int refactorsCounter, 
                    int conventionsCounter, 
                    String[] messages) {
        if (messages == null) {
            messages = new String[] { "\\\\Doomayka CheckStyle critic//", "Lines prepared: %lc",
                    "By expression: 10-((%emp*%ect+%wmp*%wct+%rmp*%rct+%cmp*%cct)/%lc)*10", "Result: %r" };
        }

        this.messages = messages;

        this.rating = rating;
        this.linesCount = linesCount;
        this.errorMultiplier = errorMultiplier;
        this.warningMultiplier = warningMultiplier;
        this.refactorMultiplier = refactorMultiplier;
        this.conventionMultiplier = conventionMultiplier;
        this.errorsCounter = errorsCounter;
        this.warningsCounter = warningsCounter;
        this.refactorsCounter = refactorsCounter;
        this.conventionsCounter = conventionsCounter; // %cct
    }

    public void printMessages() {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = messages[i].replace("%lc", Long.toString(linesCount));
            messages[i] = messages[i].replace("%emp", Integer.toString(errorMultiplier));
            messages[i] = messages[i].replace("%wmp", Integer.toString(warningMultiplier));
            messages[i] = messages[i].replace("%rmp", Integer.toString(refactorMultiplier));
            messages[i] = messages[i].replace("%cmp", Integer.toString(conventionMultiplier));
            messages[i] = messages[i].replace("%ect", Integer.toString(errorsCounter));
            messages[i] = messages[i].replace("%wct", Integer.toString(warningsCounter));
            messages[i] = messages[i].replace("%rct", Integer.toString(refactorsCounter));
            messages[i] = messages[i].replace("%cct", Integer.toString(conventionsCounter));
            messages[i] = messages[i].replace("%r", Float.toString(rating));

            System.out.println(messages[i]);
        }
    }
}
