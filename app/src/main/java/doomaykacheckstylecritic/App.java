package doomaykacheckstylecritic;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // file options
        // path
        // xml name

        // parse options
        // errorMessages
        // warningMessages
        // refactorMessages
        // conventionMessages
        // multipliers(error,warning,refactor,convention)

        // message options

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

        String XMLpath = cr.getXMLpath(); // ++ Path to XML
        String XMLname = cr.getXMLname(); // ++ XML filename

        List<String> errorMessages = cr.getErrorMessages(); // errorMessages to check ++
        List<String> warningMessages = cr.getWarningMessages(); // warningMessages to check ++
        List<String> refactorMessages = cr.getRefactorMessages(); // refactorMessages to check ++
        List<String> conventionMessages = cr.getConventionMessages(); // convertationMessages to check ++

        int errorMultiplier = cr.getErrorMultiplier();
        int warningMultiplier = cr.getWarningMultiplier();
        int refactorMultiplier = cr.getRefactorMultiplier();
        int conventionMultiplier = cr.getConventionMultiplier();

        String[] messages = cr.getMessages(); // messages to print++

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

        CodeCounter counter = new CodeCounter(model, errorMultiplier, warningMultiplier, refactorMultiplier,
                conventionMultiplier);

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

        MessageGenerator mGenerator = new MessageGenerator(rating, counter.getLinesPrepared(), errorMultiplier,
                warningMultiplier, refactorMultiplier, conventionMultiplier, counter.getCounter(), messages);
        mGenerator.printMessages();
    }
}
