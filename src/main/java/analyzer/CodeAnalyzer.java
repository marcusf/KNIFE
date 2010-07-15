package analyzer;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import analyzer.analysis.Analysis;
import analyzer.analysis.AnalysisModule;
import analyzer.analysis.AvailableAnalyses;

import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

public class CodeAnalyzer {
       
    private String analysisName;
    private final AnalysisProvider provider;

    public static void main(String[] argv) throws Exception {
        
        try {
            Injector injector = Guice.createInjector(
                    new MainModule(Lists.newArrayList(argv)), 
                    new AnalysisModule());

            CodeAnalyzer main = injector.getInstance(CodeAnalyzer.class);
                    
            main.startAnalysis();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Inject
    CodeAnalyzer(@Named("Argv") List<String> argv, AnalysisProvider provider) throws IllegalArgumentException {
        
        this.provider = provider;
        checkArgument(argv.size() > 0, usage());
        analysisName = argv.get(0);

    }
    
    private void startAnalysis() {
        try {
            Analysis analysis = provider.getAnalysis(analysisName);
            analysis.execute().write();            
        } catch (ConfigurationException ce) {
            System.err.println("No such analysis " + analysisName);
        } catch (ProvisionException pe) {
            System.err.println("Fatal error in instantiating, see error");
            pe.printStackTrace();
        }
    }

    private String usage()
    {
        StringBuilder usage = new StringBuilder();
        usage.append("Usage: imports <analysis> [opts] [file [file ..]]");
        usage.append("Where analyses are:");
        for (String k: AvailableAnalyses.getAnalyses().keySet()) {
            usage.append("\t" + k);
        }
        return usage.toString();
    }

}
