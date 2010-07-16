package knife;

import java.io.PrintStream;

import knife.analysis.Analysis;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AnalysisRunner {
    
    private Analysis analysis;
    private final PrintStream err;
    private final Provider<Output> outputFactory;

    @Inject
    AnalysisRunner(Analysis analysis, 
                   @Output.Err PrintStream err, 
                   Provider<Output> outputFactory) 
        throws IllegalArgumentException 
    {
        this.analysis = analysis;
        this.err = err;
        this.outputFactory = outputFactory;
    }
    
    public Output startAnalysis() {
        try {
            return analysis.execute();
        } catch (IllegalArgumentException iae) {
            err.println(iae.getMessage());
        }
        // Return empty output if everything goes pearshaped
        return outputFactory.get();
    }
}
