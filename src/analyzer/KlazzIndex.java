package analyzer;

import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

/**
 * Index of classes for use in analysis step
 * 
 * @author marcusf
 */
public class KlazzIndex {

    private SetMultimap<String, Klazz> simpleNameResolver; 
    private Set<Klazz> classes;
        
    KlazzIndex(int initialSize) {
        simpleNameResolver = HashMultimap.create(initialSize, (int) Math.ceil(Math.sqrt(initialSize)));
        classes            = Sets.newHashSet();
    }
    
    void add(String packageName, String className) {
        Klazz klazz = new Klazz(packageName, className);
        classes.add(klazz);
        simpleNameResolver.put(className, klazz);
    }
    
    boolean contains(String packageName, String className) {
        return classes.contains(new Klazz(packageName, className));
    }
    
    boolean contains(String className) {
        return simpleNameResolver.containsKey(className);
    }
    
    Set<Klazz> getResolvedClassesByName(String className) {
         return simpleNameResolver.get(className);
    }
}
