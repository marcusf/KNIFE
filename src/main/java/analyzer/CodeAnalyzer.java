package analyzer;

import analyzer.analysis.Analysis;
import analyzer.analysis.AnalysisModule;
import analyzer.analysis.AvailableAnalyses;

import com.google.inject.AbstractModule;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.ProvisionException;
import com.google.inject.name.Names;

public class CodeAnalyzer {
       
    public static void main(String[] argv) throws Exception {
        
        if (argv.length == 0) {
            usage();
            return;
        }
        
        final String analysisName = argv[0];
        final String[] arguments = constructArguments(argv);

        Injector injector = Guice.createInjector(
                new MainModule(), 
                new AnalysisModule(),
                getArgvInjector(arguments)
        );

        try {
            Analysis analysis = injector.getInstance(Key.get(Analysis.class, Names.named(analysisName)));
            analysis.execute();
        } catch (ConfigurationException ce) {
            System.err.println("No such analysis " + argv[0]);
        } catch (ProvisionException pe) {
            System.err.println("Fatal error in instantiating, see error");
            pe.printStackTrace();
        }
    }

    private static Module getArgvInjector(final String[] arguments)
    {
        return new AbstractModule() {
            protected void configure()
            {
                bind(String[].class)
                    .annotatedWith(Names.named("Argv"))
                    .toInstance(arguments);
            }            
        };
    }

    private static void usage()
    {
        System.err.println("Usage: imports <analysis> [opts] [file [file ..]]");
        System.err.println("Where analyses are:");
        for (String k: AvailableAnalyses.getAnalyses().keySet()) {
            System.err.println("\t" + k);
        }
    }

    private static String[] constructArguments(String[] argv)
    {
        String[] arguments = new String[argv.length-1];
        
        for (int i = 0; i < argv.length-1; i++) {
            arguments[i] = argv[i+1];
        }
        
        return arguments;
    }
}
