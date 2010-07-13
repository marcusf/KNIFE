package analyzer.analysis;

import java.io.PrintStream;
import java.util.Set;

import org.apache.commons.cli.CommandLine;

import analyzer.AppOptions;
import analyzer.Common;

import com.google.inject.Inject;

public class ImportedByAnalysis implements Analysis {

    private final PrintStream out;
    private final UsageMap depMap;
    private final CommandLine opts;

    @Inject
    ImportedByAnalysis(CommandLine opts, UsageMap depMap, PrintStream out) {
        this.opts = opts;
        this.depMap = depMap;
        this.out = out;        
    }
    
    public void execute()
    {
        String className = opts.getOptionValue(AppOptions.OPT_IMPORTED_BY);
        Set<String> usagesByClass = depMap.getUsagesByClass(className);
        out.println(Common.orderedJoin("\n", usagesByClass));      

    }

}
