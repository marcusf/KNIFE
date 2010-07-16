package knife;

import java.io.PrintStream;

import knife.analysis.Analysis;

import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.ProvisionException;

public class AnalysisRunner {
    
    private Analysis analysis;
    private UsageWriter uw;
    private final PrintStream err;
    private final Provider<Output> outputFactory;

    @Inject
    AnalysisRunner(Analysis analysis, 
                   UsageWriter uw,
                   @Output.Err PrintStream err, 
                   Provider<Output> outputFactory) 
        throws IllegalArgumentException 
    {
        this.analysis = analysis;
        this.uw = uw;
        this.err = err;
        this.outputFactory = outputFactory;
    }
    
    public Output startAnalysis() {
        try {
            return analysis.execute();            
        } catch (ConfigurationException ce) {
            uw.usage();
        } catch (ProvisionException pe) {
            uw.usage();
            pe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            err.println(iae.getMessage());
        }
        // Return empty output if everything goes pearshaped
        return outputFactory.get();
    }
}
