package analyzer;

import analyzer.analysis.AnalysisModule;
import analyzer.analysis.AvailableAnalyses;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.internal.Lists;

public class Entrypoint {

    private final String[] argv;
    private final UsageWriter uw;

    public Entrypoint(String[] argv, UsageWriter uw) {
        
        this.argv = argv;
        this.uw = uw;
        
        if (argv.length == 0 || !AvailableAnalyses.getAnalyses().containsKey(argv[0])) {
            uw.usage();
            throw new IllegalArgumentException();
        }
    }

    public void run()
    {
        try {
            Injector injector = Guice.createInjector(
                    new AnalysisModule(argv[0]),
                    new MainModule(Lists.newArrayList(argv)) 
            );

            AnalysisRunner main = injector.getInstance(AnalysisRunner.class);                    
            main.startAnalysis().write();
            
        } catch (IllegalArgumentException e) {
            uw.usage();
            System.err.println(e.getMessage());
        }        
    }

}
