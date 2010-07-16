package knife.analysis;

import java.util.Set;

import knife.Common;
import knife.ListOutput;
import knife.Output;

import org.apache.commons.cli.CommandLine;


import com.google.inject.Inject;

public class ImportedByAnalysis implements Analysis {

    private final ListOutput out;
    private final UsageMap depMap;
    private final CommandLine opts;

    @Inject
    ImportedByAnalysis(CommandLine opts, UsageMap depMap, ListOutput out) {
        this.opts = opts;
        this.depMap = depMap;
        this.out = out;        
    }
    
    public Output execute()
    {
        String className = opts.getOptionValue(Common.OPT_IMPORTED_BY);
        Set<String> usagesByClass = depMap.getUsagesByClass(className);
        if (usagesByClass.size() > 0) {
            out.add(Common.orderedJoin("\n", usagesByClass));
        }
        
        return out;
    }

}
