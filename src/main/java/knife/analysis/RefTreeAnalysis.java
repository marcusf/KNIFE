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

/**
 * RefTreeAnalysis
 * 
 * o Options:
 *   -n How many of the most referenced classes should be used as a 
 *      starting point. Default is 3. 
 * 
 * Picks out the n most references classes and follows their dependencies 
 * recursively until all dependencies are enumerated. Can be used 
 * to find natural slicing points in large code trees.
 * 
 */
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

        Preconditions.checkArgument(MAX_COUNT > 0, 
                "When using -n for amount of class roots, you need at least one.");
                
        SetMultimap<String, String> usages = depMap.getUsagesByClass();

        TreeSet<SortableClass> sorted = sortClassesByTimesUsed(usages);
        LinkedList<String> imports = createStartingList(MAX_COUNT, sorted);
        
        if (imports.size() > 0) {
            Set<String> result = addDependencies(imports);
            for (String dep: result) {
                out.add(dep);
            }
        }
        return out;
        
    }

    /** 
     * Goes over an initial set of classes given by the analysis and follows
     * all imports in each of those classes to find the forest of imports 
     * that can be reached from that class. It does this recursively until
     * all classes reachable from the top level class set has been reached. 
     * 
     * @param classQueue
     * @return
     */
    private Set<String> addDependencies(LinkedList<String> classQueue)
    {
        /* A list of classes that has already been visited */
        Set<String> blackList = Sets.newHashSet();
        
        /* The result */
        Set<String> result = Sets.newTreeSet();
                
        do {
            /* Pop of the first class from the queue of available classes */
            String analysable = classQueue.remove();
          
            /* Check that this class hasn't been visited before and 
             * had its dependencies enumerated. 
             */
            if (!blackList.contains(analysable)) {
                /* If so, add its imports last in the queue of classes
                 * to visit, and add it to the black list so we dont
                 * visit it again.
                 */
                classQueue.addAll(depMap.getImportsByClass(analysable));
                blackList.add(analysable);
                
                /* If the class is part of our corpus of interesting
                 * classes (the input set to KNIFE), add it to result.
                 */
                if (depMap.hasClass(analysable)) {
                    result.add(analysable);
                }                
            }
        /* Keep doing this until the class queue is empty and all
         * reachable classes have been enumerated. 
         */
        } while (classQueue.size() > 0);
            
        return result;
    }

    /**
     * Takes a dependency map of the kind (a -> {b,c,d}, f -> {d,b}, ...} 
     * and picks out the keys, sorted by how many dependencies each key has. 
     */
    private TreeSet<SortableClass> sortClassesByTimesUsed(SetMultimap<String, String> usages)
    {
        TreeSet<SortableClass> sorted = Sets.newTreeSet(new SortableClassComparator());

        for (String dep: usages.keySet()) {
            sorted.add(new SortableClass(dep, usages.get(dep).size()));
        }
        return sorted;
    }

    /**
     * Creates the starting list for the analysis. Goes through a pre-sorted
     * list of classes and picks up the <i>listSize</i> first elements in that list
     * that are part of the corpus of classes we are interested in.
     */
    private LinkedList<String> createStartingList(int listSize,
            TreeSet<SortableClass> sorted)
    {
        LinkedList<String> imports = Lists.newLinkedList();
        
        int i = 0;     
        for (Iterator<SortableClass> it = sorted.descendingIterator(); 
             it.hasNext() && i < listSize;) 
        {
            String klazz = it.next().name;

            if (!depMap.hasClass(klazz)) {
                continue;
            }
            
            imports.add(klazz);
            i++;
        }
        return imports;
    }
    
    /**
     * Dummy class used for sorting classes by number of references.
     */
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
