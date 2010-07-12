package analyzer;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;


import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

public class RefCountAnalysis {

    private final CommandLine opts;
    private final UsageMap depMap;

    RefCountAnalysis(CommandLine opts,
            UsageMap depMap)
    {
        this.opts = opts;
        this.depMap = depMap;
        
    }
    
    public Set<String> execute() {
        
        int MAX_COUNT = opts.hasOption(CodeAnalyzer.OPT_REFCOUNT_SHOW) 
                ? Integer.parseInt(opts.getOptionValue(CodeAnalyzer.OPT_REFCOUNT_SHOW))
                : 3;

        SetMultimap<String, String> usages = depMap.getUsagesByClass();

        /*for (String dep: usages.keySet()) {
        int cnt = usages.get(dep).size();
        System.out.print(cnt + " " + dep);
        if (cnt <= MAX_COUNT) {
        System.out.print(":\t" + orderedJoin(", ", usages.get(dep)));
        }
        System.out.print('\n');
        }*/

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
        
        Set<String> result = addDependencies(imports);

        for (String dep: result) {
            System.out.println(dep);
        }
        
        return result;
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
          }
          
          if (depMap.hasClass(analysable)) {
              result.add(analysable);
          }
          
        } while (imports.size() > 0);
     
        return result;
    }
    
    static class SortableClass {
        
        public SortableClass(String name, int count) {
            this.count = count;
            this.name = name;
        }
        
        int count;
        String name;
    }
    
    static class SortableClassComparator implements Comparator<SortableClass> {

        public int compare(SortableClass x, SortableClass y)
        {
            return x.count - y.count;
        }
        
    }
    
}
