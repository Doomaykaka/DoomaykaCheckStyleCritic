package doomaykacheckstylecritic;

import java.util.List;

public class App {
    public static void main(String[] args) {   
        // Config path using
        ConfigReader cr;
        if (args.length > 0) {
            if ((args[0] != null) && (args[0].length() > 0)) {
                cr = new ConfigReader(args[0]);
            } else {
                cr = new ConfigReader();
            }
        } else {
            cr = new ConfigReader();
        }

        cr.readConfig();

        String XMLpath = cr.getXMLpath();
        String XMLname = cr.getXMLname();

        List<String> errorMessages = cr.getErrorMessages();
        List<String> warningMessages = cr.getWarningMessages();
        List<String> refactorMessages = cr.getRefactorMessages();
        List<String> conventionMessages = cr.getConventionMessages();

        int errorMultiplier = cr.getErrorMultiplier();
        int warningMultiplier = cr.getWarningMultiplier();
        int refactorMultiplier = cr.getRefactorMultiplier();
        int conventionMultiplier = cr.getConventionMultiplier();
        
        // Console messages
        String[] messages = cr.getMessages();

        CheckStyleParser csp;

        csp = new CheckStyleParser();

        if ((XMLname != null)) {
            csp = new CheckStyleParser(XMLname);
        }

        if ((XMLpath != null) && (XMLname != null)) {
            csp = new CheckStyleParser(XMLpath, XMLname);
        }

        csp.readXML();
        CheckStyleModel model = csp.getXmlUnparsed();

        CodeCounter counter = new CodeCounter(
                                  model, 
                                  errorMultiplier, 
                                  warningMultiplier, 
                                  refactorMultiplier,
                                  conventionMultiplier
                                  );

        if (errorMessages != null) {
            counter.setErrorMessages(errorMessages);
        }

        if (warningMessages != null) {
            counter.setWarningMessages(warningMessages);
        }

        if (refactorMessages != null) {
            counter.setRefactorMessages(refactorMessages);
        }

        if (conventionMessages != null) {
            counter.setConventionMessages(conventionMessages);
        }

        float rating = 0;
        rating = counter.calculate();

        MessageGenerator mGenerator = new MessageGenerator(
                                          rating, 
                                          counter.getLinesPrepared(), 
                                          errorMultiplier,
                                          warningMultiplier, 
                                          refactorMultiplier, 
                                          conventionMultiplier, 
                                          counter.getErrorsCounter(),
                                          counter.getWarningsCounter(),
                                          counter.getRefactorsCounter(),
                                          counter.getConventionsCounter(),
                                          messages
                                          );
        mGenerator.printMessages();
    }
}
