package analyzer.analysis;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.inject.BindingAnnotation;
import com.google.inject.Singleton;


/**
 * Index of classes for use in analysis step
 * 
 * @author marcusf
 */
@Singleton
public class KlazzIndex {

    private SetMultimap<String, Klazz> simpleNameResolver; 
    private Set<Klazz> classes;
        
    KlazzIndex() {
        int initialSize = 1000;
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
    
    @BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
    public @interface New {}

}
