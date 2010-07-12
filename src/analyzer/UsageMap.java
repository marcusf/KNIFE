package analyzer;

import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

public class UsageMap {

    private Set<String> globalUses;
    private Set<String> classes;
    private SetMultimap<String, String> importsByClass;
    private SetMultimap<String, String> usagesByClass;

    
    UsageMap() {
        globalUses     = Sets.newHashSet();
        classes        = Sets.newHashSet();
        importsByClass = HashMultimap.create();
        usagesByClass  = null;
    }
    
    public void addFilesUsedByClass(String className, List<String> uses)
    {
        if (className != null) {
            importsByClass.putAll(className, uses);
            globalUses.addAll(uses);
            classes.addAll(uses);
        }
    }

    public void addFilesUsedByClass(String className, String use) {
        if (className != null) {
            importsByClass.put(className, use);
            globalUses.add(use);
            classes.add(className);
        }
    }

    public Set<String> getUsedGlobal()
    {
        return globalUses;
    }

    public Set<String> getImportsByClass(String className)
    {
        return importsByClass.get(className);
    }
    
    
    public SetMultimap<String, String> getImportsByClass()
    {
        return importsByClass;
    }
    
    public boolean hasClass(String className) {
        return classes.contains(className);
    }
    
    public Set<String> getUsagesByClass(String className)
    {
        return getUsagesByClass().get(className);
    }
    
    public SetMultimap<String, String> getUsagesByClass() 
    {
        if (usagesByClass == null) {
            usagesByClass = HashMultimap.create();
            Multimaps.invertFrom(importsByClass, usagesByClass);
        }
        return usagesByClass;
    }  

    public Set<String> getClasses()
    {
        return classes;
    }
    
}
