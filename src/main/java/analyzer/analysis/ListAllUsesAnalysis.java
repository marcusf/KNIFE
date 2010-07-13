package analyzer.analysis;

import java.io.PrintStream;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;

public class ListAllUsesAnalysis implements Analysis {

    private final UsageMap depMap;
    private final PrintStream out;

    @Inject
    ListAllUsesAnalysis(UsageMap depMap, PrintStream out) {
        this.depMap = depMap;
        this.out = out;    
    }
    
    @Override
    public void execute()
    {
        List<String> sortedResult = Ordering.natural().sortedCopy(depMap.getUsedGlobal());
        out.println(Joiner.on('\n').skipNulls().join(sortedResult));
    }

}
