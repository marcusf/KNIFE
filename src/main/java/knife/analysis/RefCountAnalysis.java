package knife.analysis;

import knife.Common;
import knife.ListOutput;
import knife.Output;

import org.apache.commons.cli.CommandLine;


import com.google.common.collect.SetMultimap;
import com.google.inject.Inject;

public class RefCountAnalysis implements Analysis {

    private final CommandLine opts;
    private final UsageMap depMap;
    private final ListOutput out;

    @Inject
    public RefCountAnalysis(CommandLine opts, UsageMap depMap, ListOutput out)
    {
        this.opts = opts;
        this.depMap = depMap;
        this.out = out;
        
    }
    
    public Output execute() {
        
        int MAX_COUNT = opts.hasOption(Common.OPT_COUNT) 
                ? Integer.parseInt(opts.getOptionValue(Common.OPT_COUNT))
                : 3;

        SetMultimap<String, String> usages = depMap.getUsagesByClass();

        for (String dep: usages.keySet()) {
            int cnt = usages.get(dep).size();
            String s = "";
            s += cnt + " " + dep;
            if (cnt <= MAX_COUNT) {
                s += (": " + Common.orderedJoin(", ", usages.get(dep)));
            }
            out.add(s);
        }
        
        return out;
    }
    
}
