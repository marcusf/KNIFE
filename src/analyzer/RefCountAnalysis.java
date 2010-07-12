package analyzer;

import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;

import com.google.common.collect.SetMultimap;

public class RefCountAnalysis {

    private final CommandLine opts;
    private final UsageMap depMap;
    private final PrintStream out;

    public RefCountAnalysis(CommandLine opts,
                            UsageMap depMap,
                            PrintStream out)
    {
        this.opts = opts;
        this.depMap = depMap;
        this.out = out;
        
    }
    
    public void execute() {
        
        int MAX_COUNT = opts.hasOption(CodeAnalyzer.OPT_REFCOUNT_SHOW) 
                ? Integer.parseInt(opts.getOptionValue(CodeAnalyzer.OPT_REFCOUNT_SHOW))
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
