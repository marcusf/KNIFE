package knife.analysis;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import knife.Common;
import knife.ListOutput;
import knife.Output;

import org.apache.commons.cli.CommandLine;


import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.internal.Preconditions;

public class RefTreeAnalysis implements Analysis {

    private final CommandLine opts;
    private final UsageMap depMap;
    private final ListOutput out;

    @Inject
    public RefTreeAnalysis(CommandLine opts, UsageMap depMap, ListOutput out)
    {
        this.opts = opts;
        this.depMap = depMap;
        this.out = out;
        
    }
    
    public Output execute() {
        
        int MAX_COUNT = opts.hasOption(Common.OPT_COUNT) 
                ? Integer.parseInt(opts.getOptionValue(Common.OPT_COUNT))
                : 3;

        Preconditions.checkArgument(MAX_COUNT > 0, "When using -n for amount of class roots, you need at least one.");
                
        SetMultimap<String, String> usages = depMap.getUsagesByClass();

        TreeSet<SortableClass> sorted = Sets.newTreeSet(new SortableClassComparator());

        for (String dep: usages.keySet()) {
            sorted.add(new SortableClass(dep, usages.get(dep).size()));
        }

        int i = 0;     
        
        LinkedList<String> imports = Lists.newLinkedList();
        
        for (Iterator<SortableClass> it = sorted.descendingIterator(); 
             it.hasNext() && i < MAX_COUNT;) 
        {
            String klazz = it.next().name;

            if (!depMap.hasClass(klazz)) {
                continue;
            }
            
            imports.add(klazz);
            i++;
        }
        
        if (imports.size() > 0) {
            Set<String> result = addDependencies(imports);
            for (String dep: result) {
                out.add(dep);
            }
        }
        return out;
        
    }
    
    private Set<String> addDependencies(LinkedList<String> imports)
    {
        Set<String> result = Sets.newTreeSet();
        Set<String> blackList = Sets.newHashSet();
        String analysable;
        
        do {         
            analysable = imports.remove();
          
            if (!blackList.contains(analysable)) {
                imports.addAll(depMap.getImportsByClass(analysable));
                blackList.add(analysable);
                
                if (depMap.hasClass(analysable)) {
                    result.add(analysable);
                }                
            }
          
            
          
        } while (imports.size() > 0);
            
        return result;
    }
    
    class SortableClass {
        
        public SortableClass(String name, int count) {
            this.count = count;
            this.name = name;
        }
        
        int count;
        String name;
    }
    
    class SortableClassComparator implements Comparator<SortableClass> {

        public int compare(SortableClass x, SortableClass y)
        {
            return x.count - y.count;
        }
        
    }

    
}
