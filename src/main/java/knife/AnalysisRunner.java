package knife;

import knife.analysis.Analysis;

import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.ProvisionException;

public class AnalysisRunner {
    
    private Analysis analysis;
    private UsageWriter uw;

    @Inject
    AnalysisRunner(Analysis analysis, UsageWriter uw) 
        throws IllegalArgumentException 
    {
        this.analysis = analysis;
        this.uw = uw;
    }
    
    public Output startAnalysis() {
        try {
            return analysis.execute();            
        } catch (ConfigurationException ce) {
            uw.usage();
            System.exit(1);
            return null;
        } catch (ProvisionException pe) {
            uw.usage();
            pe.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
