package knife.analysis;

import java.util.Map;

import com.google.inject.internal.Maps;

/**
 * Analyses that can be run from the command line. Used by the AnalysisRunner
 * to decide what is a valid analysis.
 */
public class AvailableAnalyses {
    
    public static Map<String, Class<? extends Analysis>> getAnalyses() {
        
        Map<String, Class<? extends Analysis>> r = Maps.newHashMap();
        
        r.put("refcount",   RefCountAnalysis.class);
        r.put("reftree",    RefTreeAnalysis.class);
        r.put("importedby", ImportedByAnalysis.class);
        r.put("imports",    ListAllUsesAnalysis.class);
        
        return r;
        
    }
    
}
