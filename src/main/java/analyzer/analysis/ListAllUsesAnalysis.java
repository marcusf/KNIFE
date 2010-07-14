package analyzer.analysis;

import java.util.List;

import analyzer.ListOutput;
import analyzer.Output;

import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;

public class ListAllUsesAnalysis implements Analysis {

    private final UsageMap depMap;
    private final ListOutput out;

    @Inject
    ListAllUsesAnalysis(UsageMap depMap, ListOutput out) {
        this.depMap = depMap;
        this.out = out;    
    }
    
    @Override
    public Output execute()
    {
        List<String> sortedResult = Ordering.natural().sortedCopy(depMap.getUsedGlobal());
        out.add(Joiner.on('\n').skipNulls().join(sortedResult));
        return out;
    }

}
