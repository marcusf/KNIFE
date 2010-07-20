package knife;

import knife.analysis.AnalysisModule;
import knife.analysis.AvailableAnalyses;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.internal.Lists;

/**
 * Bootstraps the entire application by first trying to find
 * out which analysis the user wants to run from the first argument
 * on the command line, and based on that bootstrapping Guice,
 * running the analysis via the AnalysisRunner and finally printing
 * the result.
 */
public class Entrypoint {

    private final String[] argv;

    public Entrypoint(String[] argv, UsageWriter uw) {
        
        this.argv = argv;
        
        if (argv.length == 0 || !AvailableAnalyses.getAnalyses().containsKey(argv[0])) {
            uw.usage();
            throw new IllegalArgumentException();
        }
    }

    public void run()
    {
        Injector injector = Guice.createInjector(
                new AnalysisModule(argv[0]),
                new MainModule(Lists.newArrayList(argv)) 
        );

        AnalysisRunner main = injector.getInstance(AnalysisRunner.class);                    
        Output out = main.startAnalysis();
        if (out != null) {
            out.write();
        } else {
            System.exit(1);
        }
    }

}
