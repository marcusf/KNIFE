package analyzer.analysis;

import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;

import analyzer.AppOptions;
import analyzer.Common;

import com.google.common.collect.SetMultimap;
import com.google.inject.Inject;

public class RefCountAnalysis implements Analysis {

    private final CommandLine opts;
    private final UsageMap depMap;
    private final PrintStream out;

    @Inject
    public RefCountAnalysis(CommandLine opts, UsageMap depMap, PrintStream out)
    {
        this.opts = opts;
        this.depMap = depMap;
        this.out = out;
        
    }
    
    public void execute() {
        
        int MAX_COUNT = opts.hasOption(AppOptions.OPT_COUNT) 
                ? Integer.parseInt(opts.getOptionValue(AppOptions.OPT_COUNT))
                : 3;

        SetMultimap<String, String> usages = depMap.getUsagesByClass();

        for (String dep: usages.keySet()) {
            int cnt = usages.get(dep).size();
            out.print(cnt + " " + dep);
            if (cnt <= MAX_COUNT) {
                out.print(":\t" + Common.orderedJoin(", ", usages.get(dep)));
            }
            out.print('\n');
        }

    }
    
}
